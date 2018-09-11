package shopping.reloaded.com.shoppingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

import shopping.reloaded.com.shoppingapp.models.Products;
import shopping.reloaded.com.shoppingapp.R;

/**
 * Created by PROPHET on 3/22/2018.
 */

public class CustomAdapter extends BaseAdapter {

    public Context context;
    ArrayList<Products> arrayList;

    public CustomAdapter(Context context, ArrayList<Products> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View list = inflater.inflate(R.layout.product_layout,null);

        ImageView productImage = list.findViewById(R.id.productImage);
        TextView productName = list.findViewById(R.id.productName);
        TextView productPrice = list.findViewById(R.id.productPrice);

        productName.setText(arrayList.get(i).getName());
        productPrice.setText(arrayList.get(i).getPrice());
        Glide.with(context).load(arrayList.get(i).getImage()).into(productImage);

        return list;
    }
}
