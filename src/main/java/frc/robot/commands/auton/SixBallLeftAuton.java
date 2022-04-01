package frc.robot.commands.auton;

import com.pathplanner.lib.PathPlanner;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.ComplexCommands.FridayRamseteCommand;
import frc.robot.commands.ComplexCommands.IntakeAndShootCommandGroup;
import frc.robot.commands.ComplexCommands.PickUpBallCommandGroup;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Index;

import java.util.HashMap;
import java.util.Map;

public class SixBallLeftAuton extends SequentialCommandGroup {
  public SixBallLeftAuton(DriveTrain drive, Limelight limelight, Shooter shooter, Intake intake, Index index, double intakePower, double intakeToIndexPower, double indexPower, double indexToShooterPower, double shooterPower) {
    Trajectory path = PathPlanner.loadPath("6-Ball-Blue", 3, 5);
    // Reset odometry to the starting pose of the trajectory.
    drive.resetOdometry(path.getInitialPose());

    Map<Double, Command> commands = new HashMap<Double,Command>();
    commands.put(1.3496353308099465, new PickUpBallCommandGroup(intake, index, shooterPower, shooterPower, shooterPower));
    commands.put(4.2078244623106436, new IntakeAndShootCommandGroup(shooter, index, limelight, intake, shooterPower, shooterPower, shooterPower));
    commands.put(7.297797460834611, new PickUpBallCommandGroup(intake, index, shooterPower, shooterPower, shooterPower));
    addCommands(
      new FridayRamseteCommand(path, drive, commands),
      deadline(new IntakeAndShootCommandGroup(shooter, index, limelight, intake, shooterPower, shooterPower, shooterPower), new RunCommand(() -> drive.tankDriveVolts(0, 0), drive)),
      new InstantCommand(() -> System.out.println("trajectory over")),
      new RunCommand(() -> drive.tankDriveVolts(0, 0), drive)
    );
  }
}
