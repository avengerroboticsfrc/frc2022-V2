package frc.robot.commands.ComplexCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.SimpleCommands.IndexCommand;
import frc.robot.commands.SimpleCommands.IntakeCommand;
import frc.robot.commands.SimpleCommands.IntakeExtendCommand;
import frc.robot.commands.SimpleCommands.IntakeRetractCommand;
import frc.robot.commands.SimpleCommands.IntakeToIndexCommand;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;


public class AutomaticIntakeCommandGroup extends SequentialCommandGroup {
  private static final double indexPower = 1;
  private static final double intakePower = 1; //might need to change values
  private static final double intakeToIndexPower = 1;
  public AutomaticIntakeCommandGroup(Index index, Intake intake) {
    addCommands(
      deadline(new WaitCommand(0), new IntakeExtendCommand(intake)),
      deadline(new WaitCommand(0.2), new IntakeCommand(intake, intakePower),
        parallel(new WaitCommand(0), new IntakeToIndexCommand(index, intakeToIndexPower)),
        parallel(new WaitCommand(0), new IndexCommand(index, indexPower))
      ),
      deadline(new WaitCommand(0.2), new IntakeRetractCommand(intake))
    );
  }
}
