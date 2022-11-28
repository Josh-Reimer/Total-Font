package com.josh.totalfont;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Menu;
import android.view.MenuItem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
//import android.graphics.Typeface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ActivityResultLauncher<Intent> resultLauncher;

    private String fontPath;

    //private Typeface customFont;

    private TextView uriView;
    private TextView main;
    private TextView selectButton;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.nav_open,R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setNavigationViewListener();


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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        //this function is called by total font to open the navigation menu when the hamburger menu icon is tapped
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.filepathchooserlink){
            Intent choosefile = new Intent(this,FilePathChooser.class);
            startActivity(choosefile);
        } else if(item.getItemId() == R.id.settings){
            Intent settings = new Intent(this,SettingsActivity.class);
            startActivity(settings);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationViewListener(){
        NavigationView nv = findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(this);
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