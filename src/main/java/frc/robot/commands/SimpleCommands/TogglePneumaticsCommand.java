package frc.robot.commands.SimpleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class TogglePneumaticsCommand extends CommandBase {
    private final Intake intake;
    private int toggle;

  public TogglePneumaticsCommand(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if(toggle == 1){
    intake.extend();
    toggle = 0;
    }else
    {
      intake.retract();
      toggle = 1;
    }
  }

  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}

    

