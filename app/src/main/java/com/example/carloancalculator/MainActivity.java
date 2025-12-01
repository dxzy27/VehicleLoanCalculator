package com.example.carloancalculator;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    // Declare variables for the UI elements
    private EditText editTextVehiclePrice;
    private EditText editTextDownPayment;
    private EditText editTextLoanPeriod;
    private EditText editTextInterestRate;
    private Button buttonCalculate;
    private Button buttonReset;

    // Result TextViews
    private TextView textViewLoanAmountResult;
    private TextView textViewTotalInterestResult;
    private TextView textViewTotalPaymentResult;
    private TextView textViewMonthlyPaymentResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextVehiclePrice = findViewById(R.id.editTextVehiclePrice);
        editTextDownPayment = findViewById(R.id.editTextDownPayment);
        editTextLoanPeriod = findViewById(R.id.editTextLoanPeriod);
        editTextInterestRate = findViewById(R.id.editTextInterestRate);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonReset = findViewById(R.id.buttonReset);

        textViewLoanAmountResult = findViewById(R.id.textViewLoanAmountResult);
        textViewTotalInterestResult = findViewById(R.id.textViewTotalInterestResult);
        textViewTotalPaymentResult = findViewById(R.id.textViewTotalPaymentResult);
        textViewMonthlyPaymentResult = findViewById(R.id.textViewMonthlyPaymentResult);

        // Set up the toolbar as the ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateLoan();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });
    }

    // This method contains the logic for resetting all fields
    private void resetFields() {
        // Clear all EditText input fields
        editTextVehiclePrice.setText("");
        editTextDownPayment.setText("");
        editTextLoanPeriod.setText("");
        editTextInterestRate.setText("");

        // Reset all result TextViews to their default state
        textViewLoanAmountResult.setText("");
        textViewTotalInterestResult.setText("");
        textViewTotalPaymentResult.setText("");
        textViewMonthlyPaymentResult.setText("");
    }

    private void calculateLoan() {
        String vehiclePriceStr = editTextVehiclePrice.getText().toString();
        String downPaymentStr = editTextDownPayment.getText().toString();
        String loanPeriodStr = editTextLoanPeriod.getText().toString();
        String interestRateStr = editTextInterestRate.getText().toString();

        if (TextUtils.isEmpty(vehiclePriceStr) || TextUtils.isEmpty(downPaymentStr) ||
                TextUtils.isEmpty(loanPeriodStr) || TextUtils.isEmpty(interestRateStr)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double vehiclePrice = Double.parseDouble(vehiclePriceStr);
            double downPayment = Double.parseDouble(downPaymentStr);
            int loanPeriodInYears = Integer.parseInt(loanPeriodStr);
            double interestRate = Double.parseDouble(interestRateStr);

            double loanAmount = vehiclePrice - downPayment;
            double totalInterest = loanAmount * (interestRate / 100) * loanPeriodInYears;
            double totalPayment = loanAmount + totalInterest;
            double monthlyPayment = totalPayment / (loanPeriodInYears * 12);

            textViewLoanAmountResult.setText(String.format("RM %.2f", loanAmount));
            textViewTotalInterestResult.setText(String.format("RM %.2f", totalInterest));
            textViewTotalPaymentResult.setText(String.format("RM %.2f", totalPayment));
            textViewMonthlyPaymentResult.setText(String.format("RM %.2f", monthlyPayment));

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }

    // Inflate the menu items into the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    // Handle item selection for menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class); // Replace with your actual activity
            startActivity(intent);
            return true;
        }else if (id == R.id.info) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class); // Replace with your actual activity
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
