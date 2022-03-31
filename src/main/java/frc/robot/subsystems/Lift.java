package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.PortConstants;

public class Lift extends SubsystemBase {
  private VictorSPX verticalMotor;
  private VictorSPX liftArmMotor;

  public Lift() {
    super();

    verticalMotor = new VictorSPX(PortConstants.VERTICAL_LIFT_MOTORS);
    liftArmMotor = new VictorSPX(PortConstants.ARM_MOTOR);
  }

  public void vertical(double power) {
    verticalMotor.set(VictorSPXControlMode.PercentOutput, power);
  }

  public void turn(double power) {
    liftArmMotor.set(VictorSPXControlMode.PercentOutput, power);
   }
}
