package com.example.sais.androidsensor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Fragment mContentFragment;
    private ListSensorFragment lstFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ListSensorFragment lstFragment = (ListSensorFragment)getFragmentManager().findFragmentById(R.id.listSensorFragment);
        setDefalutFragment();
    }

    private void setDefalutFragment(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        lstFragment = new ListSensorFragment();
        ft.replace(R.id.container, lstFragment);
        ft.commit();
    }

    public void switchFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
        mContentFragment = fragment;
    }
}
