package frc.robot.commands.SimpleCommands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class DataTestingFlywheelCommand extends CommandBase {
  private final Shooter shooter;
  private final double rpm;

<<<<<<< HEAD
  public DataTestingFlywheelCommand(Shooter shooter, double shooterPower) {
=======
/**
 * @param shooter
 * @param shooterRPM
 */
  public DataTestingFlywheelCommand(Shooter shooter, double shooterRPM) {
>>>>>>> f6f095b776da3317d39270e09e7d0e11d02dcbe5
    this.shooter = shooter;
    this.rpm = shooterPower;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
<<<<<<< HEAD
    shooter.spin(rpm);
=======
    shooter.setVelocity(shooterPower);
>>>>>>> f6f095b776da3317d39270e09e7d0e11d02dcbe5
  }

  @Override
  public void end(boolean interrupted) {
<<<<<<< HEAD
    shooter.spin(0);
=======
    shooter.setVelocity(0);
>>>>>>> f6f095b776da3317d39270e09e7d0e11d02dcbe5
  }

  @Override
  public boolean isFinished() {
    return false;
  }

}
