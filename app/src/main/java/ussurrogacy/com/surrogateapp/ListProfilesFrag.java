package ussurrogacy.com.surrogateapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    protected List<Profile> mDataSet;
    protected List<String> mQuestions;
    private List<Profile> profiles;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DashboardActivity activity = new DashboardActivity();

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        mDataSet = new ArrayList<>();
        mQuestions = new ArrayList<>();

        // initialize list of profiles
        mDataSet = activity.getProfileList();
        mQuestions = activity.getQuestions();
    }

    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState) {

        //mDataSet = getArguments().getParcelableArrayList("Profiles");
        if(container != null) {
            View v = inflator.inflate(R.layout.list_profiles, container, false);
            RecyclerView rv = v.findViewById(R.id.my_recycler_view);

            //populate the recyclerview dynamically
            for (int i = 0; i < profiles.size(); i++) {

                //TextView tv = new TextView();
                //((TextView) tv).setText();
            }
            return v;
        }

        else
        {
            System.out.println("ListProfilesFrag conatiner = null");
            return null;
        }
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

