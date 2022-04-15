package frc.robot.commands.SimpleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class DataTestingFlywheelCommand extends CommandBase {
  private final Shooter shooter;
  private final double rpm;

  /**
   * runs the shooter at an RPM.
   * 
   * @param shooter the shooter
   * @param shooterRpm the rpm
   */
  public DataTestingFlywheelCommand(Shooter shooter, double shooterRpm) {
    this.shooter = shooter;
    this.rpm = shooterRpm;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    shooter.setVelocity(rpm);
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
