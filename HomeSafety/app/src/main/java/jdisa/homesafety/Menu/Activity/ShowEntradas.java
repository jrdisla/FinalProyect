package jdisa.homesafety.Menu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jdisa.homesafety.Data_Form.Entradas;
import jdisa.homesafety.MainActivity;
import jdisa.homesafety.R;
public class ShowEntradas extends AppCompatActivity {
    private DatabaseReference Data;
    private ArrayList<Entradas> entradases = new ArrayList<>();
    private Tabla tabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_entradas);
        setTitle("Historial de Entrada");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabla = new Tabla(this, (TableLayout)findViewById(R.id.tabla));
        tabla.agregarCabecera(R.array.cabecera_tabla);
        Intent i = getIntent();
        final String value= getIntent().getStringExtra("getData");

        Data = FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH_Entradas);
        Data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int j = 1;
                for (DataSnapshot item: dataSnapshot.child(value).getChildren()
                        ) {


                    Entradas entradas;
                    entradas = item.getValue(Entradas.class);
                    entradases.add(entradas);
                    ArrayList <String> elementos = new ArrayList<String>();
                    elementos.add(Integer.toString(j));
                    elementos.add(entradas.getFecha());
                    elementos.add(entradas.getHora());
                    elementos.add(entradas.getUser());
                    elementos.add(entradas.getDevice());
                    tabla.agregarFilaTabla(elementos);
                    j++;

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
