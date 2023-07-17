package com.example.trainpedia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.BillingAddress;
import com.midtrans.sdk.corekit.models.CustomerDetails;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.ShippingAddress;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;


import java.util.ArrayList;

public class Checkout extends AppCompatActivity {

    TextView BeliArgo, BeliJadwal, BeliKelas, BeliJurusan, BeliHarga, namaPenumpang, noKursi;
    String key ="";
    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        BeliArgo = findViewById(R.id.beliArgo);
        BeliJadwal = findViewById(R.id.beliJadwal);
        BeliKelas = findViewById(R.id.beliKelas);
        BeliJurusan = findViewById(R.id.beliJurusan);
        BeliHarga =findViewById(R.id.beliHarga);
        namaPenumpang = findViewById(R.id.namapenumpang);
        noKursi = findViewById(R.id.nomorkursi);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String argo = bundle.getString("argo");
            String jadwal = bundle.getString("jadwal");
            String kelas = bundle.getString("kelas");
            String jurusan = bundle.getString("jurusan");
            Integer harga = bundle.getInt("harga");
            key = bundle.getString("key");

            // Simpan data ke SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("argo", argo);
            editor.putString("jadwal", jadwal);
            editor.putString("kelas", kelas);
            editor.putString("jurusan", jurusan);
            editor.putInt("harga", harga);
            editor.putString("Key", key);
            editor.apply();

        }
        BeliArgo.setText(sharedPreferences.getString("argo", ""));
        BeliJadwal.setText(sharedPreferences.getString("jadwal", ""));
        BeliKelas.setText(sharedPreferences.getString("kelas", ""));
        BeliJurusan.setText(sharedPreferences.getString("jurusan", ""));
        BeliHarga.setText("RP "+sharedPreferences.getInt("harga", 0));
        namaPenumpang.setText(sharedPreferences.getString("fullname", ""));
        noKursi.setText("1");


        SdkUIFlowBuilder.init()
                .setClientKey("SB-Mid-client-oIRLks6imRW0lZIJ") // client_key is mandatory
                .setContext(getApplicationContext()) // context is mandatory
                .setTransactionFinishedCallback(new TransactionFinishedCallback() {
                    @Override
                    public void onTransactionFinished(TransactionResult result) {
                        // Handle finished transaction here.
                    }
                }) // set transaction finish callback (sdk callback)
                .setMerchantBaseUrl("https://traintiketpedia.000webhostapp.com/request.php/")
                .enableLog(true) // enable sdk log (optional)
                .setColorTheme(new CustomColorTheme("#008080", "#008080", "#008080"))
                .setLanguage("id")
                .buildSDK();
        Button pesan = findViewById(R.id.btnMetode);
        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Harga = sharedPreferences.getInt("harga", 0);
                String namas = sharedPreferences.getString("argo", "");
                String Orderid = "Train-pedia"+System.currentTimeMillis()+"";
                TransactionRequest transactionRequest = new TransactionRequest(Orderid, Harga);
                ItemDetails details = new ItemDetails("TiketItemID", (double) Harga, 1, namas);

                // Create array list and add above item details in it and then set it to transaction request.
                ArrayList<ItemDetails> itemDetailsList = new ArrayList<>();
                itemDetailsList.add(details);
                uiKitDetails(transactionRequest);
                // Set item details into the transaction request.
                transactionRequest.setItemDetails(itemDetailsList);
                MidtransSDK.getInstance().setTransactionRequest(transactionRequest);
                MidtransSDK.getInstance().startPaymentUiFlow(Checkout.this);
            }
        });

        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                Intent intent = new Intent(Checkout.this, Home.class);
                startActivity(intent);
            }
        });
    }

    public void uiKitDetails(TransactionRequest transactionRequest){
        String nama = sharedPreferences.getString("fullname", "");
        String lastname = sharedPreferences.getString("lastname", "");
        String firstname = sharedPreferences.getString("firstname", "");
        String email = sharedPreferences.getString("email", "");
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setCustomerIdentifier(nama);
        customerDetails.setPhone("081999123756");
        customerDetails.setFirstName(firstname);
        customerDetails.setLastName(lastname);
        customerDetails.setEmail(email);

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setAddress("Sentolo, Kulon Progo");
        shippingAddress.setCity("DIY");
        shippingAddress.setPostalCode("55664");
        customerDetails.setShippingAddress(shippingAddress);

        BillingAddress billingAddress = new BillingAddress();
        billingAddress.setAddress("Sentolo, Kulon Progo");
        billingAddress.setCity("DIY");
        billingAddress.setPostalCode("55664");
        customerDetails.setBillingAddress(billingAddress);

        transactionRequest.setCustomerDetails(customerDetails);
    }



}
