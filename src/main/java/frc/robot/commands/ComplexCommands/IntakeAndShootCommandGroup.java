package frc.robot.commands.ComplexCommands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.SimpleCommands.IndexCommand;
import frc.robot.commands.SimpleCommands.IntakeCommand;
import frc.robot.commands.SimpleCommands.IntakeToIndexCommand;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.IndexToShooter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.IntakeToIndex;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class IntakeAndShootCommandGroup extends SequentialCommandGroup {
  public IntakeAndShootCommandGroup(Shooter shooter, Index index, Limelight limelight, Intake intake,
      IntakeToIndex intakeToIndex, IndexToShooter indexToShooter, double indexPower,
      double intakeToIndexPower, double intakePower, double indexToShooterPower) {
    addCommands(
        new IntakeCommand(intake, intakePower).withTimeout(2),
        new ParallelDeadlineGroup(
            new WaitCommand(4),
            new IntakeToIndexCommand(intakeToIndex, intakeToIndexPower),
            new IndexCommand(index, indexPower),
            new ShootBallCommandGroup(shooter, index, indexToShooter, limelight, indexPower, indexToShooterPower)),
        deadlineWith(
            new IntakeCommand(intake, intakePower),
            new IntakeToIndexCommand(intakeToIndex, intakeToIndexPower),
            new IndexCommand(index, indexPower),
            new ShootBallCommandGroup(shooter, index, indexToShooter, limelight, indexPower, indexToShooterPower))
    );

  }
}