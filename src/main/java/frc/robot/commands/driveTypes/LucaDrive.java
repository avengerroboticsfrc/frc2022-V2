package frc.robot.commands.driveTypes;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import java.util.function.DoubleSupplier;
import java.util.function.BooleanSupplier;

public class LucaDrive extends CommandBase {
  private final DriveTrain drive;
  private final DoubleSupplier forward;
  private final DoubleSupplier rotation;
  private final BooleanSupplier turn;
  private final DoubleSupplier reverse;
  // private final BooleanSupplier turbo;
  private final SlewRateLimiter speedFilter;
  private final SlewRateLimiter turnFilter;

  /**
   * Creates a new DefaultDrive.
   *
   * @param subsystem The drive subsystem this command wil run on.
   * @param forward   The control input for driving forwards/backwards
   * @param rotation  The control input for turning
   */
  public LucaDrive(DriveTrain subsystem, DoubleSupplier reverse, DoubleSupplier forward, DoubleSupplier rotation,
      BooleanSupplier turn, BooleanSupplier turbo) {
    super();
    this.drive = subsystem;
    this.reverse = reverse;
    this.forward = forward;
    this.rotation = rotation;
    this.turn = turn;
    // this.turbo = turbo;
    speedFilter = new SlewRateLimiter(1);
    turnFilter = new SlewRateLimiter(2);
    addRequirements(drive);
  }

  @Override
  public void execute() {
    double speed = speedFilter.calculate(((reverse.getAsDouble() * -1) + forward.getAsDouble()) * .35);
    double turnMultiplier = turn.getAsBoolean() ? .5 : 1;
    double rotate = turnFilter.calculate(rotation.getAsDouble() * .5);
    System.out.println(rotate);
    drive.curvatureDrive(speed, rotate * turnMultiplier, turn.getAsBoolean());

    if (turn.getAsBoolean()) {
      drive.curvatureDrive(speed * .6, rotation.getAsDouble() * .2, turn.getAsBoolean());
    } else {
      drive.curvatureDrive(speed, rotate * turnMultiplier, turn.getAsBoolean());
    }
  }
}

// @Override
// public void execute() {
// double turboOn = turbo.getAsBoolean() ? 10 : 0;
// double sped = ((reverse.getAsDouble()*-1 + forward.getAsDouble()));
// double rotate = rotation.getAsDouble();
// double speed = sped*.4;
// double val = turn.getAsBoolean() ? .6 : 1;
// double val2 = Math.pow(rotate, 3);

// // System.out.println("Real Speed" + speed2);
// // System.out.println("Held Speed" + heldSpeed2);
// // If you stop putting in inputs
// // Robot keeps moving, exponentially decreasing speed

// if (turn.getAsBoolean()) {
// drive.curvatureDrive(speed+turboOn, val2 * val, turn.getAsBoolean());
// }
// // else if (heldSpeed2 * speed < 0 && (Math.abs(heldSpeed2) <
// Math.abs(speed))) {
// // heldSpeed2 *= decayVar;
// // drive.curvatureDrive(heldSpeed2, (val2 * val), turn.getAsBoolean());
// // }
// else {
// if (Math.abs(speed) < 0.2) {
// heldSpeed2 *= decayVar;
// drive.curvatureDrive(heldSpeed2+turboOn, (val2 * val), turn.getAsBoolean());
// } else {
// drive.curvatureDrive((speed+turboOn), (val2 * val), turn.getAsBoolean());
// // Hold inputs at end due to how it cycles
// heldSpeed2 = speed;
// }
// }
// }
// }