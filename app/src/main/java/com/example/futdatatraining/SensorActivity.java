package com.example.futdatatraining;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.xsens.dot.android.sdk.XsensDotSdk;
import com.xsens.dot.android.sdk.events.XsensDotData;
import com.xsens.dot.android.sdk.interfaces.XsensDotDeviceCb;
import com.xsens.dot.android.sdk.interfaces.XsensDotScannerCb;
import com.xsens.dot.android.sdk.models.XsPayload;
import com.xsens.dot.android.sdk.models.XsensDotDevice;
import com.xsens.dot.android.sdk.utils.XsensDotScanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
public class SensorActivity  extends AppCompatActivity
        implements XsensDotDeviceCb, XsensDotScannerCb {

    private XsensDotScanner mXsScanner;
    private XsensDotDevice xsDevice;

    ArrayAdapter<String> attivitaAdapter;
    ArrayList<String> attivita;
    ListView activity;
    TextView nameAtleta;

    String nomeAtleta;
    String nomeAllenamento;
    String calcio;

    Button stop;
    Button save;
    TextView quality;
    SeekBar slider;
    int qualit;
    Date starTime;
    Date endTime;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    ArrayList<Campionamento> misurazioni = new ArrayList<>();


    private void initXsScanner() {
        mXsScanner = new XsensDotScanner(getApplicationContext(), this);
        mXsScanner.setScanMode(
                ScanSettings.SCAN_MODE_BALANCED);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        XsensDotSdk.setDebugEnabled(true);
        XsensDotSdk.setReconnectEnabled (true);
        initXsScanner();
        BluetoothDevice sensore = (BluetoothDevice) getIntent().getExtras().getParcelable("Sensore");

        nomeAtleta = getIntent().getExtras().getString("nomeAtleta");
        attivita = getIntent().getStringArrayListExtra("attivita");
        calcio = getIntent().getExtras().getString("Sport");

        activity = findViewById(R.id.listaAttivita);
        nameAtleta = findViewById(R.id.nameAtleta);
        nameAtleta.setText("Atleta : "+ nomeAtleta);

        stop = findViewById(R.id.stopMeasuring);
        save = findViewById(R.id.saveMeasuring);
        quality = (TextView) findViewById(R.id.quality);
        slider = (SeekBar) findViewById(R.id.seekBar);
        slider.setMax(5);
        slider.setProgress(2);

        mXsScanner.startScan();
        onXsensDotScanned(sensore);
        xsDevice = new XsensDotDevice(getApplicationContext(), sensore, SensorActivity.this);
        xsDevice.connect();
        xsDevice.setMeasurementMode(XsPayload.PAYLOAD_TYPE_HIGH_FIDELITY_NO_MAG);
        attivitaAdapter= new ArrayAdapter<String>(this, R.layout.adapter_attivita, R.id.nomeAttivita, attivita);
        activity.setAdapter(attivitaAdapter);
        Snackbar.make(findViewById(R.id.connesso), "Attendi connessione...", Snackbar.LENGTH_SHORT).show();

        activity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stop.setVisibility(View.VISIBLE);
                activity.setClickable(false);
                nomeAllenamento = attivita.get(position);
                starTime = Calendar.getInstance().getTime();
                xsDevice.startMeasuring();
            }
        });

        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                qualit=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    /**
     * Called when the activity has detected the user's press of the back
     * key. The {@link #getOnBackPressedDispatcher() OnBackPressedDispatcher} will be given a
     * chance to handle the back button before the default behavior of
     * {@link Activity#onBackPressed()} is invoked.
     *
     * @see #getOnBackPressedDispatcher()
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        xsDevice.disconnect();
        Intent createActivity = new Intent(SensorActivity.this, TrainingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Sport", calcio);
        createActivity.putExtras(bundle);
        startActivity(createActivity);
    }

    @Override
    public void onXsensDotConnectionChanged(String s, int i) {
        if (i == XsensDotDevice.CONN_STATE_DISCONNECTED) {
            displayMessage("Connessione fallita...");
            finish();
        }

    }

    @Override
    public void onXsensDotServicesDiscovered(String s, int i) {
        if (i == BluetoothGatt.GATT_SUCCESS) {
            Snackbar.make(findViewById(R.id.connesso), "Connesso", Snackbar.LENGTH_SHORT).show();
            activity.setClickable(true);
        }
    }
    @Override
    public void onXsensDotFirmwareVersionRead(String s, String s1) {

    }

    @Override
    public void onXsensDotTagChanged(String s, String s1) {

    }

    @Override
    public void onXsensDotBatteryChanged(String s, int i, int i1) {

    }



    @Override
    public void onXsensDotDataChanged(String s, XsensDotData data) {

        final double[] dq = data.getDq();
        final double[] dv = data.getDv();
        final double[] mag = data.getMag();
        final float[] quat = data.getQuat();
        final float[] freeAcc = data.getFreeAcc();

        Campionamento campionamento = new Campionamento(
                data.getPacketCounter(), data.getSampleTimeFine(), dq[0], dq[1], dq[2], dq[3],
                dv[0], dv[1], dv[2], mag[0], mag[1], mag[2], quat[0], quat[1], quat[2], quat[3],
                freeAcc[0], freeAcc[1], freeAcc[2]
        );
        misurazioni.add(campionamento);
    }

    @Override
    public void onXsensDotCalibrationResult(String s, int i, int i1, int i2) {

    }

    @Override
    public void onXsensDotOtaChecked(String s, boolean b, String s1, String s2) {

    }

    @Override
    public void onXsensDotOtaRollback(String s, boolean b, String s1, String s2) {

    }

    @Override
    public void onXsensDotOtaFileMismatch(String s) {

    }

    @Override
    public void onXsensDotOtaDownloaded(String s, int i) {

    }

    @Override
    public void onXsensDotOtaUpdated(String s, int i, int i1, int i2, int i3, int i4) {

    }

    @Override
    public void onXsensDotNewFirmwareVersion(String s, boolean b, String s1, String s2) {

    }

    @Override
    public void onXsensDotOtaDischarge(String s) {

    }

    @Override
    public void onXsensDotScanned(BluetoothDevice bluetoothDevice) {
        String name = bluetoothDevice.getName();
        String address = bluetoothDevice.getAddress();

        if (name.isEmpty() || address.isEmpty()) {
            displayMessage("Dispositivo non riconosciuto...");
            finish();
        }

    }

    private void displayMessage(String message) {

    }

    public void stopMeasuring(View view) {
        stop.setVisibility(View.INVISIBLE);
        save.setVisibility(View.VISIBLE);
        quality.setVisibility(View.VISIBLE);
        slider.setVisibility(View.VISIBLE);
        activity.setClickable(true);

        xsDevice.stopMeasuring();
        endTime = Calendar.getInstance().getTime();
    }

    public void export(View view) throws FileNotFoundException {
        save.setVisibility(View.INVISIBLE);
        quality.setVisibility(View.INVISIBLE);
        slider.setVisibility(View.INVISIBLE);

        //Text of the Document
        StringBuilder data = new StringBuilder();
        data.append("\n"+"Start Time,"+ starTime+"\n");
        data.append("End Time,"+ endTime+"\n");
        data.append("Atleta,"+ nomeAtleta +",Qualita,"+qualit+"\n");
        data.append("PacketCounter,SampleTimeFine,dQ_W,dQ_X,dQ_Y,dQ_Z,dV[1],dV[2],dV[3]," +
                    "Mag_X,Mag_Y,Mag_Z,Quat_W,Quat_X,Quat_Y,Quat_Z,FreeAcc_X,FreeAcc_Y,FreeAcc_Z, Attivita");

        for (int i=0; i<misurazioni.size(); i++) {
            data.append(misurazioni.get(i).toString()+","+nomeAllenamento);
        }

        //Checking the availability state of the External Storage.
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {

            //If it isn't mounted - we can't write into it.
            return;
        }

        //Create a new file that points to the root directory, with the given name:
        Allenamento all = new Allenamento(nomeAtleta, nomeAllenamento, Calendar.getInstance());
        File text = new File (getExternalFilesDir(null), calcio+".txt");
        String [] saveText = (all.getNomeCognomeAtleta().replace(' ','_') + " " + all.getDescrizione().replace(' ','_') + " " + format.format(all.getDataAllenamento().getTime())).split(System.getProperty("line.separator"));

        Save(text, saveText);

        File folder = new File(getExternalFilesDir(null)/*Environment.DIRECTORY_PICTURES)*/, calcio);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(folder,nomeAtleta+"_"+nomeAllenamento+".csv");
        int n=1;
        while (file.exists()) {
            file = new File(folder,nomeAtleta+"_"+nomeAllenamento+"_"+n+".csv");
            n++;
        }

        //This point and below is responsible for the write operation
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            //second argument of FileOutputStream constructor indicates whether
            //to append or create new file if one exists
            outputStream = new FileOutputStream(file, true);
            outputStream.write((data.toString()).getBytes());;
            outputStream.flush();
            outputStream.close();
            Toast.makeText(getBaseContext(), "Saved to" + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        misurazioni.clear();
    }

    // Funzione per la scrittura su file
    public static void Save(File file, String[] data)
    {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file, true);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        try
        {
            try
            {
                for (int i = 0; i<data.length; i++)
                {
                    fos.write(data[i].getBytes());
                    if (i < data.length-1)
                    {
                        fos.write("\n".getBytes());
                    }
                }
                fos.write("\n".getBytes());
            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }

}