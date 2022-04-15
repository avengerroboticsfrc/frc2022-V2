package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.PortConstants;

// Creates index class
public class IntakeToIndex extends SubsystemBase {
  // Creates the index motor
  // private final WPI_VictorSPX indexMotor;
  private final WPI_VictorSPX intakeToIndex;

  /**
   * creates a new index subsystem.
   */
  public IntakeToIndex() {
    super();
    intakeToIndex = new WPI_VictorSPX(PortConstants.INTAKE_TO_INDEX_MOTOR);
  }

  /**
   * set the index to a certain power.
   */

  public void power(double speed) {
    intakeToIndex.set(speed);
  }
}