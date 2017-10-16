package jdisa.homesafety.Menu.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.github.mikephil.charting.charts.LineChart;
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

import jdisa.homesafety.Data_Form.Humedad;
import jdisa.homesafety.MainActivity;
import jdisa.homesafety.R;

public class HumShow extends AppCompatActivity {
    private DatabaseReference nDatabaseRef;
    private ListView lv;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hum_show);
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        nDatabaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH_HUM);

        nDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                setContentView(R.layout.activity_hum_show);
                LineChart lineChart = (LineChart) findViewById(R.id.chart3);
                ArrayList<Entry> entries = new ArrayList<>();
                ArrayList<String> labels = new ArrayList<String>();
                int count=0;
                for (DataSnapshot snapshot: dataSnapshot.getChildren()
                        ) {

                    Humedad tmp = snapshot.getValue(Humedad.class);
                    Long value = tmp.getValue();
                    entries.add(new Entry((float)value, count));
                    labels.add(""+count);
                    count+=1;
                }
                LineDataSet dataset = new LineDataSet(entries, "data");
                dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                dataset.setDrawFilled(true);
                dataset.setDrawValues(false);
                LineData data = new LineData(labels, dataset);
                lineChart.setData(data); // set the data and list of lables into chart<br />
                lineChart.setDescription("Description");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
