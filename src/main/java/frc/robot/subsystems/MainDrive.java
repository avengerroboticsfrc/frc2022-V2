package frc.robot.subsystems;

import frc.robot.constants.*;

public class MainDrive extends DriveTrain {
  /**
   * this method is called when the MainDrive class is initialized.
   * it calls the super class {@link DriveTrain} and instantiates it with
   * the 4 talons on the main robot.
   */
  public MainDrive() {
    super(PortConstants.LEFT_DRIVE, PortConstants.RIGHT_DRIVE);
  }
}


