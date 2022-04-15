package frc.robot.commands.complex;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IntakeToIndex;
import frc.robot.commands.simple.IndexCommand;
import frc.robot.commands.simple.IntakeToIndexCommand;
import frc.robot.subsystems.Index;

public class AllIndexCommand extends SequentialCommandGroup {
  public AllIndexCommand(IntakeToIndex intakeToIndex, Index index, double intakeToIndexPower,
      double indexPower) {
    addCommands(new ParallelCommandGroup(new IndexCommand(index, indexPower),
        new IntakeToIndexCommand(intakeToIndex, intakeToIndexPower)));

  }
}
