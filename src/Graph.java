import java.io.*;

public class Graph {
    public static void main(String[] args) throws IOException {
        int maxSpeed = 100;
        int intervals = 100;
        int speedIncrement = maxSpeed/intervals;
        double [][] speedTorque = new double[intervals * Formula.gearRatios.length][2];



        String outputFile = "C:\\Users\\sidr0616\\MY FILES\\graph files\\javaoutput.subl";
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));

        for(int i = 1; i <= Formula.gearRatios.length; i++) {
            double gearRatio = Formula.gearRatios[i - 1];
            double speed = 0;
            for(int j = 0; j < intervals; j++, speed += speedIncrement) {
                speedTorque[j][0] = speed;
                speedTorque[j][1] = Formula.wheelTorque(Formula.engineTorque(Formula.rpm(speed, gearRatio)), gearRatio);
            }
            bufferedWriter.write(
                    new PointSeries(i, speedTorque).getData().toString())
            ;
        }
        bufferedWriter.close();
    }
}

class PointSeries {
    private String name = "[PointSeriesX]";
    private int i;
    private String beforeString = "\n" +
            "FillColor = clRed\n" +
            "LineColor = clBlue\n" +
            "Size = 0\n" +
            "Style = 0\n" +
            "LineStyle = 0\n" +
            "LineSize = 2\n" +
            "Interpolation = 3\n" +
            "LabelPosition = 1\n" +
            "Points = ";
    private StringBuilder pointString = new StringBuilder();

    public PointSeries(int i, double[][] points) {
        this.i = i;

        for(double[] point:points)
            pointString.append(String.format("%.3f", point[0])).append(',').append(String.format("%.3f", point[1])).append(';');
    }

    public StringBuilder getData() {
        return new StringBuilder()
                .append(name.replace("X", "" + i))
                .append(beforeString)
                .append(pointString)
                .append("\n\n");
    }
}
