#! /bin/bash

echo "Setting up link to java source for Android Studio"

cd ./android/app/src/main/
ln -s ../../../../java/ java
