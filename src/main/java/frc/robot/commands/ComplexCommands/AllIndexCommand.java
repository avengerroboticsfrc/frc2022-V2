package frc.robot.commands.ComplexCommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.SimpleCommands.IndexCommand;
import frc.robot.commands.SimpleCommands.IndexToShooterCommand;
import frc.robot.commands.SimpleCommands.IntakeToIndexCommand;
import frc.robot.subsystems.IndexToShooter;
import frc.robot.subsystems.IntakeToIndex;
import frc.robot.subsystems.Index;

public class AllIndexCommand extends SequentialCommandGroup {
    public AllIndexCommand(IndexToShooter indexToShooter, IntakeToIndex intakeToIndex, Index index, double intakeToIndexPower, double indexPower, double indexToShooterPower){
        addCommands(
            new ParallelCommandGroup(
                new IndexToShooterCommand(indexToShooter, indexToShooterPower),
                new IndexCommand(index, indexPower),
                new IntakeToIndexCommand(intakeToIndex, intakeToIndexPower)
            )
        );  


    }
}
