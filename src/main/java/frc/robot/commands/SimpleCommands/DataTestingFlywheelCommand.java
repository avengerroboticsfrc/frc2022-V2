package frc.robot.commands.SimpleCommands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class DataTestingFlywheelCommand extends CommandBase {
  private final Shooter shooter;
  private final double shooterPower;

/**
 * @param shooter
 * @param shooterRPM
 */
  public DataTestingFlywheelCommand(Shooter shooter, double shooterRPM) {
    this.shooter = shooter;
    this.shooterPower = shooterRPM;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    shooter.setVelocity(shooterPower);
  }

  @Override
  public void end(boolean interrupted) {
    shooter.stopShooter();
  }

  @Override
  public boolean isFinished() {
    return false;
  }

}
