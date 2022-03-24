package frc.robot.commands;

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
  // private double kMaxTorque = 4.69 * 2;
  // private double wheelRadiusInMeters = 0.0762;

  // kMaxTorque = 4.69 newton meters for one

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

    // if percent out is negative, multiply it by -1
    double speed = percentOutput > 0 ? Math.pow(percentOutput, 2) : -Math.pow(percentOutput, 2);

    // slow down the drivetrain if turnInPlace is pressed
    double speedMultiplier = turnInPlace.getAsBoolean() ? .35 : 1;

    drive.curvatureDrive(
        (speed * .6),
        (rotation.getAsDouble() * speedMultiplier),
        turnInPlace.getAsBoolean());
  }

  /*
  @Override
  public void execute() {
    double percentOutput = (forward.getAsDouble() - reverse.getAsDouble());
    // // if percent out is negative, multiply it by -1
  
    double speed = percentOutput > 0 ? Math.pow(percentOutput, 2) : -Math.pow(percentOutput, 2);
    var motor = DCMotor.getFalcon500(2);
  
    // slow down the drivetrain if turnInPlace is pressed
    double speedMultiplier = turnInPlace.getAsBoolean() ? .35 : 1;
  
    double voltageLeft = percentOutput * 12;
    double voltageRight = percentOutput * 12;
    double voltageLeft2;
  
    // IF SOMETHING DOESNT WORK ITS PROBABLY THIS LINE BELOW
    double encoderSpeedLeft = drive.getWheelSpeeds().leftMetersPerSecond * wheelRadiusInMeters;
    double encoderSpeedRight = drive.getWheelSpeeds().rightMetersPerSecond * wheelRadiusInMeters;
    double torqueLeft = motor.KtNMPerAmp * motor.getCurrent(encoderSpeedLeft, voltageLeft);
    double torqueRight = motor.KtNMPerAmp * motor.getCurrent(encoderSpeedLeft, voltageRight);
  
    double encoder = drive.getAverageEncoderDistance() * wheelRadiusInMeters;
    double torque = (torqueLeft + torqueRight) / 2;
    double encoderSpeed = (encoderSpeedLeft + encoderSpeedRight) / 2;
    // Find voltage that limits torque
    if (Math.abs(torque) > kMaxTorque) {
      voltageLeft2 = kMaxTorque * Math.signum(torque) / motor.KtNMPerAmp * motor.rOhms +
          encoder / motor.KvRadPerSecPerVolt;
  
      // drive.curvatureDrive(
      // (speed * .6),
      // (rotation.getAsDouble() * speedMultiplier),
      // turnInPlace.getAsBoolean());
  
      // Theory is that since the left side acts like the right one
      // I can just mirror to other side
      // Honestly have 0 clue if this works lol
  
      drive.curvatureDrive(voltageLeft2, rotation.getAsDouble(), turnInPlace.getAsBoolean());
    } // should work
      // if you do drive.curvatureDriveVolts(voltage * 2); if controlling all 4 motor
      // voltages at once
      // I can't figure out how to limit voltages for curvatureDrive, I think you can
      // take over
  }
*/
}