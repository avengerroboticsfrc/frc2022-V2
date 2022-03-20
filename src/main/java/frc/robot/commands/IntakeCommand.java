package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {

  private final Intake intake;
  private final double power;

  public IntakeCommand(Intake intake, double power) {
    this.intake = intake;
    this.power = power;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    intake.power(power);
  }

  @Override
  public void end(boolean interrupted) {
    intake.power(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
