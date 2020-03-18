package mk.test.gamesbrowser.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.activity.GameActivity;
import mk.test.gamesbrowser.activity.GamesFromGenreActivity;
import mk.test.gamesbrowser.adapter.GameAdapter;
import mk.test.gamesbrowser.adapter.GenreAdapter;
import mk.test.gamesbrowser.helper.Helper;
import mk.test.gamesbrowser.interfaces.GameClickInterface;
import mk.test.gamesbrowser.interfaces.GenreClickInterface;
import mk.test.gamesbrowser.model.Game;
import mk.test.gamesbrowser.model.GamePhrase;
import mk.test.gamesbrowser.viewmodel.GameViewModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeFragment extends Fragment implements GameClickInterface, GenreClickInterface {
    public static final String TAG = HomeFragment.class.getSimpleName();

    private CardView progressBarLayout;
    private GameAdapter comingSoonAdapter, popularAdapter, visitedAdapter;

    private List<Game> comingSoonGames = new ArrayList<>();
    private List<Game> popularGames = new ArrayList<>();

    private ArrayList<GamePhrase> genres = new ArrayList<>();
    private GenreAdapter genreAdapter;
    private Gson gson;

    private GameViewModel gameViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment fragment = new HomeFragment();
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
        gson = new Gson();
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        progressBarLayout = view.findViewById(R.id.progress_bar_layout);
        RecyclerView popularRV = view.findViewById(R.id.popular_recycler_view);
        RecyclerView recyclerView = view.findViewById(R.id.coming_soon_recycler_view);
        RecyclerView genresRV = view.findViewById(R.id.genres_recycler_view);
        RecyclerView visitedRV = view.findViewById(R.id.recently_visited_recycler_view);

        popularRV.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        visitedRV.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        popularAdapter = new GameAdapter(getContext(), this);
        comingSoonAdapter = new GameAdapter(getContext(), this);
        visitedAdapter = new GameAdapter(getContext(), this);

        popularAdapter.setGames(popularGames);
        comingSoonAdapter.setGames(comingSoonGames);

        popularRV.setAdapter(popularAdapter);
        recyclerView.setAdapter(comingSoonAdapter);
        visitedRV.setAdapter(visitedAdapter);

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        gameViewModel.getVisitedGames().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> visitedGames) {
                visitedAdapter.setGames(visitedGames);
                visitedAdapter.notifyDataSetChanged();
            }
        });

        genreAdapter = new GenreAdapter(getContext(), this);
        genresRV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        genresRV.setAdapter(genreAdapter);

        loadPopularGames();
        loadComingSoonGames();

        loadGenres();

        return view;
    }

    public void loadComingSoonGames() {

        OkHttpClient client = new OkHttpClient();

        Calendar calendar = Calendar.getInstance();
        long timeinmilis = calendar.getTimeInMillis();

        String bodyString = "fields name, id, cover.*, summary, first_release_date, game_modes.*, artworks.*, genres.*, platforms.*, " +
                "player_perspectives.*, popularity, rating, rating_count, screenshots.*, game_engines.*, " +
                "involved_companies.*, involved_companies.company.*, themes.*, videos.*;" +
                " sort first_release_date asc; where first_release_date > " + timeinmilis / 1000 + "; limit 25;";

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
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                            progressBarLayout.setVisibility(View.GONE);
                        }
                    });
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonString = response.body().string();
                    gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Game>>() {
                    }.getType();
                    comingSoonGames = gson.fromJson(jsonString, listType);
                    comingSoonAdapter.setGames(comingSoonGames);

                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                comingSoonAdapter.notifyDataSetChanged();
                                progressBarLayout.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            }
        });
    }

    public void loadPopularGames(){

        OkHttpClient client = new OkHttpClient();

        String bodyString = "fields name, id, cover.*, summary, first_release_date, game_modes.*, artworks.*, genres.*, platforms.*, " +
                "player_perspectives.*, popularity, rating, rating_count, screenshots.*, game_engines.*, " +
                "involved_companies.*, involved_companies.company.*, themes.*, videos.*;" +
                " where rating > 0; sort rating desc; limit 25;";

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
                if (getActivity() != null)
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
                if (response.isSuccessful()) {
                    String jsonString = response.body().string();
                    gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Game>>(){}.getType();
                    popularGames = gson.fromJson(jsonString, listType);
                    popularAdapter.setGames(popularGames);

                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                popularAdapter.notifyDataSetChanged();
                                progressBarLayout.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            }
        });
    }

    public void loadGenres(){
        GamePhrase tactical = new GamePhrase(24, "Tactical", R.drawable.tactical);
        GamePhrase fighting = new GamePhrase(4, "Fighting", R.drawable.fighting);
        GamePhrase simulator = new GamePhrase(13, "Simulator", R.drawable.simulator);
        GamePhrase strategy = new GamePhrase(15, "Strategy", R.drawable.strategy);
        GamePhrase quiz = new GamePhrase(26, "Quiz / Trivia", R.drawable.quiz);
        GamePhrase adventure = new GamePhrase(31, "Adventure", R.drawable.adventure);
        GamePhrase rpg = new GamePhrase(12, "RPG", R.drawable.rpg);
        GamePhrase shooter = new GamePhrase(5, "Shooter", R.drawable.shooter);
        GamePhrase sport = new GamePhrase(14, "Sport", R.drawable.sport);
        GamePhrase puzzle = new GamePhrase(9, "Puzzle", R.drawable.puzzle);
        GamePhrase racing = new GamePhrase(10, "Racing", R.drawable.racing);
        GamePhrase arcade = new GamePhrase(33, "Arcade", R.drawable.arcade);
        GamePhrase platform = new GamePhrase(8, "Platform", R.drawable.platform);

        genres.add(tactical);
        genres.add(fighting);
        genres.add(simulator);
        genres.add(strategy);
        genres.add(quiz);
        genres.add(adventure);
        genres.add(rpg);
        genres.add(shooter);
        genres.add(sport);
        genres.add(puzzle);
        genres.add(racing);
        genres.add(arcade);
        genres.add(platform);

        genreAdapter.setGenres(genres);
        genreAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGameClick(Game game) {
        Intent intent = new Intent(getActivity(), GameActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

    @Override
    public void onGenreClick(GamePhrase genre) {
        Intent intent = new Intent(getActivity(), GamesFromGenreActivity.class);
        intent.putExtra("id", genre.getId());
        intent.putExtra("name", genre.getName());
        startActivity(intent);
    }
}
