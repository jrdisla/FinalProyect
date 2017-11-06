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

import jdisa.homesafety.Data_Form.ImageUpload;
import jdisa.homesafety.MainActivity;
import jdisa.homesafety.R;

public class ImageListActivity2 extends AppCompatActivity {

    private DatabaseReference nDatabaseRef;
    private List<ImageUpload> imgList;
    private ListView lv;
    private ImageListAdapter adapter;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list2);
        Intent i = getIntent();
        final String value= getIntent().getStringExtra("getData");
        setTitle("Fotos de Intrusos ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imgList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewImage2);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait loading list image... ");
        progressDialog.show();
        nDatabaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH);

        nDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()
                        ) {
                    ImageUpload img = snapshot.getValue(ImageUpload.class);
                    if(img.getDevice() != null){
                    if(value.equalsIgnoreCase(img.getDevice())) {

                    ImageUpload fire = new ImageUpload();
                    String name = img.getName();
                    String utl = img.getUrl();
                    fire.setName(name);
                    fire.setUrl(utl);
                    fire.setDevice(img.getDevice());

                        imgList.add(fire);
                    }
                }}
                adapter = new ImageListAdapter(ImageListActivity2.this,R.layout.image_item,imgList);
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
