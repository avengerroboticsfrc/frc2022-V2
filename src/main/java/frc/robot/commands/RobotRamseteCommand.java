package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.DriveTrain;

public class RobotRamseteCommand extends RamseteCommand {
  /**
   * creates a Ramsete Command with a given trajectory using the settings for our
   * robot (F.R.I.D.A.Y.)
   */
  public RobotRamseteCommand(Trajectory trajectory, DriveTrain drive) {
    super(
        trajectory,
        drive::getPose,
        new RamseteController(DriveConstants.K_RAMSETE, DriveConstants.K_RAMSETE_ZETA),
        new SimpleMotorFeedforward(
            DriveConstants.KS_VOLTS,
            DriveConstants.KV_VOLT_SECONDS_PER_METER,
            DriveConstants.KA_VOLT_SECONDS_SQUARED_PER_METER),
        DriveConstants.K_DRIVE_KINEMATICS,
        drive::getWheelSpeeds,
        new PIDController(DriveConstants.KP_DRIVE_VELOCITY, 0, 0),
        new PIDController(DriveConstants.KP_DRIVE_VELOCITY, 0, 0),
        // RamseteCommand passes volts to the callback
        drive::tankDriveVolts,
        drive);
  }
}
