package mk.test.gamesbrowser.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.fragment.HomeFragment;
import mk.test.gamesbrowser.fragment.ListsFragment;
import mk.test.gamesbrowser.fragment.PlatformsFragment;
import mk.test.gamesbrowser.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);

        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (menuItem.getItemId()){
            case R.id.menu_home:
                ft.replace(R.id.frame_container, HomeFragment.newInstance(new Bundle()), HomeFragment.TAG);
                //ft.addToBackStack(HomeFragment.TAG);
                ft.commit();
                return true;
            case R.id.menu_search:
                ft.replace(R.id.frame_container, SearchFragment.newInstance(new Bundle()), SearchFragment.TAG);
                //ft.addToBackStack(SearchFragment.TAG);
                ft.commit();
                return true;
            case R.id.menu_platforms:
                ft.replace(R.id.frame_container, PlatformsFragment.newInstance(new Bundle()), PlatformsFragment.TAG);
                //ft.addToBackStack(PlatformsFragment.TAG);
                ft.commit();
                return true;
            case R.id.menu_lists:
                ft.replace(R.id.frame_container, ListsFragment.newInstance(new Bundle()), ListsFragment.TAG);
                //ft.addToBackStack(ListsFragment.TAG);
                ft.commit();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (bottomNavigationView.getSelectedItemId() == R.id.menu_home) {
            finish();
            super.onBackPressed();
        } else {
            bottomNavigationView.setSelectedItemId(R.id.menu_home);
        }
    }
}
