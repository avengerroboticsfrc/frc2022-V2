package frc.robot.commands.SimpleCommands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;

public class FlywheelCommand extends CommandBase {

  private final Shooter shooter;
  private final Limelight limelight;

  public FlywheelCommand(Shooter shooter, Limelight limelight) {
    this.shooter = shooter;
    this.limelight = limelight;
    addRequirements(shooter, limelight);
  }

  @Override
  public void initialize() {
    limelight.enableLights();
  }

  @Override
  public void execute() {
    //TODO: MATH FOR FLYWHEEL SPEED
    shooter.spin(limelight.getDistance());
  }

  @Override
  public void end(boolean interrupted) {
    shooter.spin(0);
    limelight.disableLights();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
