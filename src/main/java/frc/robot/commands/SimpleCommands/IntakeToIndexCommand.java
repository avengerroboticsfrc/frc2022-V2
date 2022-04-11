package frc.robot.commands.SimpleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeToIndex;

public class IntakeToIndexCommand extends CommandBase  {
  private double power;
  private IntakeToIndex inIndex;

  public IntakeToIndexCommand(IntakeToIndex inIndex, double power) {
    this.inIndex = inIndex;
    this.power = power;
    addRequirements(inIndex);
  }

@Override
  public void initialize() {
  }

  @Override
  public void execute() {
    inIndex.power(power);
  }

  @Override
  public void end(boolean interrupted) {
    inIndex.power(0);
  }


}
