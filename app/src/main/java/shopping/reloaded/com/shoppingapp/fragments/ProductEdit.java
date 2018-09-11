package shopping.reloaded.com.shoppingapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import shopping.reloaded.com.shoppingapp.R;
import shopping.reloaded.com.shoppingapp.adapters.ProductEditAdapter;
import shopping.reloaded.com.shoppingapp.models.Hamdard;

public class ProductEdit extends Fragment {

    ListView listView;
    ArrayList<Hamdard> arrayList;
    ProductEditAdapter adapter;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_list_farmer,null);

        listView = view.findViewById(R.id.products_editable);
        arrayList = new ArrayList<>();
        adapter = new ProductEditAdapter(getContext(),arrayList);
        listView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("hamdard_products");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot feedbackSnapshot : dataSnapshot.getChildren()){
                    String id = feedbackSnapshot.child("id").getValue().toString();
                    String name = feedbackSnapshot.child("name").getValue().toString();
                    String imageLink = feedbackSnapshot.child("imageLink").getValue().toString();
                    String desc = feedbackSnapshot.child("description").getValue().toString();
                    String action = feedbackSnapshot.child("actionAndUsages").getValue().toString();
                    String sideeffect = feedbackSnapshot.child("side_effectstorage").getValue().toString();

                    Hamdard product = new Hamdard(id,name,imageLink,desc,action,sideeffect);
                    arrayList.add(product);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
}
