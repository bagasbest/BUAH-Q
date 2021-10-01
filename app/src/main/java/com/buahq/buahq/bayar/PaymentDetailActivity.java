package com.buahq.buahq.bayar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.buahq.buahq.R;
import com.buahq.buahq.databinding.ActivityPaymentDetailBinding;
import com.buahq.buahq.pesan.CheckoutAdapter;
import com.buahq.buahq.pesan.CheckoutModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class PaymentDetailActivity extends AppCompatActivity {

    public static final String EXTRA_PAYMENT = "payment";
    private ActivityPaymentDetailBinding binding;
    private PaymentModel model;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = getIntent().getParcelableExtra(EXTRA_PAYMENT);
        NumberFormat formatter = new DecimalFormat("#,###");

        Log.e("TAG", model.toString());

        binding.nameEt.setText(model.getCustomerName());
        binding.price.setText("Rp." + formatter.format(model.getPrice()));

        initRecyclerView();

    }

    private void initRecyclerView() {
        binding.rvCart.setLayoutManager(new LinearLayoutManager(this));
        CheckoutAdapter adapter = new CheckoutAdapter();
        binding.rvCart.setAdapter(adapter);
        adapter.setData(model.getData());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}