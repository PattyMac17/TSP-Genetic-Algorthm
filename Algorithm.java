import java.util.Arrays;
public class Algorithm {
    private int numCities;
    private City[] map;
    private int populationSize;
    private double mutationRate;
    public Algorithm(int numCities, int populationSize, double mutationRate, City[] map){
        this.mutationRate = mutationRate;
        this.populationSize = populationSize;
        this.numCities = numCities;
        this.map = map;
    }
    public Population initialize(){
        return new Population(this.numCities, this.populationSize);
    }
    public void calcFitness(Population population){
        double totalDistance = totalPopulationDistance(population);
        double[] inverseRouteDistances = new double[this.populationSize];
        double totalInverseDistance = 0;
        for (int i = 0; i < this.populationSize; i++){
            inverseRouteDistances[i] = 1.0/population.getIndividual(i).getDistance();
            totalInverseDistance += 1.0/population.getIndividual(i).getDistance();
        }
        for (int j = 0; j < this.populationSize; j++){
            double fitness = inverseRouteDistances[j]/totalInverseDistance;
            population.getIndividual(j).setFitness(fitness);
        }
    }
    public double totalPopulationDistance(Population population){
        double populationDistance = 0;
        for (int i = 0; i < this.populationSize; i++){
            populationDistance += calculateRouteDistance(population.getIndividual(i));
        }
        return populationDistance;
    }
    public double calculateRouteDistance(Individual chromosome){
        double distance = 0;
        for (int i = 0; i < this.numCities-1; i++){
            distance += calculateSingleDistance(chromosome.getChromosome()[i], chromosome.getChromosome()[i+1]);
        }
        distance += calculateSingleDistance(chromosome.getChromosome()[numCities - 1], chromosome.getChromosome()[0]);
        chromosome.setDistance(distance);
        return distance;
    }
    public double calculateSingleDistance(int a, int b){
        double x2 = map[b].getX();
        double x1 = map[a].getX();
        double y2 = map[b].getY();
        double y1 = map[a].getY();
        return Math.sqrt(Math.pow((x2 - x1),2) + Math.pow((y2 - y1),2));
    }
    public Individual fittestIndividual(Population population){
        double max = population.getIndividual(0).getFitness();
        int bestIndividual = 0;
        for (int i = 0; i < this.populationSize; i++){
            if (population.getIndividual(i).getFitness() > max){
                max = population.getIndividual(i).getFitness();
                bestIndividual = i;
            }
        }
        return population.getIndividual(bestIndividual);
    }
    public Individual selectParentTournament(Population population){
        int contender1 = (int)(Math.random()*this.populationSize);
        int contender2 = (int)(Math.random()*this.populationSize);
        while (contender1 == contender2){
            contender2 = (int)(Math.random()*this.populationSize);
        }
        if (population.getIndividual(contender1).getFitness() > population.getIndividual(contender1).getFitness()){
            return population.getIndividual(contender1);
        }
        else {
            return population.getIndividual(contender2);
        }
    }
    public Individual selectParentRoulette(Population population){
        double[] CDF = new double[this.populationSize];
        double wheelPosition = 0;
        for (int i = 0; i < this.populationSize; i++){
            wheelPosition += population.getIndividual(i).getFitness();
            CDF[i] = wheelPosition;
        }
        double spin = Math.random();
        int selected = 0;
        for (int j = 0; j < this.populationSize; j++){
            if(spin < CDF[j]){
                selected = j;
                break;
            }
        }
        return population.getIndividual(selected);
    }

    public Population crossover(Population population){
        Individual[] nextGen = new Individual[this.populationSize];
        for (int i = 0; i < this.populationSize; i++){
            int[] parent1 = selectParentRoulette(population).getChromosome();
            int[] parent2 = selectParentTournament(population).getChromosome();
            while(Arrays.equals(parent1, parent2)){
                parent2 = selectParentTournament(population).getChromosome();
            }
            int crossIn = (int)(Math.random() * this.numCities);
            int crossOut = (int)(Math.random() * this.numCities);
            while (crossIn == crossOut){
                crossOut = (int)(Math.random() * this.numCities);
            }
            if (crossIn > crossOut){
                int temp = crossIn;
                crossIn = crossOut;
                crossOut = temp;
            }
            int[] child = new int[this.numCities];
            for (int j = 0; j < numCities; j++){
                child[j] = -1;
            }
            for (int d = crossIn; d < crossOut; d++){
                child[d] = parent1[d];
            }
            int[] holder = new int[this.numCities - (crossOut-crossIn)];
            int z = 0;
            for (int k = 0; k < this.numCities; k++){
                if (!arrayContains(child, parent2[k])){
                    holder[z] = parent2[k];
                    z++;
                }
            }
            int filler = 0;
            for (int f = 0; f < this.numCities; f++){
                if (child[f] == -1){
                    child[f] = holder[filler];
                    filler++;
                }
            }
            Individual offspring = new Individual(child);
            nextGen[i] = offspring;
        }
        population.setGeneration(nextGen);
        return population;
    }

    public boolean arrayContains(int[] child, int value) {
        boolean contains = false;
        for (int i = 0; i < child.length; i++){
            if (value == child[i]){
                contains = true;
            }
        }
        return contains;
    }
    public Population mutation(Population population){
        for (int i = 0; i < this.populationSize; i++){
            for (int j = 0; j < this.numCities; j++){
                if (this.mutationRate > Math.random()){
                    int g1 = (int)(Math.random()*this.numCities);
                    while (g1 == j){
                        g1 = (int)(Math.random()*this.numCities);
                    }
                    population.getIndividual(i).invertPair(population.getIndividual(i).getChromosome(), g1, j);
                }
            }
        }
        return population;
    }
}
