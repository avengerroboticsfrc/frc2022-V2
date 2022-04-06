package frc.robot.commands.SimpleCommands;

import java.lang.annotation.Target;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Targeting;

public class TargetHubCommand extends CommandBase {

  private final Shooter shooter;
  private final Limelight limelight;


  /**
   * command to target the turret.
   */
  public TargetHubCommand(Shooter shooter, Limelight limelight, Targeting targeting) {
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
    targeting.getRightPreset();
    //TODO: HOOD ADJUST
    //shooter.extendHood(limelight.getDistance()*.05);
  }

  @Override
  public void end(boolean interrupted) {
    System.out.println("Limelight OFF");
    limelight.disableLights();
    shooter.turn(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
