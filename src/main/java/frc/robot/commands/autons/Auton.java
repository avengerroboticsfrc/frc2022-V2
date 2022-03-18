package frc.robot.commands.autons;

import com.pathplanner.lib.PathPlanner;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.FridayRamseteCommand;
import frc.robot.subsystems.DriveTrain;

public class Auton extends SequentialCommandGroup {
  public Auton(DriveTrain drive) {
    Trajectory sixBallPath = PathPlanner.loadPath("4-Ball", 3, 5);
    addCommands(
        new FridayRamseteCommand(sixBallPath, drive) // this will fail since it needs 3 commands.
    );
  }
}
