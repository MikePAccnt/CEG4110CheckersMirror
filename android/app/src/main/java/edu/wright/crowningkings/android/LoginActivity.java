package edu.wright.crowningkings.android;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
//    private EditText ipAddrTextView;
    private EditText usernameTextView;
    private View mProgressView;
    private View mLoginFormView;

    private String TAG = "LoginActivity";
    private String username = null;
    private IntentFilter loginIntentFilter = new IntentFilter();
    private BroadcastReceiver loginBroadcastReceiver = new LoginBroadcastReceiver();
//    private Pattern pattern;
//    private Matcher matcher;
//    private static final String IPADDRESS_PATTERN =
//            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
//                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
//                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
//                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        usernameTextView = (EditText) findViewById(R.id.username);
//        populateAutoComplete();

//        ipAddrTextView = (EditText) findViewById(R.id.ip_addr);
//        ipAddrTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
//                    return true;
//                }
//                return false;
//            }
//        });

        Button signInButton = (Button) findViewById(R.id.crowning_kings_sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        loginIntentFilter.addAction(Constants.USERNAME_REQUEST_INTENT);
        loginIntentFilter.addAction(Constants.ERROR_NET_EXCEPTION_INTENT);
        loginIntentFilter.addAction(Constants.ERROR_NAME_IN_USE_INTENT);
        loginIntentFilter.addAction(Constants.ERROR_BAD_MESSAGE_INTENT);
        loginIntentFilter.addAction(Constants.ERROR_BAD_USERNAME_INTENT);
        loginIntentFilter.addAction(Constants.IN_LOBBY_INTENT);

        startService(new Intent(this, AndroidUIService.class));
        //loginIntentFilter.addAction(Constants.SEND_USERNAME_REPLY_INTENT);
//        pattern = Pattern.compile(IPADDRESS_PATTERN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(loginBroadcastReceiver, loginIntentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(loginBroadcastReceiver);
        super.onPause();
    }

//    private void populateAutoComplete() {
//        getLoaderManager().initLoader(0, null, this);
//    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
//        ipAddrTextView.setError(null);
        usernameTextView.setError(null);

        // Store values at the time of the login attempt.
//        String email = ipAddrTextView.getText().toString();
        username = usernameTextView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(username) && !isUsernameValid(username)) {
            usernameTextView.setError(getString(R.string.error_invalid_username));
            focusView = usernameTextView;
            cancel = true;
        }

        // Check for a valid email address.
//        if (TextUtils.isEmpty(email)) {
//            ipAddrTextView.setError(getString(R.string.error_field_required));
//            focusView = ipAddrTextView;
//            cancel = true;
//        } else if (!isEmailValid(email)) {
//            ipAddrTextView.setError(getString(R.string.error_invalid_ip_addr));
//            focusView = ipAddrTextView;
//            cancel = true;
//        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            System.out.println("else in loginactivityyy");
            showProgress(true);
            mAuthTask = new UserLoginTask(username);
            mAuthTask.execute((Void) null);
        }
    }

//    private boolean isEmailValid(String ipAddr) {
//        matcher = pattern.matcher(ipAddr);
//        return matcher.matches();
//    }

    private boolean isUsernameValid(String username) {
        return !username.contains(" ");
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void inLobby() {
        Intent tableIntent = new Intent(this, Lobby.class);
        tableIntent.putExtra(Constants.USERNAME_EXTRA, username);
        startActivity(tableIntent);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String username;

        UserLoginTask(String username) {
            this.username = username;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                System.out.println("doInBackground try");
                sendBroadcast(new Intent(Constants.SEND_USERNAME_REPLY_INTENT).putExtra(Constants.USERNAME_EXTRA, username));
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private class LoginBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constants.USERNAME_REQUEST_INTENT:
                    Log.d(TAG, "USERNAME_REQUEST_INTENT");
                    showProgress(false);
                    break;
                case Constants.ERROR_NET_EXCEPTION_INTENT:
                    Log.d(TAG, "ERROR_NET_EXCEPTION_INTENT");
                    break;
                case Constants.ERROR_NAME_IN_USE_INTENT:
                    Log.d(TAG, "ERROR_NAME_IN_USE_INTENT");
                    mAuthTask = null;
                    showProgress(false);
                    usernameTextView.setError(getString(R.string.error_username_taken));
                    usernameTextView.requestFocus();
                    break;
                case Constants.ERROR_BAD_MESSAGE_INTENT:
                    Log.d(TAG, "ERROR_BAD_MESSAGE_INTENT");
                    break;
                case Constants.ERROR_BAD_USERNAME_INTENT:
                    Log.d(TAG, "ERROR_BAD_USERNAME_INTENT");
                    mAuthTask = null;
                    showProgress(false);
                    usernameTextView.setError(getString(R.string.error_invalid_username));
                    usernameTextView.requestFocus();
                    break;
                case Constants.IN_LOBBY_INTENT:
                    Log.d(TAG, "IN_LOBBY_INTENT");
                    inLobby();
                    //finish();
                    break;
            }
        }
    }
}

