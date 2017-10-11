#!/bin/bash
cd class
jar cfm GameOfLife.jar ../src/manifest.txt *.class res
mv GameOfLife.jar ../
cd ..
