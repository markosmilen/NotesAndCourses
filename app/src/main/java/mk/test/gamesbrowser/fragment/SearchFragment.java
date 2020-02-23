package mk.test.gamesbrowser.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
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
import mk.test.gamesbrowser.activity.MainActivity;
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

public class SearchFragment extends Fragment implements GameClickInterface {
    public static final String TAG = SearchFragment.class.getSimpleName();
    private static final String API_KEY = "00c0d1eda626d2b49c0f0b6ecbc90b9e";

    private EditText searchEditText;
    private RecyclerView searchRecyclerView;
    private ArrayList<Game> searchedGames = new ArrayList<>();
    private GameListAdapter searchAdapter;
    private Gson gson;
    private String queryString;

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

        searchEditText = view.findViewById(R.id.search_games_et);
        searchRecyclerView = view.findViewById(R.id.search_recycler_view);

        loadSearchedGames("hitman"); //load from local DB

        searchAdapter = new GameListAdapter(getActivity(), searchedGames, this);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        searchRecyclerView.setAdapter(searchAdapter);

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
                    searchedGames = new ArrayList<>();
                    searchAdapter.setGames(searchedGames);
                    searchRecyclerView.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();
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
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String jsonString = response.body().string();
                    gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Game>>(){}.getType();
                    searchedGames = gson.fromJson(jsonString, listType);
                    searchAdapter.setGames(searchedGames);

                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                searchAdapter.notifyDataSetChanged();
                                //progressBarLayout.setVisibility(View.GONE);
                            }
                        });
                    }
                }}
        });
    }

    private void loadSearchedGamesFromDB() {

    }

    @Override
    public void onGameClick(Game game) {
        Intent intent = new Intent(getActivity(), GameActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }
}
