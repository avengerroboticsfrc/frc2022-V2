package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.DriveTrain;

public class FRIDAYRamseteCommand extends RamseteCommand {
  /**
   * creates a Ramsete Command with a given trajectory using the settings for our
   * robot (F.R.I.D.A.Y.)
   */
  public FRIDAYRamseteCommand(Trajectory trajectory, DriveTrain drive) {
    super(
        trajectory,
        drive::getPose,
        new RamseteController(DriveConstants.kRamsete, DriveConstants.kRamseteZeta),
        new SimpleMotorFeedforward(
            DriveConstants.ksVolts,
            DriveConstants.kvVoltSecondsPerMeter,
            DriveConstants.kaVoltSecondsSquaredPerMeter),
        DriveConstants.kDriveKinematics,
        drive::getWheelSpeeds,
        new PIDController(DriveConstants.kPDriveVel, 0, 0),
        new PIDController(DriveConstants.kPDriveVel, 0, 0),
        // RamseteCommand passes volts to the callback
        drive::tankDriveVolts,
        drive);
  }
}
