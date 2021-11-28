package ltd.hujing.myaccount;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import ltd.hujing.myaccount.ui.history.HistoryActivity;
import ltd.hujing.myaccount.ui.home.HomeActivity;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint({"NonConstantResourceId", "RtlHardcoded"})
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.nav_front:

                            break;
                        case R.id.nav_history:
                            intent = new Intent(MainActivity.this, HistoryActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.nav_analyse:
                            break;
                        case R.id.nav_person:
                            break;
                    }
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    return false;
                }
            });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_to_top)
            Toast.makeText(this, "test",Toast.LENGTH_SHORT).show();
        return true;
    }

}