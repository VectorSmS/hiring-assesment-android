package com.somesh.temperatureupdates;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class TemperatureMonitor extends AppCompatActivity {

    private TextView currentTemperature;
    private Socket temperatureSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_monitor);
        String serverAddress = getIntent().getStringExtra("SERVER_ADDRESS");
        Log.d("connection", serverAddress);
        currentTemperature = (TextView) findViewById(R.id.currentTemperature);
        currentTemperature.setText("Testing current temperature");
        try {
            temperatureSocket = IO.socket(serverAddress);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        temperatureSocket.connect();
        currentTemperature.setText("connected to server. waiting for data");
        temperatureSocket.emit("Status", "Client Connected");
        temperatureSocket.on("temperature", onNewMessage);

    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject)args[0];
                    Log.d("connection", data.toString());
                    try {
                        String value = data.getString("temperature");
                        currentTemperature.setText(value);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
}