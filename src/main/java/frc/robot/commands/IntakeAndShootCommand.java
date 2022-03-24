package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class IntakeAndShootCommand extends SequentialCommandGroup {
  public IntakeAndShootCommand(Shooter shooter, Index index, Limelight limelight, Intake intake) {
    addCommands(
        new PickUpBallCommand(intake, index),
        new ShootBallCommand(shooter, index, limelight));
  }
}