package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.PortConstants;

public class Shooter extends SubsystemBase {
  private final WPI_VictorSPX shooterInMotor;
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
    shooterInMotor = new WPI_VictorSPX(PortConstants.SHOOTER_IN_MOTOR);
    flywheelMotor = new TalonFX(PortConstants.FLYWHEEL_MOTOR);
    turretTurnMotor = new TalonSRX(PortConstants.TURRET_TURN_MOTOR);
    hoodMotor = new CANSparkMax(PortConstants.HOOD_MOTOR, MotorType.kBrushless);

    hoodEncoder = hoodMotor.getEncoder();
  }

  @Override
  public void periodic() {
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
   * Set the hood's target angle in degrees.
   */
  public void angle(double degrees) {
    int turns = (int) (ROTATIONS_PER_HOOD_DEGREE * degrees);
    targetPosition = turns;
  }

  public void intakeBall(double speed) {
    shooterInMotor.set(1);
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
