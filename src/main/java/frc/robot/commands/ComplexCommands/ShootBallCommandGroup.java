package frc.robot.commands.ComplexCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.SimpleCommands.FlywheelCommand;
import frc.robot.commands.SimpleCommands.IndexCommand;
import frc.robot.commands.SimpleCommands.IndexToShooterCommand;
import frc.robot.commands.SimpleCommands.TargetHubCommand;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.IndexToShooter;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class ShootBallCommandGroup extends SequentialCommandGroup {

  public ShootBallCommandGroup(Shooter shooter, Index index, IndexToShooter indexToShooter, Limelight limelight,
      double shooterPower, double indexPower, double indexToShooterPower) {
    addCommands(
        deadline(new WaitCommand(1), new TargetHubCommand(shooter, limelight)),
        parallel(
            new FlywheelCommand(shooter, shooterPower),
            sequence(new WaitCommand(2),
                parallel(
                    new IndexToShooterCommand(indexToShooter, indexToShooterPower),
                    new IndexCommand(index, indexPower))))

    );

    // deadline(
    // new WaitCommand(1),
    // new TargetHubCommand(shooter, limelight)),
    // deadline(
    // new WaitCommand(1),
    // new FlywheelCommand(shooter, shooterPower)),
    // parallel(
    // new IndexCommand(index, indexPower),
    // new IndexToShooterCommand(indexToShooter, indexToShooterPower)
    // )
  }
}
