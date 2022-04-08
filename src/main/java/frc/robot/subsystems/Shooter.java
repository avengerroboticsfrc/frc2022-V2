package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.PortConstants;

public class Shooter extends SubsystemBase {
  private final TalonFX flywheelMotor;
  private final TalonFX flywheelMotor2;
  public final CANSparkMax turretTurnMotor;
  private final Servo[] hood;
  public final SparkMaxPIDController m_pidController;
  public final RelativeEncoder m_encoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

  private static final double HOOD_ACTUATOR_LENGTH_CM = 14;
  private static final double TURRET_GEAR_RATIO = 15;

  // Creates turret motor and sets PID values
  public static final int kSlotIdx = 0;
  public static final int kPIDLoopIdx = 0;
  public static final int kTimeoutMs = 30;
  public static boolean kSensorPhase = false;
  public static boolean kMotorInvert = false;
  // kP, kI, kD, kF, kIzone, kPeakOutput;
  public static final double[] kTurretGains = { 0, 0, 0, 0, 0, 1 };
  // public static final double[] kTurretGains = { 0, 0, 0, .1705, 0, 1 };
  // kP, kI, kD, kIz, kFF, kMinOutput, kMaxOutput;
  public static final double[] kHoodGains = { 0, 0, 0, 0, 0, 0, 1 };
  public final double[] preDistance = { 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5, 8 };
  public final double[] preHoodAngle = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
  public final double[] preShooterPower = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };

  /**
   * create a new shooter class.
   * init the motors, configure them.
   */
  public Shooter() {
    super();
    flywheelMotor = new TalonFX(PortConstants.FLYWHEEL_MOTOR);
    flywheelMotor2 = new TalonFX(PortConstants.FLYWHEEL_MOTOR2);
    turretTurnMotor = new CANSparkMax(5, MotorType.kBrushless);
    m_pidController = turretTurnMotor.getPIDController();
    m_encoder = turretTurnMotor.getEncoder();
    hood = new Servo[] {
        new Servo(PortConstants.HOOD_SERVOS[0]),
        new Servo(PortConstants.HOOD_SERVOS[1])
    };

    configureShooter();
  }

  /**
   * turn the shooter by a certain number of counts. If there is no target, reset
   * turret position to center.
   */
  public void turn(double counts) {
    if (counts == 0) {
      m_pidController.setReference(0, CANSparkMax.ControlType.kPosition);
    } else {
      m_pidController.setReference(counts * TURRET_GEAR_RATIO, CANSparkMax.ControlType.kPosition);
    }
  }

  /**
   * Run the flywheel at a power.
   */
  public void spin(double power) {
    flywheelMotor.set(TalonFXControlMode.PercentOutput, power);
  }

  /**
   * takes in RPM. sets velocity.
   */
  public void setRPM(double rpm) {
    // rpm ranges from 0 <---> 6380
    flywheelMotor.set(TalonFXControlMode.Velocity, rpm);
  }

  /**
   * Set the hood's position in cm.
   */
  public void extendHood(double cm) {
    hood[0].set(cm / HOOD_ACTUATOR_LENGTH_CM);
    hood[1].set(cm / HOOD_ACTUATOR_LENGTH_CM);
    System.out.println("Hoodlength is " + getHoodPos());
  }

  public double getHoodPos() {
    return hood[0].get() * HOOD_ACTUATOR_LENGTH_CM;
  }

  private void configureShooter() {
    flywheelMotor.setInverted(TalonFXInvertType.Clockwise);
    flywheelMotor2.follow(flywheelMotor);
    flywheelMotor2.setInverted(TalonFXInvertType.OpposeMaster);

    flywheelMotor.configOpenloopRamp(1);
    flywheelMotor2.configOpenloopRamp(1);

    hood[0].setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
    hood[1].setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
    // PID coefficients
    // TODO: CHANGE THESE VALS
    kP = .1;
    kI = 1e-4;
    kD = 1;
    kIz = 0;
    kFF = 0;
    kMaxOutput = .5;
    kMinOutput = -.5;

    turretTurnMotor.restoreFactoryDefaults();
    // set PID coefficients
    m_pidController.setP(kP);
    // m_pidController.setI(kI);
    // m_pidController.setD(kD);
    // m_pidController.setIZone(kIz);
    // m_pidController.setFF(kFF);
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);
    turretTurnMotor.setClosedLoopRampRate(.5);
    turretTurnMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
    turretTurnMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
    turretTurnMotor.setIdleMode(IdleMode.kBrake);
    // TODO: CHANGE THESE VALS
    turretTurnMotor.setSoftLimit(SoftLimitDirection.kForward, 24f);
    turretTurnMotor.setSoftLimit(SoftLimitDirection.kReverse, -24f);
    m_encoder.setPosition(0);
  }
}
