package frc.robot.constants;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public class DriveConstants {
  // Voltage
  public static final double KS_VOLTS = 0.73098;
  public static final double KV_VOLT_SECONDS_PER_METER = 2.505;
  public static final double KA_VOLT_SECONDS_SQUARED_PER_METER = 0.3608;

  public static final double KP_DRIVE_VELOCITY = 3.4718;

  // Differential Drive Kinematics
  public static final double K_TRACK_WIDTH_METERS = 0.53;
  public static final DifferentialDriveKinematics K_DRIVE_KINEMATICS =
      new DifferentialDriveKinematics(K_TRACK_WIDTH_METERS);

  // Max Velocity/Acceleration
  public static final double K_MAX_SPEED_METER_PER_SECOND = .4788;
  public static final double K_MAX_ACCELERATION_METERS_PER_SECOND_SQUARED = .2;

  // RAMSETE Parameters
  public static final double K_RAMSETE = 2;
  public static final double K_RAMSETE_ZETA = 0.7;
  public static final double MAX_DRIVE_VOLTAGE = 7;
}
