package shopping.reloaded.com.shoppingapp.fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import shopping.reloaded.com.shoppingapp.R;
import shopping.reloaded.com.shoppingapp.adapters.Hamdard_List_Adapter;
import shopping.reloaded.com.shoppingapp.models.Hamdard;
import shopping.reloaded.com.shoppingapp.models.Products;
import shopping.reloaded.com.shoppingapp.models.TestProduct;

/**
 * Created by PROPHET on 3/13/2018.
 */

public class PopularFragment extends Fragment{

    FirebaseDatabase database;
    DatabaseReference myRef;
    ListView listView;
    ArrayList<Hamdard> arrayList;
    Dialog dialog;
    ProgressBar progressBar;

    Hamdard_List_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.popular_fragment_layout,null);

        dialog = new Dialog(getContext());

        progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);

        listView = view.findViewById(R.id.product_listview);
        arrayList = new ArrayList<>();
        adapter = new Hamdard_List_Adapter(getContext(),arrayList);
        listView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("hamdard_products");

        progressBar.setVisibility(View.VISIBLE);
        //fecthProductList();

        new HeavyWorker(getContext()).execute();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setDialogWidth();

                TextView txtClose = dialog.findViewById(R.id.txtclose);
                ImageView image = dialog.findViewById(R.id.image_show);

                TextView name = dialog.findViewById(R.id.name_show);
                TextView desc = dialog.findViewById(R.id.description_show);
                TextView action = dialog.findViewById(R.id.action_show);
                TextView side = dialog.findViewById(R.id.sideeffect_show);

                name.setText(arrayList.get(position).getName());
                desc.setText(arrayList.get(position).getDescription());
                action.setText(arrayList.get(position).getActionAndUsages());
                side.setText(arrayList.get(position).getSide_effectstorage());

                Glide.with(getContext()).load(arrayList.get(position).getImageLink()).into(image);
                dialog.setCancelable(false);
                dialog.show();

                txtClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        return view;
    }

    private void setDialogWidth() {
        dialog.setContentView(R.layout.hamdard_item_layout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);
    }
    public void fecthProductList(){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot productSnapshot : dataSnapshot.getChildren()){
                    String id = productSnapshot.child("id").getValue().toString();
                    String name = productSnapshot.child("name").getValue().toString();
                    String imageLink = productSnapshot.child("imageLink").getValue().toString();
                    String description = productSnapshot.child("description").getValue().toString();
                    String actionUsages = productSnapshot.child("actionAndUsages").getValue().toString();
                    String side_effectstorage = productSnapshot.child("side_effectstorage").getValue().toString();

                    Hamdard product = new Hamdard(id,name,imageLink,description, actionUsages,side_effectstorage);

                    arrayList.add(product);
                    adapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Cant send feedback. Try Again!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class HeavyWorker extends AsyncTask< String , Context , Void > {

        private ProgressDialog progressDialog ;
        private Context targetCtx ;

        public HeavyWorker ( Context context) {
            this.targetCtx = context ;
            progressDialog = new ProgressDialog ( targetCtx ) ;
            progressDialog.setCancelable ( false ) ;
            progressDialog.setMessage ( "Retrieving latest information" ) ;
            progressDialog.setTitle ( "Please wait" ) ;
            progressDialog.setIndeterminate ( true ) ;
        }

        @ Override
        protected void onPreExecute ( ) {
            progressDialog.show();
            progressBar.setVisibility(View.VISIBLE);
        }

        @ Override
        protected Void doInBackground ( String ... params ) {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    arrayList.clear();
                    for(DataSnapshot productSnapshot : dataSnapshot.getChildren()){
                        String id = productSnapshot.child("id").getValue().toString();
                        String name = productSnapshot.child("name").getValue().toString();
                        String imageLink = productSnapshot.child("imageLink").getValue().toString();
                        String description = productSnapshot.child("description").getValue().toString();
                        String actionUsages = productSnapshot.child("actionAndUsages").getValue().toString();
                        String side_effectstorage = productSnapshot.child("side_effectstorage").getValue().toString();

                        Hamdard product = new Hamdard(id,name,imageLink,description, actionUsages,side_effectstorage);

                        arrayList.add(product);
                        adapter.notifyDataSetChanged();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Cant send feedback. Try Again!", Toast.LENGTH_SHORT).show();
                }
            });
            return null ;
        }

        @ Override
        protected void onPostExecute ( Void result ) {
            progressDialog.dismiss();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
