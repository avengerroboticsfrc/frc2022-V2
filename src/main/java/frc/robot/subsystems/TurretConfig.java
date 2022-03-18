package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.motorcontrol.Talon;

public class TurretConfig {
    public TurretConfig() {

        //TODO: WIP
        TalonSRXConfiguration allConfig = new TalonSRXConfiguration();
        TalonSRX t = new TalonSRX(1);
        allConfig.clearPositionOnQuadIdx = true;
        allConfig.openloopRamp = 1;
    }
}
