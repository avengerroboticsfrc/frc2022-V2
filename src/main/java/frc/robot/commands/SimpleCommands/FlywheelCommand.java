package frc.robot.commands.SimpleCommands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import java.lang.Math.*;
import java.util.Arrays;
import java.util.HashMap;

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
    double distance = limelight.getDistance();
   
    double velocity = (6990 + -62.5 * distance) + (0.203 * Math.pow(distance, 2));
    shooter.setVelocity(velocity);
    System.out.println(velocity);

    // FALLBACK CODE!!!
    //shooter.spin(.75);
  }

  @Override
  public void end(boolean interrupted) {
    shooter.spin(0);
    // shooter.setRPM(0);
    limelight.disableLights();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
