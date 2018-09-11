package shopping.reloaded.com.shoppingapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import shopping.reloaded.com.shoppingapp.models.Products;
import shopping.reloaded.com.shoppingapp.R;
import shopping.reloaded.com.shoppingapp.adapters.CustomAdapter;

/**
 * Created by PROPHET on 3/13/2018.
 */

public class FoodFragment extends Fragment {

    public static final String GET_URL = "http://jachaibd.com/lict_wigl/food_getData.php";

    ArrayList<Products> arrayList;
    //ArrayList<String> arrayList;

    //ArrayAdapter<String> adapter;
    CustomAdapter adapter;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_fragment_layout,null);

        arrayList = new ArrayList<>();
        listView = view.findViewById(R.id.listview);

        RequestQueue queue = Volley.newRequestQueue(getContext());

        adapter = new CustomAdapter(getActivity(), arrayList);
        listView.setAdapter(adapter);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                GET_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for(int i=0;i<jsonArray.length();i++){
                            try {
                                String food_id = jsonArray.getJSONObject(i).getString("food_id");
                                String food_name = jsonArray.getJSONObject(i).getString("food_name");
                                String food_image = jsonArray.getJSONObject(i).getString("food_image");
                                String food_price = jsonArray.getJSONObject(i).getString("food_price");

                                Products products = new Products(food_id, food_name, food_price, food_image);
                                arrayList.add(products);
                                //arrayList.add(food_name + food_price);
                                adapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                Log.e("Error",e.toString());
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                    }
                }
        );
        Toast.makeText(getContext(), ""+arrayList.size(), Toast.LENGTH_SHORT).show();

        queue.add(jsonArrayRequest);

        return view;
    }

}
