package com.example.drone_communication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;



import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class MainActivity extends AppCompatActivity {

    Button submitBtn;
    LinearLayout sentLayout;

    EditText toSend;
    private static final int SERVER_PORT = 4210; // Port to listen on and send to
    private static final int PACKET_SIZE = 1024; // Maximum size of the packet
    public String return_msg = "";
    public Boolean isSent = true;
    public String username = "";
    public boolean isLogged = false;
    int lpurple = Color.parseColor("#9370db");
    int lblue= Color.parseColor("#8aa3ba");

    public void sendMessageUDP(final String message, DatagramSocket socket) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] data = message.getBytes();
                    InetAddress serverAddress = InetAddress.getByName("192.168.4.1");
                    DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, SERVER_PORT);
                    System.out.println("here");
                    socket.send(packet);
                    System.out.println("here");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
        builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        NetworkRequest request = builder.build();
        connManager.requestNetwork(request, new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                connManager.bindProcessToNetwork(network);
            }
        });

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {

            throw new RuntimeException(e);
        }



        submitBtn = findViewById(R.id.submitButton);
        sentLayout = findViewById(R.id.Sent);

        toSend = findViewById(R.id.toSend);
        sendMessage("Welcome to EMS drone service");
        sendMessage("What is your name?");
        DatagramSocket finalSocket = socket;





        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLogged) {
                    String text = String.valueOf(toSend.getText());
                    String myScreen = "You: " + text;
                    text = username + ": " + text;
                    sendMessage(myScreen); // Display sent message in the chat
                    sendMessageUDP(text, finalSocket);
                }else{
                    username = String.valueOf(toSend.getText());
                    sendMessage("You: " + username);
                    sendMessage("You may now communicate");
                    isLogged = true;
                }
                toSend.setText("");
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    LinearLayout recvLayout;
                    recvLayout = findViewById(R.id.Received);
                    while(true) {
                        TextView textView = new TextView(getApplicationContext());

                        InetAddress serverAddress = InetAddress.getByName("192.168.4.1");
                        byte[] receiveData = new byte[1024];
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length, serverAddress, SERVER_PORT);
                        System.out.println("Reached here");
                        finalSocket.receive(receivePacket);
                        System.out.println("Reached here1");
                        String modifiedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
                        System.out.println(modifiedSentence);
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                textView.setText(modifiedSentence);

                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                layoutParams.setMargins(0, 0, 0, 16); // Adjust bottom margin for each message
                                textView.setLayoutParams(layoutParams);
                                textView.setPadding(8, 4, 8, 4);
                                textView.setTextColor(Color.WHITE);
                                textView.setBackgroundColor(lpurple); // Set background color to transparent

                                // Add the message TextView to the sentLayout
                                recvLayout.addView(textView);
                                // Add a spacer
                                View spacer = new View(getApplicationContext());
                                LinearLayout.LayoutParams spacerParams = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        16 // Adjust height of the spacer as needed
                                );
                                spacer.setLayoutParams(spacerParams);
                                spacer.setBackgroundColor(Color.TRANSPARENT); // Set background color to transparent
                                recvLayout.addView(spacer);


                            }
                        });
                    }

                    // Assuming sendMessage is defined elsewhere

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        sendMessageUDP("setup", finalSocket);


    }
    private void sendMessage(String message) {
        // Create a TextView for the message
        TextView textView = new TextView(this);
        textView.setText(message);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, 16); // Adjust bottom margin for each message
        textView.setLayoutParams(layoutParams);
        textView.setPadding(8, 4, 8, 4);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(lblue); // Set background color to transparent
        // Add the message TextView to the sentLayout
        sentLayout.addView(textView);

        // Add a spacer
        View spacer = new View(this);
        LinearLayout.LayoutParams spacerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                16 // Adjust height of the spacer as needed
        );
        spacer.setLayoutParams(spacerParams);
        spacer.setBackgroundColor(Color.TRANSPARENT); // Set background color to transparent
        sentLayout.addView(spacer);
    }
}