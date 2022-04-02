package frc.robot.commands.ComplexCommands;

import frc.robot.commands.SimpleCommands.IndexCommand;
import frc.robot.commands.SimpleCommands.IntakeCommand;
import frc.robot.commands.SimpleCommands.IntakeToIndexCommand;
import frc.robot.subsystems.IntakeToIndex;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class IntakeToIndexCommandGroup extends ParallelCommandGroup{
    public IntakeToIndexCommandGroup
    (Intake intake, IntakeToIndex intakeToIndex, Index index){
        addCommands(
            deadline(
                new IntakeCommand(intake, .5),
                new IntakeToIndexCommand(intakeToIndex, 1.0)),
                new IndexCommand(index, .5));
                // // TODO:: FIX THIS CHANGE MOTOR RUN ON INTAKECOMMAND
                // new InstantCommand(intake::extend),
                // new WaitCommand(1),
                // deadline(new WaitCommand(5), new IntakeCommand(intake, 0.5),
                //     sequence(new WaitCommand(3), new IndexCommand(index, 0.5)))));
    
      }

}
