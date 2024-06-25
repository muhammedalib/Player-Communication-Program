# Player Communication Program

## Overview

The Player Communication Program allows players to interact and communicate efficiently in both single-process and multi-process modes. The single-process mode uses threads for concurrency, while the multi-process mode operates across different JVM instances using file-based communication.

## Problem Statement

Given a Player class, instances of which can communicate with other Players:
1. Create two Player instances.
2. One player (the initiator) sends a message to the second player.
3. When a player receives a message, it replies with a message that includes the received message concatenated with a counter value representing the number of messages this player has already sent.
4. The program finalizes after the initiator has sent and received back 10 messages.
5. Players can run in the same Java process (single-process mode) or in separate Java processes (multi-process mode).

## Requirements

- Java 8 or higher
- Maven

## Technologies

- Java
- Multi-threading
- Multi-processing
- JVM
- JUnit
- Maven

## Class Definitions

### AbstractPlayer

An abstract class providing a base implementation for a player that can send and receive messages. Subclasses must implement specific methods for their communication mode.

### SingleProcessPlayer

Represents a player that communicates within a single JVM process using blocking queues.

Responsibilities:
- Sending messages to the other player.
- Receiving messages from the other player.
- Tracking the number of messages sent.

### MultiProcessPlayer

Represents a player that communicates between different JVM processes using files.

Responsibilities:
- Sending messages by writing to a file.
- Receiving messages by reading from a file.
- Tracking the number of messages sent.

### SingleProcessManager

Manages player processes in single-process mode where both players run in the same JVM process.

Responsibilities:
- Initializing players.
- Setting up communication channels.
- Starting and managing player threads.

### MultiProcessManager

Manages player processes in multi-process mode where each player runs in a separate JVM process.

Responsibilities:
- Starting each player in a separate process.
- Ensuring the correct setup of communication files.
- Managing the lifecycle of each player process.

### Main

The entry point of the application. It decides whether to run the players in single-process or multi-process mode based on the provided arguments.

### Test Classes

#### SingleProcessPlayerTest

This test class verifies the functionality of the SingleProcessPlayer class.

Responsibilities:
- Ensure messages are correctly sent and received within the same JVM.
- Verify the message counter is incremented correctly.
- Validate that the stop condition (10 messages sent and received) is properly handled.

#### MultiProcessPlayerTest

This test class verifies the functionality of the MultiProcessPlayer class.

Responsibilities:
- Ensure messages are correctly sent and received across different JVM instances.
- Verify the file-based communication mechanism works as expected.
- Validate that the message counter is incremented correctly.
- Ensure the stop condition (10 messages sent and received) is properly handled.

## How to Build and Run

### Build the Project


## How to Build and Run

### Build the Project


mvn clean install

Running the Script in Unix/Linux

To run the single-process version:

chmod +x run.sh

./run.sh

To run the multi-process version:

chmod +x run.sh

./run.sh multi


Running the Script in Windows

To run the single-process version:

mvn exec:java -Dexec.mainClass="Player.Main"

To run the multi-process version:

mvn exec:java -Dexec.mainClass="Player.Main" -Dexec.args="multi"

Testing

Unit tests are written using JUnit. To run the tests, execute:

mvn test








