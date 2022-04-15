package frc.robot.commands.SimpleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

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
    // int rpm = 5000;
    // System.out.println("Distance    RPM");
    // System.out.println(limelight.getDistance() + " " + rpm);
    // shooter.setRPM(rpm);

    // FALLBACK CODE!!!
    shooter.spin(.75);
  }

  @Override
  public void end(boolean interrupted) {
    shooter.spin(0);
    //shooter.setRPM(0);
    limelight.disableLights();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
