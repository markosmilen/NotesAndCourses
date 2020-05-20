package com.marko.notesandcourses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private NoteRecyclerAdapter mNoteRecyclerAdapter;
    private RecyclerView mRecyclerItems;
    private CourseRecyclerAdapter mCourseRecyclerAdapter;
    private GridLayoutManager mCoursesLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NoteActivity.class));
            }
        });

        PreferenceManager.setDefaultValues(this, R.xml.messages_preferences, false);
        PreferenceManager.setDefaultValues(this, R.xml.sync_preferences, false);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initializeDisplayContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNoteRecyclerAdapter.notifyDataSetChanged();
        updateNavHeader();
    }

    private void updateNavHeader() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView textUserName = header.findViewById(R.id.text_user_name);
        TextView textEmailAddress = header.findViewById(R.id.text_email_address);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = pref.getString("user_display_name", "");
        String userEmail = pref.getString("user_email_address", "");

        textUserName.setText(userName);
        textEmailAddress.setText(userEmail);
    }

    private void initializeDisplayContent() {
        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        mCoursesLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.course_grid_span));

        mRecyclerItems = findViewById(R.id.list_items);
        mRecyclerItems.setLayoutManager(new LinearLayoutManager(this));
        mNoteRecyclerAdapter = new NoteRecyclerAdapter(this, notes);
        mCourseRecyclerAdapter = new CourseRecyclerAdapter(this, courses);
        displayNotes();
    }

    private void displayCourses() {
        mRecyclerItems.setLayoutManager(mCoursesLayoutManager);
        mRecyclerItems.setAdapter(mCourseRecyclerAdapter);

        selectNavigationItem(R.id.nav_courses);
    }

    private void displayNotes() {
        mRecyclerItems.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerItems.setAdapter(mNoteRecyclerAdapter);

        selectNavigationItem(R.id.nav_notes);
    }

    private void selectNavigationItem(int p) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        menu.findItem(p).setChecked(true);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mNoteRecyclerAdapter.notifyDataSetChanged();
        // mAdapterNotes.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_notes) {
            displayNotes();
        } else if (id == R.id.nav_courses) {
            displayCourses();
        } else if (id == R.id.nav_share) {
            handleShare();
        } else if (id == R.id.nav_send) {
            handleSelection(R.string.nav_send_message);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void handleShare() {
        View view = findViewById(R.id.list_items);
        Snackbar.make(view, "Share to - " + PreferenceManager.getDefaultSharedPreferences(this)
                .getString("user_favorite_social", ""),
                Snackbar.LENGTH_LONG).show();
    }

    private void handleSelection(int message_id) {
        View view = findViewById(R.id.list_items);
        Snackbar.make(view, message_id, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
