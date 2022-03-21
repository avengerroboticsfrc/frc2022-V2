package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;

public class PickUpBallCommand extends SequentialCommandGroup {

  public PickUpBallCommand(Intake intake, Index index, Double power) {
    addCommands(
        new InstantCommand(intake::extend),
        deadline(new WaitCommand(2), new IntakeCommand(intake, power), new IndexCommand(index, power/2)),
        new InstantCommand(intake::retract)
    );

    
 }
}
