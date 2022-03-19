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

  public void forwardAuto() {
    drive.tankDrive(.2, .2);
  }
}