package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button submit;
    private Spinner select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addItemsOnSpinner();
        addListenerOnButton();
        readClassInfo();
    }
    private List<ClassInfo> ClassInfoList = new ArrayList<>();
    private void readClassInfo() {
        InputStream is = getResources().openRawResource(R.raw.sp2018);
        BufferedReader read = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line;
        try {
            while ((line = read.readLine()) != null) {
                String[] tokens = line.split(",");
                ClassInfo theInfo = new ClassInfo();
                theInfo.setSubject(tokens[0]);
                theInfo.setIndex(Integer.parseInt(tokens[1]));
                theInfo.setAverageGPA(Double.parseDouble(tokens[2]));
                theInfo.setInstructor(tokens[3]);
                theInfo.setPeople(Integer.parseInt(tokens[4]));
                theInfo.setPercentageA(Double.parseDouble(tokens[5]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addItemsOnSpinner() {
        select = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("list 1");
        list.add("list 2");
        list.add("list 3");
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
                        "OnClickListener : " +
                                "\nSpinner: "+ String.valueOf(select.getSelectedItem()),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

}
