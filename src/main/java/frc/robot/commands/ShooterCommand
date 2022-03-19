package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterCommand extends CommandBase {

    private final Shooter shooter;
    private final double shooterPower;

    public ShooterCommand(Shooter shooter, double power){
        this.shooter = shooter;
        shooterPower = power;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
        shooter.spin(shooterPower);

    }

    @Override
    public void end(boolean interrupted) {
        shooter.spin(0);
    }
}
