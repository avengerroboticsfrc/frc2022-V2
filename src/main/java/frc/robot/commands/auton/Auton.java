package frc.robot.commands.auton;

import com.pathplanner.lib.PathPlanner;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.FridayRamseteCommand;
import frc.robot.commands.IntakeAndShootCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.ShootBallCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Index;

import java.util.HashMap;
import java.util.Map;

public class Auton extends SequentialCommandGroup {
  public Auton(DriveTrain drive, Shooter turret, Limelight limelight, Shooter shooter, Intake intake, Index index) {
    Trajectory path = PathPlanner.loadPath("4-Ball", 3, 5);
    // Reset odometry to the starting pose of the trajectory.
    drive.resetOdometry(path.getInitialPose());

    Map<Double, Command> commands = new HashMap<Double,Command>();
    commands.put(1.4980619414234015, new IntakeAndShootCommand(shooter, index, limelight, intake));
    commands.put(3.1415553579132585, new IntakeAndShootCommand(shooter, index, limelight, intake));

    addCommands(
      deadline(new ShootBallCommand(shooter, index, limelight), new RunCommand(() -> drive.tankDriveVolts(0, 0), drive)),
      new FridayRamseteCommand(path, drive, commands),
      deadline(new IntakeAndShootCommand(shooter, index, limelight, intake), new RunCommand(() -> drive.tankDriveVolts(0, 0), drive)),
      new InstantCommand(() -> System.out.println("trajectory over")),
      new RunCommand(() -> drive.tankDriveVolts(0, 0), drive)
    );
  }
}
