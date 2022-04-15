package frc.robot.commands.complex;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.simple.IndexCommand;
import frc.robot.commands.simple.IndexToShooterCommand;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.IndexToShooter;

public class IndextoFlywheelCommandGroup extends SequentialCommandGroup {

  public IndextoFlywheelCommandGroup(Index index, IndexToShooter inShooter, double indexPower,
      double indexToShooterPower) {
    addCommands(
        deadlineWith(new ParallelDeadlineGroup(
            new WaitCommand(2),
            new IndexCommand(index, indexPower),
            new IndexToShooterCommand(inShooter, indexToShooterPower)))

    );
  }
}
