package mk.test.gamesbrowser.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.adapter.GameListAdapter;
import mk.test.gamesbrowser.helper.Helper;
import mk.test.gamesbrowser.interfaces.GameClickInterface;
import mk.test.gamesbrowser.model.Game;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GamesFromGenreActivity extends AppCompatActivity implements GameClickInterface {

    private CardView progressBarLayout;
    private EditText searchEditText;

    private ArrayList<String> platforms = new ArrayList<>();
    private ArrayList<String> genres = new ArrayList<>();

    private ArrayList<Game> games = new ArrayList<>();
    private GameListAdapter gamesAdapter;

    private Gson gson;
    private String queryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_from_genre);

        progressBarLayout = findViewById(R.id.progress_bar_layout);
        searchEditText = findViewById(R.id.search_games_et);

        gson = new Gson();

        final RecyclerView gamesRecyclerView = findViewById(R.id.games_recycler_view);
        gamesRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        gamesAdapter = new GameListAdapter(this, this);
        gamesRecyclerView.setAdapter(gamesAdapter);

        loadGenreNames();
        loadPlatformNames();

        Toolbar toolbar = findViewById(R.id.toolbar);

        int id = getIntent().getIntExtra("id", 0);
        String name = getIntent().getStringExtra("name");

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(name + " games");
        }

        if (name != null) {
            if (genres.contains(name)) {
                loadGamesFromGenre(id);
            } else if (platforms.contains(name)) {
                loadGamesFromPlatform(id);
            }
        }

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getSupportActionBar().setTitle("Search games");
                queryString = editable.toString();
                if (queryString.length() > 3){
                    loadSearchedGames(queryString);
                } else{
                    games = new ArrayList<>();
                    gamesAdapter.setGames(games);
                    gamesRecyclerView.setAdapter(gamesAdapter);
                    gamesAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadGamesFromGenre(int genreId) {
        OkHttpClient client = new OkHttpClient();

        String bodyString = "fields name, id, cover.*, summary, first_release_date, game_modes.*, genres.*, platforms.*, " +
                "player_perspectives.*, popularity, rating, rating_count, screenshots.*, themes.*, videos.*, " +
                "involved_companies.*, involved_companies.company.*; " +
                "where genres = (" + genreId + "); limit 100;";

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(bodyString, JSON);

        final Request request = new Request.Builder()
                .url("https://api-v3.igdb.com/games")
                .addHeader("user-key", Helper.API_KEY)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(GamesFromGenreActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                        progressBarLayout.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String jsonString = response.body().string();
                    gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Game>>(){}.getType();
                    //TODO FIX EXCEPTION
                    try {
                        games = gson.fromJson(jsonString, listType);

                        if (games.size() != 0) {
                            gamesAdapter.setGames(games);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    gamesAdapter.notifyDataSetChanged();
                                    progressBarLayout.setVisibility(View.GONE);
                                }
                            });
                        }else {
                            progressBarLayout.setVisibility(View.GONE);
                        }
                    }catch (Exception e){
                        Log.d("LOG", e.toString());
                    }
                }}
        });
    }

    private void loadGamesFromPlatform(int platformId) {
        OkHttpClient client = new OkHttpClient();

        String bodyString = "fields name, id, cover.*, summary, first_release_date, game_modes.*, genres.*, platforms.*, " +
                "player_perspectives.*, popularity, rating, rating_count, screenshots.*, themes.*, videos.*, " +
                "involved_companies.*, involved_companies.company.*; " +
                "where platforms = (" + platformId + "); limit 100;";

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(bodyString, JSON);

        final Request request = new Request.Builder()
                .url("https://api-v3.igdb.com/games")
                .addHeader("user-key", Helper.API_KEY)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(GamesFromGenreActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                        progressBarLayout.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String jsonString = response.body().string();
                    gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Game>>(){}.getType();
                    //TODO FIX EXCEPTION
                    try {
                        games = gson.fromJson(jsonString, listType);

                        if (games.size() != 0) {
                            gamesAdapter.setGames(games);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    gamesAdapter.notifyDataSetChanged();
                                    progressBarLayout.setVisibility(View.GONE);
                                }
                            });
                        }else {
                            progressBarLayout.setVisibility(View.GONE);
                        }
                    }catch (Exception e){
                        Log.d("LOG", e.toString());
                    }
                }}
        });
    }

    private void loadSearchedGames(String searchString) {
        OkHttpClient client = new OkHttpClient();

        String bodyString = "fields name, id, cover.*, summary, first_release_date, artworks.*, game_modes.*, genres.*, platforms.*, " +
                "player_perspectives.*, popularity, rating, rating_count, screenshots.*, game_engines.*, themes.*, videos.*, " +
                "involved_companies.*, involved_companies.company.*;\n" +
                " search \"" + searchString + "\"; limit 30; ";

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(bodyString, JSON);

        final Request request = new Request.Builder()
                .url("https://api-v3.igdb.com/games")
                .addHeader("user-key", Helper.API_KEY)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(GamesFromGenreActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                        progressBarLayout.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonString = response.body().string();
                    gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Game>>() {
                    }.getType();
                    //TODO FIX EXCEPTION
                    try {
                        games = gson.fromJson(jsonString, listType);
                        gamesAdapter.setGames(games);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                gamesAdapter.notifyDataSetChanged();
                                progressBarLayout.setVisibility(View.GONE);
                            }
                        });
                    } catch (Exception e) {
                        Log.d("LOG", e.toString());
                    }
                }
            }
        });
    }

    public void loadPlatformNames(){
        platforms.add("PC (Microsoft Windows)");
        platforms.add("Mac");
        platforms.add("PlayStation 4");
        platforms.add("PlayStation 5");
        platforms.add("Xbox One");
        platforms.add("Xbox Series X");
        platforms.add("Android");
        platforms.add("iOS");
        platforms.add("Linux");
    }

    public void loadGenreNames(){
        genres.add("Tactical");
        genres.add("Fighting");
        genres.add("Simulator");
        genres.add("Strategy");
        genres.add("Quiz / Trivia");
        genres.add("Adventure");
        genres.add("RPG");
        genres.add("Shooter");
        genres.add("Sport");
        genres.add("Puzzle");
        genres.add("Racing");
        genres.add("Arcade");
        genres.add("Platform");
    }

    @Override
    public void onGameClick(Game game) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }
}
