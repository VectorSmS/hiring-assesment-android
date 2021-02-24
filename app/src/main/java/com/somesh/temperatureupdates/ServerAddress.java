package com.somesh.temperatureupdates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ServerAddress extends AppCompatActivity {

    private EditText serverAddress;
    private Button connectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_address);

        serverAddress = (EditText) findViewById(R.id.serverAddress);
        connectButton = (Button) findViewById(R.id.connect);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TemperatureMonitor.class);
                intent.putExtra("SERVER_ADDRESS", serverAddress.getText().toString());
                startActivity(intent);
            }
        });
    }
}