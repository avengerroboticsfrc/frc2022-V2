package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Intake;

public class IndexIntake extends SequentialCommandGroup {
    public void IntakeIndex(Intake intake, int power) {
        addCommands(
            new IntakeCommand(intake, power),
            new //IndexCommand(index, power)
        );
    }
}
