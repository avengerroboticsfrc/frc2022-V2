package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ComplexCommands.PickUpBallCommandGroup;
import frc.robot.commands.ComplexCommands.ShootBallCommandGroup;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.IndexToShooter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.IntakeToIndex;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class ThreeBallTimeBasedAuton extends SequentialCommandGroup {
    public ThreeBallTimeBasedAuton(DriveTrain drive, Intake intake, Index index, IntakeToIndex intakeToIndex,
            Shooter shooter, IndexToShooter indexToShooter, double intakePower, double indexPower,
            double intakeToIndexPower, double indexToShooterPower, Limelight limelight) {
        addCommands(
                new ShootBallCommandGroup(shooter, index, indexToShooter, limelight, indexPower,
                        indexToShooterPower).withTimeout(3),
                new RunCommand(() -> drive.tankDrive(0.5, -0.5), drive).withTimeout(0.5),
                new RunCommand(() -> drive.tankDrive(-0.5, -0.5), drive).withTimeout(1.2),
                new WaitCommand(1),
                new RunCommand(() -> drive.tankDrive(0, 0), drive),
                new PickUpBallCommandGroup(intake, intakeToIndex, index, intakePower, intakeToIndexPower, indexPower),
                new RunCommand(() -> drive.tankDrive(0.5, 0.5), drive).withTimeout(0.5),
                new WaitCommand(1),
                new RunCommand(() -> drive.tankDrive(0, 0), drive),
                new RunCommand(() -> drive.tankDrive(-0.5, 0.5), drive).withTimeout(0.15),
                new WaitCommand(1),
                new RunCommand(() -> drive.tankDrive(0, 0), drive),
                new RunCommand(() -> drive.tankDrive(-0.5, -0.5), drive).withTimeout(1.6),
                new PickUpBallCommandGroup(intake, intakeToIndex, index, intakePower, intakeToIndexPower, indexPower),
                new WaitCommand(1),
                new RunCommand(() -> drive.tankDrive(0, 0), drive),
                new RunCommand(() -> drive.tankDrive(-0.5, 0.5), drive).withTimeout(.75),
                new ShootBallCommandGroup(shooter, index, indexToShooter, limelight, indexPower,
                        indexToShooterPower).withTimeout(3));

    }

}
