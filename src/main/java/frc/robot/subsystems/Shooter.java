package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.SensorVelocityMeasPeriod;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.DriveConstants;
import frc.robot.constants.PortConstants;

public class Shooter extends SubsystemBase {
  private final TalonFX flywheelMotor;
  private final TalonFX flywheelMotor2;
  public final CANSparkMax turretTurnMotor;
  private final Servo[] hood;
  public final SparkMaxPIDController m_pidController;
  public final RelativeEncoder m_encoder;
  public double kP;
  public double kI;
  public double kD;
  public double kIz;
  public double kFF;
  public double kMaxOutput;
  public double kMinOutput;
  private static final double HOOD_ACTUATOR_LENGTH_CM = 14;
  private static final double TURRET_GEAR_RATIO = 15;
  private static final int startupCanTimeout = 100; // milliseconds

  // Creating configuration for talon shooter motor
  private static final TalonFXConfiguration SHOOTER_CONFIGURATION = new TalonFXConfiguration();
  // Configuring PID slot on roborio for the shooter's talon config
  private static final int shooterPIDSlot = 0;
  private static final SimpleMotorFeedforward FEEDFORWARD =
      new SimpleMotorFeedforward(0.23393, 0.39004, 0.019617); // Values will change: (ks, kv, ka)

  static {
    final var shooterCurrentLimit = new SupplyCurrentLimitConfiguration();
    shooterCurrentLimit.currentLimit = 30; // Amps
    shooterCurrentLimit.triggerThresholdCurrent = 40; // Amps
    shooterCurrentLimit.triggerThresholdTime = 0.2; // sec
    shooterCurrentLimit.enable = true;
    SHOOTER_CONFIGURATION.supplyCurrLimit = shooterCurrentLimit;

    // TODO: Tune
    final var velocityLoopConfig = new SlotConfiguration();
    velocityLoopConfig.kP = 0.35791;
    velocityLoopConfig.kI = 0.0;
    velocityLoopConfig.kD = 0.0;
    velocityLoopConfig.kF = 0.000;
    SHOOTER_CONFIGURATION.slot0 = velocityLoopConfig;

    SHOOTER_CONFIGURATION.velocityMeasurementPeriod = SensorVelocityMeasPeriod.Period_2Ms;
    SHOOTER_CONFIGURATION.velocityMeasurementWindow = 4;
  }

  // Creates turret motor and sets PID values
  public static final int kSlotIdx = 0;
  public static final int kPIDLoopIdx = 0;
  public static final int kTimeoutMs = 30;
  public static boolean kSensorPhase = false;
  public static boolean kMotorInvert = false;

  /**
   * create a new shooter class. init the motors, configure them.
   */
  public Shooter() {
    super();
    flywheelMotor = new TalonFX(PortConstants.FLYWHEEL_MOTOR);
    flywheelMotor2 = new TalonFX(PortConstants.FLYWHEEL_MOTOR2);
    turretTurnMotor = new CANSparkMax(5, MotorType.kBrushless);
    m_pidController = turretTurnMotor.getPIDController();
    m_encoder = turretTurnMotor.getEncoder();
    hood = new Servo[] {new Servo(PortConstants.HOOD_SERVOS[0]),
        new Servo(PortConstants.HOOD_SERVOS[1])};

    configureShooter();
  }

  // Shooter Methods
  /**
   * Run the flywheel at a power.
   */
  public void spin(double power) {
    flywheelMotor.set(TalonFXControlMode.PercentOutput, power);
  }

  /**
   * @param rpm of mechanism
   */
  public void setVelocity(double rpm) {
    flywheelMotor.set(ControlMode.Velocity,
        // 1 is the gear ratio on the shooter from the falcon to the flywheel
        DriveConstants.RPMToFalcon(rpm, 1), DemandType.ArbitraryFeedForward,
        FEEDFORWARD.calculate(rpm / 60));

  }

  private void configureShooter() {
    flywheelMotor.configAllSettings(SHOOTER_CONFIGURATION, startupCanTimeout);
    flywheelMotor.setInverted(TalonFXInvertType.Clockwise);
    flywheelMotor2.follow(flywheelMotor);
    flywheelMotor2.setInverted(TalonFXInvertType.OpposeMaster);
    flywheelMotor.selectProfileSlot(shooterPIDSlot, 0);

    // CAN Bus Usage Optimisation.
    flywheelMotor2.setStatusFramePeriod(StatusFrame.Status_1_General, 255);
    flywheelMotor2.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 255);

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

  // Hood Methods
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

  public void stopShooter() {
    flywheelMotor.set(ControlMode.Velocity, 0);
    flywheelMotor.set(ControlMode.PercentOutput, 0);
  }

  /**
   * turn the shooter by a certain number of counts. If there is no target, reset turret position to
   * center.
   */
  public void turn(double counts) {
    if (counts == 0) {
      m_pidController.setReference(0, CANSparkMax.ControlType.kPosition);
    } else {
      m_pidController.setReference(counts * TURRET_GEAR_RATIO, CANSparkMax.ControlType.kPosition);
    }
  }
}
