package jdisa.homesafety.Menu.Activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import jdisa.homesafety.Data_Form.ImageUpload;
import jdisa.homesafety.MainActivity;
import jdisa.homesafety.R;

public class UploadImage extends AppCompatActivity {
    private StorageReference mStorageRef;
    private DatabaseReference nDatabase;
    private ImageView imageView;
    private EditText txtImageName;
    private Uri imgUrl;
   public static final String FB_STORAGE_PATH_IMAGENP = "buenas/";
    public  String modo = "";
    public static final int REQUES_CODE = 1234;
    String value= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_upload_image);
        setTitle("Cargar Imagen ");
        String get = getIntent().getStringExtra("getData");
        value = get;

        modo = value +System.currentTimeMillis();
        final String FB_DATABASE_PATH = ("buenas/"+value);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        nDatabase= FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);
        imageView = (ImageView) findViewById(R.id.imageView);
        txtImageName = (EditText) findViewById(R.id.txtImageName);
    }
    public void btnBrowse_Click (View v)
    {
  /*      Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),REQUES_CODE);*/

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if(requestCode == REQUES_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            imgUrl = data.getData();
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUrl);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        imgUrl = data.getData();
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }
    public String getImageExt (Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    @SuppressWarnings("VisibleForTests")
    public void btnUpload_Click (View v)
    {
        if (imgUrl != null)
        {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Uploading Image");
            dialog.show();
            //Get the storage References
            StorageReference reference = mStorageRef.child(FB_STORAGE_PATH_IMAGENP + modo + txtImageName.getText().toString() + "."+getImageExt(imgUrl));
            //Add File to reference
            reference.putFile(imgUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dialog.dismiss();
                    //Display succes
                    Toast.makeText(getApplicationContext(),"image uploaded",Toast.LENGTH_SHORT).show();

                    String value = getIntent().getStringExtra("getData");
                    Intent i = new Intent(UploadImage.this,MainActivity.class);
                    i.putExtra("getData",value);
                    startActivity(i);

                    ImageUpload imageUpload = new ImageUpload(txtImageName.getText().toString(),taskSnapshot.getDownloadUrl().toString());

                    //Save image info to firebase database
                    String uploadId = nDatabase.push().getKey();
                    nDatabase.child(modo+txtImageName.getText().toString()).setValue(imageUpload);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            //Display error
                            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @SuppressWarnings("VisibleForTests")
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //show upload progress
                            double progress = (100 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            dialog.setMessage("uploaded"+(int)progress + "%");
                        }
                    });

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Select an Image",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
