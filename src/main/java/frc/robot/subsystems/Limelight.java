package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {
  private final NetworkTable table;
  // how many degrees back is your limelight rotated from perfectly vertical?
  private static final double LIMELIGHT_MOUNT_ANGLE_DEGREES = 17.00;
  // distance from the center of the Limelight lens to the floor
  private static final double LIMELIGHT_LENS_HEIGHT_METERS = 0.669925;
  // distance from the target to the floor
  private static final double GOAL_HEIGHT_METERS = 2.6416;

  private static final double KP = -0.1; // Proportional control constant
  private static final double MIN_COMMAND = 0.05; // Minimum amount to slightly move

  private double steering_adjust;

  /**
   * Creates a limelight instance with a default name of "limelight"
   */
  public Limelight() {
    this("limelight");
  }

  /**
   * Creates a new limelight class with the provided name.
   */
  public Limelight(String name) {
    super();

    table = NetworkTableInstance.getDefault().getTable(name);
    table.getEntry("ledMode").setNumber(0);
    table.getEntry("pipeline").setNumber(0);
  }

  /**
   * Updates the NetworkTables values every 20ms.
   */
  @Override
  public void periodic() {
    SmartDashboard.putNumber("LimelightX", getTargetXOffset());
    SmartDashboard.putNumber("LimelightY", getTargetYOffset());
    SmartDashboard.putNumber("LimelightArea", getTargetArea());
  }

  /**
   * uses the targetXOffset value to calculate the rotationAdjust value.
   */
  public double getRotationAdjust() {
    double headingError = getTargetXOffset();

    if (headingError > 1.0) {
      steering_adjust = -KP * headingError - MIN_COMMAND;
    } else if (headingError < 1.0) {
      steering_adjust = -KP * headingError + MIN_COMMAND;
    }

    return steering_adjust;
  }

  /**
   * Calculates the distance to the hub using the targetYOffset.
   */
  public double getDistance() {
    double targetOffsetAngle_Vertical = getTargetYOffset();

    double angle = LIMELIGHT_MOUNT_ANGLE_DEGREES + targetOffsetAngle_Vertical;
    double height = GOAL_HEIGHT_METERS - LIMELIGHT_LENS_HEIGHT_METERS;

    // tan = opp / hypot; divide opp to get adj.
    double distance = height / Math.tan(Math.toRadians(angle));

    return distance;
  }

  private double getTargetXOffset() {
    // returns 0 if no target
    return table.getEntry("tx").getDouble(0);
  }

  private double getTargetYOffset() {
    // returns 0 if no target
    return table.getEntry("ty").getDouble(0);
  }

  private double getTargetArea() {
    // returns 0 if no target
    return table.getEntry("ta").getDouble(0);
  }

  public void disableLights() {
    // Disables LEDs
    table.getEntry("ledMode").setNumber(0);
  }

  public void enableLights() {
    // Enables LEDs
    table.getEntry("ledMode").setNumber(3);
  }
}