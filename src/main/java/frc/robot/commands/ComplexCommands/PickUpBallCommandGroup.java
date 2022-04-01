package frc.robot.commands.ComplexCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.SimpleCommands.IndexCommand;
import frc.robot.commands.SimpleCommands.IntakeCommand;
import frc.robot.commands.SimpleCommands.IntakeToIndexCommand;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;

public class PickUpBallCommandGroup extends SequentialCommandGroup {
  public PickUpBallCommandGroup(Intake intake, Index index, double intakePower, double intakeToIndexPower, double indexPower) {
    addCommands(
        deadline( // TODO:: FIX THIS CHANGE MOTOR RUN ON INTAKECOMMAND
            new InstantCommand(intake::extend),
            new WaitCommand(1),
            new ParallelDeadlineGroup(
              new WaitCommand(5), 
              new IntakeCommand(intake, intakePower),
              new IntakeToIndexCommand(index, intakeToIndexPower),
              new IndexCommand(index, indexPower)
            )));

  }
}
