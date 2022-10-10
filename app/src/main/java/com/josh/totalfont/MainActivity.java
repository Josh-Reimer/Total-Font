package com.josh.totalfont;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
//import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> resultLauncher;

    private String fontPath;

    //private Typeface customFont;

    private TextView uriView;
    private TextView main;
    private TextView selectButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = findViewById(R.id.mainTextView);
        selectButton = findViewById(R.id.selectButton);
        uriView = findViewById(R.id.uriView);


        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>(){
                    @Override
                    public void onActivityResult(ActivityResult result){
                        Intent data = result.getData();
                        if(data != null){
                             Uri sUri = data.getData();
                            fontPath = sUri.getPath();
                            uriView.setText(fontPath);
                            System.out.println(fontPath);
                            //customFont = Typeface.createFromFile("/storage/emulated/0"+fontPath);
                            //main.setTypeface(customFont);
                        }
                    }
                }

        );



        selectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


                if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);


                } else {
                    selectTTF();
                }

            }
        });

    }


    private void selectTTF()

    {

        // Initialize intent

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        // set type
        //some sources say application/font-sfnt is the right MIME type for ttf files
        intent.setType("*/*");
        //apparently application/x-font-ttf is the correct MIME type for ttf files
        //but as it doesn't seem to work,
        //i set it to */* which will allow
        //any file to be opened
        // Launch intent

        resultLauncher.launch(intent);

    }



    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)

    {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);



        // check condition

        if (requestCode == 1 && grantResults.length > 0

                && grantResults[0]

                == PackageManager.PERMISSION_GRANTED) {

            // When permission is granted

            // Call method

            selectTTF();

        }

        else {

            // When permission is denied

            // Display toast

            Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();

        }

    }


}