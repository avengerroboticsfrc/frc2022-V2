// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ExampleCommand;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  // The robot's subsystems and commands are defined here...
  private final DriveTrain drive;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    RamseteCommand reverseCommand = new RamseteCommand(
        reverseTrajectory,
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

    // Reset odometry to the starting pose of the trajectory.
    drive.resetOdometry(reverseTrajectory.getInitialPose());

    Command stopDriveCommand = new RunCommand(() -> drive.tankDriveVolts(0, 0), drive);
    Command powerShooterCommand = new RunCommand(() -> shooter.hoodPower(1), shooter);
    Command powerIndexCommand = new RunCommand(() -> index.power(.6), index);

    // Command forwardDrive = new RunCommand(() -> drive.tankDrive(.4, .4), drive);

    // Command holdCom = new WaitCommand(0);

    return new ParallelDeadlineGroup(
        new WaitCommand(3),
        stopDriveCommand,
        powerShooterCommand).andThen(
            new ParallelDeadlineGroup(
                new WaitCommand(3),
                stopDriveCommand,
                powerShooterCommand,
                powerIndexCommand))
        .andThen(reverseCommand).andThen(new ParallelCommandGroup(
            stopDriveCommand,
            new RunCommand(() -> shooter.hoodPower(0), shooter),
            new RunCommand(() -> index.power(0), index)));
  }
}
