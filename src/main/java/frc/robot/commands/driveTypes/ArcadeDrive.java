package frc.robot.commands.driveTypes;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class ArcadeDrive extends CommandBase {
  private final DriveTrain drive;
  private final DoubleSupplier forward;
  private final DoubleSupplier turn;
  private final BooleanSupplier turbo;

  /**
   * Creates a new DefaultDrive.
   *
   * @param subsystem The drive subsystem this command wil run on.
   * @param left      The control input for driving backwards
   * @param right     The control input for turning
   * @param turbo     The button input for toggling the robot speed
   */
  public ArcadeDrive(
      DriveTrain subsystem,
      DoubleSupplier left,
      DoubleSupplier right,
      BooleanSupplier turbo) {
        super();

    this.drive = subsystem;
    this.forward = left;
    this.turn = right;
    this.turbo = turbo;

    addRequirements(drive);
  }

  @Override
  public void execute() {
    double scalar = turbo.getAsBoolean() ? 0.6 : 0.4;
    drive.arcadeDrive(forward.getAsDouble() * -scalar, turn.getAsDouble() * -scalar);
  }
}
