package frc.robot.commands.auton.FiveBallAuton;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ComplexCommands.PickUpBallCommandGroup;
import frc.robot.commands.ComplexCommands.ShootBallCommandGroup;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.IndexToShooter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.IntakeToIndex;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class FiveBallAutonBlue extends SequentialCommandGroup {

    public FiveBallAutonBlue(DriveTrain drive, Limelight limelight, Shooter shooter, Intake intake, Index index, IntakeToIndex inIndex, 
                            IndexToShooter inShooter, double intakePower, double intakeToIndexPower, double indexPower, 
                            double indexToShooterPower, double shooterPower) {
        addCommands(
            new SixBAutonTrajectory1(),
            new PickUpBallCommandGroup(intake, inIndex, index, intakePower, intakeToIndexPower, indexPower),
            new SixbAutonTrajectory2(),
            new PickUpBallCommandGroup(intake, inIndex, index, intakePower, intakeToIndexPower, indexPower),
            new SixBAutoTrajectory3(),
            new ShootBallCommandGroup(shooter, index, inShooter, limelight, indexPower, indexToShooterPower),
            new SixBAutoTrajectory4(),
            new ShootBallCommandGroup(shooter, index, inShooter, limelight, indexPower, indexToShooterPower),
            new SixBAutoTrajectory5(),
            new ShootBallCommandGroup(shooter, index, inShooter, limelight, indexPower, indexToShooterPower),
            new InstantCommand(() -> System.out.println("trajectory over")),
            new RunCommand(() -> drive.tankDriveVolts(0, 0), drive)
        );
    }
}
