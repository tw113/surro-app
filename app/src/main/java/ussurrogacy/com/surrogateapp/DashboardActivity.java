package ussurrogacy.com.surrogateapp;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class DashboardActivity extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks{

    private GoogleAccountCredential mCredential;
    private String accountName;
    private TextView messageBox;
    private Button loginButton;
    private List<ImageButton> dashboardButtons;
    private List<View> lines;
    private List<TextView> labels;
    private List<Profile> profiles;
    private static List<String> theQuestions;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { SheetsScopes.SPREADSHEETS_READONLY };
    private static final String REQ_GOOGLE_PLAY = "This app requires Google Play Services. " +
            "Please install Google Play Services on your device and relaunch this app.";
    private static final String NO_CONNECTION = "No network connection available.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // set login button so can be hidden
        loginButton = findViewById(R.id.googleLoginButton);

        // initialize lists
        dashboardButtons = new ArrayList<>();
        lines = new ArrayList<>();
        labels = new ArrayList<>();
        profiles = new ArrayList<>();
        theQuestions = new ArrayList<>();

        // set rest of hideables
        setListOfHideableItems();

        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());

        // if user is already logged in, show dashboard items and hide login button
        // if not, hide dashboard items and show login button
        if (accountName != null) {
            showDashboardItems();
            loginButton.setVisibility(Button.INVISIBLE);
        } else {
            hideDashboardItems();
            loginButton.setVisibility(Button.VISIBLE);
        }
    }

    /**
     *  when login button pressed
     */
    public void login(View view) {
        chooseAccount();

        messageBox.setText("Welcome, " + accountName + "!");
    }

    public List<Profile> getProfileList() {
        return this.profiles;
    }

    /**
     * Sets all of the UI elements that need to be hideable during app use
     *  and store them in lists
     */
    private void setListOfHideableItems() {
        // set welcome message box
        messageBox = findViewById(R.id.welcomeTextView);
        // add to list of TextViews to be hidden and unhidden
        labels.add(messageBox);

        // set dashboard items that need to be hidden and store them in a list
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        ImageButton approvedListButton = findViewById(R.id.approvedListButton);
        ImageButton profileListButton = findViewById(R.id.profilesListButton);
        dashboardButtons.add(settingsButton);
        dashboardButtons.add(approvedListButton);
        dashboardButtons.add(profileListButton);
        View line_left = findViewById(R.id.line_left);
        View line_right = findViewById(R.id.line_right);
        lines.add(line_left);
        lines.add(line_right);
        TextView settingsLabel = findViewById(R.id.textView5);
        TextView approvedLabel = findViewById(R.id.textView6);
        TextView profileListLabel = findViewById(R.id.textView7);
        labels.add(settingsLabel);
        labels.add(approvedLabel);
        labels.add(profileListLabel);
    }

    /**
     *  go through each list of hideables and set each item in the lists to INVISIBLE
     */
    private void hideDashboardItems() {
        // buttons
        for (ImageButton i : dashboardButtons) {
            i.setVisibility(ImageButton.INVISIBLE);
        }

        // textviews
        for (TextView t : labels) {
            t.setVisibility(TextView.INVISIBLE);
        }

        // lines
        for (View line : lines) {
            line.setVisibility(View.INVISIBLE);
        }
    }

    /**
     *  go through each list of hideables and set each item in the lists to VISIBLE
     */
    private void showDashboardItems() {
        // buttons
        for (ImageButton i : dashboardButtons) {
            i.setVisibility(ImageButton.VISIBLE);
        }

        // textviews
        for (TextView t : labels) {
            t.setVisibility(TextView.VISIBLE);
        }

        // lines
        for (View line : lines) {
            line.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Attempts to set the account used with the API credentials. If an account
     * name was previously saved it will use that one; otherwise an account
     * picker dialog will be shown to the user. Note that the setting the
     * account to use with the credentials object requires the app to have the
     * GET_ACCOUNTS permission, which is requested here if it is not already
     * present. The AfterPermissionGranted annotation indicates that this
     * function will be rerun automatically whenever the GET_ACCOUNTS permission
     * is granted.
     */
    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                System.out.println("Account name: " + accountName);
                mCredential.setSelectedAccountName(accountName);

                this.accountName = accountName;

                // someone is logged in
                showDashboardItems();
                loginButton.setVisibility(Button.INVISIBLE);

            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode code indicating the result of the incoming
     *     activity result.
     * @param data Intent (containing result data) returned by incoming
     *     activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    messageBox.setText(REQ_GOOGLE_PLAY);
                } else {
                    // someone is logged in
                    showDashboardItems();
                    loginButton.setVisibility(Button.INVISIBLE);
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);

                        this.accountName = accountName;

                        // someone is logged in
                        showDashboardItems();
                        loginButton.setVisibility(Button.INVISIBLE);
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    // someone is logged in
                    showDashboardItems();
                    loginButton.setVisibility(Button.INVISIBLE);
                }
                break;
        }
    }

    /**
     * Respond to requests for permissions at runtime for API 23 and above.
     * @param requestCode The request code passed in
     *     requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    /**
     * Callback for when a permission is granted using the EasyPermissions
     * library.
     * @param requestCode The request code associated with the requested
     *         permission
     * @param list The requested permission list. Never null.
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Callback for when a permission is denied using the EasyPermissions
     * library.
     * @param requestCode The request code associated with the requested
     *         permission
     * @param list The requested permission list. Never null.
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /**
     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
     * Play Services installation via a user dialog, if possible.
     */
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                DashboardActivity.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    /**
     * Called when surrogate profile list button is pressed
     * Needs the GoogleAccountCredential from the login activity
     *
     * @param view - used for get list button onClick
     */
    public void getList(View view) {
        new MakeRequestTask(this, mCredential).execute();
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
         * Gets the data from the google spreadsheet where surrogate form answers are stored
         * @return
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException {
            DashboardActivity activity = activityRef.get();

            String spreadsheetId = "1qIFBaQ3aiQVOwkxclxkvXPW2M9daQJuc7QzNSLpfoV0";
            String range = "A:FQ";
            ValueRange response = this.mService.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();

            List<List<Object>> values = response.getValues();
            List<String> questions = new ArrayList<String>();
            List<String> answers = new ArrayList<String>();
            activity.profiles = new ArrayList<>();
            int profileID = 0;

            if (values != null) {
                // get keys (form questions)
                for (Object question : values.get(0)) {
                    questions.add(question.toString());
                }

                for (int i = 1; i < values.size(); i++) {
                    for (Object answer : values.get(i)) {
                        answers.add(answer.toString());
                    }

                    Profile profile = new Profile(questions, answers, profileID++);
                    activity.profiles.add(profile);
                }

            }

            activity.theQuestions.addAll(questions);

            return questions;
        }

        /**
         *  End of request for profiles from spreadsheet
         *
         * @param questions - the list of keys for the profile class
         */
        @Override
        protected void onPostExecute(List<String> questions) {
            //TODO: go to list of profiles view fragment
            System.out.println("Executed");
        }
    }

}
