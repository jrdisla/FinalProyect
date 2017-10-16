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

import jdisa.homesafety.Data_Form.Temperatura;
import jdisa.homesafety.MainActivity;
import jdisa.homesafety.R;

public class TempShow extends AppCompatActivity {
    private DatabaseReference nDatabaseRef;
    private ListView lv;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_show);
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        nDatabaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH_);

        nDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                setContentView(R.layout.activity_temp_show);
                LineChart lineChart = (LineChart) findViewById(R.id.chart2);
                ArrayList<Entry> entries = new ArrayList<>();
                ArrayList<String> labels = new ArrayList<String>();
                int count=0;
                for (DataSnapshot snapshot: dataSnapshot.getChildren()
                        ) {

                    Temperatura tmp = snapshot.getValue(Temperatura.class);
                    Long value = tmp.getVslue();
                    entries.add(new Entry((float)value, count));
                    labels.add(""+count);
                    count+=1;
                }
                LineDataSet dataset = new LineDataSet(entries, "data");
                dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                dataset.setDrawFilled(true);
                dataset.setDrawValues(false);
                LineData data = new LineData(labels, dataset);
                lineChart.setData(data);
                lineChart.setDescription("Description");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
