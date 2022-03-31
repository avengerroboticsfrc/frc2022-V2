package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.PortConstants;

//Creates index class 
public class Index extends SubsystemBase {
  // Creates the index motor
  // private final WPI_VictorSPX indexMotor;
  private final WPI_VictorSPX indexMotor;
  private final WPI_VictorSPX IntakeToIndex;
  private final WPI_VictorSPX IndexToShooter;

  /**
   * creates a new index subsystem.
   */
  public Index() {
    super();

    indexMotor = new WPI_VictorSPX(PortConstants.INDEX_MOTOR);
    IntakeToIndex = new WPI_VictorSPX(PortConstants.INTAKE_TO_INDEX_MOTOR);
    IndexToShooter = new WPI_VictorSPX(PortConstants.INDEX_MOTOR2);
  }

  /**
   * set the index to a certain power.
   */
  public void power(double speed) {
    indexMotor.set(-speed);
  }

  public void IntakeIntoIndexPower(double speed) {
    IntakeToIndex.set(-speed);
  }

  public void IndexToShooterPower(double speed) {
    IndexToShooter.set(-speed); 
  }
}