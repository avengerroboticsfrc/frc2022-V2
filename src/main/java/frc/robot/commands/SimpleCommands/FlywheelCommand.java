package frc.robot.commands.SimpleCommands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import java.util.HashMap;

public class FlywheelCommand extends CommandBase {

  private final Shooter shooter;
  private final Limelight limelight;

  private static final HashMap<Integer, Double> dataTable = new HashMap<Integer, Double>() {
    {
      put(Integer.valueOf(62), Double.valueOf(2500));
      put(Integer.valueOf(82), Double.valueOf(4000));
      put(Integer.valueOf(104), Double.valueOf(4500));
    }
  };

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
    // Integer keyValue = Integer.valueOf(-1);
    // // If it doesn't work how it's supposed to,
    // // comment out the enhanced for loop
    // for (Integer i : dataTable.keySet()) {
    //   if (i.intValue() == (int) limelight.getDistance() ||
    //       i.intValue() + 2 == (int) limelight.getDistance() ||
    //       i.intValue() - 2 == (int) limelight.getDistance()) {
    //     keyValue = i;
    //   }
    // }

    // if (keyValue.intValue() != -1) {
    //   shooter.setVelocity(dataTable.get(keyValue).doubleValue());
    // } else {
    //   shooter.setVelocity(4849.002
    //       + (- 1537478000 - 1537478000 - 4849.002) / (1 + Math.pow((limelight.getDistance() / 1.336557), 3.67092)));
      
    // }

    shooter.spin(.75);
  }

  @Override
  public void end(boolean interrupted) {
    shooter.stopShooter();
    // shooter.setRPM(0);
    limelight.disableLights();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
