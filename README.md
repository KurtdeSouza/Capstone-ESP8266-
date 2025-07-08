Disaster Drone 📡🚁

An Android + IoT system designed to restore emergency communication in disaster zones without cellular infrastructure.

📝 Overview

Disaster Drone is a capstone project built by a team of four, using Agile methodologies, to help disaster survivors communicate when power and cellular networks fail.
The system uses a drone equipped with an ESP8266 module to broadcast a Wi-Fi hotspot. Nearby users running our Android app can connect and exchange real-time messages using UDP.

🚀 Features

Android app (Java, built in Android Studio) for survivors and EMS to:

Connect to the drone's Wi-Fi hotspot

Send and receive short messages to coordinate rescue efforts

Arduino application running on ESP8266 module to:

Host up to 10 devices simultaneously

Forward UDP datagram packets (max 255 bytes) between connected devices

Real-world use case: earthquake, flood, or other events where traditional communication infrastructure is unavailable

🛠 Technology Stack

Android SDK (Java): User-facing application

ESP8266 (Arduino): Wi-Fi hotspot & message forwarding

UDP protocol: Lightweight, connectionless communication

Agile (Scrum): Managed development timeline and tasks

📷 System Architecture

[ Drone-mounted ESP8266 Wi-Fi Hotspot ]
                ⬇
[ User Devices Running Android App ]
                ⬇
[ Send & Receive Messages over UDP ]

⚙ How It Works

The drone flies over the disaster area and activates the ESP8266 hotspot.

Nearby devices connect to the hotspot using the Android app.

Survivors and EMS send short messages via UDP.

The Arduino script on the ESP8266 receives and forwards packets to other connected devices.

📦 Installation

Android App

Clone the repository.

Open in Android Studio.

Build and install on your device.

ESP8266 Firmware

Open the Arduino IDE.

Flash the esp8266_drone_server.ino script to your ESP8266 module.

✅ Status

✔ Prototype completed and tested

✔ Supports up to 10 connected devices

⚙ Future improvements: encryption, message history, and UI polish

