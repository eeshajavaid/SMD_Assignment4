package com.example.assignment4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView fname, lname, email, phone;
    Button save, addImage;
    LinearLayout linearLayout;
    Uri uri;
    private final String CHANNEL_ID = "personal_notifications";
    private final int NOTIFICATION_ID = 001;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       linearLayout = (LinearLayout)findViewById(R.id.linear);
       ImageButton button = (ImageButton) findViewById(R.id.add);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               EditText editTextView = new EditText(MainActivity.this);
               linearLayout.addView(editTextView);
           }
       });
       save = findViewById(R.id.save);
       fname = findViewById(R.id.fnamee);
       lname = findViewById(R.id.lnamee);
       phone = findViewById(R.id.contact1);
       email = findViewById(R.id.emaile);
       addImage = findViewById(R.id.addImageBtn);
       addImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //check runtime permission
               if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
               {
                   if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                   { //permission not granted, request permission.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                       requestPermissions(permissions,PERMISSION_CODE);
                   }
                   else
                   { //Permission already granted
                        pickImageFromGallery();
                   }
               }
               else
               {//System os less than marshmallow
                   pickImageFromGallery();
               }
           }
       });

       save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //int phone1 = Integer.parseInt(phone.getText().toString());
               if(phone.getText().toString().isEmpty())
                   Toast.makeText(getApplicationContext(), "Number is compulsory", Toast.LENGTH_SHORT).show();
               else if(uri == null)
                   Toast.makeText(getApplicationContext(), "Image is compulsory", Toast.LENGTH_SHORT).show();
               else {
                   String image = uri.toString();
                   User user = new User(fname.getText().toString() + " " + lname.getText().toString(), getNumbers(), email.getText().toString(),image );
                   ContactList.myAppDatabase.myDao().addUser(user);
                   displayNotification();
                   Intent intent = new Intent(MainActivity.this, ContactList.class);
                   startActivity(intent);
               }
           }
       });
    }

    private void displayNotification() {
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.notify);
        builder.setContentTitle("Simple Notification from Contacts");
        builder.setContentText(fname.getText().toString()+ " " +lname.getText().toString() + " has been added to contacts");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());

    }

    private void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "Personal Notifications";
            String Description = "Include all the personal notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(Description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }

    private void pickImageFromGallery() {
        //Intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case PERMISSION_CODE:
            {
                if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                     pickImageFromGallery();
                else
                    Toast.makeText(this,"Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //HANDLE RESULT OF PICKED IMAGE


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE)
            uri = data.getData();
    }

    private String getNumbers()
    {
        EditText number;
        String numbers = "";

        for(int i = 0; i < linearLayout.getChildCount(); i++)
        {
            number = (EditText) linearLayout.getChildAt(i);
            if(i == linearLayout.getChildCount()-1)
                numbers += number.getText().toString();
            else
                numbers += number.getText().toString() + '\n';
        }
        return numbers;
    }
}
