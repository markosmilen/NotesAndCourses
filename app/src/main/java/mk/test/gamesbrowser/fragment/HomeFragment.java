package mk.test.gamesbrowser.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.activity.GameActivity;
import mk.test.gamesbrowser.activity.MainActivity;
import mk.test.gamesbrowser.adapter.GameAdapter;
import mk.test.gamesbrowser.interfaces.GameClickInterface;
import mk.test.gamesbrowser.model.Game;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class HomeFragment extends Fragment implements GameClickInterface {
    public static final String TAG = HomeFragment.class.getSimpleName();
    private static final String API_KEY = "00c0d1eda626d2b49c0f0b6ecbc90b9e";

    private RecyclerView recyclerView;
    private GameAdapter adapter;
    private ArrayList<Game> games = new ArrayList<>();
    private Gson gson;

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
        recyclerView = view.findViewById(R.id.home_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        loadGames();

        adapter = new GameAdapter(getContext(), games);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void loadGames() {

        OkHttpClient client = new OkHttpClient();

        String bodyString = "fields name, id, cover.*, first_release_date, game_modes.*, genres.*, player_perspectives.*, popularity, rating, rating_count, screenshots.*, game_engines.*, themes.*, videos.*, storyline, url;\n" +
                "where id = (1942,45,22,56,72,114);";
        String body2 = "fields name, cover.*;limit 10;";

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
                    games = gson.fromJson(jsonString, listType);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                            adapter = new GameAdapter(getActivity(), games);
                            recyclerView.setAdapter(adapter);
                        }
                    });
                }}
        });
    }

    @Override
    public void onGameClick(Game game) {
        Intent intent = new Intent(getActivity(), GameActivity.class);
        startActivity(intent);
    }
}
