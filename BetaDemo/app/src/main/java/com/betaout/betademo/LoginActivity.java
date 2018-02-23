package com.betaout.betademo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.betaout.sdk.app.BetaOut;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by root on 30/6/17.
 */
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    //CallbackManager callbackManager;
    private ProgressDialog mProgressDialog;

    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onStop() {
        super.onStop();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("AppInfo", "onConnectionFailed:" + connectionResult);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);



        SharedPreferences sharedPreferences = getSharedPreferences("gmail", MODE_PRIVATE);

        if (sharedPreferences.getString("gmail", "").length() == 0) {

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();



            initViews();
            initClickListeners();
            signIn();

        }else{
            Toast.makeText(this, sharedPreferences.getString("gmail", ""), Toast.LENGTH_LONG).show();
            finishAffinity();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }


    private void initViews() {

    }


    private void initClickListeners() {

    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        showProgressDialog();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            hideProgressDialog();
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("AppInfo", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            SharedPreferences sharedPreferences = getSharedPreferences("gmail", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", acct.getDisplayName());
            editor.putString("gmail", acct.getEmail());
            long ts = System.currentTimeMillis();
            editor.putString("id", acct.getId()/* + ts*/);
            editor.commit();

            /*Hashtable<String, String> props =  new Hashtable<>();
            props.put("fullname", acct.getDisplayName());
            props.put("email", acct.getEmail());
            BetaOut.getInstance().updateUserProperties(props);*/

            BetaOut.getInstance().explicitLogout();
            BetaOut.getInstance().setCustomerEmail(acct.getEmail());
            BetaOut.getInstance().setCustomerId(acct.getId()/* + ts*/);

            finishAffinity();
            Intent intent = new Intent(this, SplashScreen.class);
            startActivity(intent);

            mGoogleApiClient.clearDefaultAccountAndReconnect();


        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("gmail", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            // Signed out, show unauthenticated UI.
        }
    }



    @Override
    public void onBackPressed() {
        finish();

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        signIn();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
