package jdisa.homesafety.Menu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jdisa.homesafety.Data_Form.Dispositivo;
import jdisa.homesafety.MainActivity;
import jdisa.homesafety.R;

public class device_selecction extends AppCompatActivity {
    private DatabaseReference Data;
    private FirebaseAuth auth;
    private ArrayList<String> strings = new ArrayList<>();
    private String[] letra = new String[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_selecction);
        auth = FirebaseAuth.getInstance();
        Data= FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH_Dispositivo);
        Data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int cout = 0;

                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    Dispositivo data = new Dispositivo();
                    data = item.getValue(Dispositivo.class);
                    if(data.getUser().equalsIgnoreCase(auth.getCurrentUser().getEmail())){
                    if(data.getName() !=null){
                     strings.add(data.getName());
                    }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        strings.add("Seleccione el dispositivo");
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                Toast.makeText(adapterView.getContext(),
                        (String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();

                String selected = adapterView.getItemAtPosition(pos).toString();
                if(!selected.equalsIgnoreCase("Seleccione el dispositivo")){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("getData",selected);
                startActivity(intent);}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });


    }
}
