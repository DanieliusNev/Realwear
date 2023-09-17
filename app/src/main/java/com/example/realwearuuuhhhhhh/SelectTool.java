package com.example.realwearuuuhhhhhh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class SelectTool extends AppCompatActivity {
    private Tools tools;
    private ArrayList<String> items;
    private Button backButton, nextButton, editTurbines;
    private EditText component;
    private int selectedItem = -1;
    static String chosenTool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calibrated_tool_selection);
        getSupportActionBar().hide();
        tools = new Tools();
        //items = tools.returnList();
        ListView listView = findViewById(R.id.toolsSelection);
        component = findViewById(R.id.component);
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
            Gson gson = new Gson();
            String json = gson.toJson(items);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("list", json);
            editor.apply();
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

        backButton = findViewById(R.id.backToToolsButton);
        editTurbines = findViewById(R.id.EditTurbines);
        SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(this);
        String componentText = sharedPreferences2.getString("Component", "");
        if (!componentText.isEmpty()) {
            component.setText(componentText);
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = component.getText().toString();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SelectTool.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Component", inputText);
                editor.apply();
                Intent intent = new Intent(SelectTool.this,Main.class);
                startActivity(intent);
            }
        });
        editTurbines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = component.getText().toString();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SelectTool.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Component", inputText);
                editor.apply();
                Intent intent = new Intent(SelectTool.this,Tools.class);
                startActivity(intent);
            }
        });

        // Set a click listener on the list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = listView.getCheckedItemPosition();
                setTools(items.get(selectedItem));

            }
        });
        nextButton = findViewById(R.id.selectedButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedItem != -1) {
                    String inputText = component.getText().toString();
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SelectTool.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Component", inputText);
                    editor.apply();
                    Intent intent = new Intent(SelectTool.this,EmployeeList.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(SelectTool.this, "Please select a turbine", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public String getChosenTool()
    {
        return chosenTool;
    }
    public void setTools(String tool)
    {
        this.chosenTool = tool;
    }
}
