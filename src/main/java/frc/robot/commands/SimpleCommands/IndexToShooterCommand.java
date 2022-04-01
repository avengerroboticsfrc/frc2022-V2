package frc.robot.commands.SimpleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;

public class IndexToShooterCommand extends CommandBase {

  private final Index index;
  private final double power;

  public IndexToShooterCommand(Index index, double power) {
    this.index = index;
    this.power = power;
    addRequirements(index);
  }

  @Override
  public void execute() {
    index.IndexToShooterPower(power);
  }

  @Override
  public void end(boolean interrupted) {
    index.IndexToShooterPower(0);
  }
}
