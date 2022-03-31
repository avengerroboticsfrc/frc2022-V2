// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.auton.SimpleDriveandShoot;  //Keep Import. Needed For Auton
import frc.robot.commands.auton.SixBallLeftAuton;     //Keep Import. Needed For Auton
import frc.robot.commands.auton.ThreeBallLeftAuton;   //Keep Import. Needed For Auton
import frc.robot.commands.auton.ThreeBallRightAuton;  //Keep Import. Needed For Auton
import frc.robot.commands.driveTypes.DefaultDrive;    //Keep Import. Needed For Auton
import frc.robot.commands.driveTypes.LucaDrive;
import frc.robot.commands.ComplexCommands.ShootBallCommandGroup;
import frc.robot.commands.ComplexCommands.TargetHubCommand;
import frc.robot.commands.SimpleCommands.IndexCommand;
import frc.robot.commands.SimpleCommands.IntakeCommand;
import frc.robot.commands.SimpleCommands.IntakeExtendCommand;
import frc.robot.commands.SimpleCommands.IntakeRetractCommand;
import frc.robot.commands.SimpleCommands.LiftCommand;
import frc.robot.constants.ButtonConstants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.MainDrive;
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

    drive = new MainDrive();
    index = new Index();
    shooter = new Shooter();
    limelight = new Limelight();
    intake = new Intake();
    lift = new Lift();


    configureDriveTrain();
    configureButtonBindings();
  }

  private void configureDriveTrain() {
    // A split-stick arcade command, with forward/backward controlled by the left
    // hand, and turning controlled by the right. Has a constant turning radius.
    // Can turn in place with button press.
    drive.setDefaultCommand(
        // pass in a reference to a method
        new LucaDrive( 
            drive,
            controller::getL2Axis,
            controller::getR2Axis,
            controller::getLeftX,
            controller::getCircleButton
        ));
        // new DefaultDrive(drive, controller::getLeftY, controller::getRightY, controller::getR1Button));
  }

  private void configureButtonBindings() {
    JoystickButton toggleIntake = new JoystickButton(buttonPanel, ButtonConstants.INTAKE_TOGGLE_AND_OPEN);
    toggleIntake.whenHeld(new IntakeExtendCommand(intake));

    JoystickButton toggleIntakeRetract = new JoystickButton(buttonPanel, ButtonConstants.INTAKE_RETRACT);
    toggleIntakeRetract.whenHeld(new IntakeRetractCommand(intake));

    JoystickButton indexUp = new JoystickButton(buttonPanel, ButtonConstants.INDEX_UP);
    indexUp.whenHeld(new IndexCommand(index, .5));

    JoystickButton indexOut = new JoystickButton(buttonPanel, ButtonConstants.INDEX_OUT);
    indexOut.whenHeld(new IndexCommand(index, -.5));

    JoystickButton runIntakeIn = new JoystickButton(buttonPanel, ButtonConstants.INTAKE_IN);
    runIntakeIn.whenHeld(new IntakeCommand(intake, .4));

    JoystickButton runIntakeOut = new JoystickButton(buttonPanel, ButtonConstants.INTAKE_OUT);
    runIntakeOut.whenHeld(new IntakeCommand(intake, -.7));

    //Shoot button
    JoystickButton shootButton = new JoystickButton(buttonPanel, ButtonConstants.FLYWHEEL_ON);
    shootButton.whenHeld(new ShootBallCommandGroup(shooter, index, limelight));

    //unused
    JoystickButton targetHub = new JoystickButton(buttonPanel, ButtonConstants.TARGET_SHOOTER);
    targetHub.whenHeld(new TargetHubCommand(shooter, limelight));


    //unused
    // replace these when we calibrate the hood
    JoystickButton hoodUp = new JoystickButton(buttonPanel, ButtonConstants.HOOD_UP);
    hoodUp.whileActiveContinuous(new InstantCommand(() -> shooter.extendHood(shooter.getHoodPos() + 0.1), shooter));
    JoystickButton hoodDown = new JoystickButton(buttonPanel, ButtonConstants.HOOD_DOWN);
    hoodDown.whileActiveContinuous(new InstantCommand(() -> shooter.extendHood(shooter.getHoodPos() - 0.1), shooter));


    JoystickButton raiseLift = new JoystickButton(buttonPanel, ButtonConstants.LIFT_UP);
    raiseLift.whenHeld(new LiftCommand(lift, -1));

    JoystickButton lowerLift = new JoystickButton(buttonPanel, ButtonConstants.LIFT_DOWN);
    lowerLift.whenHeld(new LiftCommand(lift, 1));

    // JoystickButton liftForward = new JoystickButton(buttonPanel, ButtonConstants.LIFT_FORWARD);
    // liftForward.whenHeld(new LiftCommand(lift, 0.3, true));

    // JoystickButton liftBackward = new JoystickButton(buttonPanel, ButtonConstants.LIFT_BACK);
    // liftBackward.whenHeld(new LiftCommand(lift, -0.3, true));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //return new SimpleDriveandShoot(drive, shooter, index, limelight);
    //return new SixBallLeftAuton(drive, limelight, shooter, intake, index);
    //return new ThreeBallLeftAuton(drive, limelight, shooter, intake, index);
    //return new ThreeBallRightAuton(drive, limelight, shooter, intake, index);
    return new SixBallLeftAuton(drive, limelight, shooter, intake, index);
  }

  public Command getTeleCommand() {
    return drive.getDefaultCommand();
  }
}
