#! /bin/bash

echo "Setting up links to different source code locations."

ln -s ../android/app/src/main/java/edu/wright/crowningkings/android/
ln -s ../android/crowningkingsbase/src/main/java/edu/wright/crowningkings/base/
ln -s ../android/crowningkingsdesktop/src/main/java/edu/wright/crowningkings/desktop/

echo "Done setting up links. All source files are located in the src folder at
the root of the repo."

