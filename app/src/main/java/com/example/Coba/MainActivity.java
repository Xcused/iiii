package com.example.Coba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.Coba.adapter.TemanAdapter;
import com.example.Coba.database.DBControll;
import com.example.Coba.database.Friends;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TemanAdapter adapter;
    private ArrayList<Friends> friendsArrayList;
    DBControll dbControll = new DBControll(this);
    String id, nama, telpon;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.reFriend);
        fabAdd = findViewById(R.id.recycleview);
        readData();
        adapter = new TemanAdapter(friendsArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddFriend.class);
                startActivity(intent);
            }
        });
    }

    public void readData() {
        ArrayList<HashMap<String, String>> listTeman = dbControll.getAllTeman();
        friendsArrayList = new ArrayList<>();
        for (int i=0; i < listTeman.size(); i++) {
            Friends friends = new Friends();
            friends.setId(listTeman.get(i).get("id"));
            friends.setNama(listTeman.get(i).get("nama"));
            friends.setTelpon(listTeman.get(i).get("telpon"));

            friendsArrayList.add(friends);
        }
    }
}