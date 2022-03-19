package frc.robot.commands;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.DriveTrain;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class LucaDrive extends CommandBase {
  private final DriveTrain drive;
  private final DoubleSupplier forward;
  private final DoubleSupplier rotation;
  private final BooleanSupplier turnInPlace;
  private final DoubleSupplier reverse;

  // kMaxTorque = 4.69 newton meters for one
  double kMaxTorque = 4.69 * 2;
  double wheelRadiusInMeters = 0.0762; // @Truender find this please I don't know the wheels
  /**
   * Creates a new LucaDrive.
   *
   * @param subsystem   The drive subsystem this command wil run on.
   * @param forward     The control input for driving forwards
   * @param reverse     The control input for reversing
   * @param rotation    The control input for turning
   * @param turnInPlace The button input for whether to turn in place
   */
  public LucaDrive(
      DriveTrain subsystem,
      DoubleSupplier forward,
      DoubleSupplier reverse,
      DoubleSupplier rotation,
      BooleanSupplier turnInPlace) {
    super();

    this.drive = subsystem;
    this.reverse = reverse;
    this.forward = forward;
    this.rotation = rotation;
    this.turnInPlace = turnInPlace;

    addRequirements(drive);
  }

  @Override
  public void execute() {
    double percentOutput = (forward.getAsDouble() - reverse.getAsDouble());
    // // if percent out is negative, multiply it by -1
    double speed = percentOutput > 0 ? Math.pow(percentOutput, 2) : -Math.pow(percentOutput, 2);

    // // slow down the drivetrain if turnInPlace is pressed
    // double speedMultiplier = turnInPlace.getAsBoolean() ? .35 : 1;

    // drive.curvatureDrive(
    //     (speed * .6),
    //     (rotation.getAsDouble() * speedMultiplier),
    //     turnInPlace.getAsBoolean());
   
    // Theory is that since the left side acts like the right one
    // I can just mirror to other side
    // Honestly have 0 clue if this works lol
    double voltage = speed * 12.0;
    var motor = DCMotor.getFalcon500(2);
    // IF SOMETHING DOESNT WORK ITS PROBABLY THIS LINE BELOW
    double encoderSpeedLeft = drive.getWheelSpeeds().leftMetersPerSecond / wheelRadiusInMeters;
    // double encoderSpeedRight = drive.getWheelSpeeds().rightMetersPerSecond * wheelRadiusInMeters;
    double torqueLeft = motor.KtNMPerAmp * motor.getCurrent(encoderSpeedLeft, voltage);
    // double torqueRight = motor.KtNMPerAmp * motor.getCurrent(encoderSpeedLeft, voltageLeft);
    
    // Find voltage that limits torque
    if (Math.abs(torqueLeft) > kMaxTorque) {
      voltage = kMaxTorque * Math.signum(torqueLeft) / motor.KtNMPerAmp * motor.rOhms +
        encoderSpeedLeft / motor.KvRadPerSecPerVolt;
      drive.curvatureDrive(voltage, rotation.getAsDouble(), turnInPlace.getAsBoolean());// should work
      // if you do drive.curvatureDriveVolts(voltage * 2); if controlling all 4 motor voltages at once
      // I can't figure out how to limit voltages for curvatureDrive, I think you can take over
    }

    
  }

  public void forwardAuto() {
    drive.tankDrive(.2, .2);
  }
}