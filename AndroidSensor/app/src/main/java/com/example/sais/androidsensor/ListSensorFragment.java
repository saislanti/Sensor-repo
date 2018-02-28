package com.example.sais.androidsensor;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by ywq on 2018-01-24.
 */
public class ListSensorFragment extends Fragment {
    private SensorManager mSensorManager;
    private List<Sensor> deviceSensors;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_list_sensors, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = (ListView)view.findViewById(R.id.list_sensors);
        final ArrayAdapter<Sensor> listAdapter = new ArrayAdapter<Sensor>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, deviceSensors.toArray(new Sensor[deviceSensors.size()]));
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Sensor sensor = (Sensor)listAdapter.getItem(i);

                Bundle bundle = new Bundle();
                bundle.putInt(SensorDataFragment.KEY_SENSOR_TYPE, sensor.getType());
                getParentActivity().switchFragment(SensorDataFragment.getInstance(bundle));
            }
        });

    }

    protected MainActivity getParentActivity(){
        return (MainActivity)getActivity();
    }
}
