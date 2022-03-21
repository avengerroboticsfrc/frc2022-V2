package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;

public class PickUpBallCommand extends SequentialCommandGroup {


    public PickUpBallCommand(Intake intake, Index index, Double power, Intake Intakepneumatics) {
        addCommands(
            deadline(new PneumaticsCommand(Intakepneumatics)), new WaitCommand(1),
            deadline(new IntakeCommand(intake, power), new IndexCommand(index, power))
        );

    
 }
}
