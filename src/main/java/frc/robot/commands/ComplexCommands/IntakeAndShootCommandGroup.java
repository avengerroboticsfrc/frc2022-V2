package frc.robot.commands.ComplexCommands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.IndexToShooter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.IntakeToIndex;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class IntakeAndShootCommandGroup extends SequentialCommandGroup {
  public IntakeAndShootCommandGroup(Shooter shooter, Index index, Limelight limelight, Intake intake, IntakeToIndex intakeToIndex, IndexToShooter intakeToShooter, double shooterPower, double indexPower, double intakeToIndexPower, double intakePower) {
    addCommands(
      new ParallelDeadlineGroup(new WaitCommand(5), new PickUpBallCommandGroup(intake, intakeToIndex, index, intakePower, intakeToIndexPower, indexPower),
      new ShootBallCommandGroup(shooter, index, intakeToShooter, limelight, shooterPower, indexPower, intakePower)));
     
  }
}