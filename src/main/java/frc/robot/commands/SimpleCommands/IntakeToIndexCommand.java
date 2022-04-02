package frc.robot.commands.SimpleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;

public class IntakeToIndexCommand extends CommandBase  {
  private double power;
  private Index inIndex;

  public IntakeToIndexCommand(Index index, double power) {
    this.inIndex = index;
    this.power = power;
    addRequirements(index);
  }
  
  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    inIndex.intakeIntoIndexPower(power);
  }

  @Override
  public void end(boolean interrupted) {
    inIndex.intakeIntoIndexPower(0);
  }
  //Something is wrong with class I created


}
