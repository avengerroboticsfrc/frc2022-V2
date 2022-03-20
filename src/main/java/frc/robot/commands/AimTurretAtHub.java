package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class AimTurretAtHub extends CommandBase {

  private final Shooter turret;
  private final Limelight limelight;


  /**
   * command to target the turret.
   */
  public AimTurretAtHub(Shooter turret, Limelight limelight) {
    this.turret = turret;
    this.limelight = limelight;
    addRequirements(turret, limelight);
  }


  @Override
  public void initialize() {
    System.out.println("Enable");
    System.out.println("Limelight ON");
    limelight.enableLights();
  }
  
  

  @Override
  public void execute() {
    turret.turn(limelight.getRotationAdjust());
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
