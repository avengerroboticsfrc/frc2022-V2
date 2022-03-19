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
import frc.robot.commands.DefaultDrive;
import frc.robot.commands.LucaDrive;
import frc.robot.commands.autons.Auton;
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

  private final PS4Controller controller;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    System.out.println("Hello, Driver");
    controller = new PS4Controller(ButtonConstants.CONTROLLER_PORT);
    drive = new DriveTrain();
    index = new Index();
    shooter = new Shooter();

      drive.setDefaultCommand(
        new LucaDrive( 
          drive,
          controller::getL2Axis,
          controller::getR2Axis,
          controller::getLeftX,
          controller::getCircleButton
      ));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    RamseteCommand reverseCommand = new FridayRamseteCommand(threeBallTrajectory, drive);

    // Reset odometry to the starting pose of the trajectory.
    drive.resetOdometry(threeBallTrajectory.getInitialPose());

  public Command getTeleCommand() {
    return drive.getDefaultCommand();
  }
}
