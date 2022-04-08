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

  private static final HashMap<Integer, Double> dataTable = new HashMap<Integer, Double>() {{
    put(Integer.valueOf(120), Double.valueOf(0.5));
    put(Integer.valueOf(125), Double.valueOf(0.52));
    put(Integer.valueOf(132), Double.valueOf(0.57));
    put(Integer.valueOf(138), Double.valueOf(0.57));
    put(Integer.valueOf(144), Double.valueOf(0.6));
    put(Integer.valueOf(150), Double.valueOf(0.6));
    put(Integer.valueOf(156), Double.valueOf(0.65));
    put(Integer.valueOf(162), Double.valueOf(0.65));
    put(Integer.valueOf(168), Double.valueOf(0.7));
    put(Integer.valueOf(174), Double.valueOf(0.7));
    put(Integer.valueOf(180), Double.valueOf(0.72));
    put(Integer.valueOf(186), Double.valueOf(0.9));
    put(Integer.valueOf(192), Double.valueOf(0.9));
    put(Integer.valueOf(204), Double.valueOf(0.95));
  }};

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
    // If it doesn't work how it's supposed to, 
    // comment out the enhanced for loop
    // for (Integer i : dataTable.keySet()) {
    //   if (i.intValue() == (int)limelight.getDistance() || 
    //       i.intValue() + 1 == (int)limelight.getDistance() ||   
    //       i.intValue() - 1 == (int)limelight.getDistance()) {
    //     keyValue = i;
    //   }
    // }

    // if (keyValue.intValue() != -1) {
    //   shooter.spin(dataTable.get(keyValue).doubleValue());
    // } else {
    //   shooter.setRPM(stupidEquationThatWeCameUpWith);
    // }

    // RPM RANGES FROM 0 <---> 6380 !!!
    shooter.setRPM(6380);
    // FALLBACK CODE!!!
    shooter.spin(.75);
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
