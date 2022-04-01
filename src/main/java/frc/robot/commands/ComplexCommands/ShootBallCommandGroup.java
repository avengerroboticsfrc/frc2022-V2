package frc.robot.commands.ComplexCommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.SimpleCommands.FlywheelCommand;
import frc.robot.commands.SimpleCommands.IndexCommand;
import frc.robot.commands.SimpleCommands.IntakeToIndexCommand;
import frc.robot.commands.SimpleCommands.TargetHubCommand;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class ShootBallCommandGroup extends SequentialCommandGroup {

  public ShootBallCommandGroup(Shooter shooter, Index index, Limelight limelight, double shooterPower, double indexPower, double intakeToIndexPower ) {
    addCommands(
      deadline(new WaitCommand(2), new TargetHubCommand(shooter, limelight)),
      new WaitCommand(1),
      deadline(
        new WaitCommand(5), 
        sequence(new FlywheelCommand(shooter, intakeToIndexPower), new WaitCommand(2), new ParallelCommandGroup(
          new IntakeToIndexCommand(index, intakeToIndexPower),
          new IndexCommand(index, indexPower),
          new IndextoFlywheelCommandGroup(index, indexPower, intakeToIndexPower, intakeToIndexPower)
          )
        )
      )    
    );
  }
}
