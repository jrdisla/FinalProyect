package jdisa.homesafety.Menu.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jdisa.homesafety.Data_Form.Co2;
import jdisa.homesafety.Data_Form.Gas;
import jdisa.homesafety.Data_Form.Humedad;
import jdisa.homesafety.Data_Form.Temperatura;
import jdisa.homesafety.MainActivity;
import jdisa.homesafety.R;

public class Resume extends AppCompatActivity {
    private DatabaseReference nDatabaseRef;
    private DatabaseReference nDatabaseRef2;
    private DatabaseReference nDatabaseRef3;
    private DatabaseReference nDatabaseRef4;

    private int cont=0;
    ArrayList<BarEntry> entries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();
    ArrayList<Entry> entries2 = new ArrayList<>();
    ArrayList<String> labels2 = new ArrayList<String>();
    private ArrayList<Float> data_sh = new ArrayList<>();
    LineData data;
    LineData data1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        nDatabaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH_CO2);
        nDatabaseRef2 = FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH_);
        nDatabaseRef3 = FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH_GAZ);
        nDatabaseRef4 = FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH_HUM);

        nDatabaseRef.limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setContentView(R.layout.activity_resume);

                int count=0;
                for (DataSnapshot snapshot: dataSnapshot.getChildren()
                        ) {

                    Co2 tmp = snapshot.getValue(Co2.class);
                    Long value = tmp.getValue();
                    entries.add(new BarEntry((float)value, count));
                    data_sh.add((float)value);
                    labels.add("CO2");

                    count+=1;
                    cont=count;
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        nDatabaseRef2.limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setContentView(R.layout.activity_resume);


                for (DataSnapshot snapshot: dataSnapshot.getChildren()
                        ) {

                    Temperatura tmp = snapshot.getValue(Temperatura.class);
                    Long value = tmp.getVslue();
                    entries.add(new BarEntry((float)value, cont));
                    labels.add("Temp.");
                    cont+=1;
                    data_sh.add((float)value);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        nDatabaseRef4.limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setContentView(R.layout.activity_resume);


                for (DataSnapshot snapshot: dataSnapshot.getChildren()
                        ) {

                    Humedad tmp = snapshot.getValue(Humedad.class);
                    Long value = tmp.getValue();
                    entries.add(new BarEntry((float)value, cont));
                    labels.add("Hum");
                    cont+=1;
                    data_sh.add((float)value);
                    // cont=count;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        nDatabaseRef3.limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setContentView(R.layout.activity_resume);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()
                        ) {

                    Gas tmp = snapshot.getValue(Gas.class);
                    Long value = tmp.getValue();
                    entries.add(new BarEntry((float)value, cont));
                    labels.add("Gas");
                    cont+=1;
                    data_sh.add((float)value);

                }
                BarDataSet dataset = new BarDataSet(entries,"data");
                dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                //  dataset.setDrawFilled(true);
                dataset.setDrawValues(false);
                BarData dataB = new BarData(labels,dataset);

                BarChart lineChart = (BarChart) findViewById(R.id.chart10);
                lineChart.setData(dataB);
               TextView temp =  (TextView)  findViewById(R.id.temp);
               TextView hum = (TextView) findViewById(R.id.hum);
                TextView gas = (TextView) findViewById(R.id.gas);
               TextView co2 = (TextView) findViewById(R.id.co2);

                co2.setText("Co2: "+data_sh.get(0)+" ppm");
                temp.setText("Temp.: " + data_sh.get(1) + " C");
                hum.setText("Humedad: " + data_sh.get(2) + "%");
                gas.setText("Propano: " + data_sh.get(data_sh.size()-1) + " ppm");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
