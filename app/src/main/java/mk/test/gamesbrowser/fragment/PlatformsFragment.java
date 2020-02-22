package mk.test.gamesbrowser.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mk.test.gamesbrowser.R;
import mk.test.gamesbrowser.model.Platform;


public class PlatformsFragment extends Fragment {
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
        return inflater.inflate(R.layout.fragment_platforms, container, false);
    }

    public void loadPlatforms(){
        Platform ps4 = new Platform();
        ps4.setName("Play Station");
        ps4.setPlatform_logo("https://upload.wikimedia.org/wikipedia/commons/thumb/0/00/PlayStation_logo.svg/1200px-PlayStation_logo.svg.png");

        platforms.add(ps4);
    }


}
