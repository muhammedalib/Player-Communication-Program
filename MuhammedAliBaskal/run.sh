#!/bin/bash

echo "Running single-process version..."
mvn exec:java -Dexec.mainClass="Player.Main"

echo "Running multi-process version..."
mvn exec:java -Dexec.mainClass="Player.Main" -Dexec.args="multi"
