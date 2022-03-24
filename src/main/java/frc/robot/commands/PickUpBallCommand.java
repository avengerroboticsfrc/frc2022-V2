package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;

public class PickUpBallCommand extends SequentialCommandGroup {
  public PickUpBallCommand(Intake intake, Index index) {
    addCommands(
        deadline(
            new WaitCommand(.5),
            new IntakeCommand(intake, 0.75),
            new IndexCommand(index, 0.5)));
  }
}
