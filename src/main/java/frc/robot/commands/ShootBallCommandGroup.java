package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class ShootBallCommandGroup extends SequentialCommandGroup {
  private static final double indexPower = 0.3;
  private static final double shooterPower = 0.5;

  public ShootBallCommandGroup(Shooter shooter, Index index, Limelight limelight) {
    addCommands(
      deadline(new WaitCommand(.3), new TurretCommand(shooter, limelight)),
      deadline(new WaitCommand(3), new FlywheelCommand(shooter, shooterPower),
        sequence(new WaitCommand(2), new IndexCommand(index, indexPower))
      )
    );
  }
}
