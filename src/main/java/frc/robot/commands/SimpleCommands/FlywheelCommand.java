package frc.robot.commands.SimpleCommands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import java.util.HashMap;

public class FlywheelCommand extends CommandBase {

  private final Shooter shooter;
  private final Limelight limelight;

  private static final HashMap<Integer, Double> dataTable = new HashMap<Integer, Double>() {{
    put(Integer.valueOf(104), Double.valueOf(0.5));
    put(Integer.valueOf(108), Double.valueOf(0.5));
    put(Integer.valueOf(113), Double.valueOf(0.52));
    put(Integer.valueOf(120), Double.valueOf(0.57));
    put(Integer.valueOf(125), Double.valueOf(0.57));
    put(Integer.valueOf(132), Double.valueOf(0.6));
    put(Integer.valueOf(138), Double.valueOf(0.6));
    put(Integer.valueOf(144), Double.valueOf(0.65));
    put(Integer.valueOf(150), Double.valueOf(0.65));
    put(Integer.valueOf(156), Double.valueOf(0.7));
    put(Integer.valueOf(162), Double.valueOf(0.7));
    put(Integer.valueOf(168), Double.valueOf(0.72));
    put(Integer.valueOf(174), Double.valueOf(0.9));
    put(Integer.valueOf(180), Double.valueOf(0.9));
    put(Integer.valueOf(186), Double.valueOf(0.95));
    put(Integer.valueOf(192), Double.valueOf(0.95));
    put(Integer.valueOf(204), Double.valueOf(1));
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
    Integer keyValue = Integer.valueOf(-1);

    // If it doesn't work how it's supposed to, 
    // comment out the enhanced for loop
    for (Integer i : dataTable.keySet()) {
      if (i.intValue() == (int)limelight.getDistance() || 
          i.intValue() + 1 == (int)limelight.getDistance() || 
          i.intValue() - 1 == (int)limelight.getDistance()) {
        keyValue = i;
      }
    }

    if (keyValue.intValue() != -1) {
      shooter.spin(dataTable.get(keyValue).doubleValue());
    } else {
      shooter.spin(1.168144 + (0.5089078 - 1.168144) / (1 + Math.pow((limelight.getDistance()/174.4239), 7.906148)));
    }
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
