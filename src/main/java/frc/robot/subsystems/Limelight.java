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
  private static final double LIMELIGHT_LENS_HEIGHT_INCHES = 26.375;
  // distance from the target to the floor
  private static final double GOAL_HEIGHT_INCHES = 104;

  private static final double KP = -0.1; // Proportional control constant
  private static final double MIN_COMMAND = 0.05; // Minimum amount to slightly move

  /**
   * creates a new limelight class.
   */
  public Limelight() {
    super();
    table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
  }

  @Override
  public void periodic() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
    SmartDashboard.putNumber("LimelightX", getTargetXOffset());
    SmartDashboard.putNumber("LimelightY", getTargetYOffset());
    SmartDashboard.putNumber("LimelightArea", getTargetArea());
  }

  /**
   * uses the targetXOffset value to calculate the rotationAdjust value.
   */
  public double getRotationAdjust() {
    double rotationAdjust = 0.0;
    double targetX = getTargetXOffset();

    rotationAdjust = (targetX > 1.0)
        ? KP * -1 * targetX - MIN_COMMAND
        : KP * -1 * targetX + MIN_COMMAND;

    return rotationAdjust;
  }

  private double getTargetXOffset() {
    // returns 0 if no target
    return table.getEntry("tx").getDouble(0);
  }

  private double getTargetYOffset() {
    return table.getEntry("ty").getDouble(0);
  }

  private double getTargetArea() {
    return table.getEntry("ta").getDouble(0);
  }
  
  public void disableLights() {
    NetworkTableInstance.getDefault().getTable("limelight-b").getEntry("ledMode").setNumber(0);
  }

  public void enableLights() {
    NetworkTableInstance.getDefault().getTable("limelight-b").getEntry("ledMode").setNumber(3);
  }
}