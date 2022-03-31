package frc.robot.constants;

public class ButtonConstants {
  public static final int CONTROLLER_PORT = 0;
  public static final int BUTTON_PANEL_PORT = 1;

  public static final ControllerType CONTROLLER_TYPE = ControllerType.PS4;

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

  public static final int INTAKE_TOGGLE_AND_OPEN = 3;
  public static final int INTAKE_RETRACT = 4;
  public static final int INTAKE_OUT = 8;
  public static final int INTAKE_IN = 7;

  // TESTING BUTTONS (hopefully)
  public static final int FLYWHEEL_ON = 9;
  public static final int HOOD_UP = 12;
  public static final int HOOD_DOWN = 12;
  //public static final int HOOD_MIN = -1;
  //public static final int HOOD_MAX = -1;

  // can combine these later
  public static final int TARGET_SHOOTER = 12;
  public static final int SHOOT_BALL = 10;
  //public static final int HOOD_PRE_1 = -1;
  //public static final int HOOD_PRE_2 = -1;
  //public static final int HOOD_PRE_3 = -1;

  public static final int INDEX_UP = 5;
  public static final int INDEX_OUT = 6;

  public static final int LIFT_UP = 1;
  public static final int LIFT_DOWN = 2;
  //public static final int LIFT_FORWARD = 2;
  //public static final int LIFT_BACK = 4;

  public static enum ControllerType {
    PS4,
    Xbox
  }
}
