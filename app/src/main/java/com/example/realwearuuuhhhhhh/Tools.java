package com.example.realwearuuuhhhhhh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class Tools extends AppCompatActivity {

    private Button deleteButton;
    private Button addButton;

    private Button home;

    private int selectedItem;
    static ArrayList<String> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
        getSupportActionBar().hide();
        ListView listView = findViewById(R.id.list_view);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);

        home = findViewById(R.id.homeButton);
        // Get the list from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyTools", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        if (allEntries.size() == 0) {
            this.items = new ArrayList<>();
            items.add("S/N:");
            items.add("HZA1-11000153");
            items.add("HZA2-11000156");
            items.add("HZA3-11000157");
            items.add("HZA4-11000158");
            items.add("HZA5-11000159");
            items.add("HZB1-11000154");
            items.add("HZB2-11000160");
            items.add("HZB3-11000161");
            items.add("HZB4-11000162");
            items.add("HZB5-11000163");
        }
        else {
            String json = sharedPreferences.getString("list", null);

            // Convert the JSON string back to an ArrayList
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            this.items = gson.fromJson(json, type);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, items);
        listView.setAdapter(adapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Set a click listener on the list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = listView.getCheckedItemPosition();

                if (selectedItem != -1) {
                    deleteButton.setVisibility(View.VISIBLE);

                } else {
                    deleteButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Tool");

// Inflate the custom layout
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.add_new_tool, null);

// Set the custom layout as the view for the AlertDialog
        builder.setView(view);

// Get a reference to the EditText view
        final EditText input = view.findViewById(R.id.addTool);

        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the text from the input field
                String text = input.getText().toString();
                adapter.add(text);
                Gson gson = new Gson();
                String json = gson.toJson(items);

                SharedPreferences sharedPreferences = getSharedPreferences("MyTools", MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("list", json);
                editor.apply();
                input.setText("");
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                input.setText("");
            }
        });
        AlertDialog dialog = builder.create();


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.remove(adapter.getItem(selectedItem));
                listView.setItemChecked(selectedItem, false);
                Gson gson = new Gson();
                String json = gson.toJson(items);

                SharedPreferences sharedPreferences = getSharedPreferences("MyTools", MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("list", json);
                editor.apply();
                deleteButton.setVisibility(View.INVISIBLE);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tools.this, SelectTool.class);
                startActivity(intent);
            }
        });
    }

    public ArrayList<String> returnList()
    {
        return items;
    }
}
//ArrayList<String> items = new ArrayList<>();
        /*items.add("Hydraulic pump");
        items.add("Pressure gauge");
        items.add("Torque and tension heads");*/

       /* // Convert the list to a JSON string
        Gson gson = new Gson();
        String json = gson.toJson(items);

// Get a reference to the shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyTools", MODE_PRIVATE);

// Save the list to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("list", json);
        editor.apply();*/
