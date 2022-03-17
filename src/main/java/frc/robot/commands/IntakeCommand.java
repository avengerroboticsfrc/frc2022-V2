package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {

  private final Intake intake;
  private static int currentPower;

  public IntakeCommand(Intake intake, int power) {
    this.intake = intake;
    currentPower = power;
    addRequirements(intake);
  }

  @Override
  public void execute() {
    intake.power(currentPower);
  }

  @Override
  public void end(boolean interrupted) {
    intake.power(0);
  }
}
