package frc.robot.constants;

public class ButtonConstants {
  public static final int CONTROLLER_PORT = 0;
  public static final int BUTTON_PANEL_PORT = 1;

  /*
   * Button Panel Mapping
   * -------------------
   * |    ^    | 1 | 2 |
   * |   < >   | 3 | 4 |
   * |    v    | 5 | 6 |
   * |  9 | 10 | 7 | 8 |
   * | 11 | 12 |   |   |
   * -------------------
   */

  // TODO: map these better @Glidepixel can you take a look at this?
  public static final int TOGGLE_INTAKE_EXTENDED = 3;
  public static final int INTAKE_OUT = 7;
  public static final int INTAKE_IN = 8;

  public static final int TARGET_SHOOTER = 9;
  public static final int FLYWHEEL_SLOW = -1;
  public static final int FLYWHEEL_MID = -1;
  public static final int FLYWHEEL_FAST = -1;

  public static final int INDEX_UP = 5;
  public static final int INDEX_OUT = 6;

  public static final int LIFT_UP = -1;
  public static final int LIFT_DOWN = -1;
  public static final int LIFT_FORWARD = -1;
  public static final int LIFT_BACK = -1;
}
