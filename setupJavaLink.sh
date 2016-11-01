#! /bin/bash

echo "Setting up links to different source code locations."

if [ ! -d "src" ]; then
  mkdir src
fi

cd src

ln -s ../android/app/src/main/java/edu/wright/crowningkings/android/ android
ln -s ../android/crowningkingsbase/src/main/java/edu/wright/crowningkings/base/ base
cd ..

echo "Done setting up links. All source files are located in the src folder at
the root of the repo."
