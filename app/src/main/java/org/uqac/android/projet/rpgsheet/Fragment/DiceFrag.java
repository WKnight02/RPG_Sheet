package org.uqac.android.projet.rpgsheet.Fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import org.uqac.android.projet.rpgsheet.R;

/**
 * Created by Bruno.J on 16/03/2017.
 */

public class DiceFrag extends Fragment implements SensorEventListener {

    private final double SEUIL = 3;
    private double lastNorm = 9.8;

    private SeekBar diceSeekBar;
    private TextView diceResult;
    private EditText faceNumber;
    private Switch accToggle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_dice_rolling, container, false);

        if(getActivity()==null) {
            return view;
        }

        SensorManager manager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        diceSeekBar = (SeekBar) view.findViewById(R.id.diceSeekBar);
        diceResult = (TextView) view.findViewById(R.id.diceResult);
        faceNumber = (EditText) view.findViewById(R.id.diceFaceNumber);
        accToggle = (Switch) view.findViewById(R.id.accToggle);

        view.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    onShake();
                    return true;
                } return false;
            }
        });

        return view;
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

}
