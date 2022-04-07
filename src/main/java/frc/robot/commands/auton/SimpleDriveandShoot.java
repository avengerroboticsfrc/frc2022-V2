// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ComplexCommands.ShootBallCommandGroup;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.IndexToShooter;
import frc.robot.subsystems.IntakeToIndex;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SimpleDriveandShoot extends SequentialCommandGroup {
  /** Creates a new SimpleDriveandShoot. */
  public SimpleDriveandShoot(DriveTrain drive, Shooter shooter, Index index, Limelight limelight, IntakeToIndex intakeToIndex, IndexToShooter indexToShooter, double indexPower, double indexToShooterPower) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new RunCommand(() -> drive.tankDrive(.5, .5), drive).withTimeout(1.5),
      new WaitCommand(1),
      new ShootBallCommandGroup(shooter, index, indexToShooter, limelight, indexPower, indexToShooterPower)
    );
  }
}
