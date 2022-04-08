package frc.robot.commands.ComplexCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.SimpleCommands.DataTestingFlywheelCommand;
import frc.robot.commands.SimpleCommands.DirectFlywheelCommand;
import frc.robot.commands.SimpleCommands.FlywheelCommand;
import frc.robot.commands.SimpleCommands.IndexCommand;
import frc.robot.commands.SimpleCommands.IndexToShooterCommand;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.IndexToShooter;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class WrongBallCommandGroup extends SequentialCommandGroup {

/*Command Group to get Data for shooter.*/
  public WrongBallCommandGroup(Shooter shooter, Index index, IndexToShooter indexToShooter, Limelight limelight,
     double indexPower, double indexToShooterPower, double ShooterPower) {
    addCommands(
        parallel(
            new DirectFlywheelCommand(shooter, ShooterPower),
            sequence(new WaitCommand(.5),
                parallel(
                    new IndexToShooterCommand(indexToShooter, indexToShooterPower),
                    new IndexCommand(index, indexPower)))));
  }
}
