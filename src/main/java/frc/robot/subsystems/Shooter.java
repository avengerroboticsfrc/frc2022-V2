package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

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
   * create a new shooter class.
   * init the motors, configure them.
   */
  public Shooter() {
    super();

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
    if (getHoodPosition() > targetPosition + 0.5 || getHoodPosition() < targetPosition - 0.5) {
      // can make this slower or faster if needed
      power = getHoodPosition() > targetPosition ? -0.3 : 0.3;
    }
    hoodMotor.set(power);
  }

  /**
   * turn the shooter by a certain number of ticks.
   */
  public void turn(int ticks) {
    turretTurnMotor.set(TalonSRXControlMode.Position, ticks);
  }

  /**
   * Run the flywheel at a power.
   */
  public void runFlywheel(double power) {
    flywheelMotor.set(TalonFXControlMode.PercentOutput, power);
  }

  /**
   * Set the hood's target angle in degrees.
   */
  public void turnHood(double degrees) {
    int turns = (int) (ROTATIONS_PER_HOOD_DEGREE * degrees);
    targetPosition = turns;
  }

  /**
   * Return the current angle of the hood.
   */
  public double getHoodPosition() {
    return hoodEncoder.getPosition() / ROTATIONS_PER_HOOD_DEGREE;
  }

  /**
   * Return the angle that the hood trying to reach.
   */
  public double getHoodTarget() {
    return targetPosition / ROTATIONS_PER_HOOD_DEGREE;
  }
}
