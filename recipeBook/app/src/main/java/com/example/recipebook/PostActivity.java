package com.example.recipebook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recipebook.Model.Recipe;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class PostActivity extends AppCompatActivity {
    Uri uri;
    ImageView imageView;
    EditText edName, edDesc, edChef;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        imageView= findViewById(R.id.imageView);
        edName=findViewById(R.id.tvName);
        edDesc= findViewById(R.id.tvDesc);
        edChef= findViewById(R.id.tvChef);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else{
            Toast.makeText(this, "permission allowed", Toast.LENGTH_SHORT).show();
        }
    }


    public void pickImage(View view) {

        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== RESULT_OK){
            uri=data.getData();
            imageView.setImageURI(uri);
        }
        else{
            Toast.makeText(this, "need to pick an image", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadRecipe(View view) {
        uploadImage();
    }

    public void uploadImage(){

        ProgressDialog progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Uploading");
        progressDialog.show();

        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("RecipeBook").child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task task=taskSnapshot.getStorage().getDownloadUrl();
                while(!task.isSuccessful());
                Uri uriImage= (Uri)task.getResult();
                imageUrl= uriImage.toString();
                uploadRecipe();
                progressDialog.dismiss();
                Toast.makeText(PostActivity.this, "Recipe upload successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PostActivity.this , HomeActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(PostActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PostActivity.this , HomeActivity.class));
            }
        });
    }

    public void uploadRecipe(){
        String name= edName.getText().toString().trim();
        String desc= edDesc.getText().toString().trim();
        String chef= edChef.getText().toString().trim();

        String currentTime= DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Recipe recipe= new Recipe(name,desc,chef,imageUrl);
        FirebaseDatabase.getInstance().getReference("RecipeBook").child(currentTime).setValue(recipe).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(PostActivity.this, "success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PostActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}