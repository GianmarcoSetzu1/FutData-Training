package com.example.futdatatraining;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TrainingAdapter extends ArrayAdapter<Allenamento> {

    private Context mContext;
    int mResource;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private static class ViewHolder {
        TextView name;
        TextView descrizione;
        TextView data;
    }

    public TrainingAdapter(Context context, int resource, ArrayList<Allenamento> objects)
    {
        super(context, resource, objects);
        mContext=context;
        mResource=resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String name = getItem(position).getNomeCognomeAtleta();
        String birthday = getItem(position).getDescrizione();
        Calendar sex = getItem(position).getDataAllenamento();

        //Create the person object with the information
        Allenamento training = new Allenamento(name,birthday,sex);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textView1);
            holder.descrizione = (TextView) convertView.findViewById(R.id.textView2);
            holder.data = (TextView) convertView.findViewById(R.id.textView3);
            result = convertView;
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        holder.name.setText(training.getNomeCognomeAtleta());
        holder.descrizione.setText(training.getDescrizione());
        holder.data.setText(format.format(training.getDataAllenamento().getTime()));


        return convertView;
    }
}
