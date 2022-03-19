package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;

public class IndexCommand extends CommandBase {

  private final Index index;
  private static double currentPower;

  public IndexCommand(Index index, double power) {
    this.index = index;
    currentPower = power;
    addRequirements(index);
  }

  @Override
  public void execute() {
    index.power(currentPower);
  }

  @Override
  public void end(boolean interrupted) {
    index.power(0);
  }
}
