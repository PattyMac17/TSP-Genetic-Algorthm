public class City {
    private double x;
    private double y;
    public City(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public String toString(){
        double step1x = x * 1000;
        int step2x = (int)step1x;
        double step3x = step2x/1000.0;
        double step1y = y * 1000;
        int step2y = (int)step1y;
        double step3y = step2y/1000.0;
        return step3x + ", " + step3y;
    }
}
