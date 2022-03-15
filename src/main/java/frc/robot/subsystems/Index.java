//Package

package frc.robot.subsystems;

//Imports
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

//More Imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.*;

//Creates index class 
public class Index extends SubsystemBase {
  // Creates the index motor
  private final WPI_VictorSPX[] indexMotors = {
    new WPI_VictorSPX(PortConstants.INDEX_MOTOR) 
  };

  // Index Container
  public Index() {
    super();
  }

  //Method that lifts the balls to the shooter
  public void power(double speed) {
    indexMotors[0].set(speed);
  }
}