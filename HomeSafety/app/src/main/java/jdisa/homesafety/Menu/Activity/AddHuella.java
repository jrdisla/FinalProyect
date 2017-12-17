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

import jdisa.homesafety.Data_Form.Abrir;
import jdisa.homesafety.Data_Form.Huella;
import jdisa.homesafety.MainActivity;
import jdisa.homesafety.R;

public class AddHuella extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnLogin;
    private DatabaseReference Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_huella);
        setTitle("Añadir Nueva Huella");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        final String value= getIntent().getStringExtra("getData");
        auth = FirebaseAuth.getInstance();
        Data= FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH_Huella);
        inputPassword = (EditText) findViewById(R.id.password_h);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_h);
        btnLogin = (Button) findViewById(R.id.btn_login_h);

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
                                        Toast.makeText(AddHuella.this, getString(R.string.auth_failed_p), Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Abrir abrir = new Abrir();
                                    abrir.setDevice(value);
                                    abrir.setUser(auth.getCurrentUser().getEmail());

                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String strDate = mdformat.format(calendar.getTime());

                                    Huella huella = new Huella();
                                    huella.setDevice(android.os.Build.MODEL);
                                    huella.setUser(auth.getCurrentUser().getEmail());
                                    Data.child(value).child(strDate).setValue(huella);

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("getData",value);
                                    startActivity(intent);
                                }

                            }

                        });
            }
        });
    }
}
