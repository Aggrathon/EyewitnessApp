# Eyewitness Light
An app for a psychology experiment.  
This is the alternative "light" version.  


## Eyewitness Original
The original version you can find [here](https://github.com/Aggrathon/EyewitnessApp/).  


## Download
[Download the light version Android apks here.](https://github.com/Aggrathon/EyewitnessApp/releases/download/v1.1-light/eyewitness_light.apk)  
[Download the latest Android apks here (both versions).](https://github.com/Aggrathon/EyewitnessApp/releases)


## File structure
The app reads images from and writes logs to a publicly available directory (by default in 'Documents'). This is how the files are structured:

| Folder | Files | Instructions |
| --- | --- | --- |
| EyewitnessLight/ | | The base folder. |
| EyewitnessLight/ | instructions.txt | A file with optional extra information to be shown in the beginning of the experiment. |
| EyewitnessLight/logs/ | | Here the logs are stored. |
| EyewitnessLight/images/ | | This folder contains folders with lineups. |
| EyewitnessLight/images/A1/ | | Lineup 1 for option 1. |
| EyewitnessLight/images/A1/ | instructions.txt | Optional file with header replacement for the lineup |
| EyewitnessLight/images/A1/ | image.png | The folder contains 8-9 images for the lineup, the names can be freely chosen, but one must contain "correct" for it to be automatically recognised as correct. |
| EyewitnessLight/images/AX/ | | You can have multiple lineups for option 1. Just keep the "A" in the folder name. The layout is the same as for A1. |
| EyewitnessLight/images/BX/ | | The lineups for option 2 must have a "B" in their names, otherwise it is the same as for lineup A1 |
