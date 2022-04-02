package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.PortConstants;

//Creates index class 
public class IndexToShooter extends SubsystemBase {
  // Creates the index motor
  // private final WPI_VictorSPX indexMotor;
  private static WPI_VictorSPX indexToShooter;

  /**
   * creates a new index subsystem.
   */
  public IndexToShooter() {
    super();

    indexToShooter = new WPI_VictorSPX(PortConstants.INDEX_TO_FLYWHEEL_MOTOR);
  }

  /**
   * set the index to a certain power.
   */

  public void power(double speed) {
    indexToShooter.set(-speed); 
  }
}