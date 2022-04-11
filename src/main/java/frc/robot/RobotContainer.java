// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.PathPlanner;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.auton.TwoBallTimeBased;
import frc.robot.commands.driveTypes.ArcadeDrive;
import frc.robot.commands.driveTypes.DefaultDrive; //Keep Import. Needed For Auton
import frc.robot.commands.driveTypes.LucaDrive; //Keep Import. Luca Drive 
import frc.robot.commands.ComplexCommands.AllIndexCommand;
import frc.robot.commands.ComplexCommands.DataTestingCommandGroup;
import frc.robot.commands.ComplexCommands.FridayRamseteCommand;
import frc.robot.commands.ComplexCommands.PickUpBallCommandGroup;
import frc.robot.commands.ComplexCommands.ShootBallCommandGroup;
import frc.robot.commands.SimpleCommands.IntakeCommand;
import frc.robot.commands.SimpleCommands.IntakeExtendCommand;
import frc.robot.commands.SimpleCommands.IntakeRetractCommand;
import frc.robot.commands.SimpleCommands.IntakeToIndexCommand;
import frc.robot.commands.SimpleCommands.LiftCommand;
import frc.robot.commands.SimpleCommands.LiftHorizontalCommand;
import frc.robot.constants.ButtonConstants;
import frc.robot.constants.DriveConstants;
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

    JoystickButton raiseLift = new JoystickButton(buttonPanel,ButtonConstants.LIFT_UP);
    //raiseLift.whenHeld(new LiftCommand(lift, -1));
    raiseLift.whenHeld(new DataTestingCommandGroup(shooter, index, indexToShooter, limelight, 0.5, 0.5, 3000));

    JoystickButton lowerLift = new JoystickButton(buttonPanel,ButtonConstants.LIFT_DOWN);
    //lowerLift.whenHeld(new LiftCommand(lift, 1));
    lowerLift.whenHeld(new DataTestingCommandGroup(shooter, index, indexToShooter, limelight, 0.5, 0.5, 3280));

    JoystickButton extendIntake = new JoystickButton(buttonPanel,ButtonConstants.INTAKE_EXTEND);
    //extendIntake.whenPressed(intake::extend, intake); 
    extendIntake.whenHeld(new DataTestingCommandGroup(shooter, index, indexToShooter, limelight, 0.5, 0.5, 3570));

    JoystickButton retractIntake = new JoystickButton(buttonPanel,ButtonConstants.INTAKE_RETRACT);
    //retractIntake.whenPressed(intake::retract, intake);
    retractIntake.whenHeld(new DataTestingCommandGroup(shooter, index, indexToShooter, limelight, 0.5, 0.5, 3855));

    JoystickButton runIntakeIn = new JoystickButton(buttonPanel, ButtonConstants.INTAKE_IN);
    //runIntakeIn.whenHeld(new IntakeToIndexCommandGroup(intake, intakeToIndex, index));
    runIntakeIn.whenHeld(new DataTestingCommandGroup(shooter, index, indexToShooter, limelight, 0.5, 0.5, 4140));

    JoystickButton runIntakeOut = new JoystickButton(buttonPanel, ButtonConstants.INTAKE_OUT);
    //runIntakeOut.whenHeld(new IntakeToIndexCommandGroup(intake, intakeToIndex, index));
    runIntakeOut.whenHeld(new DataTestingCommandGroup(shooter, index, indexToShooter, limelight, 0.5, 0.5, 4425));

    JoystickButton indexUp = new JoystickButton(buttonPanel, ButtonConstants.INDEX_UP);
    //indexUp.whenHeld(new AllIndexCommand(intakeToIndex, index, 0.5, 0.5));
    indexUp.whenHeld(new DataTestingCommandGroup(shooter, index, indexToShooter, limelight, 0.5, 0.5, 4710));

    JoystickButton indexOut = new JoystickButton(buttonPanel, ButtonConstants.INDEX_OUT);
    //indexOut.whenHeld(new AllIndexCommand(intakeToIndex, index, -0.5, -0.5));
    indexOut.whenHeld(new DataTestingCommandGroup(shooter, index, indexToShooter, limelight, 0.5, 0.5, 4995));
    
    JoystickButton liftForward = new JoystickButton(buttonPanel,ButtonConstants.LIFT_FORWARD);
    //liftForward.whenHeld(new LiftHorizontalCommand(lift, 1));
    liftForward.whenHeld(new DataTestingCommandGroup(shooter, index, indexToShooter, limelight, 0.5, 0.5, 5280));

    JoystickButton liftBackward = new JoystickButton(buttonPanel,ButtonConstants.LIFT_BACK);
    //liftBackward.whenHeld(new LiftHorizontalCommand(lift, -1));
    liftBackward.whenHeld(new DataTestingCommandGroup(shooter, index, indexToShooter, limelight, 0.5, 0.5, 5565));

    // Shoot button
    JoystickButton shootButton = new JoystickButton(buttonPanel, ButtonConstants.FLYWHEEL_ON);
    // null values subject to change
    shootButton.whenHeld(new DataTestingCommandGroup(shooter, index, indexToShooter, limelight, 0.5, 0.5, 5850));

    JoystickButton shootWrongBall = new JoystickButton(buttonPanel, ButtonConstants.SHOOT_WRONG_BALL);
    shootWrongBall.whenHeld(new DataTestingCommandGroup(shooter, index, indexToShooter, limelight, 0.5, .5, 6380));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    var autoVoltageConstraint =
      new DifferentialDriveVoltageConstraint(
        new SimpleMotorFeedforward(
          DriveConstants.KS_VOLTS,
          DriveConstants.KV_VOLT_SECONDS_PER_METER,
          DriveConstants.KA_VOLT_SECONDS_SQUARED_PER_METER),
      DriveConstants.K_DRIVE_KINEMATICS, 
      10);
    
    TrajectoryConfig config =
      new TrajectoryConfig(
          DriveConstants.K_MAX_SPEED_METER_PER_SECOND, 
          DriveConstants.K_MAX_ACCELERATION_METERS_PER_SECOND_SQUARED)
        .setKinematics(DriveConstants.K_DRIVE_KINEMATICS)
        .addConstraint(autoVoltageConstraint);
    
    Trajectory path = PathPlanner.loadPath("6-ball-left", 3, 5);

    RamseteCommand ramseteCommand =
    new RamseteCommand(
        path,
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


    drive.resetOdometry(path.getInitialPose());

    return ramseteCommand.andThen(() -> drive.tankDriveVolts(0, 0));


  }
}
