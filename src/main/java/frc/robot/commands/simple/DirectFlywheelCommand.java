package frc.robot.commands.simple;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class DirectFlywheelCommand extends CommandBase {
  private final Shooter shooter;
  private final double shooterPower;

  public DirectFlywheelCommand(Shooter shooter, double shooterRPM) {
    this.shooter = shooter;
    this.shooterPower = shooterRPM;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    shooter.spin(shooterPower);
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
