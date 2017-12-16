package jdisa.homesafety.Menu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jdisa.homesafety.Data_Form.Abrir;
import jdisa.homesafety.Data_Form.Entradas;
import jdisa.homesafety.MainActivity;
import jdisa.homesafety.R;

public class OpenDoor extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnLogin;
    private DatabaseReference Data;
    private DatabaseReference Data2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_door);
        setTitle("Abrir Puerta");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        final String value= getIntent().getStringExtra("getData");
        auth = FirebaseAuth.getInstance();
        Data= FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH_Abrir);
        Data2= FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH_Entradas);
        inputPassword = (EditText) findViewById(R.id.password_p);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_p);
        btnLogin = (Button) findViewById(R.id.btn_login_p);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String password = inputPassword.getText().toString();


                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                AuthCredential credential = EmailAuthProvider
                        .getCredential(auth.getCurrentUser().getEmail(), password);

// Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(OpenDoor.this, getString(R.string.auth_failed_p), Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                Abrir abrir = new Abrir();
                                abrir.setDevice(value);
                                abrir.setUser(auth.getCurrentUser().getEmail());
                                Date currentTime = Calendar.getInstance().getTime();
                                Data.child(value).child(currentTime.toString()).setValue(abrir);

                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd ");
                                    String strDate = mdformat.format(calendar.getTime());

                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                                    String formattedDate = df.format(c.getTime());

                                    Entradas entradas = new Entradas();
                                    String modelo = android.os.Build.MODEL;
                                    entradas.setDevice(modelo);
                                    entradas.setUser(auth.getCurrentUser().getEmail());
                                    entradas.setFecha(strDate);
                                    entradas.setHora(formattedDate);
                                    String date = strDate+formattedDate;

                                    Data2.child(value).child(date).setValue(entradas);


                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("getData",value);
                                startActivity(intent);
                            }

                            }

                        });
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
