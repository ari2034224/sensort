package com.example.tarea_sensorproximidad;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{
    RelativeLayout main;
    TextView text;
    SensorManager senma;
    Sensor senpro;
    Sensor acceleromesen;
    MediaPlayer music;

    int curr = 0;

    int[] canciones = {R.raw.lil_nas, R.raw.chlorine,
            R.raw.hold_on, R.raw.bezo, R.raw.fantastic,
            R.raw.dicen, R.raw.ghost, R.raw.in_the_end_link,
            R.raw.paz, R.raw.dos, R.raw.planb, R.raw.sean,
            R.raw.shaki, R.raw.tengo, R.raw.never_let, R.raw.on_my};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = (RelativeLayout) findViewById(R.id.main);
        text = (TextView) findViewById(R.id.text);
        senma = (SensorManager) getSystemService(SENSOR_SERVICE);
        senpro = senma.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        acceleromesen = senma.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senma.registerListener(this, senpro, SensorManager.SENSOR_DELAY_NORMAL);
        senma.registerListener(this, acceleromesen, SensorManager.SENSOR_DELAY_NORMAL);
        music = MediaPlayer.create(this, canciones[curr]);

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        String texto = String.valueOf(sensorEvent.values[0]);
        text.setText(texto);
        if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float valor = Float.parseFloat(texto);
            if(valor == 5){
                main.setBackgroundColor(Color.BLACK);
                music.start();
            }
            else
            {
                main.setBackgroundColor(Color.BLUE);
                music.pause();
            }
        }
        else if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            if (x > 15 || y > 15 || z > 15) {
                curr++;
                if (curr == canciones.length) {
                    curr = 0;
                }
                music.reset();
                music = MediaPlayer.create(this, canciones[curr]);
                music.start();
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }
}