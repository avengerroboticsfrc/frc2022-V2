/**
 * PLEASE DON'T MODIFY THIS THX
 * -kh
 */

package frc.robot.commands.complex;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.DriveTrain;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class FridayRamseteCommand extends CommandBase {
  /**
   * init RAMSETE command variables.
   */
  private static final boolean USE_PID = true;
  private final Timer timer = new Timer();
  private final Trajectory trajectory;
  private final Supplier<Pose2d> pose;
  private final RamseteController follower;
  private final SimpleMotorFeedforward feedforward;
  private final DifferentialDriveKinematics kinematics;
  private final Supplier<DifferentialDriveWheelSpeeds> speeds;
  private final PIDController leftController;
  private final PIDController rightController;
  private final BiConsumer<Double, Double> output;
  private DifferentialDriveWheelSpeeds prevSpeeds;
  private double prevTime;
  private final Map<Double, Command> commands;
  private Command currentCommand = null;
  private boolean firstRun = true;
  private int statesCompleted = 0;
  private double[] timesCompleted;

  /**
   * creates a Ramsete Command with a given trajectory using the settings for our
   * robot (F.R.I.D.A.Y.)
   * 
   * <p>implements a feature to run a command upon reaching a state. Does not run anything upon
   * reaching the end of the path.
   * 
   * <p>Almost entirely pasted from the codebase of
   * {@link edu.wpi.first.wpilibj2.command.RamseteCommand}. Rewritten
   * because WPILib didn't make it possible to implement a method to pause a
   * trajectory easily
   */
  public FridayRamseteCommand(Trajectory trajectory, DriveTrain drive,
      Map<Double, Command> commands) {
    super();

    this.trajectory = trajectory;
    pose = drive::getPose;
    follower = new RamseteController(DriveConstants.K_RAMSETE, DriveConstants.K_RAMSETE_ZETA);
    feedforward = new SimpleMotorFeedforward(
        DriveConstants.KS_VOLTS,
        DriveConstants.KV_VOLT_SECONDS_PER_METER,
        DriveConstants.KA_VOLT_SECONDS_SQUARED_PER_METER);
    kinematics = DriveConstants.K_DRIVE_KINEMATICS;
    speeds = drive::getWheelSpeeds;
    leftController = new PIDController(DriveConstants.KP_DRIVE_VELOCITY, 0, 0);
    rightController = new PIDController(DriveConstants.KP_DRIVE_VELOCITY, 0, 0);
    // RamseteCommand passes volts to the callback
    output = drive::tankDriveVolts;

    this.commands = commands;
    timesCompleted = new double[commands.size()];

    addRequirements(drive);
  }

  @Override
  public void initialize() {
    prevTime = -1;
    var initialState = trajectory.sample(0);
    prevSpeeds = kinematics.toWheelSpeeds(
        new ChassisSpeeds(
            initialState.velocityMetersPerSecond,
            0,
            initialState.curvatureRadPerMeter * initialState.velocityMetersPerSecond));
    timer.reset();
    timer.start();
    if (USE_PID) {
      leftController.reset();
      rightController.reset();
    }
  }

  @Override
  public void execute() {
    double curTime = timer.get();
    double dt = curTime - prevTime;

    if (prevTime < 0) {
      output.accept(0.0, 0.0);
      prevTime = curTime;
      return;
    }

    double time = roundTime(curTime);
    if (commands.containsKey(roundTime(time)) && timesCompleted[statesCompleted] != time) {
      if (firstRun) {
        timer.stop();
        currentCommand = commands.get(time);
        System.out.println(currentCommand.getName());
        currentCommand.initialize();
        firstRun = false;
      }

      currentCommand.execute();
      output.accept(0.0, 0.0);

      if (currentCommand.isFinished()) {
        timesCompleted[statesCompleted] = time;
        statesCompleted++;
        currentCommand.end(false);
        firstRun = true;
        timer.start();
        runTrajectory(curTime, dt);
      }
    } else {
      runTrajectory(curTime, dt);
    }
  }

  @Override
  public void end(boolean interrupted) {
    timer.stop();

    if (interrupted) {
      output.accept(0.0, 0.0);
      if (currentCommand != null) {
        currentCommand.end(true);
      }
    }
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(trajectory.getTotalTimeSeconds());
  }

  /**
   * rounds the number to the hundredths place.
   */
  private double roundTime(double time) {
    return (double) Math.round(time * 100) / 100;
  }

  /**
   * This is what runs in the RAMSETE Command. It is just moved down here to make the code a bit
   * more readable.
   */
  private void runTrajectory(double curTime, double dt) {
    var targetWheelSpeeds = kinematics.toWheelSpeeds(
        follower.calculate(pose.get(), trajectory.sample(curTime)));

    var leftSpeedSetpoint = targetWheelSpeeds.leftMetersPerSecond;
    var rightSpeedSetpoint = targetWheelSpeeds.rightMetersPerSecond;

    double leftOutput;
    double rightOutput;

    if (USE_PID) {
      double leftFeedforward = feedforward.calculate(
          leftSpeedSetpoint, (leftSpeedSetpoint - prevSpeeds.leftMetersPerSecond) / dt);

      double rightFeedforward = feedforward.calculate(
          rightSpeedSetpoint, (rightSpeedSetpoint - prevSpeeds.rightMetersPerSecond) / dt);

      leftOutput = leftFeedforward
          + leftController.calculate(speeds.get().leftMetersPerSecond, leftSpeedSetpoint);

      rightOutput = rightFeedforward
          + rightController.calculate(
              speeds.get().rightMetersPerSecond, rightSpeedSetpoint);
    } else {
      leftOutput = leftSpeedSetpoint;
      rightOutput = rightSpeedSetpoint;
    }

    output.accept(leftOutput, rightOutput);
    prevSpeeds = targetWheelSpeeds;
    prevTime = curTime;
  }
}
