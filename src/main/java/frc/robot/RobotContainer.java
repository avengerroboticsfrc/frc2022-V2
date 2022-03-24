// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.auton.Auton;
import frc.robot.commands.LucaDrive;
import frc.robot.commands.ShootBallCommand;
import frc.robot.commands.IndexCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.LiftCommand;
import frc.robot.commands.TargetHubCommand;
import frc.robot.constants.ButtonConstants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

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
  private final Limelight limelight;
  private final Intake intake;
  private final Joystick buttonPanel;
  private final Lift lift;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    System.out.println("Hello, Driver");

    controller = new PS4Controller(ButtonConstants.CONTROLLER_PORT);
    buttonPanel = new Joystick(ButtonConstants.BUTTON_PANEL_PORT);

    drive = new DriveTrain();
    index = new Index();
    shooter = new Shooter();
    limelight = new Limelight();
    intake = new Intake();
    lift = new Lift();

    drive.setDefaultCommand(
        new LucaDrive(
            drive,
            controller::getL2Axis,
            controller::getR2Axis,
            controller::getLeftX,
            controller::getCircleButton));

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    JoystickButton toggleIntake = new JoystickButton(buttonPanel, ButtonConstants.INTAKE_TOGGLE_AND_OPEN);
    toggleIntake.whenPressed(new IntakeCommand(intake, 0.3));

    JoystickButton indexUp = new JoystickButton(buttonPanel, ButtonConstants.INDEX_UP);
    indexUp.whenHeld(new IndexCommand(index, 0.3));

    JoystickButton indexOut = new JoystickButton(buttonPanel, ButtonConstants.INDEX_OUT);
    indexOut.whenHeld(new IndexCommand(index, -0.3));

    JoystickButton shootButton = new JoystickButton(buttonPanel, ButtonConstants.FLYWHEEL_ON);
    shootButton.whenHeld(new ShootBallCommand(shooter, index, limelight));

    JoystickButton targetHub = new JoystickButton(buttonPanel, ButtonConstants.TARGET_SHOOTER);
    targetHub.whenHeld(new TargetHubCommand(shooter, limelight));

    // replace these when we calibrate the hood
    JoystickButton hoodUp = new JoystickButton(buttonPanel, ButtonConstants.HOOD_UP);
    hoodUp.whileActiveContinuous(new InstantCommand(() -> shooter.extendHood(shooter.getHoodPos() + 0.1), shooter));
    JoystickButton hoodDown = new JoystickButton(buttonPanel, ButtonConstants.HOOD_DOWN);
    hoodDown.whileActiveContinuous(new InstantCommand(() -> shooter.extendHood(shooter.getHoodPos() - 0.1), shooter));

    JoystickButton raiseLift = new JoystickButton(buttonPanel, ButtonConstants.LIFT_UP);
    raiseLift.whenHeld(new LiftCommand(lift, 0.3));

    JoystickButton lowerLift = new JoystickButton(buttonPanel, ButtonConstants.LIFT_DOWN);
    lowerLift.whenHeld(new LiftCommand(lift, -0.5));

    JoystickButton liftForward = new JoystickButton(buttonPanel, ButtonConstants.LIFT_FORWARD);
    liftForward.whenHeld(new LiftCommand(lift, 0.3, true));

    JoystickButton liftBackward = new JoystickButton(buttonPanel, ButtonConstants.LIFT_BACK);
    liftBackward.whenHeld(new LiftCommand(lift, -0.3, true));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new Auton(drive, shooter, limelight, shooter, intake, index);
  }

  public Command getTeleCommand() {
    return drive.getDefaultCommand();
  }
}
