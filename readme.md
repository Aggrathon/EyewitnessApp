# Eyewitness App

An app for a psychology experiment.  
You can find the latest developments [here](https://github.com/Aggrathon/EyewitnessApp).  

## Eyewitness Light

There is an alternative "light" version you can find [here](https://github.com/Aggrathon/EyewitnessApp/tree/light).  

## Download

[Download the latest Android apk:s here.](https://github.com/Aggrathon/EyewitnessApp/releases)

## File structure

The app reads images from and writes logs to a publicly available directory (by default in 'Documents'). This is how the files are structured:

| Directory | Files | Instructions |
| --- | --- | --- |
| Eyewitness/ | | The base directory. Usually in the 'Documents' directory.  |
| Eyewitness/logs/ | | Here you can find all the stored logs. |
|| log_X.csv | The result from a single instance. X is the unique id. |
|| log_combined.csv | All the results combined into a single file. |
| Eyewitness/images/ | | This directory contains the line-ups. |
| Eyewitness/images/1/ | | Line-up 1 |
|| image.png | The directory contains 8-9 images for the line-up. The file names can be freely chosen, but must not contain "show". |
|| correct.png | One of the image names must contain "correct" for it to be automatically recognised as the correct answer. Otherwise the rules above apply. |
|| show.png | If using non-live shows (see settings) the show files must contain "show", but otherwise the naming is unconstrained (see below). |
|| show.mp4 | The shows can also be videos. |
|| show_blurred.png | To differentiate between normal image shows and blurred image shows, the blurred ones must have "blurred" somewhere in the name.
|| show_240.png | If the shows are constrained by distances, then the last thing in the filename before the ending must be a "_" followed by the distance. This also works for videos and blurred images.
| Eyewitness/images/X/ | | You can have 1-10 different line-ups (the X is the number). The layout is the same as above. |
| Eyewitness/images/Test/ | | The tutorial line-up, otherwise same as the line-ups above. |
