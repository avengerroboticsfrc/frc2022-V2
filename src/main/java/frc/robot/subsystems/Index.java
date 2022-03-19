package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.PortConstants;

//Creates index class 
public class Index extends SubsystemBase {
  // Creates the index motor
  private final WPI_VictorSPX indexMotor;

  /**
   * creates a new index subsystem.
   */
  public Index() {
    super();

    indexMotor = new WPI_VictorSPX(PortConstants.INDEX_MOTOR);
  }

  /**
   * set the index to a certain power.
   */
  public void power(double speed) {
    indexMotor.set(speed);
  }
}