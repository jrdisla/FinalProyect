package jdisa.homesafety.Menu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import jdisa.homesafety.Data_Form.ImageUpload;
import jdisa.homesafety.MainActivity;
import jdisa.homesafety.R;


public class Delete extends AppCompatActivity {
    public static final String FB_STORAGE_PATH_IMAGENP = "buenas/";
    private StorageReference mStorageRef;
    String selected = "";
    private DatabaseReference nDatabase;
    private ArrayList<String> lists = new ArrayList<>();
    private EditText txtImageName;
    private Button button;
    String name;
    String value= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Eliminar Rostro ");
        String get = getIntent().getStringExtra("getData");
        value = get;
        final String FB_DATABASE_PATH = ("buenas/"+value);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        nDatabase= FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);

                nDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot item: dataSnapshot.getChildren())
                        {
                            ImageUpload img = item.getValue(ImageUpload.class);
                            String nameim = img.getName();
                            lists.add(nameim);
                           // if(nameim.equalsIgnoreCase(name))
                            //{
                              //  item.getRef().removeValue();
                            //}

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        Spinner spinner = (Spinner) findViewById(R.id.spinner4);
        lists.add("Seleccione Imagen");
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lists);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                Toast.makeText(adapterView.getContext(),
                        (String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();

                 selected = adapterView.getItemAtPosition(pos).toString();
                if(!selected.equalsIgnoreCase("Seleccione Imagen")){
                    nDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot item: dataSnapshot.getChildren())
                            {
                                ImageUpload img = item.getValue(ImageUpload.class);
                                String nameim = img.getName();
                                lists.add(nameim);
                                if(nameim.equalsIgnoreCase(selected))
                                {
                                    item.getRef().removeValue();
                                    for (int i =0;i<lists.size();i++)
                                    {
                                        lists.remove(i);
                                        selected = "Seleccione Imagen";
                                    }
                                        lists.clear();
                                }

                            }
                            Intent p = new Intent(Delete.this,MainActivity.class);
                            p.putExtra("getData",value);
                            startActivity(p);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    /// /Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //    intent.putExtra("getData",selected);
                  //  startActivity(intent);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });
            }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    }


