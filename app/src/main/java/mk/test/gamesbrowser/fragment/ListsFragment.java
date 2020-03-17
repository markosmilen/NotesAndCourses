package mk.test.gamesbrowser.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.activity.GameActivity;
import mk.test.gamesbrowser.adapter.GameListAdapter;
import mk.test.gamesbrowser.interfaces.GameClickInterface;
import mk.test.gamesbrowser.model.Game;
import mk.test.gamesbrowser.viewmodel.GameViewModel;

public class ListsFragment extends Fragment implements GameClickInterface {
    public static final String TAG = ListsFragment.class.getSimpleName();

    private GameViewModel gameViewModel;

    private GameListAdapter gameAdapter;

    public ListsFragment() {
        // Required empty public constructor
    }

    public static ListsFragment newInstance(Bundle bundle) {
        ListsFragment fragment = new ListsFragment();
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
        View view = inflater.inflate(R.layout.fragment_lists, container, false);

        TabLayout listsTabLayout = view.findViewById(R.id.lists_tab_layout);
        RecyclerView listsRecyclerView = view.findViewById(R.id.lists_recycler_view);

        listsTabLayout.addTab(listsTabLayout.newTab().setText(getString(R.string.want)), true);
        listsTabLayout.addTab(listsTabLayout.newTab().setText(getString(R.string.playing)));
        listsTabLayout.addTab(listsTabLayout.newTab().setText(getString(R.string.played)));
        listsTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        listsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        gameAdapter = new GameListAdapter(getActivity(), this);
        listsRecyclerView.setAdapter(gameAdapter);

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        gameViewModel.getWantedGames().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> games) {
                gameAdapter.setGames(games);
                gameAdapter.notifyDataSetChanged();
            }
        });

        listsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("WANT")){
                    gameViewModel.getWantedGames().observe(getActivity(), new Observer<List<Game>>() {
                        @Override
                        public void onChanged(List<Game> games) {
                            gameAdapter.setGames(games);
                            gameAdapter.notifyDataSetChanged();
                        }
                    });
                } else if (tab.getText().equals("PLAYING")){
                    gameViewModel.getPlayingGames().observe(getActivity(), new Observer<List<Game>>() {
                        @Override
                        public void onChanged(List<Game> games) {
                            gameAdapter.setGames(games);
                            gameAdapter.notifyDataSetChanged();
                        }
                    });
                }else {
                    gameViewModel.getPlayedGames().observe(getActivity(), new Observer<List<Game>>() {
                        @Override
                        public void onChanged(List<Game> games) {
                            gameAdapter.setGames(games);
                            gameAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    @Override
    public void onGameClick(Game game) {
        Intent intent = new Intent(getActivity(), GameActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }
}
