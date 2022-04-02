package frc.robot.commands.SimpleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeToIndex;

public class IntakeToIndexCommand extends CommandBase  {
  private double power;
  private IntakeToIndex index;

  public IntakeToIndexCommand(IntakeToIndex index, double power) {
    this.index = index;
    this.power = power;
    addRequirements(index);
  }
  
  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    index.power(power);
  }

  @Override
  public void end(boolean interrupted) {
    index.power(0);
  }
  //Something is wrong with class I created


}
