"""Main Processing File"""
# Dependencies
import cv2
from tensorflow import lite
# Local Imports
from const import *
from util import *

# Example Detection: [{'bounding_box': array([0.26929605, 0.5280721 , 0.48070395, 0.71024466], dtype=float32), 'class_id': 0.0, 'score': 0.796875}, {'bounding_box': array([0.27319047, 0.3893147 , 0.48459837, 0.55939335], dtype=float32), 'class_id': 1.0, 'score': 0.7578125}]


# Load Labels into List
classes = LABELS.copy()

def run_odt(image_path, interpreter, threshold=0.5):
    """Run object detection on the input image and return the detection results"""
    # Load the input shape required by the model
    _, input_height, input_width, _ = interpreter.get_input_details()[0]["shape"]

    # Load the input image and preprocess it
    preprocessed_image = preprocess_image(
        image_path, (input_height, input_width)
    )[0] # Only Gets Preprocessed Image Instead of Both Processed and Original

    # Run Object Detection on Image
    return detect_objects(interpreter, preprocessed_image, threshold=threshold)


def main():
  # Load TFLite model
  interpreter = lite.Interpreter(model_path=MODEL_PATH, num_threads=NUM_THREADS)
  interpreter.allocate_tensors()

  # Start Video Capture from Pre-Configured MJPEG Server
  vid = cv2.VideoCapture(SERVER)

  # Set Video Dimensions to Trained Dimensions
  vid.set(3, CAM_WIDTH) # Width
  vid.set(4, CAM_HEIGHT) # Height
  
  while True:
      # Read Frame from Webcam
      ret, frame = vid.read()
      # Empty Frame Handling
      if not ret:
        print("Frame Skipped")
        continue
      # Write Frame to File for TF Processing
      cv2.imwrite("frame.png", frame)

      # Run inference and draw detection result on the local copy of the original file
      detection_result = run_odt(
          "frame.png", interpreter, threshold=DETECTION_THRESHOLD
      )

      print(detection_result)

      # Exit on "q" Pressed
      if cv2.waitKey(1) & 0xFF == ord("q"):
          break

  # Release Webcam and Destroy Lingering Frame
  vid.release()
  cv2.destroyAllWindows()

if __name__ == "__main__":
  main()