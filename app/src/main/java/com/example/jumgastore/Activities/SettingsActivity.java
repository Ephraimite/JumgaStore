package com.example.jumgastore.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jumgastore.Model.CurrentUser;
import com.example.jumgastore.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    TextView tv_close, tv_update;
    Button btnselectProfileImage;
    CircleImageView profileImage;
    private StorageTask uploadTask;
    EditText profileName, ProfileAddress, ProfilePhone, profileEmail;
    
    ProgressBar progressDialog;

    private Uri imageUri;
    private String myUrl = "";
    private StorageReference storageProfilePictureRef;
    private String checker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        profileImage = findViewById(R.id.settings_profile_image);
        profileName = findViewById(R.id.settings_fullName);
        ProfileAddress = findViewById(R.id.profile_settings_Address);
        ProfilePhone = findViewById(R.id.settings_phone_number);
        profileEmail = findViewById(R.id.profile_settings_email);
        tv_close = findViewById(R.id.close_settings);
        tv_update = findViewById(R.id.update_settings);
        btnselectProfileImage = findViewById(R.id.profile_select_image);
        progressDialog = findViewById(R.id.progressBarUpload);

        storageProfilePictureRef  = FirebaseStorage.getInstance().getReference().child("Profile pictures");

        userInfoDisplay(profileImage, profileName, ProfilePhone, ProfileAddress, profileEmail);

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checker.equals("clicked")){
                    userInfoSaved();
                }else {
                    updateOnlyUserInfo();
                }
            }
        });

        btnselectProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1, 1)
                        .start(SettingsActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            
            profileImage.setImageURI(imageUri);
           
        }else {
            Toast.makeText(this, "Error Try Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
            finish();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateOnlyUserInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        HashMap<String, Object> usermap = new HashMap<>();
        usermap.put("name", profileName.getText().toString());
        usermap.put("address", ProfileAddress.getText().toString());
        usermap.put("phoneOrder", ProfilePhone.getText().toString());
        usermap.put("email", profileEmail.getText().toString());

        ref.child(CurrentUser.currentOnlineUsers.getPhone()).updateChildren(usermap);
        startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
        Toast.makeText(SettingsActivity.this, "Profile Info updated Successfully", Toast.LENGTH_SHORT).show();
        finish();


    }

    private void userInfoSaved() {
        
        if (TextUtils.isEmpty(profileName.getText().toString())){
            Toast.makeText(this, "Name is mandatory", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(ProfileAddress.getText().toString())){
            Toast.makeText(this, "Input address", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(ProfilePhone.getText().toString())){
            Toast.makeText(this, "Phone cannot be empty", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(profileEmail.getText().toString())){
            Toast.makeText(this, "Email is mandatory", Toast.LENGTH_SHORT).show();
        }else if (checker.equals("clicked")){
            upLoadImage();
            progressDialog.setVisibility(View.VISIBLE);
        }
    }

    private void upLoadImage() {
        if (imageUri != null){
            final  StorageReference fileRef = storageProfilePictureRef
                    .child(CurrentUser.currentOnlineUsers.getPhone() + ".jpg");
            
            uploadTask = fileRef.putFile(imageUri);
            
            uploadTask.continueWithTask((Continuation) task -> {
                if (!task.isSuccessful()){
                    throw  task.getException();

                }
                return fileRef.getDownloadUrl();
            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                if (task.isSuccessful()){
                    Uri downloadUri = task.getResult();
                    myUrl = downloadUri.toString();

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                    HashMap<String, Object> usermap = new HashMap<>();
                    usermap.put("name", profileName.getText().toString());
                    usermap.put("address", ProfileAddress.getText().toString());
                    usermap.put("phoneOrder", ProfilePhone.getText().toString());
                    usermap.put("email", profileEmail.getText().toString());
                    usermap.put("image", myUrl);

                    ref.child(CurrentUser.currentOnlineUsers.getPhone()).updateChildren(usermap);

                    progressDialog.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
                    Toast.makeText(SettingsActivity.this, "Profile Info updated Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(SettingsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    progressDialog.setVisibility(View.INVISIBLE);
                }
            });
        }else {
            Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void userInfoDisplay(CircleImageView profileImage, EditText profileName,
                                 EditText profilePhone, EditText profileAddress, EditText profileEmail) {

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUser.currentOnlineUsers.getPhone());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    if (snapshot.child("image").exists()){
                        String image = snapshot.child("image").getValue().toString();
                        String name = snapshot.child("name").getValue().toString();
                        String phone = snapshot.child("phone").getValue().toString();
                        String email = snapshot.child("email").getValue().toString();
                        String address = snapshot.child("address").getValue().toString();

                        Picasso.get().load(image).into(profileImage);
                        profileName.setText(name);
                        profilePhone.setText(phone);
                        profileEmail.setText(email);
                        profileAddress.setText(address);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}