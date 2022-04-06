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
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.PortConstants;

public class Shooter extends SubsystemBase {
  private final TalonFX flywheelMotor;
  private final TalonFX flywheelMotor2;
  public final CANSparkMax turretTurnMotor;
  private final Servo[] hood;
  private SparkMaxPIDController m_pidController;
  private RelativeEncoder m_encoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

  private static final double HOOD_ACTUATOR_LENGTH_CM = 14;

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
  public final double[] preDistance = {1,1.5,2,2.5,3,3.5,4,4.5,5,5.5,6,6.5,7,7.5,8};
  public final double[] preHoodAngle = {0,0,0,0,0,0,0,0,0,0,0,0,0};
  public final double[] preShooterPower = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,};

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
   * turn the shooter by a certain number of counts.
   */
  public void turn(double counts) {
    m_pidController.setReference(counts, CANSparkMax.ControlType.kPosition);
    m_encoder.setPosition(counts);
  }

  /**
   * Run the flywheel at a power AND runs the top index wheels.
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
    kP = 0.1; 
    kI = 1e-4;
    kD = 1; 
    kIz = 0; 
    kFF = 0; 
    kMaxOutput = 1; 
    kMinOutput = -1;

    // set PID coefficients
    m_pidController.setP(kP);
    m_pidController.setI(kI);
    m_pidController.setD(kD);
    m_pidController.setIZone(kIz);
    m_pidController.setFF(kFF);
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);
    turretTurnMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
    turretTurnMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
    turretTurnMotor.setIdleMode(IdleMode.kBrake);
    //TODO: CHANGE THESE VALS
    turretTurnMotor.setSoftLimit(SoftLimitDirection.kForward, 2400);
    turretTurnMotor.setSoftLimit(SoftLimitDirection.kReverse, 2400);
    }
  
  //   public void getRightPreset(Limelight limelight, Shooter shooter){
  //     for(int x = 0; x <= preDistance.length; x++){
  //         if(Math.abs(limelight.getDistance() - preDistance[x]) >= 0 && Math.abs(limelight.getDistance() - preDistance[x]) < 0.3){ 
  //             shooter.extendHood(preHoodAngle[x]);
  //             shooter.spin(preShooterPower[x]);
  //     }
  //     else if (Math.abs(limelight.getDistance() - preDistance[x]) >= 0.3  && Math.abs(limelight.getDistance() - preDistance[x]) <= 0.5){
  //         shooter.extendHood(preHoodAngle[x]);
  //         shooter.spin(preShooterPower[x]);
  //     }
  //     else if (Math.abs(limelight.getDistance() - preDistance[x]) <= 0.7  && Math.abs(limelight.getDistance() - preDistance[x]) > 0.5){
  //         shooter.extendHood(preHoodAngle[x]);
  //         shooter.spin(preShooterPower[x]);
  //     }
  //     else if (Math.abs(limelight.getDistance() - preDistance[x]) > 0.7  && Math.abs(limelight.getDistance() - preDistance[x]) < 1){
  //         shooter.extendHood(preHoodAngle[x]);
  //         shooter.spin(preShooterPower[x]);
  
  //     }
  
  
  // }
  
      
  // }

  


}
