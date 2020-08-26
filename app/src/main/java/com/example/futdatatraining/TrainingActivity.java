package com.example.futdatatraining;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class TrainingActivity extends AppCompatActivity {

    Button nuovoAllenamento;
    Button info;
    TextView sport;
    TextView url;
    File file;
    String calcio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        sport = (TextView) findViewById(R.id.sport);
        url = (TextView) findViewById(R.id.url);
        Intent p = getIntent();
        calcio = p.getExtras().getString("Sport");
        sport.setText(calcio+" : "+"allenamenti più recenti");

        nuovoAllenamento = (Button) findViewById(R.id.nuovoAllenamento);
        info = (Button) findViewById(R.id.info);
        ListView mListView = (ListView) findViewById(R.id.listaAllenamenti);
        file = new File (getExternalFilesDir(null), calcio+".txt");
        ArrayList<Allenamento> allenamenti = new ArrayList<>();

        if (file.exists()) {
            String[] readText = Load(file);
            for (int i=0; i<readText.length; i++) {
                String str = readText[i].trim();
                String[] arr = str.split(" ");
                Allenamento all = new Allenamento(arr[0].replace('_', ' '), arr[1].replace('_', ' '), arr[2]);
                allenamenti.add(all);
            }
        }

        ArrayList<Allenamento> list = new ArrayList<>();
        for (int i=allenamenti.size()-1; i>=allenamenti.size()-5 && i>=0;  i--) {
            list.add(allenamenti.get(i));
        }

        TrainingAdapter adapter = new TrainingAdapter(this, R.layout.adapter_training, list);
        mListView.setAdapter(adapter);
        nuovoAllenamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createActivity = new Intent(TrainingActivity.this, CreateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Sport", calcio);
                createActivity.putExtras(bundle);
                startActivity(createActivity);
            }
        });
    }

    public static String[] Load(File file)
    {
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String test;
        int anzahl=0;
        try
        {
            while ((test=br.readLine()) != null)
            {
                anzahl++;
            }
        }
        catch (IOException e) {e.printStackTrace();}

        try
        {
            fis.getChannel().position(0);
        }
        catch (IOException e) {e.printStackTrace();}

        String[] array = new String[anzahl];

        String line;
        int i = 0;
        try
        {
            while((line=br.readLine())!=null)
            {
                array[i] = line;
                i++;
            }
        }
        catch (IOException e) {e.printStackTrace();}
        return array;
    }

    public void showInfo(View view) {
        if (url.getVisibility()==View.VISIBLE)
            url.setVisibility(View.INVISIBLE);
        else {
            if (!file.exists()) {
                url.setVisibility(View.VISIBLE);
                url.setText("Nessun allenamento recente");
            } else {
                url.setVisibility(View.VISIBLE);
                String str = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4);
                url.setText(str);
            }
        }
    }


}