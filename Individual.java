public class Individual {
    private int[] chromosome;
    private double fitness;
    private int numCities;
    private double distance;
    public Individual(int[] chromosome){
        this.chromosome = chromosome;
        this.numCities = chromosome.length;
    }
    public Individual(int numCities){
        this.numCities = numCities;
        int[] c = new int[this.numCities];
        for (int i = 0; i < this.numCities; i++){
            c[i] = i;
        }
        shuffle(c);
        this.chromosome = c;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getDistance() {
        return distance;
    }

    public double getFitness() {
        return fitness;
    }

    public void setDistance(double distance) {
        double step1 = distance * 10000.0;
        int step2 = (int)step1;
        double step3 = step2/10000.0;
        this.distance = step3;
    }

    public int[] getChromosome(){
        return this.chromosome;
    }
    public String toString(){
        String str = "";
        for (int i = 0; i < this.numCities; i++){
            str += this.chromosome[i];
        }
        return str;
    }
    public void shuffle(int[] c) {
        for (int src = 0; src < c.length - 1; src++) {
            int dest = src + (int) (Math.random() * (c.length - src));
            invertPair(c, src, dest);
        }
    }
    public void invertPair(int[] c, int a, int b) {
        if (a < 0 || a >= c.length || b < 0 || b >= c.length) {
            System.err.println("invertPair: invalid value for a or b");
            System.exit(1);
        }
        int tmp = c[a];
        c[a] = c[b];
        c[b] = tmp;
    }
}
