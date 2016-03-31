package poker.fivecardstats.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import poker.fivecardstats.R;
import poker.fivecardstats.adapter.UsersAdapter;
import poker.fivecardstats.model.User;

/**
 * Created by Victor on 2016-03-29.
 */
public class TabFragment1 extends Fragment {

    protected RecyclerView mRecyclerView;
    protected RecyclerView.Adapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ArrayList<User> mUsers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fragment_1, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.r_users);
        // Improve layout size of the RecycleView
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new UsersAdapter(mUsers);
        mRecyclerView.setAdapter(mAdapter);

        // Set onclick listners..

        return rootView;
    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private void initDataset() {
        mUsers = new ArrayList<>();
        mUsers.add(new User("Victor"));
        mUsers.add(new User("Malin"));
        mUsers.add(new User("Ulla"));
    }
}
