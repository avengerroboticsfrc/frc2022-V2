package frc.robot.commands.auton.FiveBallAuton;

import com.pathplanner.lib.PathPlanner;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.DriveTrain;

public class SixBAutoTrajectory4 extends CommandBase {
    public Command SixBallAutonBlue4(DriveTrain drive) {
        var autoVoltageConstraint =
          new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(
              DriveConstants.KS_VOLTS,
              DriveConstants.KV_VOLT_SECONDS_PER_METER,
              DriveConstants.KA_VOLT_SECONDS_SQUARED_PER_METER),
          DriveConstants.K_DRIVE_KINEMATICS, 
          10);
        
        TrajectoryConfig config =
          new TrajectoryConfig(
              DriveConstants.K_MAX_SPEED_METER_PER_SECOND, 
              DriveConstants.K_MAX_ACCELERATION_METERS_PER_SECOND_SQUARED)
            .setKinematics(DriveConstants.K_DRIVE_KINEMATICS)
            .addConstraint(autoVoltageConstraint);

        Trajectory path4 = PathPlanner.loadPath("Trajectory4-5bAuto", 3, 3);
    
        RamseteCommand ramseteCommand4 =
        new RamseteCommand(
            path4,
            drive::getPose,
            new RamseteController(DriveConstants.K_RAMSETE, DriveConstants.K_RAMSETE_ZETA),
            new SimpleMotorFeedforward(
                DriveConstants.KS_VOLTS,
                DriveConstants.KV_VOLT_SECONDS_PER_METER,
                DriveConstants.KA_VOLT_SECONDS_SQUARED_PER_METER),
            DriveConstants.K_DRIVE_KINEMATICS,
            drive::getWheelSpeeds,
            new PIDController(DriveConstants.KP_DRIVE_VELOCITY, 0, 0),
            new PIDController(DriveConstants.KP_DRIVE_VELOCITY, 0, 0),
            // RamseteCommand passes volts to the callback
            drive::tankDriveVolts,
            drive);
        
        drive.resetOdometry(path4.getInitialPose());
    
        return ramseteCommand4.andThen(() -> drive.tankDriveVolts(0, 0));
}
}
