package frc.robot;

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
import frc.robot.commands.IndexCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Index;

public class Auton extends SequentialCommandGroup {
  public Auton(DriveTrain drive, Shooter turret, Limelight limelight, Shooter shooter, double power, Intake intake, Index index) {
    Trajectory sixBallPath = PathPlanner.loadPath("4-Ball", 3, 5);
    addCommands(
        new AimTurretAtHub(turret, limelight),
        new ShooterCommand(shooter, power),
        new FridayRamseteCommand(sixBallPath, drive), // this will fail since it needs 3 commands.
        new IndexIntake(intake, power, index)
    );
  }
