package frc.robot.commands.SimpleCommands;

import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.CANSparkMax.ControlType;

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
    limelight.enableLights();
  }

  @Override
  public void execute() {
    shooter.turn(limelight.getRotationAdjust());
  }

  @Override
  public void end(boolean interrupted) {
    System.out.println("Limelight OFF");
    limelight.disableLights();
    shooter.turretTurnMotor.stopMotor();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
