## Variables
To train this model reliably, we need to balance exposing the model to different environments and eliminating unimportant variables.
This means that we need to make sure that the model is not overfitting to one environment or not working at all.

### KIM
Take images in various:
- lighting conditions
- angles
- distances
- robot counts (robot in the frame)
It might be helpful to add various objects to the background of the images to stop them from being overfit.