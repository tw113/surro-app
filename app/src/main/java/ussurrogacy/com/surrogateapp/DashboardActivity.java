package ussurrogacy.com.surrogateapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {

    private GoogleAccountCredential mCredential;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // get intent
        Intent intent = getIntent();

        // set welcome message
        String welcomeMessage = "Welcome, " +
                intent.getStringArrayListExtra(Config.ACCOUNT_CREDENTIALS).get(0) + "!";
        TextView textView = findViewById(R.id.welcomeTextView);
        textView.setText(welcomeMessage);

        //TODO: set mProgress to progressbar on dashboard

        // use gson to get google credential from json string that came through the intent
        Gson gson = new Gson();
        mCredential = gson.fromJson(
                intent.getStringArrayListExtra(Config.ACCOUNT_CREDENTIALS).get(1),
                GoogleAccountCredential.class);

    }

    /**
     * Called when surrogate profile list button is pressed
     * Needs the GoogleAccountCredential from the login activity
     *
     * @param view - used for get list button onClick
     */
    public void getList(View view) {
        new MakeRequestTask(this, mCredential);
    }

    /**
     * An asynchronous task that handles the Google Sheets API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private static class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.sheets.v4.Sheets mService = null;
        private Exception mLastError = null;
        private WeakReference<DashboardActivity> activityRef;

        MakeRequestTask(DashboardActivity activity, GoogleAccountCredential credential) {
            activityRef = new WeakReference<>(activity);

            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.sheets.v4.Sheets.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Surrogate Profiles")
                    .build();
        }

        /**
         * Background task to call Google Sheets API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of names and majors of students in a sample spreadsheet:
         * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
         * @return List of names and majors
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException {
            String spreadsheetId = "1qIFBaQ3aiQVOwkxclxkvXPW2M9daQJuc7QzNSLpfoV0";
            String range = "A:FQ";
            ValueRange response = this.mService.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();

            List<List<Object>> values = response.getValues();
            List<String> questions = new ArrayList<String>();
            List<String> answers = new ArrayList<String>();
            List<Profile> profiles = new ArrayList<>();

            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    for (Object question : values.get(0)) {
                        questions.add(question.toString());
                    }
                    for (Object answer : values.get(i)) {
                        answers.add(answer.toString());
                    }
                    System.out.println(questions + "\n" + answers);
                }

            }

            return questions;
        }



        @Override
        protected void onPreExecute() {
            DashboardActivity activity = activityRef.get();
            activity.mProgress.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<String> output) {
            DashboardActivity activity = activityRef.get();
            activity.mProgress.setVisibility(ProgressBar.INVISIBLE);

            //TODO: move to list of profiles screen
        }

        @Override
        protected void onCancelled() {

        }
    }
}
