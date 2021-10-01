package com.buahq.buahq.pesan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.buahq.buahq.R;
import com.buahq.buahq.databinding.ActivityOrderCheckoutBinding;

public class OrderCheckoutActivity extends AppCompatActivity {

    private ActivityOrderCheckoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}