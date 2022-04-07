package frc.robot.commands.auton;

import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;

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
                new RunCommand(() -> drive.tankDrive(0.5, 0.5), drive).withTimeout(1.5),
                parallel(new RunCommand(() -> drive.tankDrive(0.5, 0.5), drive).withTimeout(1)
                    .andThen(() -> drive.tankDrive(0, 0)),
                    new PickUpBallCommandGroup(intake, intakeToIndex, index, 1,
                        0.5,
                        0.5))
                    .withTimeout(2),
                new RunCommand(() -> drive.tankDrive(0.5, -0.5), drive).withTimeout(1.6),
                parallel(new ShootBallCommandGroup(shooter, index, indexToShooter, limelight,
                     0.5,
                    0.5).
                    andThen(() -> drive.tankDrive(0, 0)),
                new RunCommand(() -> drive.tankDrive(-0.5, 0.5)).withTimeout(1)
                .andThen(() -> drive.tankDrive(0, 0)),
                        new RunCommand(() -> drive.tankDrive(0.5, 0.5)).withTimeout(2)
                        .andThen(() -> drive.tankDrive(0, 0)),
                        new PickUpBallCommandGroup(intake, intakeToIndex, index, 1, 
                            0.5, 
                            0.5))
                        .withTimeout(2),
                new RunCommand(() -> drive.tankDrive(-0.5, 0.5)).withTimeout(1.1),
                parallel(new ShootBallCommandGroup(shooter, index, indexToShooter, limelight, 0.5, 0.5)),
                        new RunCommand(() -> drive.tankDrive(0, 0), drive).withTimeout(8));
    }

}
