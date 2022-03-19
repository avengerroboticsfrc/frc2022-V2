package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.PortConstants;

//Creates index class 
public class Index extends SubsystemBase {
  // Creates the index motor
  private final WPI_VictorSPX indexInMotor;
  private final WPI_VictorSPX indexUpMotor;

  /**
   * creates a new index subsystem.
   */
  public Index() {
    super();

    indexInMotor = new WPI_VictorSPX(PortConstants.INDEX_IN_MOTOR);
    indexUpMotor = new WPI_VictorSPX(PortConstants.INDEX_UP_MOTOR);
  }

  /**
   * set the index to a certain power.
   */
  public void power(double speed) {
    indexInMotor.set(speed);
  }
}