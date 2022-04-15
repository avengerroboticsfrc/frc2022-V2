package frc.robot.commands.simple;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;

public class IndexCommand extends CommandBase {

  private final Index index;
  private final double power;

  public IndexCommand(Index index, double power) {
    this.index = index;
    this.power = power;
    addRequirements(index);
  }

  @Override
  public void execute() {
    index.power(power);
  }

  @Override
  public void end(boolean interrupted) {
    index.power(0);
  }
}
