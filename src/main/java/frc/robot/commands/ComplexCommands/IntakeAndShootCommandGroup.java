package frc.robot.commands.ComplexCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.IndexToShooter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.IntakeToIndex;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class IntakeAndShootCommandGroup extends SequentialCommandGroup {
  public IntakeAndShootCommandGroup(Shooter shooter, Index index, Limelight limelight, Intake intake, IntakeToIndex inIndex, IndexToShooter inShooter, double shooterPower, double indexPower, double intakeToIndexPower, double intakePower) {
    addCommands(
      new PickUpBallCommandGroup(intake, inIndex, index, intakePower, intakeToIndexPower, indexPower),
        new ShootBallCommandGroup(shooter, index, inIndex, inShooter, limelight, shooterPower, indexPower, intakeToIndexPower, intakePower));
     
  }
}