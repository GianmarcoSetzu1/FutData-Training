package com.example.futdatatraining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {

    private TextView calcio;
    private TextView futsal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        calcio = (TextView) findViewById(R.id.calcio);
        futsal = (TextView) findViewById(R.id.futsal);

        calcio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trainingActivity = new Intent(HomeActivity.this, TrainingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Sport", "Calcio");
                trainingActivity.putExtras(bundle);
                startActivity(trainingActivity);
            }
        });

        futsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trainingActivity = new Intent(HomeActivity.this, TrainingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Sport", "Futsal");
                trainingActivity.putExtras(bundle);
                startActivity(trainingActivity);
            }
        });


    }
}