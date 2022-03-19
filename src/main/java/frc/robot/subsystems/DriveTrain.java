package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.PortConstants;

public class DriveTrain extends SubsystemBase {
  protected final WPI_TalonFX[] leftMotors;
  protected final WPI_TalonFX[] rightMotors;

  protected final DifferentialDrive driveTrain;

  private final Gyro gyro = new ADXRS450_Gyro();

  protected final DifferentialDriveOdometry odometry =
      new DifferentialDriveOdometry(gyro.getRotation2d());

  /**
   * this method is called when the DriveTrainSubsystem class is initialized.
   */
  public DriveTrain() {
    super();

    this.leftMotors = new WPI_TalonFX[] {
        new WPI_TalonFX(PortConstants.LEFT_DRIVE[0]),
        new WPI_TalonFX(PortConstants.RIGHT_DRIVE[1])
    };
    this.rightMotors = new WPI_TalonFX[] {
        new WPI_TalonFX(PortConstants.RIGHT_DRIVE[0]),
        new WPI_TalonFX(PortConstants.RIGHT_DRIVE[1])
    };

    driveTrain = new DifferentialDrive(
        leftMotors[0],
        rightMotors[0]);


    leftMotors[0].configFactoryDefault();
    leftMotors[1].configFactoryDefault();
    rightMotors[0].configFactoryDefault();
    rightMotors[1].configFactoryDefault();

    leftMotors[0].configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    leftMotors[1].configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    rightMotors[0].configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    rightMotors[1].configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    leftMotors[0].configOpenloopRamp(.5);
    leftMotors[1].configOpenloopRamp(.5);
    rightMotors[0].configOpenloopRamp(.5);
    rightMotors[1].configOpenloopRamp(.5);

    leftMotors[1].follow(leftMotors[0]);
    rightMotors[1].follow(rightMotors[0]);

    rightMotors[0].setInverted(true);
    rightMotors[1].setInverted(InvertType.FollowMaster);

    leftMotors[0].setNeutralMode(NeutralMode.Brake);
    leftMotors[1].setNeutralMode(NeutralMode.Brake);
    rightMotors[0].setNeutralMode(NeutralMode.Brake);
    rightMotors[1].setNeutralMode(NeutralMode.Brake);

    resetEncoders();
  }

  @Override
  public void periodic() {
    // Update the odometry in the periodic block
    odometry.update(
        gyro.getRotation2d(),
        leftMotors[0].getSelectedSensorPosition(),
        rightMotors[0].getSelectedSensorPosition() * -1
    );
  }

  /**
  * Returns the currently-estimated pose of the robot.

  * @return The pose.
  */
  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }


  /**
   * Returns the current wheel speeds of the robot.

   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(
        leftMotors[0].getSelectedSensorVelocity(),
        rightMotors[0].getSelectedSensorVelocity() * -1
      );
  }


  /**
   * Resets the odometry to the specified pose.

   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    odometry.resetPosition(pose, gyro.getRotation2d());
  }

  /**
   * Controls the left and right sides of the drive directly with voltages.

   * @param leftVolts the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    leftMotors[0].setVoltage(leftVolts);
    rightMotors[0].setVoltage(rightVolts);
    driveTrain.feed();
  }

  /**
   * just call the arcadedrive method with a differential drive.
   */
  public void arcadeDrive(double speed, double rotation) {
    driveTrain.arcadeDrive(speed, rotation);
  }

  public void tankDrive(double leftTrain, double rightTrain)  {
    driveTrain.tankDrive(leftTrain, rightTrain);
  }

  /**
   * Curvature drive method for differential drivetrain.

   * The rotation argument controls the curvature of the robot's path rather thanS
   * its rate of heading change. This makes the robot more controllable at high
   * speeds.
   */
  public void curvatureDrive(double speed, double rotation, boolean turn) {
    driveTrain.curvatureDrive(speed, rotation, turn);
  }

  /** Resets the drive encoders to currently read a position of 0. */
  public void resetEncoders() {
    leftMotors[0].setSelectedSensorPosition(0);
    leftMotors[1].setSelectedSensorPosition(0);
    rightMotors[0].setSelectedSensorPosition(0);
    rightMotors[0].setSelectedSensorPosition(0);
  }

  /**
   * Gets the average distance of the two encoders.

   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    return (
        leftMotors[0].getSelectedSensorPosition()
        + leftMotors[1].getSelectedSensorPosition()
        + rightMotors[0].getSelectedSensorPosition() * -1
        + rightMotors[1].getSelectedSensorPosition() * -1
      ) / 4;
  }

  public void gyroCalibrate() {
    gyro.calibrate();
  }

  public void ConfigPid() {
    int pidIdx = 0;
    int slotIdx = 0;
    double pos = 0;
    leftMotors[0].setSensorPhase(true);
    leftMotors[1].setSensorPhase(true);
    leftMotors[0].configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, pidIdx, 100);
    leftMotors[1].configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, pidIdx, 100);
    rightMotors[0].configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, pidIdx, 100);
    rightMotors[1].configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, pidIdx, 100);
    leftMotors[0].config_kP(slotIdx, 0);
    leftMotors[1].config_kP(slotIdx, 0);
    rightMotors[0].config_kP(slotIdx, 0);
    rightMotors[1].config_kP(slotIdx, 0);
    leftMotors[0].config_kI(slotIdx, 0);
    leftMotors[1].config_kI(slotIdx, 0);
    rightMotors[0].config_kI(slotIdx, 0);
    rightMotors[1].config_kI(slotIdx, 0);
    leftMotors[0].config_kD(slotIdx, 0);
    leftMotors[1].config_kD(slotIdx, 0);
    rightMotors[0].config_kD(slotIdx, 0);
    rightMotors[1].config_kD(slotIdx, 0);
    leftMotors[0].config_kF(slotIdx, 0);
    leftMotors[1].config_kF(slotIdx, 0);
    rightMotors[0].config_kF(slotIdx, 0);
    rightMotors[1].config_kF(slotIdx, 0);
    leftMotors[0].setSelectedSensorPosition(pos, pidIdx, 100);
    leftMotors[1].setSelectedSensorPosition(pos, pidIdx, 100);
    rightMotors[0].setSelectedSensorPosition(pos, pidIdx, 100);
    rightMotors[1].setSelectedSensorPosition(pos, pidIdx, 100);
  }
}
