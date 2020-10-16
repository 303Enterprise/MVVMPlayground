package com.anikinkirill.mvitutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.anikinkirill.mvitutorial.model.AuthResource;
import com.anikinkirill.mvitutorial.model.User;
import com.anikinkirill.mvitutorial.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private MainViewModel mainViewModel;
    private EditText userIdEditText;
    private Button authButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        subscribeObservers();
    }

    private void initUi() {
        userIdEditText = findViewById(R.id.userIdEditText);
        authButton = findViewById(R.id.authButton);

        authButton.setOnClickListener(this);
    }

    private void authenticateUser() {
        mainViewModel.authenticateUserById(Integer.parseInt(userIdEditText.getText().toString()));
    }

    private void subscribeObservers() {
        mainViewModel.observeUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource.status == AuthResource.AuthStatus.LOADING) {
                    // show progress bar
                    Log.d(TAG, "LOADING...");
                }
                if(userAuthResource.status == AuthResource.AuthStatus.AUTHENTICATED) {
                    // user logged in
                    Log.d(TAG, "SUCCESS: " + userAuthResource.data);
                }
                if(userAuthResource.status == AuthResource.AuthStatus.ERROR) {
                    // show error toast/dialog
                    Log.d(TAG, "ERROR: " + userAuthResource.message);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.authButton) {
            authenticateUser();
        }
    }
}