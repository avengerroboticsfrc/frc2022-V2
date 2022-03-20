import cv2
import json
import numpy as np

# this should be used as little as possible since on the pi,
# we will have to use the tflite_runtime library instead
import tensorflow as tf
# import tflite_runtime as tflite

# can't find much documentation on these, they're official wpilib libraries
# I think they're about equivalent to their java counterparts:
# https://docs.wpilib.org/en/stable/docs/software/vision-processing/introduction/cameraserver-class.html
# from cscore import CameraServer
# from networktables import NetworkTables

import util
import const

# Print Tensorflow version
print(tf.__version__)

# Check available GPU devices.
print(f"The following GPU devices are available: {tf.test.gpu_device_name()}")

# tf_table = NetworkTables.getTable('tensorflow')

# raw_out = CameraServer.putVideo('RAW', width, height)
# processed_out = CameraServer.putVideo('Processed', p_width, p_height)

# Allocating new images is very expensive, always try to preallocate
img = np.zeros(
    shape=(const.PROCESSED_WIDTH, const.PROCESSED_HEIGHT, 3),
    dtype=np.uint8
)
