// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.DefaultDrive;
import frc.robot.commands.RobotRamseteCommand;
import frc.robot.constants.ButtonConstants;
import frc.robot.constants.ButtonConstants.ControllerType;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Shooter;

import java.io.IOException;
import java.nio.file.Path;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveTrain drive;
  private final Index index;
  private final Shooter shooter;

  private final GenericHID controller;

  public final String trajectoryJson = "pathweaver/output/3ball.wpilib.json";
  private Trajectory threeBallTrajectory;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    try {
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJson);
      threeBallTrajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
    } catch (IOException e) {
      DriverStation.reportError("Unable to open trajectory: " + trajectoryJson, e.getStackTrace());
      threeBallTrajectory = null;
    }
    
    
    drive = new DriveTrain();
    index = new Index();
    shooter = new Shooter();
    
    if (ButtonConstants.CONTROLLER_TYPE == ControllerType.PS4) {
      PS4Controller p = new PS4Controller(ButtonConstants.CONTROLLER_PORT);
      drive.setDefaultCommand(
        new DefaultDrive(drive, p::getLeftY, p::getRightX, () -> p.getR2Axis() > 0)
      );

      controller = p;
    } else {
      XboxController x = new XboxController(ButtonConstants.CONTROLLER_PORT);
      drive.setDefaultCommand(
        new DefaultDrive(drive, x::getLeftY, x::getRightX, () -> x.getRightTriggerAxis() > 0)
      );

      controller = x;
    }
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    RamseteCommand reverseCommand = new RobotRamseteCommand(threeBallTrajectory, drive);

    // Reset odometry to the starting pose of the trajectory.
    drive.resetOdometry(threeBallTrajectory.getInitialPose());

    Command stopDriveCommand = new RunCommand(() -> drive.tankDriveVolts(0, 0), drive);
    Command powerShooterCommand = new RunCommand(() -> shooter.runFlywheel(1), shooter);
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
            new RunCommand(() -> shooter.runFlywheel(0), shooter),
            new RunCommand(() -> index.power(0), index)));
  }

  public Command getTeleCommand() {
    return drive.getDefaultCommand();
  }
}
