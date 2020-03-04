package mk.test.gamesbrowser.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import mk.test.gamesbrowser.adapter.GameListAdapter;
import mk.test.gamesbrowser.interfaces.GameClickInterface;
import mk.test.gamesbrowser.model.Game;

public class ListsFragment extends Fragment implements GameClickInterface {
    public static final String TAG = ListsFragment.class.getSimpleName();

    private TabLayout listsTabLayout;
    private RecyclerView listsRecyclerView;
    private GameListAdapter gameAdapter;
    private ArrayList<Game> wantGames = new ArrayList<>();
    private ArrayList<Game> playingGames = new ArrayList<>();
    private ArrayList<Game> playedGames = new ArrayList<>();

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

        listsTabLayout = view.findViewById(R.id.lists_tab_layout);
        listsRecyclerView = view.findViewById(R.id.lists_recycler_view);

        listsTabLayout.addTab(listsTabLayout.newTab().setText(getString(R.string.want)));
        listsTabLayout.addTab(listsTabLayout.newTab().setText(getString(R.string.playing)));
        listsTabLayout.addTab(listsTabLayout.newTab().setText(getString(R.string.played)));
        listsTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        listsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        gameAdapter = new GameListAdapter(getActivity(), wantGames, this);
        listsRecyclerView.setAdapter(gameAdapter);

        TabLayout.Tab tab = listsTabLayout.getTabAt(0);
        tab.select();
        listsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(getActivity(), tab.getText(), Toast.LENGTH_SHORT).show();

                gameAdapter.notifyDataSetChanged();
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
        Toast.makeText(getContext(), game.getName(), Toast.LENGTH_SHORT).show();
    }
}
