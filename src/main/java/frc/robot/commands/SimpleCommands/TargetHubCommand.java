package frc.robot.commands.SimpleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class TargetHubCommand extends CommandBase {

  private final Shooter shooter;
  private final Limelight limelight;


  /**
   * command to target the turret.
   */
  public TargetHubCommand(Shooter shooter, Limelight limelight) {
    this.shooter = shooter;
    this.limelight = limelight;
    addRequirements(shooter, limelight);
  }


  @Override
  public void initialize() {
    System.out.println("Enable");
    System.out.println("Limelight ON");
    limelight.enableLights();
  }

  @Override
  public void execute() {
    shooter.turn(limelight.getRotationAdjust());
    shooter.extendHood(limelight.getDistance()*.05);
  }

  @Override
  public void end(boolean interrupted) {
    System.out.println("Limelight OFF");
    limelight.disableLights();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
