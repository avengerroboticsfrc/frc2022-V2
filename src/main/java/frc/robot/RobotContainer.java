// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.auton.SimpleDriveandShoot; //Keep Import. Needed For Auton
import frc.robot.commands.auton.SixBallLeftAuton; //Keep Import. Needed For Auton
import frc.robot.commands.auton.ThreeBallLeftAuton; //Keep Import. Needed For Auton
import frc.robot.commands.auton.ThreeBallRightAuton; //Keep Import. Needed For Auton
import frc.robot.commands.auton.TwoBallTimeBased; //Keep Import. Needed For Auton
import frc.robot.commands.driveTypes.ArcadeDrive;
import frc.robot.commands.driveTypes.DefaultDrive; //Keep Import. Needed For Auton
import frc.robot.commands.driveTypes.LucaDrive; //Keep Import. Luca Drive 
import frc.robot.commands.ComplexCommands.AllIndexCommand;
import frc.robot.commands.ComplexCommands.IntakeAndShootCommandGroup;
import frc.robot.commands.ComplexCommands.IntakeToIndexCommandGroup;
import frc.robot.commands.ComplexCommands.PickUpBallCommandGroup;
import frc.robot.commands.ComplexCommands.ShootBallCommandGroup;
import frc.robot.commands.SimpleCommands.IndexCommand;
import frc.robot.commands.SimpleCommands.IntakeCommand;
import frc.robot.commands.SimpleCommands.IntakeExtendCommand;
import frc.robot.commands.SimpleCommands.IntakeRetractCommand;
import frc.robot.commands.SimpleCommands.LiftCommand;
import frc.robot.commands.SimpleCommands.TargetHubCommand;
import frc.robot.constants.ButtonConstants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.IndexToShooter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.IntakeToIndex;
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
  private final IntakeToIndex intakeToIndex;
  private final Index index;
  private final IndexToShooter indexToShooter;
  private final Shooter shooter;
  private final XboxController controller;
  private final Limelight limelight;
  private final Intake intake;
  private final Joystick buttonPanel;
  private final Lift lift;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    System.out.println("Hello, Driver");

    controller = new XboxController(ButtonConstants.CONTROLLER_PORT);
    buttonPanel = new Joystick(ButtonConstants.BUTTON_PANEL_PORT);

    drive = new DriveTrain();
    index = new Index();
    shooter = new Shooter();
    limelight = new Limelight();
    intake = new Intake();
    intakeToIndex = new IntakeToIndex();
    indexToShooter = new IndexToShooter();
    lift = new Lift();
    drive.gyroCalibrate();

    configureDriveTrain();
    configureButtonBindings();
  }

  private void configureDriveTrain() {
    // sets the command to drive the robot.
    // will run whenever the drivetrain is not being used.
    drive.setDefaultCommand(
        // pass in a reference to a method
        // new LucaDrive(
        // drive,
        // controller::getL2Axis,
        // controller::getR2Axis,
        // controller::getLeftX,
        // controller::getCircleButton
        // ));
        // new DefaultDrive(drive, controller::getLeftY, controller::getRightY,
        // controller::getRightBumper));
        new ArcadeDrive(drive, controller::getLeftY, controller::getRightX, controller::getRightBumper));
  }

  private void configureButtonBindings() {
    JoystickButton extendIntake = new JoystickButton(buttonPanel,
        ButtonConstants.INTAKE_EXTEND);
    extendIntake.whenPressed(intake::extend, intake);

    JoystickButton retractIntake = new JoystickButton(buttonPanel,
        ButtonConstants.INTAKE_RETRACT);
    retractIntake.whenPressed(intake::retract, intake);

    JoystickButton indexUp = new JoystickButton(buttonPanel, ButtonConstants.INDEX_UP);
    indexUp.whenHeld(new AllIndexCommand(intakeToIndex, index, 0.5, 0.5));

    JoystickButton indexOut = new JoystickButton(buttonPanel, ButtonConstants.INDEX_OUT);
    indexOut.whenHeld(new AllIndexCommand(intakeToIndex, index, -0.5, -0.5));

    JoystickButton runIntakeIn = new JoystickButton(buttonPanel, ButtonConstants.INTAKE_IN);
    runIntakeIn.whenHeld(new IntakeCommand(intake, 0.5));

    JoystickButton runIntakeOut = new JoystickButton(buttonPanel, ButtonConstants.INTAKE_OUT);
    runIntakeOut.whenHeld(new IntakeCommand(intake, -0.3));

    // Shoot button
    JoystickButton shootButton = new JoystickButton(buttonPanel, ButtonConstants.FLYWHEEL_ON);
    // null values subject to change
    shootButton.whenHeld(new ShootBallCommandGroup(shooter, index, indexToShooter, limelight, 1, .5, .5));

    JoystickButton shootWrongBall = new JoystickButton(buttonPanel, ButtonConstants.SHOOT_WRONG_BALL);
    // Null values subject to change
    shootWrongBall.whenHeld(new ShootBallCommandGroup(shooter, index, indexToShooter, limelight, 0.2, .5, .5));

    // unused
    JoystickButton targetHub = new JoystickButton(buttonPanel, ButtonConstants.TARGET_SHOOTER);
    targetHub.whenHeld(new TargetHubCommand(shooter, limelight));

    // replace these when we calibrate the hood
    JoystickButton hoodUp = new JoystickButton(buttonPanel,
        ButtonConstants.HOOD_UP);
    hoodUp.whileActiveContinuous(new InstantCommand(() -> shooter.extendHood(shooter.getHoodPos() + 0.1), shooter));
    JoystickButton hoodDown = new JoystickButton(buttonPanel,
        ButtonConstants.HOOD_DOWN);
    hoodDown.whileActiveContinuous(new InstantCommand(() -> shooter.extendHood(shooter.getHoodPos() - 0.1), shooter));

    JoystickButton raiseLift = new JoystickButton(buttonPanel,
    ButtonConstants.LIFT_UP);
    raiseLift.whenHeld(new LiftCommand(lift, -1));

    JoystickButton lowerLift = new JoystickButton(buttonPanel,
    ButtonConstants.LIFT_DOWN);
    lowerLift.whenHeld(new LiftCommand(lift, 1));

    // JoystickButton liftForward = new JoystickButton(buttonPanel,
    // ButtonConstants.LIFT_FORWARD);
    // liftForward.whenHeld(new LiftCommand(lift, 0.3, true));

    // JoystickButton liftBackward = new JoystickButton(buttonPanel,
    // ButtonConstants.LIFT_BACK);
    // liftBackward.whenHeld(new LiftCommand(lift, -0.3, true));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new TwoBallTimeBased(drive, intake, index, intakeToIndex, shooter, indexToShooter, limelight);
  }
}
