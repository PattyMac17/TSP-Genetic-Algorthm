public class Population {
    private Individual[] generation;

    private int populationSize;
    public Population(int numCities, int populationSize){
        this.populationSize = populationSize;
        Individual[] gen = new Individual[this.populationSize];
        for (int i = 0; i < this.populationSize; i++){
            gen[i] = new Individual(numCities);
        }
        this.generation = gen;
    }

    public void setGeneration(Individual[] generation) {
        this.generation = generation;
    }

    public Individual getIndividual(int index){
        return this.generation[index];
    }
    public String toString(){
        String str = "";
        for (int i = 0; i < populationSize; i++){
            str += this.generation[i].toString() + "\n";
        }
        return str;
    }
}
