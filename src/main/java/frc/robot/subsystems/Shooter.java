package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.PortConstants;

public class Shooter extends SubsystemBase {
  private final TalonFX flywheelMotor;
  public final TalonSRX turretTurnMotor;
  private final CANSparkMax hoodMotor;

  private final RelativeEncoder hoodEncoder;

  // this variable stores the total rotations of the motor to reach the target position
  private static double targetPosition = 0;

  // TODO: find this value
  private static final double ROTATIONS_PER_HOOD_DEGREE = 0;

  /**
   * init the motors, configure them, 
   */
  public Shooter() {
    flywheelMotor = new TalonFX(PortConstants.FLYWHEEL_MOTOR);
    turretTurnMotor = new TalonSRX(PortConstants.TURRET_TURN_MOTOR);
    hoodMotor = new CANSparkMax(PortConstants.HOOD_MOTOR, MotorType.kBrushless);

    hoodEncoder = hoodMotor.getEncoder();
  }

  @Override
  public void periodic() {

    // set the hood power based on the current position and target
    // change only if its over half a rotation off
    double power = 0;
    if (getHoodPosition() > targetPosition + 0.5 || getHoodPosition() < targetPosition - 0.5 ) {
      power = getHoodPosition() > targetPosition ? -0.3 : 0.3; // can make this slower or faster if needed.
    }
    hoodMotor.set(power);
  }

  /**
   * turn the shooter by a certain number of ticks
   */
  public void turn(int ticks) {
    turretTurnMotor.set(TalonSRXControlMode.Position, ticks);
  }

  /**
   * run the flywheel at a power.
   */
  public void runFlywheel(double power) {
    flywheelMotor.set(TalonFXControlMode.PercentOutput, power);
  }

  /**
   * move the hood motor to an absolute angle.
   */
  public void turnHood(double degrees) {
    int turns = (int) (ROTATIONS_PER_HOOD_DEGREE * degrees);
    targetPosition = turns;
  }

  public double getHoodPosition() {
    return hoodEncoder.getPosition();
  }

  /**
   * the angle that the hood is targeting at.
   */
  public double getHoodTarget() {
    return targetPosition;
  }
}
