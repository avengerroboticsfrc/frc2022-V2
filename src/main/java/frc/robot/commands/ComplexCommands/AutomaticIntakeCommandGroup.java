package frc.robot.commands.ComplexCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.SimpleCommands.IntakeCommand;
import frc.robot.commands.SimpleCommands.IntakeExtendCommand;
import frc.robot.commands.SimpleCommands.IntakeRetractCommand;
import frc.robot.commands.SimpleCommands.IntakeToIndexCommand;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.IntakeToIndex;


public class AutomaticIntakeCommandGroup extends SequentialCommandGroup {
  public AutomaticIntakeCommandGroup(Index index, Intake intake, IntakeToIndex inIndex, double intakePower, double indexPower, double intakeToIndexPower) {
    addCommands(
      deadline(
      new WaitCommand(0.2), 
      new IntakeExtendCommand(intake)),
      deadline(
        new WaitCommand(4),
        new IntakeCommand(intake, intakePower),
        new IntakeToIndexCommand(inIndex, intakeToIndexPower)),
      deadline(new WaitCommand(0.2), new IntakeRetractCommand(intake))
    );
  }
}
