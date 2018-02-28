package com.example.sais.androidsensor;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ywq on 2018-01-24.
 */
public class SensorDataFragment extends Fragment implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mCurrentSensor;
    public static final String KEY_SENSOR_TYPE = "selected_sensor_type";

    private TextView mSensorDataView;

    public static SensorDataFragment getInstance(Bundle bundle){
        SensorDataFragment instance = new SensorDataFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public final void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);

        Bundle arguments = getArguments();
        if(arguments != null && arguments.containsKey(KEY_SENSOR_TYPE)){
            int sensorType = arguments.getInt(KEY_SENSOR_TYPE);
            mCurrentSensor = mSensorManager.getDefaultSensor(sensorType);
        } else {
            mCurrentSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_sensor_data, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        mSensorDataView = (TextView)view.findViewById(R.id.text_data_value);

        TextView textView = (TextView)view.findViewById(R.id.text_selected_sensor_value);
        textView.setText(new StringBuilder("所选传感器为:").append(mCurrentSensor));

        view.findViewById(R.id.button_clear).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mSensorDataView.setText("");
            }
        });
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    public void onSensorChanged(SensorEvent event){
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        //float lux = event.values[0];
        // Do something with this sensor value.
        StringBuilder sb = new StringBuilder();
        for(float value : event.values)
            sb.append(String.valueOf(value)).append("  ");

        mSensorDataView.append(sb.append("\n"));
    }

    public void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mCurrentSensor, SensorManager.SENSOR_DELAY_NORMAL);
        // we can use faster data delays like: SENSOR_DELAY_GAME (20,000 microsecond delay),
        // SENSOR_DELAY_UI (60,000 microsecond delay), or SENSOR_DELAY_FASTEST
    }

    public void onPause(){
        super.onPause();
        // never forget to unregister
        mSensorManager.unregisterListener(this);
    }
}
