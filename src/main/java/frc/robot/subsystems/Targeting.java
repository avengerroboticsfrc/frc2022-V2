package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Targeting extends CommandBase {

    double[] preDistance;
    double[] preHoodAngle;
    double[] preShooterPower;
    int x;

public Targeting(){

    double[] preDistance = {1,1.5,2,2.5,3,3.5,4,4.5,5,5.5,6,6.5,7,7.5,8};
    double[] preHoodAngle = {0,0,0,0,0,0,0,0,0,0,0,0,0};
    double[] preShooterPower = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,};



}

public void getRightPreset(Limelight limelight, Shooter shooter){
    for(int x = 0; x <= preDistance.length; x++){
        if(Math.abs(limelight.getDistance() - preDistance[x]) >= 0 && Math.abs(limelight.getDistance() - preDistance[x]) < 0.3){ 
            shooter.extendHood(preHoodAngle[x]);
            shooter.spin(preShooterPower[x]);
    }
    else if (Math.abs(limelight.getDistance() - preDistance[x]) >= 0.3  && Math.abs(limelight.getDistance() - preDistance[x]) <= 0.5){
        shooter.extendHood(preHoodAngle[x]);
        shooter.spin(preShooterPower[x]);
    }
    else if (Math.abs(limelight.getDistance() - preDistance[x]) <= 0.7  && Math.abs(limelight.getDistance() - preDistance[x]) > 0.5){
        shooter.extendHood(preHoodAngle[x]);
        shooter.spin(preShooterPower[x]);
    }
    else if (Math.abs(limelight.getDistance() - preDistance[x]) > 0.7  && Math.abs(limelight.getDistance() - preDistance[x]) < 1){
        shooter.extendHood(preHoodAngle[x]);
        shooter.spin(preShooterPower[x]);

    }


}

    
}
}
