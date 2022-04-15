package frc.robot.commands.simple;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class MoveToTargetCommand extends CommandBase {
  private final Limelight limelight;
  private final DriveTrain drive;
  private final Shooter shooter;

  // holds values for different preset distance values for shooting.
  private static final Map<Double, Double[]> distanceValues = new HashMap<Double, Double[]>();

  /**
   * This command will turn the body of the robot AND move forward/back to the CLOSEST point where
   * we have a measurement of an angle and power to successfully score in the upper hub.
   */
  public MoveToTargetCommand(DriveTrain drive, Limelight limelight, Shooter shooter) {
    super();

    this.drive = drive;
    this.limelight = limelight;
    this.shooter = shooter;

    // add distances from the hub + power/angle that we found to work on the
    // turret
    distanceValues.put(2.13, new Double[] {0.0, 0.73});
  }

  @Override
  public void execute() {
    double distanceM = limelight.getDistance();

    // find the closest distance in our map to the distance from the hub.
    Double[] distances = (Double[]) distanceValues.keySet().toArray();
    Arrays.sort(distances,
        // sorts for the lowest difference between the points
        (a, b) -> (int) (10 * (Math.abs(a - distanceM) - Math.abs(b - distanceM))));

    double targetDistance = distances[0];

    if (targetDistance - distanceM > 0.1) {
      drive.arcadeDrive(-0.3, limelight.getRotationAdjust());
    } else if (targetDistance - distanceM < -0.1) {
      drive.arcadeDrive(0.3, limelight.getRotationAdjust());
    }

    shooter.spin(distanceValues.get(targetDistance)[1]);
    shooter.extendHood(distanceValues.get(targetDistance)[0]);
  }
}
