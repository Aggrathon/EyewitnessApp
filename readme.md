# Eyewitness
An app for a psychology experiment.   

## Eyewitness Light
There is an alternative "light" version you can find [here](https://github.com/Aggrathon/EyewitnessApp/tree/light).  


## Download
[Download the latest Android apks here (both versions).](https://github.com/Aggrathon/EyewitnessApp/releases)


## File structure
The app reads images from and writes logs to a publicly available directory (by default in 'Documents'). This is how the files are structured:

| Folder | Files | Instructions |
| --- | --- | --- |
| Eyewitness/ | | The base folder. |
| Eyewitness/logs/ | | Here the logs are stored. |
| Eyewitness/images/ | | This folder contains folders with lineups. |
| Eyewitness/images/1/ | | Lineup 1. |
| Eyewitness/images/1/ | image.png | The folder contains 8-9 images for the lineup, the names can be freely chosen, but one must contain "correct" for it to be automatically recognised as correct. |
| Eyewitness/images/X/ | | You can have 4 different lineups (the name is the number). The layout is the same as for 1. |
| Eyewitness/images/Test/ | | The tutorial lineup, otherwise same as the lineup 1. |
