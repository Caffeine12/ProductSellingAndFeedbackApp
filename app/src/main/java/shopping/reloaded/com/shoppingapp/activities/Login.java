package shopping.reloaded.com.shoppingapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import shopping.reloaded.com.shoppingapp.R;

public class Login extends AppCompatActivity {

    Button login;
    EditText username, password;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);

        login = findViewById(R.id.login);
        username = findViewById(R.id.editTextUserName);
        password = findViewById(R.id.editTextPassword);
        
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });


    }

    private void validate() {
        boolean error = false;
        
        if(username.getText().toString().trim().length()==0){
            error = true;
            Toast.makeText(this, "No username inserted", Toast.LENGTH_SHORT).show();
        }else if(password.getText().toString().trim().length()==0){
            error = true;
            Toast.makeText(this, "No password inserted", Toast.LENGTH_SHORT).show();
        }
        
        if(!error){
            if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                Intent intent = new Intent(Login.this, SellerHomeScreen.class);
                startActivity(intent);
                username.setText("");
                password.setText("");
            }else{
                Toast.makeText(this, "Incorrect username or password!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
