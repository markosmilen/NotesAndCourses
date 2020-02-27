package mk.test.gamesbrowser.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.activity.GameActivity;
import mk.test.gamesbrowser.adapter.GameListAdapter;
import mk.test.gamesbrowser.interfaces.GameClickInterface;
import mk.test.gamesbrowser.model.Game;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PlatformGamesFragment extends Fragment implements GameClickInterface {

    public static final String TAG = PlatformGamesFragment.class.getSimpleName();
    private static final String API_KEY = "00c0d1eda626d2b49c0f0b6ecbc90b9e";

    private CardView progressBarLayout;
    private EditText searchEditText;
    private RecyclerView gamesRecyclerView;
    private ArrayList<Game> platformGames = new ArrayList<>();
    private GameListAdapter gamesAdapter;
    private Gson gson;
    private String queryString;

    public PlatformGamesFragment() {
        // Required empty public constructor
    }

    public static PlatformGamesFragment newInstance(Bundle bundle) {
        PlatformGamesFragment fragment = new PlatformGamesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_platform_games, container, false);
        searchEditText = view.findViewById(R.id.search_games_et);
        gamesRecyclerView = view.findViewById(R.id.search_platforms_recycler_view);
        progressBarLayout = view.findViewById(R.id.progress_bar_layout);

        int platformID = getArguments().getInt("platform_id");
        loadPlatformGames(platformID);

        gamesAdapter = new GameListAdapter(getActivity(), platformGames, this);
        gamesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        gamesRecyclerView.setAdapter(gamesAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                queryString = editable.toString();
                if (queryString.length() > 3){
                    loadSearchedGames(queryString);
                } else{
                    platformGames = new ArrayList<>();
                    gamesAdapter.setGames(platformGames);
                    gamesRecyclerView.setAdapter(gamesAdapter);
                    gamesAdapter.notifyDataSetChanged();
                }
            }
        });
        return view;
    }

    private void loadSearchedGames(String searchString){
        OkHttpClient client = new OkHttpClient();

        String bodyString = "fields name, id, cover.*, summary, first_release_date, game_modes.*, genres.*, platforms.*, " +
                "player_perspectives.*, popularity, rating, rating_count, screenshots.*, game_engines.*, themes.*, videos.*, " +
                "storyline, involved_companies.*, involved_companies.company.*, url;\n" +
                "              search \"" + searchString + "\"; limit 30; ";

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(bodyString, JSON);

        final Request request = new Request.Builder()
                .url("https://api-v3.igdb.com/games")
                .addHeader("user-key", API_KEY)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Failed request", Toast.LENGTH_SHORT).show();
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
                        platformGames = gson.fromJson(jsonString, listType);
                        gamesAdapter.setGames(platformGames);
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    gamesAdapter.notifyDataSetChanged();
                                    progressBarLayout.setVisibility(View.GONE);
                                }
                            });
                        }
                    }catch (Exception e){
                        Log.d("LOG", e.toString());
                    }
                }}
        });
    }

    private void loadPlatformGames(int platformId){
        OkHttpClient client = new OkHttpClient();

        String bodyString = "fields name, id, cover.*, summary, first_release_date, game_modes.*, genres.*, platforms.*, " +
                "player_perspectives.*, popularity, rating, rating_count, screenshots.*, game_engines.*, themes.*, videos.*, " +
                "storyline, involved_companies.*, involved_companies.company.*, url; " +
                "where platforms = " + platformId + ";";

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, bodyString);

        final Request request = new Request.Builder()
                .url("https://api-v3.igdb.com/games")
                .addHeader("user-key", API_KEY)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Failed request", Toast.LENGTH_SHORT).show();
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
                        platformGames = gson.fromJson(jsonString, listType);

                        if (platformGames.size() != 0) {
                            gamesAdapter.setGames(platformGames);
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        gamesAdapter.notifyDataSetChanged();
                                        progressBarLayout.setVisibility(View.GONE);
                                    }
                                });
                            }
                        }else {
                            progressBarLayout.setVisibility(View.GONE);
                        }
                    }catch (Exception e){
                        Log.d("LOG", e.toString());
                    }
                }}
        });
    }

    @Override
    public void onGameClick(Game game) {
        Intent intent = new Intent(getActivity(), GameActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }
}
