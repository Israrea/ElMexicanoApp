package com.reasoftware.elmexicano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chipNavigationBar = findViewById(R.id.chipBar);

        if(savedInstanceState == null){
            chipNavigationBar.setItemSelected(R.id.menu,true);
            fragmentManager = getSupportFragmentManager();
            MenuFragment menuFragment = new MenuFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer,menuFragment)
                    .commit();
        }

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Fragment fragment =  null;
                switch (id){

                    case R.id.menu:
                        fragment = new MenuFragment();
                        break;
                    case R.id.datos:
                        fragment = new DatosFragment();
                        break;
                    case R.id.ordenar:
                        fragment = new OrdenarFragment();
                        break;
                    default:
                        break;
                }

                if (fragment != null){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer,fragment)
                            .commit();
                }else{
                    Log.e(MainActivity.class.getSimpleName(),"ERROR 1");
                }
            }
        });

    }
}