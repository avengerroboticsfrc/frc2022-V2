package frc.robot.subsystems;


import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ColorSensorv3 extends SubsystemBase {
    
    private ColorSensorv3 ColorSensor;


    public void getRedValue(){
        ColorSensor.getRedValue();   
    }

    public void getBlueValue(){
        ColorSensor.getBlueValue();
    }
}
