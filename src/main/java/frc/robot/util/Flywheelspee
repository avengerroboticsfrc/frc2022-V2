package frc.robot.util;

public class FlywheelSpeed {

  private static final InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble> speedMap =
      new InterpolatingTreeMap<>();

  private static final double rampZone = 0.1; // meters
  private static final double longRangeZone = 1.9; // metersshooter

  static {
    speedMap.put(new InterpolatingDouble(0.0), new InterpolatingDouble(4800.0));
    speedMap.put(
        new InterpolatingDouble(longRangeZone - rampZone), new InterpolatingDouble(4800.0));
    speedMap.put(
        new InterpolatingDouble(longRangeZone + rampZone), new InterpolatingDouble(5200.0));
    speedMap.put(new InterpolatingDouble(20.0), new InterpolatingDouble(5200.0));
  }

  public static double getFlywheelSpeed(double range) {
    return speedMap.getInterpolated(new InterpolatingDouble(range)).value;
  }
}