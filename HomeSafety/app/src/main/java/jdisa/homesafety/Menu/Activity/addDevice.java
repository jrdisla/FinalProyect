package jdisa.homesafety.Menu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import jdisa.homesafety.Data_Form.Dispositivo;
import jdisa.homesafety.MainActivity;
import jdisa.homesafety.R;

public class addDevice extends AppCompatActivity {
    private Button btnAdd;
    private EditText inputDevi;
    private DatabaseReference Data;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        setTitle("Añadir Dispositivo ");
        btnAdd = (Button) findViewById(R.id.btn_add);
        inputDevi = (EditText) findViewById(R.id.Device);
        auth = FirebaseAuth.getInstance();
        Data= FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH_Dispositivo);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String devic = inputDevi.getText().toString();


                if (TextUtils.isEmpty(devic)) {
                    Toast.makeText(getApplicationContext(), "Enter device number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                    Dispositivo dispositivo = new Dispositivo();
                    dispositivo.setName(devic);
                    dispositivo.setUser(auth.getCurrentUser().getEmail());
                    Data.child(dispositivo.getName()).setValue(dispositivo);

                    Intent intent = new Intent(addDevice.this, MainActivity.class);
                    intent.putExtra("getData",dispositivo.getName());
                    startActivity(intent);
                    finish();


            }
        });

    }
}
