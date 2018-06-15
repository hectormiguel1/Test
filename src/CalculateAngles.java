import java.text.DecimalFormat;


public class CalculateAngles {
   private final double winterOffset = 29D;
   private final double summerOffset = 23.5;
   private final double latitudeMultiplier = 0.9;
   private final double springFallOffset = 2.5;
   private double finalAngle = 0;

    public CalculateAngles(double latitude, Season season) {


        switch (season) {
            case SUMMER: {
                finalAngle = (latitude * latitudeMultiplier) - summerOffset;
                break;
            }
            case WINTER: {
                finalAngle = (latitude * latitudeMultiplier) + winterOffset;
                break;
            }
            case FALL: {
                finalAngle = latitude - springFallOffset;
                break;
            }
            case SPRING: {
                finalAngle = latitude - springFallOffset;
                break;
            }
            default: {
                return;
            }
        }
    }

    public double getFinalAngle() {
        return Double.parseDouble(new DecimalFormat("##.##").format(finalAngle));
    }
}
