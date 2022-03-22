"""Only Used to Test Object Detection with OpenCV"""
# Dependencies
import cv2
from PIL import Image
import numpy as np
import tensorflow as tf
from time import time

# Local Imports
from const import *
from util import *

# Load Labels into List
classes = LABELS.copy()


def run_odt_and_draw_results(image_path, interpreter, threshold=0.5):
    """Run object detection on the input image and draw the detection results"""
    # Load the input shape required by the model
    _, input_height, input_width, _ = interpreter.get_input_details()[0]["shape"]

    # Load the input image and preprocess it
    preprocessed_image, original_image = preprocess_image(
        image_path, (input_height, input_width)
    )

    # Run object detection on the input image
    results = detect_objects(interpreter, preprocessed_image, threshold=threshold)

    # Plot the detection results on the input image
    original_image_np = original_image.numpy().astype(np.uint8)
    for obj in results:
        # Convert the object bounding box from relative coordinates to absolute
        # coordinates based on the original image resolution
        ymin, xmin, ymax, xmax = obj["bounding_box"]
        xmin = int(xmin * original_image_np.shape[1])
        xmax = int(xmax * original_image_np.shape[1])
        ymin = int(ymin * original_image_np.shape[0])
        ymax = int(ymax * original_image_np.shape[0])

        # Find the class index of the current object
        class_id = int(obj["class_id"])

        # Draw the bounding box and label on the image
        color = COLORS[class_id]
        cv2.rectangle(original_image_np, (xmin, ymin), (xmax, ymax), color, 2)
        # Make adjustments to make the label visible for all objects
        y = ymin - 15 if ymin > 30 else ymin + 15
        label = "{}: {:.0f}%".format(classes[class_id], obj["score"] * 100)
        cv2.putText(
            original_image_np, label, (xmin, y), cv2.FONT_HERSHEY_SIMPLEX, 0.5, color, 2
        )
    return original_image_np.astype(np.uint8)

def main():
    # Start Video Capture
    vid = cv2.VideoCapture(0)

    # Set Video Dimensions to Trained Dimensions
    vid.set(3, CAM_WIDTH) # Width
    vid.set(4, CAM_HEIGHT) # Height

    # Load TFLite model
    interpreter = tf.lite.Interpreter(model_path=MODEL_PATH, num_threads=NUM_THREADS)
    interpreter.allocate_tensors()

    while True:
        # Read Frame from Webcam
        ret, frame = vid.read()
        # Write Frame to File for TF Processing
        cv2.imwrite("frame.png", frame)

        # Run Inference and Draw Detection Result on Local Copy of Original File
        it = time()
        detection_result_image = run_odt_and_draw_results(
            "frame.png", interpreter, threshold=DETECTION_THRESHOLD
        )
        print(f"Elapsed: {time() - it}")

        # Show Detection Result
        fin_img = cv2.cvtColor(
            np.asarray(Image.fromarray(detection_result_image)), cv2.COLOR_RGB2BGR
        )
        cv2.imshow("frame", fin_img)

        # Exit on "q" Pressed
        if cv2.waitKey(1) & 0xFF == ord("q"):
            break

    # Release Webcam and Destroy Windows
    vid.release()
    cv2.destroyAllWindows()

if __name__ == "__main__":
    main()