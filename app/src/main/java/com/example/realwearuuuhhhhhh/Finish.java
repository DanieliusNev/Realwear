package com.example.realwearuuuhhhhhh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class Finish extends AppCompatActivity {

    static ArrayList<String> listOfCheck;
    private Button exit;
    private Button pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish);
        getSupportActionBar().hide();
        exit = findViewById(R.id.newCheck);
        pdf = findViewById(R.id.openPDF);
        SharedPreferences sharedPreferences = getSharedPreferences("CheckList", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        if (allEntries.size() == 0) {
            this.listOfCheck = new ArrayList<>();
        }
        else {
            String json = sharedPreferences.getString("list", null);

            // Convert the JSON string back to an ArrayList
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            this.listOfCheck = gson.fromJson(json, type);
        }
        int listSize = listOfCheck.size();
        int numRows = listSize/2;
        int numCols = 2;
        String[][] array = new String[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int index = i * numCols + j;
                if (index < listSize) {
                    array[i][j] = listOfCheck.get(index);
                } else {
                    array[i][j] = null;
                }
            }
        }
        // Create a RecyclerView and set its layout manager
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        // Create an adapter for the list of items
        TableAdapter adapter = new TableAdapter(array);

        // Set the adapter as the data source for the RecyclerView
        recyclerView.setAdapter(adapter);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Finish.this, Main.class);
                startActivity(intent);
            }
        });
        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Finish.this, PDF.class);
                startActivity(intent);
            }
        });

    }
}
