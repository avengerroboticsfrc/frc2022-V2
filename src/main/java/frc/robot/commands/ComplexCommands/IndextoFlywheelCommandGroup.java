package frc.robot.commands.ComplexCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.SimpleCommands.IndexCommand;
import frc.robot.commands.SimpleCommands.IndexToShooterCommand;
import frc.robot.subsystems.Index;

public class IndextoFlywheelCommandGroup extends SequentialCommandGroup{

    private static final double indexPower = 1;
    private static final double IndextoShooterPower = 1;//probably need to change values


    public IndextoFlywheelCommandGroup(Index index) {
        addCommands(
      deadline(new WaitCommand(0), new IndexCommand(index, indexPower),
        parallel(new WaitCommand(0), new IndexToShooterCommand(index, IndextoShooterPower)) 
      )
    );
      }
}
