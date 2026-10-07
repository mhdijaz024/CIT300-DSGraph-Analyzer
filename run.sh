#!/bin/bash
# ===================================================================
#  CIT300 Assignment 2
#  Data Structure and Graph Performance Analyzer
#  Compiles into bin/ and starts the application.
# ===================================================================

set -e
cd "$(dirname "$0")"

echo
echo "  Compiling the project..."

mkdir -p bin
javac -d bin src/structures/*.java src/algorithms/*.java src/performance/*.java src/ui/*.java src/app/*.java

echo "  Compilation successful."
echo
echo "  Choose what to run:"
echo "    1. The application"
echo "    2. The automated test suite"
echo
read -r -p "  Enter 1 or 2: " choice

if [ "$choice" = "2" ]; then
    java -cp bin app.StructureTest
else
    java -cp bin app.Main
fi
