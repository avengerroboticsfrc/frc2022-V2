package frc.robot.commands.SimpleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class DataTestingFlywheelCommand extends CommandBase {
  private final Shooter shooter;
  private final double rpm;

  public DataTestingFlywheelCommand(Shooter shooter, double shooterPower) {
    this.shooter = shooter;
    this.rpm = shooterPower;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    shooter.spin(rpm);
  }

  @Override
  public void end(boolean interrupted) {
    shooter.spin(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

}
