package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.commands.IndexCommand;
import frc.robot.commands.IntakeCommand;

public class IndexIntake extends ParallelCommandGroup {
    Intake intake;
    Index index;
    public void IntakeIndex(Intake intake, int power, Index index) {
        addCommands(
            new IntakeCommand(intake, power),
            new IndexCommand(index, power)
        );
    }


    public void powIntInd(double power){
        intake.power(power);
        index.power(1);
    }



}
