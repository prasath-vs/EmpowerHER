package com.example.javabiometric.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.javabiometric.Adapter.CartAdapter;
import com.example.javabiometric.Helper.ManagmentCart;
import com.example.javabiometric.R;
import com.example.javabiometric.databinding.ActivityCartBinding;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class CartActivity extends BaseActivity implements PaymentResultListener {
    ActivityCartBinding binding;
    private double tax;
    private ManagmentCart managmentCart;
    //Button checkOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Checkout.preload(getApplicationContext());

        binding.checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentNow("100");
            }
        });


        managmentCart = new ManagmentCart(this);
        caculatorCart();
        setVariable();
        initCartList();
    }

    private void PaymentNow(String amount) {
        final Activity activity = this;

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_live_9sVXb9hJHWB7yj");
        checkout.setImage(R.drawable.ic_launcher_background);

        double finalAmount = Float.parseFloat(amount)*100;

        try {
            JSONObject options = new JSONObject();
            options.put("name", "EmpowerHER");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", finalAmount+"");
            options.put("prefill.email", "vsprasath@gmail.com");
            options.put("prefill.contact", "9786881909");

            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    private void initCartList() {
        if(managmentCart.getListCart().isEmpty()){
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scroolViewCart.setVisibility(View.GONE);
        }else{
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scroolViewCart.setVisibility(View.VISIBLE);
        }
        binding.cartView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        binding.cartView.setAdapter(new CartAdapter(managmentCart.getListCart(), this, () -> caculatorCart()));
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(view -> finish());
    }

    private void caculatorCart() {
        double percentTax = 0.02;
        double delivery = 10;
        tax = Math.round((managmentCart.getTotalFee() * percentTax * 100.0)) / 100.0;

        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100;

        binding.totalFeeTxt.setText("Rs."+itemTotal);
        binding.TaxTxt.setText("Rs."+tax);
        binding.deliveryTxt.setText("Rs."+delivery);
        binding.TotalTxt.setText("Rs."+total);
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Success!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed!", Toast.LENGTH_SHORT).show();
    }
}