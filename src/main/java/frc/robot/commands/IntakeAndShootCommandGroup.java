package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class IntakeAndShootCommandGroup extends SequentialCommandGroup {
  public IntakeAndShootCommandGroup(Shooter shooter, Index index, Limelight limelight, Intake intake) {
    addCommands(
        new PickUpBallCommandGroup(intake, index),
        new ShootBallCommandGroup(shooter, index, limelight));
  }
}