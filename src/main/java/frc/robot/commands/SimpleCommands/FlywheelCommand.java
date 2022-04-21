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

<<<<<<< HEAD
  private static final HashMap<Integer, Double> dataTable = new HashMap<Integer, Double>() {{
    put(Integer.valueOf(62), Double.valueOf(2500));
    put(Integer.valueOf(82), Double.valueOf(4000)));
    put(Integer.valueOf(104), Double.valueOf(4500));
  }};

=======
>>>>>>> Houston
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
<<<<<<< HEAD
    Integer keyValue = Integer.valueOf(-1);
    // If it doesn't work how it's supposed to, 
    // comment out the enhanced for loop
    for (Integer i : dataTable.keySet()) {
      if (i.intValue() == (int)limelight.getDistance() || 
          i.intValue() + 2 == (int)limelight.getDistance() ||   
          i.intValue() - 2 == (int)limelight.getDistance()) {
        keyValue = i;
      }
    }

    if (keyValue.intValue() != -1) {
      shooter.spin(dataTable.get(keyValue).doubleValue());
    } else {
      shooter.setVelocity(4849.002 + (-1537478000 - 1537478000 - 4849.002)/(1 + Math.pow((limelight.getDistance()/1.336557),3.67092)));
    }

    // shooter.spin(.75);
=======
    double distance = limelight.getDistance();
   
    double velocity = (6990 + -62.5 * distance) + (0.203 * Math.pow(distance, 2));
    shooter.setVelocity(velocity);
    System.out.println(velocity);

    // FALLBACK CODE!!!
    //shooter.spin(.75);
>>>>>>> Houston
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
