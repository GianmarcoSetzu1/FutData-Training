package com.example.futdatatraining;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Allenamento {
    private String nomeCognomeAtleta;
    private String descrizione;
    private Calendar dataAllenamento;

    public Allenamento(String nomeCognomeAtleta, String descrizione, Calendar dataAllenamento) {
        this.nomeCognomeAtleta = nomeCognomeAtleta;
        this.descrizione = descrizione;
        this.dataAllenamento = dataAllenamento;
    }

    public Allenamento(String nomeCognomeAtleta, String descrizione, String dataAllenamento) {
        this.nomeCognomeAtleta = nomeCognomeAtleta;
        this.descrizione = descrizione;

        Calendar cal=Calendar.getInstance();
        try {
            DateFormat formatter ;
            Date date ;
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = (Date)formatter.parse(dataAllenamento);
            cal.setTime(date);
            System.out.println("Today is " +date );
        } catch (ParseException e)
        {System.out.println("Exception :"+e);  }

        this.dataAllenamento = cal;
    }

    public String getNomeCognomeAtleta() {
        return nomeCognomeAtleta;
    }

    public void setNomeCognomeAtleta(String nomeCognomeAtleta) {
        this.nomeCognomeAtleta = nomeCognomeAtleta;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Calendar getDataAllenamento() {
        return dataAllenamento;
    }

    public void setDataAllenamento(Calendar dataAllenamento) {
        this.dataAllenamento = dataAllenamento;
    }
}
