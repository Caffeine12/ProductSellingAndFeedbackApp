package shopping.reloaded.com.shoppingapp.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import shopping.reloaded.com.shoppingapp.R;
import shopping.reloaded.com.shoppingapp.models.Hamdard;

public class ProductEditAdapter extends BaseAdapter {

    Context context;
    ArrayList<Hamdard> arrayList;

    public ProductEditAdapter(Context context, ArrayList<Hamdard> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.product_edit_delete_layout,null);

        ImageView image = view.findViewById(R.id.p_image);
        TextView name = view.findViewById(R.id.logo_name);
        ImageView edit = view.findViewById(R.id.editProduct);
        ImageView delete = view.findViewById(R.id.deleteProduct);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog mydialog = new Dialog(context);
                mydialog.setContentView(R.layout.product_edit_format);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(mydialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                mydialog.getWindow().setAttributes(lp);

                final EditText name = mydialog.findViewById(R.id.update_name);
                final EditText image = mydialog.findViewById(R.id.update_image_link);
                final EditText desc = mydialog.findViewById(R.id.update_description);
                final EditText action = mydialog.findViewById(R.id.update_action);
                final EditText side = mydialog.findViewById(R.id.update_side_effects);

                name.setText(arrayList.get(position).getName());
                image.setText(arrayList.get(position).getImageLink());
                desc.setText(arrayList.get(position).getDescription());
                action.setText(arrayList.get(position).getActionAndUsages());
                side.setText(arrayList.get(position).getSide_effectstorage());

                Button button = mydialog.findViewById(R.id.update_product);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(context)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Confirm!")
                                .setMessage("Do you want to update the product details?")
                                .setCancelable(false)
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String id = arrayList.get(position).getId();
                                        String up_name =  name.getText().toString().trim();
                                        String up_image =  image.getText().toString().trim();
                                        String up_desc =  desc.getText().toString().trim();
                                        String up_action =  action.getText().toString().trim();
                                        String up_side =  side.getText().toString().trim();

                                        if(TextUtils.isEmpty(up_name) || TextUtils.isEmpty(up_image) || TextUtils.isEmpty(up_desc) || TextUtils.isEmpty(up_action) || TextUtils.isEmpty(up_side)){
                                            Toast.makeText(context, "Missing field(s)", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Hamdard product = new Hamdard(id, up_name, up_image, up_desc, up_action,up_side);

                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("hamdard_products").child(id);
                                            databaseReference.setValue(product);

                                            Toast.makeText(context, "Update Successful", Toast.LENGTH_SHORT).show();

                                            mydialog.dismiss();
                                        }
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                });

                mydialog.show();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Confirm!")
                        .setMessage("Do you want to delete this product?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = arrayList.get(position).getId();
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("hamdard_products").child(id);
                                databaseReference.removeValue();
                                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
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
        });

        name.setText(arrayList.get(position).getName());
        Glide.with(context).load(arrayList.get(position).getImageLink()).into(image);

        return view;
    }
}
