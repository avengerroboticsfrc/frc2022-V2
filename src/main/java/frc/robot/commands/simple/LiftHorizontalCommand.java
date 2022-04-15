package frc.robot.commands.simple;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Lift;

public class LiftHorizontalCommand extends CommandBase {
    private final Lift liftHorizontal;
    private final double liftHorizontalPower;
    


public LiftHorizontalCommand(Lift liftHorizontal, double liftHorizontalPower) {
    this.liftHorizontal = liftHorizontal;
    this.liftHorizontalPower = liftHorizontalPower;
    addRequirements(liftHorizontal);
  }

  @Override
  public void execute() {
    liftHorizontal.turn(liftHorizontalPower);
  }

  @Override
  public void end(boolean interrupted) {
    liftHorizontal.turn(0);
  }
}

