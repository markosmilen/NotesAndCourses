package mk.test.gamesbrowser.fragment;

import android.content.Context;
import android.content.Intent;
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
import mk.test.gamesbrowser.activity.GamesFromGenreActivity;
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

        PlatformAdapter platformAdapter = new PlatformAdapter(getActivity(), this);
        platformAdapter.setPlatforms(platforms);
        platformsRecyclerView.setAdapter(platformAdapter);

        return view;
    }

    public void loadPlatforms(){
        Platform pc = new Platform(6, "PC (Microsoft Windows)", "https://images.igdb.com/igdb/image/upload/t_cover_big/irwvwpl023f8y19tidgq.png");
        Platform mac = new Platform(14, "Mac", "https://havecamerawilltravel.com/photographer/files/2014/04/mac-logo.png");
        Platform ps4 = new Platform(48, "PlayStation 4", "https://i.ya-webdesign.com/images/ps4-logo-png-3.png");
        Platform ps5 = new Platform(167, "PlayStation 5", "https://pureplaystation.com/wp-content/uploads/2016/04/PSN-New-Logo.jpg");
        Platform xboxone = new Platform(49, "Xbox One", "https://banner2.cleanpng.com/20180330/uwe/kisspng-black-xbox-360-controller-wii-xbox-5abe8065a63273.7864406015224341496808.jpg");
        Platform xboxx = new Platform(169, "Xbox Series X", "https://i.ya-webdesign.com/images/xbox-360-buttons-png-5.png");
        Platform android = new Platform(34, "Android", "https://pngimage.net/wp-content/uploads/2018/05/android-blue-png-6.png");
        Platform ios = new Platform(39, "iOS", "https://www.pngitem.com/pimgs/m/524-5244551_ios-black-wallpaper-iphone-logo-hd-png-download.png");
        Platform linux = new Platform(3, "Linux", "https://www.pngkey.com/png/detail/201-2015536_linux-logo-png-tux.png");
        Platform arcade = new Platform(52, "Arcade", "https://images.vexels.com/media/users/3/157779/isolated/preview/aa0fa749890b5ca747f9ce321aead015-arcade-gaming-machine-by-vexels.png");

        if (!platforms.contains(pc)) {
            platforms.add(pc);
            platforms.add(mac);
            platforms.add(ps4);
            platforms.add(ps5);
            platforms.add(xboxone);
            platforms.add(xboxx);
            platforms.add(android);
            platforms.add(ios);
            platforms.add(linux);
            //platforms.add(arcade);
        }
    }


    @Override
    public void onPlatformClick(Platform platform) {
        Intent intent = new Intent(getActivity(), GamesFromGenreActivity.class);
        intent.putExtra("id", platform.getId());
        intent.putExtra("name", platform.getName());
        startActivity(intent);
    }
}
