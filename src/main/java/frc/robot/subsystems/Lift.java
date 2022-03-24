package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.PortConstants;

public class Lift extends SubsystemBase {
  private VictorSPX verticalMotor;
  private CANSparkMax angularMotor;

  public Lift() {
    super();

    verticalMotor = new VictorSPX(PortConstants.VERTICAL_LIFT_MOTORS);
    angularMotor = new CANSparkMax(PortConstants.ARM_MOTOR, MotorType.kBrushless);
  }

  public void vertical(double power) {
    verticalMotor.set(VictorSPXControlMode.PercentOutput, power);
  }

  public void turn(double power) {
    angularMotor.set(power);
  }
}
