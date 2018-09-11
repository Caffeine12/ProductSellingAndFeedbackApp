package shopping.reloaded.com.shoppingapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import shopping.reloaded.com.shoppingapp.R;
import shopping.reloaded.com.shoppingapp.adapters.FeedbackAdapter;
import shopping.reloaded.com.shoppingapp.models.Feedback;

public class Search_Feedback extends Fragment {

    EditText searchText;
    ListView listView;
    ArrayList<Feedback> arrayList;
    FeedbackAdapter adapter;

    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_feedback_layout,null);

        searchText = view.findViewById(R.id.search_et);
        listView = view.findViewById(R.id.search_list_view);
        arrayList = new ArrayList<>();

        adapter = new FeedbackAdapter(arrayList,getContext());
        listView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("feedbacks");
        fecthProductList();

        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                String searchtext = searchText.getText().toString();

                Query query = databaseReference.orderByChild("name").startAt(searchtext).endAt(searchtext+"\uf8ff");
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        arrayList.clear();
                        for(DataSnapshot feedbackSnapshot : dataSnapshot.getChildren()){
                            String id = feedbackSnapshot.child("id").getValue().toString();
                            String name = feedbackSnapshot.child("name").getValue().toString();
                            String feed = feedbackSnapshot.child("feedback").getValue().toString();
                            String email = feedbackSnapshot.child("email").getValue().toString();
                            String reply = feedbackSnapshot.child("reply").getValue().toString();
                            String contact = feedbackSnapshot.child("contact").getValue().toString();
                            String time = feedbackSnapshot.child("timeUploaded").getValue().toString();

                           Feedback feedback = new Feedback(id,name,email,feed,reply,contact,time);
                            arrayList.add(feedback);

                            Collections.sort(arrayList, new Comparator<Feedback>() {
                                @Override
                                public int compare(Feedback m1, Feedback m2) {
                                    long value =  Long.parseLong(m2.getTimeUploaded())  - Long.parseLong(m1.getTimeUploaded());
                                    return (int)value;
                                }
                            });

                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                return false;
            }
        });

        return view;
    }

    public void fecthProductList(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot feedbackSnapshot : dataSnapshot.getChildren()){
                    if(feedbackSnapshot!=null){
                        String id = feedbackSnapshot.child("id").getValue().toString();
                        String name = feedbackSnapshot.child("name").getValue().toString();
                        String feed = feedbackSnapshot.child("feedback").getValue().toString();
                        String email = feedbackSnapshot.child("email").getValue().toString();
                        String reply = feedbackSnapshot.child("reply").getValue().toString();
                        String contact = feedbackSnapshot.child("contact").getValue().toString();
                        String time = feedbackSnapshot.child("timeUploaded").getValue().toString();

                        if(TextUtils.isEmpty(reply)){
                            reply = "(no reply yet)";
                        }

                        Feedback feedback = new Feedback(id,name,email,feed,reply,contact,time);
                        arrayList.add(feedback);

                        Collections.sort(arrayList, new Comparator<Feedback>() {
                            @Override
                            public int compare(Feedback m1, Feedback m2) {
                                long value =  Long.parseLong(m2.getTimeUploaded())  - Long.parseLong(m1.getTimeUploaded());
                                return (int)value;
                            }
                        });
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Cant send feedback. Try Again!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
