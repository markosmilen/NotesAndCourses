package mk.test.gamesbrowser.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.activity.GameActivity;
import mk.test.gamesbrowser.activity.MainActivity;
import mk.test.gamesbrowser.adapter.GameListAdapter;
import mk.test.gamesbrowser.adapter.GameVisitedAdapter;
import mk.test.gamesbrowser.helper.Helper;
import mk.test.gamesbrowser.interfaces.GameClickInterface;
import mk.test.gamesbrowser.interfaces.VisitedGameInterface;
import mk.test.gamesbrowser.model.Game;
import mk.test.gamesbrowser.viewmodel.GameViewModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SearchFragment extends Fragment implements GameClickInterface {
    public static final String TAG = SearchFragment.class.getSimpleName();

    private CardView progressBarLayout;
    private RelativeLayout clearSearchesLayout;
    private RecyclerView searchRecyclerView;
    private ArrayList<Game> searchedGames = new ArrayList<>();
    private GameListAdapter searchAdapter;
    private Gson gson;
    private String queryString;

    private GameViewModel gameViewModel;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(Bundle bundle) {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        EditText searchEditText = view.findViewById(R.id.search_games_et);
        searchRecyclerView = view.findViewById(R.id.search_recycler_view);
        progressBarLayout = view.findViewById(R.id.progress_bar_layout);

        clearSearchesLayout = view.findViewById(R.id.clear_searches_layout);
        Button clearSearchButton = view.findViewById(R.id.clear_searches_button);

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        gameViewModel.getVisitedGames().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> games) {
                searchAdapter.setGames(games);
                searchAdapter.notifyDataSetChanged();
            }
        });

        searchAdapter = new GameListAdapter(getActivity(), this);
        searchAdapter.setGames(searchedGames);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        searchRecyclerView.setAdapter(searchAdapter);

        clearSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.DeleteVisitedGames();
                Toast.makeText(getActivity(), "ALL SEARCHED GAMES ARE CLEARED", Toast.LENGTH_SHORT).show();
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                clearSearchesLayout.setVisibility(View.GONE);
                queryString = editable.toString();
                if (queryString.length() > 3){
                    loadSearchedGames(queryString);
                } else {
                    clearSearchesLayout.setVisibility(View.VISIBLE);
                    if (getActivity() != null) {
                        gameViewModel.getVisitedGames().observe(getActivity(), new Observer<List<Game>>() {
                            @Override
                            public void onChanged(List<Game> games) {
                                searchAdapter.setGames(games);
                                searchAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });
        return view;
    }

    private void loadSearchedGames(String searchString){
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
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
                        searchedGames = gson.fromJson(jsonString, listType);
                        searchAdapter.setGames(searchedGames);
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    searchAdapter.notifyDataSetChanged();
                                    progressBarLayout.setVisibility(View.GONE);
                                }
                            });
                        }
                    }catch (Exception e){
                        Log.d("LOG", e.toString());
                    }
                }
            }
        });
    }

    @Override
    public void onGameClick(Game game) {
        Intent intent = new Intent(getActivity(), GameActivity.class);
        game.setVisited(true);
        gameViewModel.insert(game);
        intent.putExtra("game", game);
        startActivity(intent);
    }
}
