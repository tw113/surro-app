package ussurrogacy.com.surrogateapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will Johnson on 3/21/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ProfileViewHolder> {
        private List<Profile> profiles;
        private List<String> questions;

        public static class ProfileViewHolder extends RecyclerView.ViewHolder {
            CardView cv;
            TextView profileName;
            TextView profileDob;
            TextView profileBmi;

            ProfileViewHolder(View itemView) {
                super(itemView);
                cv = itemView.findViewById(R.id.cv);
                profileName = itemView.findViewById(R.id.profile_name);
                profileDob = itemView.findViewById(R.id.profile_dob);
                profileBmi = itemView.findViewById(R.id.profile_bmi);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        RecyclerAdapter(List<Profile> profiles, List<String> questions) {
            this.profiles = new ArrayList<>();
            this.profiles.addAll(profiles);

            this.questions = new ArrayList<>();
            this.questions.addAll(questions);
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ProfileViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_cards, parent, false);
            ProfileViewHolder vh = new ProfileViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ProfileViewHolder profileViewHolder, int i) {
            profileViewHolder.profileName.setText(profiles.get(i).getData(questions.get(1)));
            profileViewHolder.profileDob.setText(profiles.get(i).getData(questions.get(11)));
            profileViewHolder.profileBmi.setText(Float.toString(profiles.get(i).getBmi()));
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {

            return profiles.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

}

