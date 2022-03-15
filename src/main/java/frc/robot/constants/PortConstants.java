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
  public static final int[] LEFT_DRIVE = { 2, 3 };
  public static final int[] RIGHT_DRIVE = { 4, 5 };

  public static final int SHOOTER_PORT = 40;
  public static final int HOOD_MOTOR = 41;
  public static final int TURRET_MOTOR = 5;

  public static final int[] LIFT_MOTORS = { 8, 9 };
  public static final int[] ARM_MOTORS = { 7, 11 };

  public static final int[] INTAKE_PNEUMATICS_PORTS = { 0, 1 };
  public static final int INTAKE_MOTOR = 12;
  public static final int INDEX_MOTOR = 6;
}