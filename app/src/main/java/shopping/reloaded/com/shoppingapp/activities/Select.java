package shopping.reloaded.com.shoppingapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import shopping.reloaded.com.shoppingapp.R;

public class Select extends AppCompatActivity {

    Button buyer, seller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_mode);

        buyer = findViewById(R.id.buyer_button);
        seller = findViewById(R.id.seller_button);

        buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Select.this, BuyerHomeScreen.class);
                startActivity(intent);
            }
        });

        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Select.this, Login.class);
                startActivity(intent);
            }
        });
    }
}
