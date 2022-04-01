package frc.robot.commands.SimpleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class TogglePneumaticsCommand extends CommandBase {
    private final Intake intake;

  public TogglePneumaticsCommand(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    intake.extend();
  }

  @Override
  public void end(boolean interrupted) {
      intake.retract();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}

    
}
