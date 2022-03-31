package frc.robot.commands.SimpleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;

public class IntakeToIndexCommand extends CommandBase {

  private final Index Inindex;
  private final double power;

  public IntakeToIndexCommand(Index index, double power) {
    this.Inindex = index;
    this.power = power;
    addRequirements(index);
  }

  @Override
  public void execute() {
    Inindex.power(power);
  }

  @Override
  public void end(boolean interrupted) {
    Inindex.power(0);
  }
}
