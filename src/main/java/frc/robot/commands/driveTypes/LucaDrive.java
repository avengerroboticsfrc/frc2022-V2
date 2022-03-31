package frc.robot.commands.driveTypes;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import java.lang.Math;
import java.util.function.DoubleSupplier;
import java.util.function.BooleanSupplier;

public class LucaDrive extends CommandBase {
  private final DriveTrain drive;
  private final DoubleSupplier forward;
  private final DoubleSupplier rotation;
  private final BooleanSupplier turn;
  private final DoubleSupplier reverse;
  private double heldSpeed2 = 0;
  private final double decayVar = 0.975;

  /**
   * Creates a new DefaultDrive.
   *
   * @param subsystem The drive subsystem this command wil run on.
   * @param forward   The control input for driving forwards/backwards
   * @param rotation  The control input for turning
   */
  public LucaDrive(DriveTrain subsystem, DoubleSupplier reverse, DoubleSupplier forward, DoubleSupplier rotation, BooleanSupplier turn) {
    super();
    this.drive = subsystem;
    this.reverse = reverse;
    this.forward = forward;
    this.rotation = rotation;
    this.turn = turn;

    
    addRequirements(drive);
  }

  @Override
  public void execute() {
    double speed = (reverse.getAsDouble() + (forward.getAsDouble()*-1)) * .5;
    double rotate = rotation.getAsDouble();

    double val = turn.getAsBoolean() ? .25 : 1;
    double speed2 = speed>0 ? Math.pow(speed, 2) : -Math.pow(speed, 2);
    double val2 = Math.pow(rotate, 3);

    System.out.println("Real Speed" + speed2);
    System.out.println("Held Speed" + heldSpeed2);
    // If you stop putting in inputs
    // Robot keeps moving, exponentially decreasing speed

    if (Math.abs(speed2) > Math.abs(heldSpeed2)) {
      if (speed2 - heldSpeed2 > 0.1) {
        heldSpeed2 += 0.001;
      } else if (speed2 - heldSpeed2 < -0.1) {
        heldSpeed2 -= 0.001;
      }
      heldSpeed2 /= decayVar;
      drive.curvatureDrive(heldSpeed2, (val2 * .3), turn.getAsBoolean());
    } else if (Math.abs(speed2) < 0.1) {
      heldSpeed2 *= decayVar;
      drive.curvatureDrive(heldSpeed2, (val2 * .3), turn.getAsBoolean());
    } else {
      drive.curvatureDrive((speed2*.5), (val2*.3), turn.getAsBoolean());
      // Hold inputs at end due to how it cycles
      heldSpeed2 = speed2;
    }
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