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

  /**
   * target while the flywheel spins up. wait to seconds to reach speed. shoot
   * ball. -k
   */
  public ShootBallCommandGroup(Shooter shooter, Index index, IndexToShooter indexToShooter, Limelight limelight,
     double indexPower, double indexToShooterPower) {
    addCommands(
        new TargetHubCommand(shooter, limelight).withTimeout(1),
        parallel(
            new FlywheelCommand(shooter, limelight),
            sequence(new WaitCommand(2),
                parallel(
                    new IndexToShooterCommand(indexToShooter, indexToShooterPower),
                    new IndexCommand(index, indexPower)))));
  }
}
