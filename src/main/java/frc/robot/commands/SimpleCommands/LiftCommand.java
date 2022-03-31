package frc.robot.commands.SimpleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Lift;

public class LiftCommand extends CommandBase {
  private final Lift lift;
  private final double power;

  
  public LiftCommand(Lift lift, double power, boolean angle) {
    super();

    this.lift = lift;
    this.power = power;

    addRequirements(lift);
  }

  public LiftCommand(Lift lift, double power) {
    this(lift, power, false);
  }

  @Override
  public void execute() {
    lift.vertical(power);
  }

  @Override
  public void end(boolean interrupted) {
    lift.vertical(0);
  }
}
