package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.PortConstants;

public class Shooter extends SubsystemBase {
  private final TalonFX flywheelMotor;
  public final TalonSRX turretTurnMotor;
  private final Servo hood;

  // TODO: find this value
  private static final double HOOD_ACTUATOR_LENGTH_CM = 14;

  /**
   * create a new shooter class.
   * init the motors, configure them.
   */
  public Shooter() {
    super();
    flywheelMotor = new TalonFX(PortConstants.FLYWHEEL_MOTOR);
    turretTurnMotor = new TalonSRX(PortConstants.TURRET_TURN_MOTOR);
    hood = new Servo(PortConstants.HOOD_SERVO);

    hood.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
  }

  /**
   * turn the shooter by a certain number of counts.
   */
  public void turn(double counts) {
    turretTurnMotor.set(TalonSRXControlMode.Position, counts);
  }

  /**
   * Run the flywheel at a power.
   */
  public void spin(double power) {
    flywheelMotor.set(TalonFXControlMode.PercentOutput, power);
  }

  /**
   * Set the hood's position in inches.
   */
  public void extendHood(double inches) {
    hood.set(inches / HOOD_ACTUATOR_LENGTH_CM);
  }
}
