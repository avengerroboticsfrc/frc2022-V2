package frc.robot.commands.SimpleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;

public class BADIndexToFlywheelCommand extends CommandBase{
    
  private final Index index;
  private final double power;

  public BADIndexToFlywheelCommand(Index index, double power) {
    this.index = index;
    this.power = power;
    addRequirements(index);
  }

  @Override
  public void execute() {
    //index.indexToShooterPower(power);
  }

  @Override
  public void end(boolean interrupted) {
    //index.indexToShooterPower(0);
  }
    
}
