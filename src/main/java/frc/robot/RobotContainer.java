// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.auton.FiveBallAuto;
import frc.robot.commands.auton.SimpleDriveandShoot;
import frc.robot.commands.auton.FiveBallAuto;
import frc.robot.commands.auton.TwoBallTimeBased;
import frc.robot.commands.driveTypes.DefaultDrive;
import frc.robot.commands.driveTypes.LucaDrive;
import frc.robot.commands.ComplexCommands.AllIndexCommand;
import frc.robot.commands.ComplexCommands.DataTestingCommandGroup;
import frc.robot.commands.ComplexCommands.IntakeToIndexCommandGroup;
import frc.robot.commands.ComplexCommands.PickUpBallCommandGroup;
import frc.robot.commands.ComplexCommands.ShootBallCommandGroup;
import frc.robot.commands.ComplexCommands.WrongBallCommandGroup;
import frc.robot.commands.SimpleCommands.IntakeCommand;
import frc.robot.commands.SimpleCommands.IntakeToIndexCommand;
import frc.robot.commands.SimpleCommands.LiftCommand;
import frc.robot.commands.SimpleCommands.TogglePneumaticsCommand;
import frc.robot.constants.ButtonConstants;
import frc.robot.constants.ButtonConstants.ControllerType;
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
    shooter = new Shooter();
    limelight = new Limelight();
    index = new Index();
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
        new LucaDrive(
            drive,
            (controller)::getL2Axis,
            (controller)::getR2Axis,
            (controller)::getLeftX,
            (controller)::getCircleButton,
            (controller)::getSquareButton));
  }

  private void configureButtonBindings() {
    JoystickButton raiseLift = new JoystickButton(buttonPanel, ButtonConstants.LIFT_UP);
    JoystickButton lowerLift = new JoystickButton(buttonPanel, ButtonConstants.LIFT_DOWN);
    JoystickButton extendIntake = new JoystickButton(buttonPanel, ButtonConstants.INTAKE_EXTEND);
    JoystickButton retractIntake = new JoystickButton(buttonPanel, ButtonConstants.INTAKE_RETRACT);
    JoystickButton runIntakeIn = new JoystickButton(buttonPanel, ButtonConstants.INTAKE_IN);
    JoystickButton runIntakeOut = new JoystickButton(buttonPanel, ButtonConstants.INTAKE_OUT);
    JoystickButton indexUp = new JoystickButton(buttonPanel, ButtonConstants.INDEX_UP);
    JoystickButton liftForward = new JoystickButton(buttonPanel, ButtonConstants.LIFT_FORWARD);
    JoystickButton indexOut = new JoystickButton(buttonPanel, ButtonConstants.INDEX_OUT);
    JoystickButton liftBackward = new JoystickButton(buttonPanel, ButtonConstants.LIFT_BACK);
    JoystickButton shootButton = new JoystickButton(buttonPanel, 9);
    JoystickButton shootWrongBall = new JoystickButton(buttonPanel, 10);
    JoystickButton shootFallback = new JoystickButton(buttonPanel, 11);

    raiseLift.whenHeld(new LiftCommand(lift, -1));
    lowerLift.whenHeld(new LiftCommand(lift, 1));
    extendIntake.whenPressed(new TogglePneumaticsCommand(intake));
    retractIntake.whenPressed(intake::retract, intake);
    runIntakeOut.whenHeld(new IntakeToIndexCommandGroup(intake, intakeToIndex, index, indexToShooter));
    runIntakeIn.whenHeld(new IntakeToIndexCommandGroup(intake, intakeToIndex, index, indexToShooter));
    indexUp.whenHeld(new AllIndexCommand(intakeToIndex, index, 0.5, 0.5));
    indexOut.whenHeld(new AllIndexCommand(intakeToIndex, index, -0.5, -0.5));
    shootButton.whenHeld(
        new ShootBallCommandGroup(shooter, index, indexToShooter, limelight, 0.5, 0.5));
    shootWrongBall.whenHeld(new WrongBallCommandGroup(shooter, index, indexToShooter, limelight, .5, .5, .3));
    shootFallback.whenHeld(new DataTestingCommandGroup(shooter, index, indexToShooter, limelight, .5, .5, .75));

    // raiseLift.whenHeld(new DataTestingCommandGroup(shooter, index,
    // indexToShooter, limelight, 0.5, 0.5, 1000));
    // lowerLift.whenHeld(new DataTestingCommandGroup(shooter, index,
    // indexToShooter, limelight, 0.5, 0.5, 1500));
    // extendIntake.whenHeld(new DataTestingCommandGroup(shooter, index,
    // indexToShooter, limelight, 0.5, 0.5, 2000));
    // retractIntake.whenHeld(new DataTestingCommandGroup(shooter, index,
    // indexToShooter, limelight, 0.5, 0.5, 2500));
    // runIntakeIn.whenHeld(new DataTestingCommandGroup(shooter, index,
    // indexToShooter, limelight, 0.5, 0.5, 3000));
    // runIntakeOut.whenHeld(new DataTestingCommandGroup(shooter, index,
    // indexToShooter, limelight, 0.5, 0.5, 3500));
    // indexUp.whenHeld(new DataTestingCommandGroup(shooter, index, indexToShooter,
    // limelight, 0.5, 0.5, 4000));
    // indexOut.whenHeld(new DataTestingCommandGroup(shooter, index, indexToShooter,
    // limelight, 0.5, 0.5, 4500));
    // liftForward.whenHeld(new DataTestingCommandGroup(shooter, index,
    // indexToShooter, limelight, 0.5, 0.5, 5000));
    // liftBackward.whenHeld(new DataTestingCommandGroup(shooter, index,
    // indexToShooter, limelight, 0.5, 0.5, 6300));
    // shootButton.whenHeld(
    // new ShootBallCommandGroup(shooter, index, indexToShooter, limelight, 0.5,
    // 0.5));
    // shootButton
    // .whileActiveContinuous(new InstantCommand(() ->
    // shooter.extendHood(shooter.getHoodPos() + 0.1), shooter));
    // shootWrongBall
    // .whileActiveContinuous(new InstantCommand(() ->
    // shooter.extendHood(shooter.getHoodPos() - 0.1), shooter));

    // shootButton.whenHeld(new DataTestingFlywheelCommand(shooter, 100));

    // Shoot with limelight
    // shootButton.whenHeld(new ShootBallCommandGroup(shooter, index,
    // indexToShooter, limelight, .5, 1));

    // Null values subject to change
    // shootWrongBall.whenHeld(new ShootBallCommandGroup(shooter, index,
    // indexToShooter, limelight, 0.1, .5, .75));

    // JoystickButton targetHub = new JoystickButton(buttonPanel,
    // ButtonConstants.TARGET_SHOOTER);
    // targetHub.whenHeld(new TargetHubCommand(shooter, limelight));

    // shootWrongBall.whenHeld(new DataTestingFlywheelCommand(shooter, 6300));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new SimpleDriveandShoot(drive, shooter, index, limelight, intakeToIndex, indexToShooter, .5, .5);
  }
}
