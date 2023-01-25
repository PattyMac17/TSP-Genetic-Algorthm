import java.awt.*;
import java.util.Scanner;

public class Project {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        int generationNumber = 1;
        int mapSize = 200;
        int numCities = 10;
        int populationSize = 1000;
        int cutOff = 10;
        int maxGenerations = 1000;
        double mutationRate = 0.01;
        City[] map;
        displayParameters(mapSize,numCities,populationSize,cutOff,maxGenerations,mutationRate);
        System.out.println();
        System.out.println("Would you like to input custom parameters? (Y/N)");
        String ans1 = userInput.next();
        while(!(ans1.equalsIgnoreCase("y")||ans1.equalsIgnoreCase("n"))){
            System.out.println("Invalid Input: Enter Y or N");
            ans1 = userInput.next();
        }
        if (ans1.equalsIgnoreCase("y")){
            System.out.println("Grid Size? (int)");
            int testMapSize;
            while (true) {
                try {
                    testMapSize = Integer.parseInt(userInput.next());
                    break;
                } catch (NumberFormatException ignore) {
                    System.out.println("Invalid Input: Enter an Integer");
                }
            }
            mapSize = testMapSize;
            System.out.println("Number of Cities? (int)");
            int testNumCities;
            while (true) {
                try {
                    testNumCities = Integer.parseInt(userInput.next());
                    break;
                } catch (NumberFormatException ignore) {
                    System.out.println("Invalid Input: Enter an Integer");
                }
            }
            numCities = testNumCities;
            System.out.println("Population Size? (int)");
            int testPopulationSize;
            while (true) {
                try {
                    testPopulationSize = Integer.parseInt(userInput.next());
                    break;
                } catch (NumberFormatException ignore) {
                    System.out.println("Invalid Input: Enter an Integer");
                }
            }
            populationSize = testPopulationSize;
            System.out.println("Maximum Generations? (int)");
            int testMaxGenerations;
            while (true) {
                try {
                    testMaxGenerations = Integer.parseInt(userInput.next());
                    break;
                } catch (NumberFormatException ignore) {
                    System.out.println("Invalid Input: Enter an Integer");
                }
            }
            maxGenerations = testMaxGenerations;
            System.out.println("Mutation Rate? (double)");
            double testMutationRate;
            while (true) {
                try {
                    testMutationRate = Double.parseDouble(userInput.next());
                    break;
                } catch (NumberFormatException ignore) {
                    System.out.println("Invalid Input: Enter a Double");
                }
            }
            mutationRate = testMutationRate;
            System.out.println("Convergence Cutoff? (int)");
            cutOff = userInput.nextInt();
            displayParameters(mapSize,numCities,populationSize,cutOff,maxGenerations,mutationRate);
        }
        System.out.println();
        if(numCities<=10){
            System.out.println("Would you like to use the pre-programmed cities");
            System.out.println("or generate random cities? (Enter: \"PRE\" or \"RANDOM\")");
            String ans2 = userInput.next();
            while(!(ans2.equalsIgnoreCase("random") || ans2.equalsIgnoreCase("pre"))){
                System.out.println("Invalid Input: Enter \"PRE\" or \"RANDOM\"");
                ans2 = userInput.next();
            }
            if (ans2.equalsIgnoreCase("random")){
                map = new City[numCities];
                for (int i = 0; i < numCities; i++){
                    map[i] = new City(Math.random()*mapSize, Math.random()*mapSize);
                }
                System.out.println();
                System.out.println("Cities Generated:");
                for(int j = 0; j < numCities; j++){
                    System.out.println(map[j].toString());
                }
            }
            else{
                mapSize = 100;
                map = new City[]{new City(90, 0), new City(0, 90), new City(50, 7), new City(45, 37), new City(0, 13), new City(22, 34), new City(5, 76), new City(2, 1), new City(25, 61), new City(20, 19)};
                System.out.println();
                System.out.println("Cities:");
                for(int j = 0; j < numCities; j++) {
                    System.out.println(map[j].toString());
                }
            }
        }
        else{
            map = new City[numCities];
            for (int i = 0; i < numCities; i++){
                map[i] = new City(Math.random()*mapSize, Math.random()*mapSize);
            }
            System.out.println();
            System.out.println("Cities Generated:");
            for(int j = 0; j < numCities; j++){
                System.out.println(map[j].toString());
            }
        }
        System.out.println();
        System.out.println("Execute algorithm? (Y/N)");
        String ans3 = userInput.next();
        while(!(ans3.equalsIgnoreCase("y") || ans3.equalsIgnoreCase("n"))){
            System.out.println("Invalid Input: Enter Y or N");
            ans3 = userInput.next();
        }
        if (ans3.equalsIgnoreCase("y")){
            Algorithm ga = new Algorithm(numCities, populationSize, mutationRate, map);
            Population population = ga.initialize();
            ga.calcFitness(population);
            int noChangeCounter = 0;
            Individual bestPath = ga.fittestIndividual(population);
            double bestSolution = ga.fittestIndividual(population).getDistance();
            while (noChangeCounter < cutOff && generationNumber <= maxGenerations){
                Individual fittest = ga.fittestIndividual(population);
                System.out.println("G: " + generationNumber + " | P: " + fittest.toString() + " | " +  "D: " + fittest.getDistance());
                population = ga.crossover(population);
                population = ga.mutation(population);
                ga.calcFitness(population);
                generationNumber++;
                if(bestSolution == ga.fittestIndividual(population).getDistance()){
                    noChangeCounter++;
                }
                else {
                    noChangeCounter = 0;
                    if(bestSolution > ga.fittestIndividual(population).getDistance()){
                        bestSolution = ga.fittestIndividual(population).getDistance();
                        bestPath = ga.fittestIndividual(population);
                    }
                }
            }
            if (generationNumber > maxGenerations){
                System.out.println("Max generations reached | MOP: " + bestPath.toString() + " | D: " + bestSolution);
            }
            else{
                bestPath = ga.fittestIndividual(population);
                System.out.println("CONVERGENCE | G: " + generationNumber + " | P: " + bestPath.toString() + " | " +  "D: " + bestPath.getDistance());
            }
            StdDraw.setScale(-25, mapSize+(25));
            for (int t = 0; t < bestPath.getChromosome().length-1; t++){
                StdDraw.setPenRadius(0.01);
                StdDraw.setPenColor(Color.black);
                StdDraw.line(map[bestPath.getChromosome()[t]].getX(), map[bestPath.getChromosome()[t]].getY(), map[bestPath.getChromosome()[t+1]].getX(), map[bestPath.getChromosome()[t+1]].getY());
            }
            StdDraw.line(map[bestPath.getChromosome()[numCities-1]].getX(), map[bestPath.getChromosome()[numCities-1]].getY(), map[bestPath.getChromosome()[0]].getX(), map[bestPath.getChromosome()[0]].getY());
            for (int h = 0; h < numCities; h++){
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius(0.05);
                StdDraw.point(map[h].getX(), map[h].getY());
                StdDraw.setPenColor(Color.white);
                StdDraw.text(map[h].getX(), map[h].getY(), "" + h);
            }
            StdDraw.setPenColor(Color.black);
            StdDraw.text(mapSize/2.0, -10, "Route: " + bestPath.toString() + " | Route Distance: " + bestPath.getDistance());
        }
        else{
            System.out.println("Exiting program...");
            System.exit(0);
        }
    }
    public static void displayParameters( int mapSize, int numCities, int populationSize, int cutOff, int maxGenerations, double mutationRate){
        System.out.println("Current Parameters:");
        System.out.println( "Size of Grid: " + mapSize);
        System.out.println( "Number of Cities: " + numCities);
        System.out.println( "Population Size: " + populationSize);
        System.out.println( "Maximum Number of Generations: " + maxGenerations);
        System.out.println( "Mutation Rate: " + mutationRate);
        System.out.println( "Convergence Cutoff: " + cutOff);
    }
}
