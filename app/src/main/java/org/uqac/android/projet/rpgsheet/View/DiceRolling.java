package org.uqac.android.projet.rpgsheet.View;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import org.uqac.android.projet.rpgsheet.R;

public class DiceRolling extends ActionBarActivity implements SensorEventListener {

    private final double SEUIL = 3;
    private double lastNorm = 9.8;

    private SeekBar diceSeekBar;
    private TextView diceResult;
    private EditText faceNumber;
    private Switch accToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_rolling);

        SensorManager manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        diceSeekBar = (SeekBar)findViewById(R.id.diceSeekBar);
        diceResult = (TextView)findViewById(R.id.diceResult);
        faceNumber = (EditText)findViewById(R.id.diceFaceNumber);
        accToggle = (Switch)findViewById(R.id.accToggle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            onShake();
            return true;
        } return false;
    }

    protected void onShake() {
        int max = 6;
            try {
                max = Integer.parseInt(faceNumber.getText().toString());
            } catch(Exception e) {
                faceNumber.setText("6");
                return;
            }

        int random = (int)(Math.random() * max) + 1;

        diceSeekBar.setMax(max);
        diceSeekBar.setProgress(random);
        diceResult.setText(String.format("%d", random));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (!accToggle.isChecked()) return;
        Sensor sensor = event.sensor;

        /* Tests
        String dvalues = "";
        for (int i = 0; i < event.values.length; i++) {
            dvalues += String.format(" / %d:%f", i, event.values[i]);
        } Log.d("SENSOR", dvalues);
        //*/

        // Norme du vecteur acceleration:
        double norm = 0;
        for (int i = 0; i < 3; i++)
            norm += Math.pow(event.values[i], 2);
        norm = Math.sqrt(norm);

        // Représentation d'un mouvement
        double delta = Math.abs(lastNorm - norm);
        lastNorm = norm;

        if (delta > SEUIL) onShake();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // PASS
    }

    public void Return(View view){
        /*
        Intent intent = new Intent(this, CharactersView.class);
        startActivity(intent);*/
        finish();
    }
}
