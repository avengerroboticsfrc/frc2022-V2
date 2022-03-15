package frc.robot.constants;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public class DriveConstants {
  // Voltage
  public static final double ksVolts = 0.72613;
  public static final double kvVoltSecondsPerMeter = 2.541;
  public static final double kaVoltSecondsSquaredPerMeter = 0.45123;

  public static final double kPDriveVel = 2.457;

  // Differential Drive Kinematics
  public static final double kTrackwidthMeters = 0.53;
  public static final DifferentialDriveKinematics kDriveKinematics =
      new DifferentialDriveKinematics(kTrackwidthMeters);

  // Max Velocity/Acceleration
  public static final double kMaxSpeedMeterPerSecond = .5;
  public static final double kMaxAccelerationMetersPerSecondSquared = .2;

  // RAMSETE Parameters
  public static final double kRamsete = 2;
  public static final double kRamseteZeta = 0.7;

}
