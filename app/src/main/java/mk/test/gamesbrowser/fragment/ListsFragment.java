package mk.test.gamesbrowser.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.activity.GameActivity;
import mk.test.gamesbrowser.adapter.GameVisitedAdapter;
import mk.test.gamesbrowser.helper.Helper;
import mk.test.gamesbrowser.interfaces.VisitedGameInterface;
import mk.test.gamesbrowser.model.Game;
import mk.test.gamesbrowser.viewmodel.GameViewModel;

public class ListsFragment extends Fragment implements VisitedGameInterface {
    public static final String TAG = ListsFragment.class.getSimpleName();
    public int selectedTab = Helper.WANT;

    private GameViewModel gameViewModel;

    private GameVisitedAdapter gameAdapter;

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
        listsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        gameAdapter = new GameVisitedAdapter(getActivity(), this);
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
                if (getActivity() != null && tab.getText() != null) {
                    if (tab.getText().equals("WANT")) {
                        selectedTab = Helper.WANT;
                        gameViewModel.getWantedGames().observe(getActivity(), new Observer<List<Game>>() {
                            @Override
                            public void onChanged(List<Game> games) {
                                gameAdapter.setGames(games);
                                gameAdapter.notifyDataSetChanged();
                            }
                        });
                    } else if (tab.getText().equals("PLAYING")) {
                        selectedTab = Helper.PLAYING;
                        gameViewModel.getPlayingGames().observe(getActivity(), new Observer<List<Game>>() {
                            @Override
                            public void onChanged(List<Game> games) {
                                gameAdapter.setGames(games);
                                gameAdapter.notifyDataSetChanged();
                            }
                        });
                    } else {
                        selectedTab = Helper.PLAYED;
                        gameViewModel.getPlayedGames().observe(getActivity(), new Observer<List<Game>>() {
                            @Override
                            public void onChanged(List<Game> games) {
                                gameAdapter.setGames(games);
                                gameAdapter.notifyDataSetChanged();
                            }
                        });
                    }
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
    public void onGameDelete(Game game) {
        if (selectedTab == Helper.WANT){
            game.setWanted(false);
        }else if (selectedTab == Helper.PLAYING){
            game.setPlaying(false);
        }else if (selectedTab == Helper.PLAYED){
            game.setPlayed(false);
        }
        gameViewModel.insert(game);
    }

    @Override
    public void onVisitedGameClick(Game game) {
        Intent intent = new Intent(getActivity(), GameActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }
}
