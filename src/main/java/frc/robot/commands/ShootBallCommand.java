package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class ShootBallCommand extends SequentialCommandGroup {
  private static final double indexPower = 0.3;
  private static final double shooterPower = 0.5;

  public ShootBallCommand(Shooter shooter, Index index, Limelight limelight) {
    addCommands(
      deadline(new WaitCommand(2), new AimTurretAtHub(shooter, limelight)),
      deadline(new WaitCommand(5), new ShooterCommand(shooter, shooterPower),
        sequence(new WaitCommand(3), new IndexCommand(index, indexPower))
      )
    );
  }
}
