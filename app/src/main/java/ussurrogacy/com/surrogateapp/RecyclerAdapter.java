package ussurrogacy.com.surrogateapp;

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
        private List<Profile> mDataSet;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class ProfileViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            TextView mTextView;
            ProfileViewHolder(TextView v) {
                super(v);
                mTextView = v;
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        RecyclerAdapter(List<Profile> myDataset) {
            mDataSet = new ArrayList<>();
            mDataSet.addAll(myDataset);
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

    // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ProfileViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.mTextView.setText(mDataSet.toString());

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {

            return mDataSet.size();
        }


}

