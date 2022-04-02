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
import frc.robot.subsystems.IntakeToIndex;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.IndexToShooter;

import java.util.HashMap;
import java.util.Map;

public class SixBallRightAuton extends SequentialCommandGroup {
  public SixBallRightAuton(DriveTrain drive, Limelight limelight, Shooter shooter, Intake intake, Index index, IntakeToIndex inIndex, IndexToShooter inShooter, double intakePower, double intakeToIndexPower, double indexPower, double indexToShooterPower, double shooterPower) {
    Trajectory path = PathPlanner.loadPath("6-Ball-Red", 3, 5);
    // Reset odometry to the starting pose of the trajectory.
    drive.resetOdometry(path.getInitialPose());

    Map<Double, Command> commands = new HashMap<Double,Command>();
    commands.put(1.1304040796428534, new PickUpBallCommandGroup(intake, inIndex, index, intakePower, intakeToIndexPower, indexPower).withTimeout(2));
    commands.put(3.0185091255112075, new IntakeAndShootCommandGroup(shooter, index, limelight, intake, inIndex, inShooter, shooterPower, shooterPower, shooterPower, shooterPower, shooterPower).withTimeout(4));
    commands.put(5.030751908041635, new PickUpBallCommandGroup(intake, inIndex, index, intakePower, intakeToIndexPower, indexPower).withTimeout(2));
    addCommands(
      new FridayRamseteCommand(path, drive, commands),
      deadline(new IntakeAndShootCommandGroup(shooter, index, limelight, intake, inIndex, inShooter, shooterPower, shooterPower, shooterPower, shooterPower, shooterPower).withTimeout(4), new RunCommand(() -> drive.tankDriveVolts(0, 0), drive)),
      new InstantCommand(() -> System.out.println("trajectory over")),
      new RunCommand(() -> drive.tankDriveVolts(0, 0), drive)
    );
  }
}
