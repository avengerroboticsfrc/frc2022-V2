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
import frc.robot.subsystems.IntakeToIndex;

public class PickUpBallCommandGroup extends SequentialCommandGroup {
  public PickUpBallCommandGroup(Intake intake, IntakeToIndex intakeToIndex, Index index, double intakePower, double intakeToIndexPower, double indexPower) {
    addCommands(

            new InstantCommand(intake::extend),
            new WaitCommand(1),
            parallel(
              new IntakeCommand(intake, intakePower),
              new IntakeToIndexCommand(intakeToIndex, intakeToIndexPower),
              new IndexCommand(index, indexPower)
            )
            )
            ;

  }
}
