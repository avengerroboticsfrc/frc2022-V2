package frc.robot.commands.autons;

import com.pathplanner.lib.PathPlanner;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AimTurretAtHub;
import frc.robot.commands.FridayRamseteCommand;
import frc.robot.commands.ShooterCommand;
import frc.robot.commands.IndexIntake;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class Auton extends SequentialCommandGroup {
  public Auton(DriveTrain drive, Shooter turret, Limelight limelight, Shooter shooter, double power, Intake intake, int power) {
    Trajectory sixBallPath = PathPlanner.loadPath("4-Ball", 3, 5);
    addCommands(
        new AimTurretAtHub(turret, limelight),
        new ShooterCommand(shooter, power),
        new FridayRamseteCommand(sixBallPath, drive), // this will fail since it needs 3 commands.
        new IntakeIndex(intake, power)
    );
  }
}
