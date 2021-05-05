package com.example.Coba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.Coba.database.DBControll;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class AddFriend extends AppCompatActivity {

    private TextInputEditText tiNama, tiNoTelfon;
    private Button btnSave;
    String nama, telpon;
    DBControll dbControll = new DBControll(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teman);

        tiNama = findViewById(R.id.addNama);
        tiNoTelfon = findViewById(R.id.addContact);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = tiNama.getText().toString();
                telpon = tiNoTelfon.getText().toString();

                if (nama.isEmpty() || telpon.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Harap lengkapi data!", Toast.LENGTH_SHORT).show();
                } else {

                    HashMap<String, String> values = new HashMap<>();
                    values.put("nama", nama);
                    values.put("telpon", telpon);

                    dbControll.insertData(values);
                    openMainActivity();
                }
            }
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}