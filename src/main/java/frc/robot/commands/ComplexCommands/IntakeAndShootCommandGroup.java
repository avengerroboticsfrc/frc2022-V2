package frc.robot.commands.ComplexCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class IntakeAndShootCommandGroup extends SequentialCommandGroup {
  public IntakeAndShootCommandGroup(Shooter shooter, Index index, Limelight limelight, Intake intake, double shooterPower, double indexPower, double intakeToIndexPower) {
    addCommands(
        new PickUpBallCommandGroup(intake, index, intakeToIndexPower, intakeToIndexPower, intakeToIndexPower),
        new ShootBallCommandGroup(shooter, index, limelight, shooterPower, indexPower, intakeToIndexPower));
     
  }
}