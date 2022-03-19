package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {

    private final Intake intakeCommand;
    private static double currentPower;
public IntakeCommand(Intake intake, double power){
    intakeCommand = intake;
    currentPower = power;
    addRequirements(intake);
}

@Override
public void initialize() {
 }

 @Override
public void execute(){
 intakeCommand.power(currentPower);
}

@Override
  public void end(boolean interrupted) {


  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }


}





