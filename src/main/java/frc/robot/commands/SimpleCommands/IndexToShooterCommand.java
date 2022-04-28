package frc.robot.commands.SimpleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexToShooter;

public class IndexToShooterCommand extends CommandBase {

  private final double power;
  private final IndexToShooter inShooter;

  public IndexToShooterCommand(IndexToShooter inShooter, double power) {
    this.power = power;
    this.inShooter = inShooter;
    addRequirements(inShooter);
  }

  @Override
  public void execute() {
    inShooter.power(.7);
  }

  @Override
  public void end(boolean interrupted) {
    inShooter.power(0);
  }
}
