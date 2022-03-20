import cv2
import matplotlib.pyplot as plt

# For drawing onto the image.
import numpy as np
from PIL import Image
from PIL import ImageColor
from PIL import ImageDraw
from PIL import ImageFont
from PIL import ImageOps

import const

def display_image(image):
    fig = plt.figure(figsize=(20, 15))
    plt.grid(False)
    plt.imshow(image)

def load_img(frame):
    # Loading the file
    # img = cv2.imread(path)
    img = frame
    # Format for the Mul:0 Tensor
    img = cv2.resize(img, dsize=(const.PROCESSED_WIDTH, const.PROCESSED_HEIGHT), interpolation=cv2.INTER_CUBIC)
    # Numpy array
    np_image_data = np.asarray(img)
    # maybe insert float convertion here - see edit remark!
    np_final = np.expand_dims(np_image_data, axis=0)
    return np_final
