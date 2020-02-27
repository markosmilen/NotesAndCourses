package mk.test.gamesbrowser.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.adapter.PlatformAdapter;
import mk.test.gamesbrowser.interfaces.PlatformClickInterface;
import mk.test.gamesbrowser.model.Platform;


public class PlatformsFragment extends Fragment implements PlatformClickInterface {

    public static final String TAG = PlatformsFragment.class.getSimpleName();

    private ArrayList<Platform> platforms = new ArrayList<>();

    public PlatformsFragment() {
        // Required empty public constructor
    }

    public static PlatformsFragment newInstance(Bundle bundle) {
        PlatformsFragment fragment = new PlatformsFragment();
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
        View view = inflater.inflate(R.layout.fragment_platforms, container, false);

        loadPlatforms();
        RecyclerView platformsRecyclerView = view.findViewById(R.id.platforms_recycler_view);
        platformsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        PlatformAdapter platformAdapter = new PlatformAdapter(getActivity(), platforms, this);
        platformsRecyclerView.setAdapter(platformAdapter);

        return view;
    }

    public void loadPlatforms(){
        Platform pc = new Platform(6, "PC (Microsoft Windows)", "https://images.igdb.com/igdb/image/upload/t_cover_big/irwvwpl023f8y19tidgq.png");
        Platform mac = new Platform(14, "Mac", "https://images.igdb.com/igdb/image/upload/t_cover_big/jl4t4o64uv2gizj2dxsy.png");
        Platform ps4 = new Platform(48, "Play Station 4", "https://images.igdb.com/igdb/image/upload/t_cover_big/pl6f.png");
        Platform ps5 = new Platform(167, "Play Station 5", "https://images.igdb.com/igdb/image/upload/t_cover_big/nocover_qhhlj6.jpg");
        Platform xboxone = new Platform(49, "Xbox One", "https://images.igdb.com/igdb/image/upload/t_cover_big/pl95.png");
        Platform xboxx = new Platform(169, "Xbox Series X", "https://images.igdb.com/igdb/image/upload/t_cover_big/plbw.png");
        Platform android = new Platform(34, "Android", "https://f0.pngfuel.com/png/22/187/android-logo-clip-art-png-clip-art.png");
        Platform ios = new Platform(39, "iOS", "https://images.igdb.com/igdb/image/upload/t_cover_big/pl6w.png");
        Platform linux = new Platform(3, "Linux", "https://images.igdb.com/igdb/image/upload/t_cover_big/plak.png");
        Platform arcade = new Platform(52, "Arcade", "https://www.recroommasters.com/v/vspfiles/photos/RM-XT-ALPHA-JAMMA-2T.jpg");

        platforms.add(pc);
        platforms.add(mac);
        platforms.add(ps4);
        //platforms.add(ps5);
        platforms.add(xboxone);
        platforms.add(xboxx);
        platforms.add(android);
        platforms.add(ios);
        platforms.add(linux);
        //platforms.add(arcade);
    }


    @Override
    public void onPlatformClick(Platform platform) {
        if (getActivity() != null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putInt("platform_id", platform.getId());
            ft.replace(R.id.frame_container, PlatformGamesFragment.newInstance(bundle), PlatformGamesFragment.TAG);
            ft.addToBackStack(SearchFragment.TAG);
            ft.commit();
        }
    }
}
