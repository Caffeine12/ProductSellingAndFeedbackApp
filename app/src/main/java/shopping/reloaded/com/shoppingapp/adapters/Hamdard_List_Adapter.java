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

import shopping.reloaded.com.shoppingapp.R;
import shopping.reloaded.com.shoppingapp.models.Hamdard;
import shopping.reloaded.com.shoppingapp.models.TestProduct;


public class Hamdard_List_Adapter extends BaseAdapter {

    public Context context;
    ArrayList<Hamdard> arrayList;

    public Hamdard_List_Adapter(Context context, ArrayList<Hamdard> arrayList) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.herbal_product_layout,null);

        ImageView image = view.findViewById(R.id.p_image);
        TextView name = view.findViewById(R.id.p_name);

        name.setText(arrayList.get(position).getName());
        Glide.with(context).load(arrayList.get(position).getImageLink()).into(image);

        return view;
    }
}
