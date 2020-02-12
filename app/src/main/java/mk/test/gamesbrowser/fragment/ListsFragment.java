package mk.test.gamesbrowser.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mk.test.gamesbrowser.R;

public class ListsFragment extends Fragment {
    public static final String TAG = ListsFragment.class.getSimpleName();

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
        return inflater.inflate(R.layout.fragment_lists, container, false);
    }
}
