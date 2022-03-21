package frc.robot.subsystems;



import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ColorSensorv3 extends SubsystemBase {
    
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 color;
    private final Color BlueColorTarget; 
    private final Color RedColorTarget; 
    private final ColorMatch StoredColors;




    public ColorSensorv3(){
        super();

        color = new ColorSensorV3(i2cPort);
        BlueColorTarget = new Color(0,0,255); //needs to change depending on the rgb value of the blue ball
        RedColorTarget = new Color(255,0,0); //needs to change depending on the rgb value of the red ball
        StoredColors = new ColorMatch();

        StoredColors.addColorMatch(BlueColorTarget);
        StoredColors.addColorMatch(RedColorTarget);
        


    }

    public boolean checkifBlue(){
        if(color.getColor() == BlueColorTarget){
            System.out.println("Detected Blue");
            return true;
        }
        return false;
    }
    
    public boolean checkifRed(){
        if(color.getColor() == RedColorTarget){
            System.out.println("Detected Red");
            return true;
        }
        return false;
    }

    /*public void SpitOutBall(String color){

        if(color == "blue"){
            if(checkifBlue() == true){

            }
        }
    }
*/



}
