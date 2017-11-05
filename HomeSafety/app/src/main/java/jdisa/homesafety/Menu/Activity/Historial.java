package jdisa.homesafety.Menu.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jdisa.homesafety.Data_Form.Data;
import jdisa.homesafety.MainActivity;
import jdisa.homesafety.R;

public class Historial extends AppCompatActivity {
     DatabaseReference Data;
    private ArrayList<Data> datas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        final String value= getIntent().getStringExtra("getData");

        Data = FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH_Data);
        Data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot item: dataSnapshot.child(value).getChildren()
                     ) {

                    Data data;
                    data = item.getValue(Data.class);
                    datas.add(data);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.spinner4);
        String [] letras = {"Seleccione Parametro","Co2","Humedad","Propano","Temperatura"};
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, letras);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                Toast.makeText(adapterView.getContext(),
                        (String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();

                String selected = adapterView.getItemAtPosition(pos).toString();

                if (selected.equalsIgnoreCase("Co2")){
                    ArrayList<Entry> entries = new ArrayList<>();
                    ArrayList<String> labels = new ArrayList<>();
                    int count = 0;
                    for (Data item: datas
                         ) {

                        entries.add(new BarEntry((float)item.getCo2(), count++));
                        labels.add(item.getDay());

                    }

                    LineDataSet dataset = new LineDataSet(entries, "data");
                    dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    dataset.setDrawFilled(true);
                    dataset.setDrawValues(false);
                    LineData data = new LineData(labels, dataset);
                    LineChart lineChart = (LineChart) findViewById(R.id.chart100);
                    lineChart.setData(data);
                    lineChart.notifyDataSetChanged();
                    lineChart.invalidate();



                }
                    else if(selected.equalsIgnoreCase("Temperatura"))
                {

                    ArrayList<Entry> entries2 = new ArrayList<>();
                    ArrayList<String> labels2 = new ArrayList<>();
                    int count = 0;
                    for (Data item: datas
                            ) {

                        entries2.add(new BarEntry((float)item.getTemp(), count++));
                        labels2.add(item.getDay());
                    }

                    LineDataSet dataset = new LineDataSet(entries2, "data");
                    dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    dataset.setDrawFilled(true);
                    dataset.setDrawValues(false);
                    LineData data = new LineData(labels2, dataset);
                    LineChart lineChart = (LineChart) findViewById(R.id.chart100);

                    lineChart.setData(data);
                    lineChart.notifyDataSetChanged();
                    lineChart.invalidate();


                }
                else if (selected.equalsIgnoreCase("Humedad"))
                {
                    ArrayList<Entry> entries3 = new ArrayList<>();
                    ArrayList<String> labels3 = new ArrayList<>();
                    int count = 0;
                    for (Data item: datas
                            ) {

                        entries3.add(new BarEntry((float)item.getHume(), count++));
                        labels3.add(item.getDay());
                    }

                    LineDataSet dataset = new LineDataSet(entries3, "data");
                    dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    dataset.setDrawFilled(true);
                    dataset.setDrawValues(false);
                    LineData data = new LineData(labels3, dataset);
                    LineChart lineChart = (LineChart) findViewById(R.id.chart100);

                    lineChart.setData(data);
                    lineChart.notifyDataSetChanged();
                    lineChart.invalidate();

                }
                else if (selected.equalsIgnoreCase("Propano"))
                {
                    ArrayList<Entry> entries3 = new ArrayList<>();
                    ArrayList<String> labels3 = new ArrayList<>();
                    int count = 0;
                    for (Data item: datas
                            ) {

                        entries3.add(new BarEntry((float)item.getPg(), count++));
                        labels3.add(item.getDay());
                    }

                    LineDataSet dataset = new LineDataSet(entries3, "data");
                    dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    dataset.setDrawFilled(true);
                    dataset.setDrawValues(false);
                    LineData data = new LineData(labels3, dataset);
                    LineChart lineChart = (LineChart) findViewById(R.id.chart100);

                    lineChart.setData(data);
                    lineChart.notifyDataSetChanged();
                    lineChart.invalidate();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });

    }
}
