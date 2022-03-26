package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class DefaultDrive extends CommandBase {
  private final DriveTrain drive;
  private final DoubleSupplier left;
  private final DoubleSupplier right;
  private final BooleanSupplier turbo;
  private double heldLeftInput = 0;
  private double heldRightInput = 0;
  private final double decayVar = 0.9999999;

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

    // If you stop putting in inputs
    // Robot keeps moving, exponentially decreasing speed
    System.out.println("Real Input " + left.getAsDouble());
    System.out.println("Held Input Left " + heldLeftInput);
    boolean leftUnmovable = Math.abs(left.getAsDouble()) < 0.2;
    boolean rightUnmovable = Math.abs(right.getAsDouble()) < 0.2;
    if (leftUnmovable || rightUnmovable) {
      if (leftUnmovable && !rightUnmovable) {
        drive.tankDrive(heldLeftInput * decayVar, right.getAsDouble() * scalar);
        heldRightInput *= decayVar;
      } else if (rightUnmovable && !leftUnmovable) {
        drive.tankDrive(left.getAsDouble() * scalar, heldRightInput * decayVar);
        heldLeftInput *= decayVar;
      } else {
        drive.tankDrive(heldLeftInput * decayVar, heldRightInput * decayVar);
        heldLeftInput *= decayVar;
        heldRightInput *= decayVar;
      }
      // 0.18 is the stopping threshold
      // Technical value the robot stops moving is 0.2
      // AKA bare minimum input to move robot ^^^

      // BELOW MIGHT BE NECESSARY
      // if (Math.abs(heldLeftInput) < 0.05 || Math.abs(heldRightInput) < 0.05) {
      //   heldLeftInput = 0;
      //   heldRightInput = 0;
      // }

      // IF there is ANY controller input, drive as normal
    } else {
      drive.tankDrive(left.getAsDouble() * scalar, right.getAsDouble() * scalar);
      // Hold inputs at end due to how it cycles
      heldLeftInput = left.getAsDouble() * scalar;
      heldRightInput = right.getAsDouble() * scalar;
    }
  }
}
