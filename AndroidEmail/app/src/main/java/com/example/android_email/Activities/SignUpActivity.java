package com.example.android_email.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android_email.R;
import com.example.android_email.databinding.ActivitySingUpBinding;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySingUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        binding = ActivitySingUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    private void setListeners(){
        binding.signIn.setOnClickListener(v -> onBackPressed());
    }
}