package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class PneumaticsCommand extends CommandBase {

  private final Intake intake;

  public PneumaticsCommand(Intake Intakepneumatics) {
    intake = Intakepneumatics;
    addRequirements(Intakepneumatics);
  }

  @Override
  public void initialize() {
    intake.toggle();

  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    intake.toggle();
  }
}