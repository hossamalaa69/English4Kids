package com.example.android.english4kids;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void OpenNumbers(View view)
    {
        //Toast.makeText(this,"Opening Numbers List",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, NumbersActivity.class);
        startActivity(i);
    }
    public void OpenFamily(View view)
    {
        //Toast.makeText(this,"Opening Family Members List",Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, FamilyActivity.class);
        startActivity(i);
    }
    public void OpenColors(View view)
    {
        //Toast.makeText(this,"Opening Colors List",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, ColorsActivity.class);
        startActivity(i);
    }
    public void OpenPhrases(View view)
    {
        //Toast.makeText(this,"Opening Phrases List",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, PhrasesActivity.class);
        startActivity(i);
    }
}
