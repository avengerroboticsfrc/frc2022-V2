package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {
  private final NetworkTable table;
  // how many degrees back is your limelight rotated from perfectly vertical?
  private final double limelightMountAngleDegrees = 20.00;

  // distance from the center of the Limelight lens to the floor
  private final double limelightLensHeightInches = 26.375;

  // distance from the target to the floor
  private final double goalHeightInches = 104;

  private final double kp = -0.1; // Proportional control constant
  private final double minCommand = 0.05; // Minimum amount to slightly move
  // the robot

  public Limelight() {
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
   * uses the targetXOffset value to calculate the steeringAdjust value.
   */
  public double getSteeringAdjust() {
    double steeringAdjust = 0.0;
    double targetX = getTargetXOffset();

    steeringAdjust = (targetX > 1.0)
        ? kp * -1 * targetX - minCommand
        : kp * -1 * targetX + minCommand;

    return steeringAdjust;
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
}