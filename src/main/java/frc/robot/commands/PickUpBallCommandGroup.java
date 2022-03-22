package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;

public class PickUpBallCommandGroup extends SequentialCommandGroup {

  public PickUpBallCommandGroup(Intake intake, Index index, Double power) {
    addCommands(
        new InstantCommand(intake::extend),
        deadline(new WaitCommand(.5), new IntakeCommand(intake, power*.75), new IndexCommand(index, power*.5)),
        new InstantCommand(intake::retract)
    );

    
 }
}
