package shopping.reloaded.com.shoppingapp.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import shopping.reloaded.com.shoppingapp.R;
import shopping.reloaded.com.shoppingapp.models.Hamdard;

public class Product_Add extends Fragment {

    EditText nameET, imageLinkET, descriptionET,actionUsagesET, sideEffectET;
    Button add_product;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.product_upload,null);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("hamdard_products");

        initialize(view);

        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertProducts();
            }
        });

        return view;
    }

    public void InsertProducts(){

        final String productId = myRef.push().getKey();
        final String name = nameET.getText().toString();
        final String imageLink = imageLinkET.getText().toString();
        final String description = descriptionET.getText().toString();
        final String actionAndUsages = actionUsagesET.getText().toString();
        final String sideEffect = sideEffectET.getText().toString();

        if(name.trim().length()==0
                || imageLink.trim().length()==0
                || description.trim().length()==0
                || actionAndUsages.trim().length()==0
                || sideEffect.trim().length()==0){
            Toast.makeText(getContext(), "Field(s) empty", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(getContext())
                .setCancelable(false)
                .setTitle("Confirm?")
                .setMessage("Are u sure to add product to server?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Hamdard product = new Hamdard(productId,name,imageLink,description,actionAndUsages,sideEffect);
                        myRef.child(productId).setValue(product);

                        nameET.setText("");
                        imageLinkET.setText("");
                        descriptionET.setText("");
                        actionUsagesET.setText("");
                        sideEffectET.setText("");
                        Toast.makeText(getContext(), "Successfully Added!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void initialize(View view) {
        nameET = view.findViewById(R.id.product_name);
        imageLinkET = view.findViewById(R.id.product_image_link);
        descriptionET = view.findViewById(R.id.product_description);
        actionUsagesET = view.findViewById(R.id.product_action_and_usages);
        sideEffectET = view.findViewById(R.id.product_side_effect);

        add_product = view.findViewById(R.id.add_product);
    }
}
