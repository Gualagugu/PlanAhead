package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button submit;
    private Spinner select;
    private List<ClassInfo> ClassInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readClassInfo();
        addItemsOnSpinner();
        addListenerOnButton();
    }
    public void readClassInfo() {
        InputStream is = getResources().openRawResource(R.raw.sp2018);
        BufferedReader read = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line = "";
        try {
            while ((line = read.readLine()) != null) {
                String[] tokens = line.split(",");
                ClassInfo theInfo = new ClassInfo();
                theInfo.setSubject(tokens[0]);
                theInfo.setIndex(Integer.parseInt(tokens[1]));
                theInfo.setAverageGPA(Double.parseDouble(tokens[2]));
                theInfo.setInstructor(tokens[3]);
                theInfo.setPeople(Integer.parseInt(tokens[5]));
                theInfo.setPercentageA(Double.parseDouble(tokens[6]));
                ClassInfoList.add(theInfo);
                Log.d("MyActivity", "Created" + theInfo.toString());
            }
        } catch (Exception e) {
            Log.wtf("MyActivity", "Error" + line, e);
            e.printStackTrace();
        }
    }

    public void addItemsOnSpinner() {
        select = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("list 1");
        list.add("list 2");
        list.add("list 5");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select.setAdapter(dataAdapter);
    }
    public void addListenerOnButton() {
        select = (Spinner) findViewById(R.id.spinner);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this,
                        "The average GPA for this course is: " +
                                "\nSpinner: "+ String.valueOf(select.getSelectedItem()),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

}
