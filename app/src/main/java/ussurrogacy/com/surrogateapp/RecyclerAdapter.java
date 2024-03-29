package ussurrogacy.com.surrogateapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will Johnson on 3/21/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ProfileViewHolder> {
        private List<Profile> profiles;
        private List<String> questions;
        public static Integer profileID = 0;

        static class ProfileViewHolder extends RecyclerView.ViewHolder {
            CardView cv;
            TextView profileName;
            TextView profileDob;
            TextView profileBmi;

            ProfileViewHolder(View itemView, final Context context) {
                super(itemView);
                cv = itemView.findViewById(R.id.cv);
                profileName = itemView.findViewById(R.id.profile_name);
                profileDob = itemView.findViewById(R.id.profile_dob);
                profileBmi = itemView.findViewById(R.id.profile_bmi);
                cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((DashboardActivity)context)
                                .loadViewProfileFragment(RecyclerAdapter.profileID);
                    }
                });
            }
        }

        // add the profiles to local data member
        RecyclerAdapter(List<Profile> profiles, List<String> questions, Context context) {
            this.profiles = new ArrayList<>();
            this.profiles.addAll(profiles);

            this.questions = new ArrayList<>();
            this.questions.addAll(questions);

        }

        // Create new views (invoked by the layout manager)
        @Override
        public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_profiles, parent, false);

            return new ProfileViewHolder(v, parent.getContext());
        }

        @Override
        public void onBindViewHolder(final ProfileViewHolder profileViewHolder, int i) {
            profileViewHolder.profileName.setText(profiles.get(i).getData("FirstAndLast"));
            profileViewHolder.profileDob.setText(profiles.get(i).getData("DateOfBirth"));
            profileViewHolder.profileBmi.setText(Float.toString(profiles.get(i).getBmi()));

            profileID = i;
        }

        // Return the size of dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {

            return profiles.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

}

