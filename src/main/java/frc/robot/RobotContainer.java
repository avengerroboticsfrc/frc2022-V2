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
    System.out.println("Hello, Driver");
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
      PS4Controller PSController = new PS4Controller(ButtonConstants.CONTROLLER_PORT);
      drive.setDefaultCommand(
        new DefaultDrive(drive, PSController::getLeftY, PSController::getRightX, () -> PSController.getR2Axis() > 0)
      );

      controller = PSController;
    } else {
      XboxController XBController = new XboxController(ButtonConstants.CONTROLLER_PORT);
      drive.setDefaultCommand(
        new DefaultDrive(drive, XBController::getLeftY, XBController::getRightX, () -> XBController.getRightTriggerAxis() > 0)
      );

      controller = XBController;
      String name = controller.getName();
      System.out.println(name + " selected");
    }
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new Auton(drive);
  } 

  public Command getTeleCommand() {
    return drive.getDefaultCommand();
  }
}
