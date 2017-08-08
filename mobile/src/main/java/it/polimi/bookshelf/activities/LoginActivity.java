package it.polimi.bookshelf.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.polimi.bookshelf.R;
import it.polimi.bookshelf.data.CloudHandler;
import it.polimi.bookshelf.data.DataHandler;
import it.polimi.bookshelf.data.PreferenceHandler;
import it.polimi.bookshelf.data.StorageHandler;
import it.polimi.bookshelf.objects.User;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private User user;

    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        PreferenceHandler pH = new DataHandler(LoginActivity.this).getPreferencesHandler();

        User user = pH.getUser();
        if (!user.getAccess_email().isEmpty() && !user.getPassword().isEmpty()) {

            testAbout();
            login(user.getAccess_email(), user.getPassword());
        }

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();

        new android.os.Handler().post(
                new Runnable() {
                    public void run() {

                        CloudHandler cdH = new DataHandler(LoginActivity.this).getCloudHandler();
                        DatabaseReference ref = cdH.getUser(email);

                        testAbout();
                        
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                user = dataSnapshot.getValue(User.class);
                                if (user.getPassword().equals(password)) {

                                    new DataHandler(getBaseContext()).getPreferencesHandler().setUser(user);
                                    onLoginSuccess();
                                } else {
                                    onLoginFailed();
                                }
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("The read failed: " + databaseError.getCode());
                            }
                        });
                    }
                });
    }

    public void login(String acc_email, String acc_password) {

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = acc_email;
        final String password = acc_password;

        new android.os.Handler().post(
                new Runnable() {
                    public void run() {

                        CloudHandler cdH = new DataHandler(LoginActivity.this).getCloudHandler();
                        DatabaseReference ref = cdH.getUser(email);

                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                user = dataSnapshot.getValue(User.class);
                                if (user.getPassword().equals(password)) {

                                    new DataHandler(getBaseContext()).getPreferencesHandler().setUser(user);
                                    onLoginSuccess();
                                } else {
                                    onLoginFailed();
                                }
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("The read failed: " + databaseError.getCode());
                            }
                        });
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                try {
                    User user = data.getExtras().getParcelable("user");
                    this._emailText.setText(user.getAccess_email());
                    this._passwordText.setText(user.getPassword());
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 15) {
            _passwordText.setError("between 4 and 15 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private void testAbout(){

        String aboutContent = "BookShelf is an application that lets you storing and organizing your books in an immediate way, " +
                "thanks to his ISBN scanner.\n" +
                "\n" +
                "Developed by Alessio Rossotti\n" +
                "\n" +
                "Copyright © 2017. All rights reserved.";

        StorageHandler sH = new DataHandler(LoginActivity.this).getStorageHandler();
        if (sH.readfile("about.txt", false).equals("")) {

            sH.writeFile("about.txt", aboutContent, false);
        }

    }
}