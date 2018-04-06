package ussurrogacy.com.surrogateapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Class fragment for the list of profiles to be displayed and expanded
 */

public class ListProfilesFrag extends Fragment {

    protected RecyclerView mRecyclerView;
    protected RecyclerAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<Profile> profiles;
    protected List<String> questions;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DashboardActivity activity = (DashboardActivity) getActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        profiles = new ArrayList<>();
        questions = new ArrayList<>();

        // initialize list of profiles
        this.profiles.addAll(activity.getProfileList());
        questions.addAll(activity.getQuestions());

    }

    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState) {

        View view = inflator.inflate(R.layout.recycler_list, container, false);
        //initialize recyclerview and layoutmanager
        //set recyclerview's layoutmanager to the layoutmanager in the fragment
        mRecyclerView = view.findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //initialize recycleradapter and set the recyclerview's adapter to it
        mAdapter = new RecyclerAdapter(profiles, questions);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        FrameLayout frameLayout = getActivity().findViewById(R.id.fragment_container);
        frameLayout.setVisibility(FrameLayout.INVISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

