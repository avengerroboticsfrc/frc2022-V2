package frc.robot.commands.complex;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.simple.DataTestingFlywheelCommand;
import frc.robot.commands.simple.IndexCommand;
import frc.robot.commands.simple.IndexToShooterCommand;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.IndexToShooter;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class DataTestingCommandGroup extends SequentialCommandGroup {

  /* Command Group to get Data for shooter. */
  public DataTestingCommandGroup(Shooter shooter, Index index, IndexToShooter indexToShooter,
      Limelight limelight, double indexPower, double indexToShooterPower, double ShooterPower) {
    addCommands(parallel(new DataTestingFlywheelCommand(shooter, ShooterPower),
        sequence(new WaitCommand(2),
            parallel(new IndexToShooterCommand(indexToShooter, indexToShooterPower),
                new IndexCommand(index, indexPower)))));
  }
}
