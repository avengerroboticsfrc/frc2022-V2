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
  private final double decayVar = 0.965;

  /**
   * Creates a new DefaultDrive.
   *
   * @param subsystem The drive subsystem this command wil run on.
   * @param forward   The control input for driving forwards/backwards
   * @param rotation  The control input for turning
   */
  public LucaDrive(DriveTrain subsystem, DoubleSupplier reverse, DoubleSupplier forward, DoubleSupplier rotation,
      BooleanSupplier turn) {
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
    double speed = ((reverse.getAsDouble() * -1 + (forward.getAsDouble())) * .5);
    double rotate = rotation.getAsDouble();

    double val = turn.getAsBoolean() ? .6 : 1;
    double speed2 = (speed > 0 ? Math.pow(speed, 2) : -Math.pow(speed, 2)) * .65;
    double val2 = Math.pow(rotate, 3);

    // System.out.println("Real Speed" + speed2);
    // System.out.println("Held Speed" + heldSpeed2);
    // If you stop putting in inputs
    // Robot keeps moving, exponentially decreasing speed

    if (turn.getAsBoolean()) {
      drive.curvatureDrive(speed2, val2 * val, turn.getAsBoolean());
    } else if (heldSpeed2 * speed2 < 0 && (Math.abs(heldSpeed2) < Math.abs(speed2))) {
      heldSpeed2 *= decayVar;
      drive.curvatureDrive(heldSpeed2, (val2 * val), turn.getAsBoolean());
    } else {
      if (Math.abs(speed2) < 0.2) {
        heldSpeed2 *= decayVar;
        drive.curvatureDrive(heldSpeed2, (val2 * val), turn.getAsBoolean());
      } else {
        drive.curvatureDrive((speed2), (val2 * val), turn.getAsBoolean());
        // Hold inputs at end due to how it cycles
        heldSpeed2 = speed2;
      }
    }
  }
}