package frc.robot.commands.SimpleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;

public class IntakeToIndex extends CommandBase  {
  private double power;
  private Index Inindex;

  public void IntakeToIndexCommand(Index index, double power) {
    this.Inindex = index;
    this.power = power;
    addRequirements(index);
  }
  
  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    Inindex.IntakeIntoIndexPower(power);
  }

  @Override
  public void end(boolean interrupted) {
    Inindex.IntakeIntoIndexPower(0);
  }
  //Something is wrong with class I created


}
