# --------- Util Constants ---------
MODEL_PATH = 'tensorflow\data\scuffed.tflite' # Path to Model
DETECTION_THRESHOLD = 0.3 # Lower Threshold for Ball Detection
CAM_WIDTH = 500
CAM_HEIGHT = 500
NUM_THREADS = 1 # "1" if Unspecified/Unknown
SERVER = 'http://roboRIO-7451-frc.local:1181'

# ------------- Testing Constants -------------
LABELS = ['red', 'blue'] # Ball Labels
COLORS = [(255, 0, 0), (0, 0, 255)] # Label Box Color ([red_color, blue_color]) (test.py only)
