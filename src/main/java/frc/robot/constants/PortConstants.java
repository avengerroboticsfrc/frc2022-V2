// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.constants;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class PortConstants {
  public static final int[] LEFT_DRIVE = { 1, 10 };
  public static final int[] RIGHT_DRIVE = { 2, 20 };

  public static final int FLYWHEEL_MOTOR = 40;
  public static final int FLYWHEEL_MOTOR2 = 41;
  public static final int[] HOOD_SERVOS = { 0,1 };
  public static final int TURRET_TURN_MOTOR = 5;

  public static final int VERTICAL_LIFT_MOTORS = 8;
  public static final int ARM_MOTOR = 7;

  public static final int[] INTAKE_PNEUMATICS_PORTS = { 0, 1 };
  public static final int INTAKE_MOTOR = 12;
  public static final int INDEX_MOTOR = 31;
  public static final int INDEX_TO_FLYWHEEL_MOTOR = 32;
  public static final int INTAKE_TO_INDEX_MOTOR = 30; //Number Subject to Change
  public static final int Gyro = 0;

}
