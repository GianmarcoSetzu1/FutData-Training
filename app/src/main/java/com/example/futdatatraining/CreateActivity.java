package com.example.futdatatraining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CreateActivity extends AppCompatActivity {

    ArrayList<String> attivita = new ArrayList<>();
    Button salva;
    Button nuovaAttivita;
    Button aggiungiAttivita;
    EditText nomeAtleta;
    EditText attivita1;
    EditText attivita2;
    EditText attivita3;
    String calcio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        nuovaAttivita = findViewById(R.id.nuovaAttivita);
        salva = findViewById(R.id.salva);

        calcio = getIntent().getExtras().getString("Sport");

        aggiungiAttivita = findViewById(R.id.aggiungi);

        nomeAtleta = findViewById(R.id.nome);
        attivita1 = findViewById(R.id.attivita1);
        attivita2 = findViewById(R.id.attivita2);
        attivita3 = findViewById(R.id.attivita3);

        nuovaAttivita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attivita1.setVisibility(View.VISIBLE);
                attivita2.setVisibility(View.VISIBLE);
                attivita3.setVisibility(View.VISIBLE);
                aggiungiAttivita.setVisibility(View.VISIBLE);
            }
        });

            aggiungiAttivita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkAttivita()) {
                        if (!(attivita1.getText().toString().isEmpty()))
                            attivita.add(attivita1.getText().toString().trim());
                        if (!(attivita2.getText().toString().isEmpty()))
                            attivita.add(attivita2.getText().toString().trim());
                        if (!(attivita3.getText().toString().isEmpty()))
                            attivita.add(attivita3.getText().toString().trim());

                        Snackbar.make(findViewById(R.id.aggiunti), "Attività aggiunte...", Snackbar.LENGTH_SHORT).show();

                        attivita1.setVisibility(View.INVISIBLE);
                        attivita2.setVisibility(View.INVISIBLE);
                        attivita3.setVisibility(View.INVISIBLE);
                        aggiungiAttivita.setVisibility(View.INVISIBLE);
                    }
                }
            });



            salva.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!attivita.isEmpty()) {
                        if (checkNome()) {
                            Intent createActivity = new Intent(CreateActivity.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("nomeAtleta", nomeAtleta.getText().toString().trim());
                            bundle.putStringArrayList("attivita", attivita);
                            bundle.putSerializable("Sport", calcio);
                            createActivity.putExtras(bundle);
                            startActivity(createActivity);
                        }
                    } else salva.setError("Inserisci almeno un'attività");
                }
            });



    }

    private boolean checkNome() {
            String nome = nomeAtleta.getText().toString().trim();
            if (nome.isEmpty()) {
                nomeAtleta.setError("Il campo non può essere vuoto");
                return false;
            }
        return true;
    }

    private boolean checkAttivita() {
        String att1 = attivita1.getEditableText().toString().trim();
        String att2 = attivita2.getEditableText().toString().trim();
        String att3 = attivita3.getEditableText().toString().trim();
        int count=0;
        if (!att1.isEmpty()) count++;
        if (!att2.isEmpty()) count++;
        if (!att3.isEmpty()) count++;
        if (count==0) {
            attivita1.setError("Inserisci almeno un'attività");
            return false;
        }
        return true;
    }

}