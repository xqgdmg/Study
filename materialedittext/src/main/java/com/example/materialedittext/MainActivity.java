package com.example.materialedittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    MaterialEditText mMaterialEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMaterialEditText = (MaterialEditText) findViewById(R.id.editText);

    }


}
