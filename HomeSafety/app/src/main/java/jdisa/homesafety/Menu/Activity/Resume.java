package jdisa.homesafety.Menu.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jdisa.homesafety.Data_Form.Data;
import jdisa.homesafety.MainActivity;
import jdisa.homesafety.R;

public class Resume extends AppCompatActivity {
    private DatabaseReference nDatabaseRef;
    private DatabaseReference nDatabaseRef2;
    private DatabaseReference nDatabaseRef3;
    private DatabaseReference nDatabaseRef4;
    private DatabaseReference Data;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private int cont=0;

    ArrayList<Entry> entries2 = new ArrayList<>();    ArrayList<BarEntry> entries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();
    ArrayList<String> labels2 = new ArrayList<String>();
    private ArrayList<Float> data_sh = new ArrayList<>();
    LineData data;
    LineData data1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        auth = FirebaseAuth.getInstance();

        Intent i = getIntent();
        final String value= getIntent().getStringExtra("getData");

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        nDatabaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH_CO2);
        nDatabaseRef2 = FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH_);
        nDatabaseRef3 = FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH_GAZ);
        nDatabaseRef4 = FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH_HUM);
        Data= FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH_Data);

        Data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

ArrayList<Data> datas = new ArrayList<Data>();
                Long co2_p= 0L;
                Long hume_p= 0L;
                Long pg_p= 0L ;
                Long temp_p= 0L ;
                String user = auth.getCurrentUser().getEmail();
                String child = user.substring(0,12);

                for (DataSnapshot snap: dataSnapshot.child(value).getChildren()
                     ) {
                        String key = dataSnapshot.getKey();
                    Data data = new Data();
                data = snap.getValue(Data.class);
                    if(data != null) {
                        datas.add(data);
                        co2_p += data.getCo2();
                        hume_p += data.getHume();
                        pg_p += data.getPg();
                        temp_p += data.getTemp();
                    }
                }
                Long co2= 0L;
                Long hume= 0L;
                Long pg= 0L ;
                Long temp= 0L ;
                String fecha= "";

for (Data item : datas)
{
   co2 = item.getCo2() ;
    hume = item.getHume();
    pg = item.getPg();
   temp = item.getTemp();
    fecha=item.getDay();



    TextView tempe =  (TextView) findViewById(R.id.temp);
    TextView hum = (TextView) findViewById(R.id.hum);
    TextView gas = (TextView) findViewById(R.id.gas);
    TextView co2e = (TextView) findViewById(R.id.co2);


    SpannableString miTexto = new SpannableString("Co2: "+ String.valueOf(co2)+" ppm");
    StyleSpan boldSpan1 = new StyleSpan(Typeface.BOLD);
    miTexto.setSpan(boldSpan1, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

    SpannableString miTexto2 = new SpannableString("Temp: " + String.valueOf(temp) + " °C");
    StyleSpan boldSpan12 = new StyleSpan(Typeface.BOLD);
    miTexto2.setSpan(boldSpan12, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);


    SpannableString miTexto3 = new SpannableString("Humedad: " + String.valueOf(hume) + "%");
    StyleSpan boldSpan13 = new StyleSpan(Typeface.BOLD);
    miTexto3.setSpan(boldSpan13, 0, 9, Spannable.SPAN_INCLUSIVE_INCLUSIVE);


    SpannableString miTexto4 = new SpannableString("Gas: " + String.valueOf(pg) + " ppm");
    StyleSpan boldSpan14 = new StyleSpan(Typeface.BOLD);
    miTexto4.setSpan(boldSpan14, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

    co2e.setText(miTexto);
    tempe.setText(miTexto2);
    hum.setText(miTexto3);
    gas.setText(miTexto4);

}

                entries.add(new BarEntry((float)co2_p/datas.size(), 0));
                labels.add("Co2");
                entries.add(new BarEntry((float)hume_p/datas.size(), 1));
                labels.add("Hum.");
                entries.add(new BarEntry((float)pg_p/datas.size(), 2));
                labels.add("Pro.");
                entries.add(new BarEntry((float)temp_p/datas.size(), 3));
                labels.add("Tem.");
                BarDataSet dataset = new BarDataSet(entries,"data");
                dataset.setColors(ColorTemplate.PASTEL_COLORS);
                dataset.setDrawValues(false);
                BarData dataB = new BarData(labels,dataset);
                BarChart lineChart = (BarChart) findViewById(R.id.chart10);
                lineChart.setData(dataB);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
