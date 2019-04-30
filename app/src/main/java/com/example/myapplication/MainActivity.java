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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button submit;
    private Spinner select;
    private Spinner index;
    private Button choose;
    private Button choose2;
    private Button submit2;
    private Spinner instructor;
    double averageGPA;
    double percentageA;
    //The two pieces of data that's going to be displayed in the toast message
    private List<ClassInfo> ClassInfoList = new ArrayList<>();
    //A List that stores all ClassInfo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readClassInfo();
        addItemsOnSpinner();
        addListenerOnButton2();
        addItemsOnSpinner2();
        addListenerOnButton();
        addItemsOnSpinner3();
        addListenerOnChoose2();
        addListenerOnSubmit2();
    }

    private void addListenerOnSubmit2() {
        submit2 = findViewById(R.id.submit2);
        instructor = findViewById(R.id.spinner3);
        select = findViewById(R.id.spinner);
        index =  findViewById(R.id.spinner2);
        submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Double> averageGPAList = new ArrayList<>();
                List<Double> percentageAList = new ArrayList<>();
                List<Integer> peopleList = new ArrayList<>();
                for(int i = 0; i < ClassInfoList.size(); i++) {
                    if (String.valueOf(select.getSelectedItem()).equals(ClassInfoList.get(i).getSubject())
                            && index.getSelectedItem().equals(ClassInfoList.get(i).getIndex())
                            && String.valueOf(instructor.getSelectedItem()).equals(ClassInfoList.get(i).getInstructor())) {
                        averageGPAList.add(ClassInfoList.get(i).getAverageGPA());
                        percentageAList.add(ClassInfoList.get(i).getPercentageA());
                        peopleList.add(ClassInfoList.get(i).getPeople());
                    }
                }
                int totalPeople = 0;
                for (int i = 0; i < peopleList.size(); i++) {
                    totalPeople = totalPeople + peopleList.get(i);
                }
                double totalGPA = 0;
                for (int i = 0; i < averageGPAList.size(); i++) {
                    totalGPA = totalGPA + peopleList.get(i) * averageGPAList.get(i);
                }
                double totalA= 0;
                for (int i = 0; i < percentageAList.size();i++) {
                    totalA = totalA + peopleList.get(i) * percentageAList.get(i);
                }
                averageGPA = totalGPA/totalPeople;
                percentageA = totalA/totalPeople;
                averageGPA = Math.round(averageGPA * 100) / 100.0;
                percentageA = Math.round(percentageA * 100);
                Toast.makeText(MainActivity.this,
                        "The average GPA for this section is: " + averageGPA + "  "
                                + "The percentage of student geting A in the section is " + percentageA + "%",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addListenerOnChoose2() {
        choose2 = findViewById(R.id.choose2);
        choose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemsOnSpinner3();
            }
        });
    }

    private void addItemsOnSpinner3() {
        instructor = findViewById(R.id.spinner3);
        List<String> list3 = new ArrayList<String>();
        for (int i = 0; i < ClassInfoList.size(); i++) {
            if (String.valueOf(select.getSelectedItem()).equals(ClassInfoList.get(i).getSubject())
                && index.getSelectedItem().equals(ClassInfoList.get(i).getIndex())) {
                if (list3.contains(ClassInfoList.get(i).getInstructor())) {
                    continue;
                }
                list3.add(ClassInfoList.get(i).getInstructor());
            }
        }
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        instructor.setAdapter(dataAdapter3);
    }

    private void readClassInfo() {
        InputStream is = getResources().openRawResource(R.raw.source);
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
        }//debugging
    }

    public void addItemsOnSpinner() {
        select = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < ClassInfoList.size(); i++) {
            if (ClassInfoList.get(i).getSubject() == null) {
                continue;
            }
            if (list.contains(ClassInfoList.get(i).getSubject())) {
                continue;
            }
            list.add(ClassInfoList.get(i).getSubject());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select.setAdapter(dataAdapter);
    }
    public void addListenerOnButton2() {
        choose = (Button) findViewById(R.id.choose);

        choose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addItemsOnSpinner2();
            }
        });
    }
    public void addItemsOnSpinner2() {
        index = (Spinner) findViewById(R.id.spinner2);

        List<Integer> list2 = new ArrayList<Integer>();
        for(int i = 0; i < ClassInfoList.size(); i++) {
            if (String.valueOf(select.getSelectedItem()).equals(ClassInfoList.get(i).getSubject())) {
                if(list2.contains(ClassInfoList.get(i).getIndex())) {
                    continue;
                }
                list2.add(ClassInfoList.get(i).getIndex());
            }
        }
        ArrayAdapter<Integer> dataAdapter2 = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        index.setAdapter(dataAdapter2);
    }
    public void addListenerOnButton() {
        select = (Spinner) findViewById(R.id.spinner);
        submit = (Button) findViewById(R.id.submit);
        index = (Spinner) findViewById(R.id.spinner2);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<Double> averageGPAList = new ArrayList<>();
                List<Double> percentageAList = new ArrayList<>();
                List<Integer> peopleList = new ArrayList<>();
                for(int i = 0; i < ClassInfoList.size(); i++) {
                    if (String.valueOf(select.getSelectedItem()).equals(ClassInfoList.get(i).getSubject())
                        && index.getSelectedItem().equals(ClassInfoList.get(i).getIndex())) {
                        averageGPAList.add(ClassInfoList.get(i).getAverageGPA());
                        percentageAList.add(ClassInfoList.get(i).getPercentageA());
                        peopleList.add(ClassInfoList.get(i).getPeople());
                    }
                }
                int totalPeople = 0;
                for (int i = 0; i < peopleList.size(); i++) {
                    totalPeople = totalPeople + peopleList.get(i);
                }
                double totalGPA = 0;
                for (int i = 0; i < averageGPAList.size(); i++) {
                    totalGPA = totalGPA + peopleList.get(i) * averageGPAList.get(i);
                }
                double totalA= 0;
                for (int i = 0; i < percentageAList.size();i++) {
                    totalA = totalA + peopleList.get(i) * percentageAList.get(i);
                }
                averageGPA = totalGPA/totalPeople;
                percentageA = totalA/totalPeople;
                averageGPA = Math.round(averageGPA * 100) / 100.0;
                percentageA = Math.round(percentageA * 100);
                Toast.makeText(MainActivity.this,
                        "The average GPA for this course is: " + averageGPA + "  "
                                + "The percentage of student geting A in the course is " + percentageA + "%",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
