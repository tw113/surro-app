package ussurrogacy.com.surrogateapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Class fragment for the ViewProfile UI.
 * Receives Individual profile data and populates the UI.*/
public class ViewProfileFrag extends Fragment {
    private Profile profile;
    private RecyclerView mRecyclerView;
    private Integer profileIndex;

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DashboardActivity activity = (DashboardActivity)getActivity();

        // add the back button when fragment is created
        if (activity.getSupportActionBar() != null) {
            String title = "Profile View";
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setTitle(title);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.view_profile, container, false);

        DashboardActivity activity = (DashboardActivity)getActivity();
        profileIndex = getArguments().getInt("ProfileIndex");
        profile = activity.getProfileList().get(profileIndex);

        System.out.println("Profile= " + profile.getData("FirstName"));

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.Adapter mAdapter = new VPRecyclerAdapter(this.getActivity(), profile);
        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onDetach() {
        super.onDetach();
    }


}
