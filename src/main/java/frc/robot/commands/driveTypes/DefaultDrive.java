package frc.robot.commands.driveTypes;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class DefaultDrive extends CommandBase {
  private final DriveTrain drive;
  private final DoubleSupplier left;
  private final DoubleSupplier right;
  private final BooleanSupplier turbo;

  /**
   * Creates a new DefaultDrive.
   *
   * @param subsystem The drive subsystem this command wil run on.
   * @param left      The control input for driving lefts/backwards
   * @param right     The control input for turning
   * @param turbo     The button input for toggling the robot speed
   */
  public DefaultDrive(
      DriveTrain subsystem,
      DoubleSupplier left,
      DoubleSupplier right,
      BooleanSupplier turbo) {
        super();

    this.drive = subsystem;
    this.left = left;
    this.right = right;
    this.turbo = turbo;

    addRequirements(drive);
  }

  @Override
  public void execute() {
    double scalar = turbo.getAsBoolean() ? 0.8 : 0.4;
    drive.tankDrive(left.getAsDouble() * -scalar, right.getAsDouble() * -scalar);
  }
}
