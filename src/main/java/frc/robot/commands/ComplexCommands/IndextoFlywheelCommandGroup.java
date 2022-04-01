package frc.robot.commands.ComplexCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.SimpleCommands.IndexCommand;
import frc.robot.commands.SimpleCommands.IndexToShooterCommand;
import frc.robot.subsystems.Index;

public class IndextoFlywheelCommandGroup extends SequentialCommandGroup{

    public IndextoFlywheelCommandGroup(Index index, double intakePower, double indexPower) {
        addCommands(
      deadline(new WaitCommand(0), new IndexCommand(index, intakePower),
        parallel(new WaitCommand(0), new IndexToShooterCommand(index, indexPower)) 
      )
    );
      }
}
