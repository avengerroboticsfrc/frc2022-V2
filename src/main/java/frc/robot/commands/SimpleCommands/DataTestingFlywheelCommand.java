package frc.robot.commands.SimpleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class DataTestingFlywheelCommand extends CommandBase {
  private final Shooter shooter;
  // private final double rpm;

  /**
   * @param shooter
   * @param shooterRPM
   */
  public DataTestingFlywheelCommand(Shooter shooter, double shooterRPM) {
    this.shooter = shooter;
    // this.rpm = shooterRPM;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    shooter.spin(.75);
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
