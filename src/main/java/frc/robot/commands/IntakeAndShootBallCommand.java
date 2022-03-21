package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class IntakeAndShootBallCommand extends SequentialCommandGroup {

  public IntakeAndShootBallCommand(Shooter shooter, Index index, Limelight limelight, Intake intake, double power) {
    addCommands(
      new PickUpBallCommand(intake, index, power),
      new ShootBallCommand(shooter, index, limelight)
      );
  }
}