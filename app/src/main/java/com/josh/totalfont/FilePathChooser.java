package com.josh.totalfont;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class FilePathChooser extends AppCompatActivity {
EditText userFilePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_path_chooser);
        userFilePath = findViewById(R.id.userfilepath);
        CheckBox useAppProvided = findViewById(R.id.alwaysusepath);
        boolean checked = useAppProvided.isChecked();
        if(checked){
                Toast.makeText(this,"checked!",Toast.LENGTH_LONG).show();
        }


    }


}