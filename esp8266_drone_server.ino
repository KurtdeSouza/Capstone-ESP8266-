#include <ESP8266WiFi.h>
const char *ssid = "ESP8266 Access Point"; // The name of the Wi-Fi network that will be created
const char *password = ""; 
#define MAX_CLIENTS 10
#define MAX_LINE_LENGTH 50
// Create an instance of the server
// specify the port to listen on as an argument
WiFiServer server(80);
WiFiClient *clients[MAX_CLIENTS] = { NULL };
char inputs[MAX_CLIENTS][MAX_LINE_LENGTH] = { 0 };

void setup() {
  Serial.begin(115200);
  WiFi.begin(ssid, password);
  server.begin();
}

void loop() {

  WiFiClient newClient = server.available();
  if (client) {
    Serial.println("new client");
    for (int i=0 ; i<MAX_CLIENTS ; ++i) {
        if (NULL == clients[i]) {
            clients[i] = new WiFiClient(newClient);
            break;
        }
     }
  }
  for (int i=0 ; i<MAX_CLIENTS ; ++i) {
    if (NULL != clients[i] && clients[i]->available() ) {
      char newChar = clients[i]->read();
      if ('\r' == newChar) {
        inputs[i][0] = NULL;
        clients[i]->flush();
        clients[i]->stop();
        delete clients[i];
        clients[i] = NULL;
      } else {
        // Add it to the string
        strcat(inputs[i], newChar);
        Serial.println(inputs[i]);
      }
    }
  }
}

