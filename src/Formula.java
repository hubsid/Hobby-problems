import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Formula {
    static String outputFile = "C:\\Users\\sidr0616\\MY FILES\\graph files\\javaoutput.subl";
    static boolean writeToFile = false;

    static double gearShiftPoint = 0;
    static double finalSpeed = 100;
    static double frameInterval = 0.1;
    static double maxTorque = 10;
    static double maxTorqueRpm = 5500;
    static double redlineRpm = 10000;
    static double gearShiftRpm = computeGearShiftRpm();
    static double[] gearRatios = {3, 2, 1.5, 1, 0.8};
    static double sprocketRatio = 10;
    static double wheelRadius = 0.3;
    static double vehicleWeight = 127;
    static double riderWeight = 80;
    static double initialSpeed = 1;

    public static double computeGearShiftRpm() {
        return maxTorqueRpm + gearShiftPoint * (gearShiftPoint < 0 ? maxTorqueRpm : (redlineRpm - maxTorqueRpm));
    }

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = null;

        if(writeToFile)
            bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
        for (double gsp = -0.99; gsp <= 0.99; gsp += 0.01) {
            gearShiftPoint = gsp;
            gearShiftRpm = computeGearShiftRpm();
            double time = 0;

            double speed = initialSpeed;
            int gear = 0;
            double rpm;

            while (speed < finalSpeed && time < 60) {
                time += frameInterval;
                rpm = rpm(speed, gearRatios[gear]);
                if (rpm > gearShiftRpm) {
                    if (gear < gearRatios.length - 1)
                        gear++;
                    double tempRpm = rpm(speed, gearRatios[gear]);
                    if ((tempRpm + rpm) / 2 < redlineRpm) {
                        rpm = (tempRpm + rpm) / 2;
                    } else
                        rpm = redlineRpm;
                    speed = speed(rpm, gearRatios[gear]);
                }

                speed += 18 / 5 * frameInterval *
                        acceleration(
                                wheelTorque(
                                        engineTorque(rpm),
                                        gearRatios[gear]
                                )
                        );
            }
            if(writeToFile)
                bufferedWriter.write(String.format("%.2f", gsp) + "," + String.format("%.3f", time) + ";");

        }
        bufferedWriter.close();
    }

    public static double acceleration(double wheelTorque) {
        return wheelTorque / wheelRadius / (vehicleWeight + riderWeight);
    }

    public static double wheelTorque(double engineTorque, double gearRatio) {
        return engineTorque * sprocketRatio * gearRatio;
    }

    public static double rpm(double speed, double gearRatio) {
        return speed * 1000 / 60 / (2 * Math.PI * wheelRadius) * gearRatio * sprocketRatio;
    }

    public static double speed(double rpm, double gearRatio) {
        return rpm / gearRatio / sprocketRatio * (2 * Math.PI * wheelRadius) * 60 / 1000;
    }

    static double engineTorque(double rpm) {
        if (rpm < maxTorqueRpm)
            return maxTorque * rpm / maxTorqueRpm;
        else if (rpm < redlineRpm)
            return maxTorque / (maxTorqueRpm - redlineRpm) * (rpm - redlineRpm);
        else
            return 0;
    }
}
