#! /bin/bash
rm checkerDesk.jar
rm checkerServerMessage.jar
rm checkerUI.jar
rm checkers.jar
jar cf checkerDesk.jar edu/wright/crowningkings/desktop/*.class desktop/*.png desktop/*.jpg
jar cf checkerServerMessage.jar edu/wright/crowningkings/base/ServerMessage/*.class
jar cf checkerUI.jar edu/wright/crowningkings/base/UserInterface/*.class
jar cmf MANIFESTBASE.MF checkers.jar edu/wright/crowningkings/base/*.class

echo "jar creation complete, please execute checkers.jar to run the program. Note that checkerDesk.jar , checkerServerMessage.jar, checkerUI.jar must be in the same directory."
