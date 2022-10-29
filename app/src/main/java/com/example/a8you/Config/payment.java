package com.example.a8you.Config;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a8you.Diet;
import com.example.a8you.Profile;
import com.example.a8you.R;
import com.example.a8you.bmi;
import com.example.a8you.resepi1;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.BreakIterator;
import java.text.DecimalFormat;

//AQCiZZc5Oiotb1aRCRg49METom9KVdRubYeHTLd9wq78740GWDkzIYEqDCHFCQjSRhsc_k9ZH0_sJ_Vj
public class payment extends AppCompatActivity {

//    public static final String clientKey = " ";
    public static final int PAYPAL_REQUEST_CODE = 1234;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

//    EditText edtAmount;
    Button backPay;

    FrameLayout basic, premium;

    String amount = "";
    private TextView txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        backPay =(Button) findViewById(R.id.backPay);
        backPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backPay = new Intent(payment.this, Profile.class);
                startActivity(backPay);
            }
        });

//        edtAmount = findViewById(R.id.edtAmount);
//        Button btnPayNow = findViewById(R.id.btnPayNow);

        basic = (FrameLayout) findViewById(R.id.basicPlan);

        basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processpayment1();
            }
        });

        premium = (FrameLayout) findViewById(R.id.premiumplan);

        premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processpayment();
            }
        });


//        btnPayNow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                processpayment();
//            }
//        });
    }

    private void processpayment1() {

        Double amount = Double.valueOf(3.99);
//        String amount2 = premium.String.valueOf(7);
//        String amount = edtAmount.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(Double.valueOf(amount)), "SGD",
                "purchase in 8you store", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);

//        val intent = Intent(MainActivity.this, PaymentDetails.class)
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    private void processpayment() {

        Double amount = Double.valueOf(7.99);
//        String amount2 = premium.String.valueOf(7);
//        String amount = edtAmount.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(Double.valueOf(amount)), "SGD",
                "purchase in 8you store", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);

//        val intent = Intent(MainActivity.this, PaymentDetails.class)
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }


    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            // If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {

                // Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                // if confirmation is not null
                if (confirm != null) {
                    try {
                        // Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        // on below line we are extracting json response and displaying it in a text view.
                        JSONObject payObj = new JSONObject(paymentDetails);
                        String payID = payObj.getJSONObject("response").getString("id");
                        String state = payObj.getJSONObject("response").getString("state");
                        txtStatus.setText("Payment " + state + "\n with payment id is " + payID);
//                        startActivity(new Intent(this, PaymentDetails.class)
//                                .putExtra("payment details", paymentDetails)
//                                .putExtra("payment amount", amount));
                    } catch (JSONException e) {
                        // handling json exception on below line
                        Log.e("Error", "an extremely unlikely failure occurred: ", e);
//                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // on below line we are checking the payment status.
                Log.i("paymentExample", "The user canceled.");
//                Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                // on below line when the invalid paypal config is submitted.
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
//                Toast.makeText(this, "invalid", Toast.LENGTH_SHORT).show();
            }
        }
    }
}