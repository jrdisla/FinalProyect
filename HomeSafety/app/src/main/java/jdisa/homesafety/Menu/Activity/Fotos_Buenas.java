package jdisa.homesafety.Menu.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jdisa.homesafety.Data_Form.ImagaBuena;
import jdisa.homesafety.MainActivity;
import jdisa.homesafety.R;

public class Fotos_Buenas extends AppCompatActivity {


    private DatabaseReference nDatabaseRef;
    private List<ImagaBuena> imgList;
    private ListView lv;
    private Fotos_buenas_adapter adapter;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list2);
        Intent i = getIntent();
        final String value= getIntent().getStringExtra("getData");
        setTitle("Fotos Conocidas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imgList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewImage2);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait loading list image... ");
        progressDialog.show();
        nDatabaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH2);

        nDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                for (DataSnapshot snapshot: dataSnapshot.child(value).getChildren()
                        ) {
                    ImagaBuena img = snapshot.getValue(ImagaBuena.class);

                            ImagaBuena fire = new ImagaBuena();
                            String name = img.getName();
                            String utl = img.getUrl();
                            fire.setName(name);
                            fire.setUrl(utl);

                            imgList.add(fire);
                        }

                adapter = new Fotos_buenas_adapter(Fotos_Buenas.this,R.layout.image_item2,imgList);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


                progressDialog.dismiss();
            }

        });

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
