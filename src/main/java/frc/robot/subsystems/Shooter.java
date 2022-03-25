package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.SensorVelocityMeasPeriod;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.PortConstants;

public class Shooter extends SubsystemBase {
  private final TalonFX flywheelMotor;
  public final TalonSRX turretTurnMotor;
  private final Servo[] hood;

  private static final double HOOD_ACTUATOR_LENGTH_CM = 14;

  // Creates turret motor and sets PID values
  public static final int kSlotIdx = 0;
  public static final int kPIDLoopIdx = 0;
  public static final int kTimeoutMs = 30;
  public static boolean kSensorPhase = false;
  public static boolean kMotorInvert = false;
  // kP, kI, kD, kF, kIzone, kPeakOutput;
  public static final double[] kTurretGains = { 0, 0, 0, .1705, 0, 1 };
  // kP, kI, kD, kIz, kFF, kMinOutput, kMaxOutput;
  public static final double[] kHoodGains = { 0, 0, 0, 0, 0, 0, 1 };

  /**
   * create a new shooter class.
   * init the motors, configure them.
   */
  public Shooter() {
    super();
    flywheelMotor = new TalonFX(PortConstants.FLYWHEEL_MOTOR);
    turretTurnMotor = new TalonSRX(PortConstants.TURRET_TURN_MOTOR);
    hood = new Servo[] {
        new Servo(PortConstants.HOOD_SERVOS[0]),
        new Servo(PortConstants.HOOD_SERVOS[1])
    };

    configureShooter();
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
   * Set the hood's position in cm.
   */
  public void extendHood(double cm) {
    hood[0].set(cm / HOOD_ACTUATOR_LENGTH_CM);
    hood[1].set(cm / HOOD_ACTUATOR_LENGTH_CM);
    System.out.println("the hood length is " + cm);
  }

  public double getHoodPos() {
    return hood[0].get() * HOOD_ACTUATOR_LENGTH_CM;
  }

  private void configureShooter() {
    hood[0].setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
    hood[1].setBounds(2.0, 1.8, 1.5, 1.2, 1.0);

    // PASTED FROM LUCA'S CODE, IDK WHAT MOST OF THIS DOES (he probably copy-pasted it too...)
    // Turret configuration
    turretTurnMotor.configFactoryDefault();
    turretTurnMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx, kTimeoutMs);
    turretTurnMotor.setSensorPhase(kSensorPhase);
    turretTurnMotor.setInverted(kMotorInvert);
    /* Config the peak and nominal outputs, 12V means full */
    turretTurnMotor.configNominalOutputForward(0, kTimeoutMs);
    turretTurnMotor.configNominalOutputReverse(0, kTimeoutMs);
    turretTurnMotor.configPeakOutputForward(.5, kTimeoutMs);
    turretTurnMotor.configPeakOutputReverse(-.5, kTimeoutMs);
    /**
     * Config the allowable closed-loop error, Closed-Loop output will be
     * neutral within this range. See Table in Section 17.2.1 for native
     * units per rotation.
     */
    turretTurnMotor.configAllowableClosedloopError(0, kPIDLoopIdx, kTimeoutMs);
    /* Config Position Closed Loop gains in slot0, tsypically kF stays zero. */
    turretTurnMotor.config_kP(kPIDLoopIdx, kTurretGains[0], kTimeoutMs);
    turretTurnMotor.config_kI(kPIDLoopIdx, kTurretGains[1], kTimeoutMs);
    turretTurnMotor.config_kD(kPIDLoopIdx, kTurretGains[2], kTimeoutMs);
    turretTurnMotor.config_kF(kPIDLoopIdx, kTurretGains[3], kTimeoutMs);
    // Sets software limits
    turretTurnMotor.configForwardSoftLimitEnable(true, kTimeoutMs);
    turretTurnMotor.configForwardSoftLimitThreshold(20480, kTimeoutMs);
    turretTurnMotor.configReverseSoftLimitEnable(true, kTimeoutMs);
    turretTurnMotor.configReverseSoftLimitThreshold(-20480, kTimeoutMs);
    turretTurnMotor.configVelocityMeasurementPeriod(SensorVelocityMeasPeriod.Period_10Ms);
    turretTurnMotor.configVelocityMeasurementWindow(32);

    /**
     * Grab the 360 degree position of the MagEncoder's absolute
     * position, and intitally set the relative sensor to match.
     */
    int absolutePosition = turretTurnMotor.getSensorCollection().getPulseWidthPosition();

    /* Mask out overflows, keep bottom 12 bits */
    absolutePosition &= 0xFFF;
    if (kSensorPhase) {
      absolutePosition *= -1;
    }
    if (kMotorInvert) {
      absolutePosition *= -1;
    }

    /* Set the quadrature (relative) sensor to match absolute */
    turretTurnMotor.setSelectedSensorPosition(absolutePosition, kPIDLoopIdx, kTimeoutMs);
  }
}
