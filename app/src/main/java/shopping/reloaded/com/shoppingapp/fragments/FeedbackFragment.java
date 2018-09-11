package shopping.reloaded.com.shoppingapp.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import shopping.reloaded.com.shoppingapp.R;
import shopping.reloaded.com.shoppingapp.models.Feedback;

/**
 * Created by PROPHET on 3/13/2018.
 */

public class FeedbackFragment extends Fragment {

    private static final String FEEDBACK_URL = "http://jachaibd.com/lict_wigl/userfeedback.php";

    EditText nameET, emailET, feedbackET, contactET;
    Button sendFeedback;

    FirebaseDatabase database;
    DatabaseReference myRef;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.feedback_layout,null);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("feedbacks");

        nameET = view.findViewById(R.id.name);
        emailET = view.findViewById(R.id.email);
        feedbackET = view.findViewById(R.id.feedback);
        sendFeedback = view.findViewById(R.id.sendfeedback);
        contactET = view.findViewById(R.id.contact_ET);

        //getFeedbacks();
        sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertFeedback();
            }
        });

        return view;
    }

    public void InsertFeedback(){
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, FEEDBACK_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(getContext(), "SUCCESSFUL", Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Override
//            protected Map<String,String> getParams() throws AuthFailureError{
//                Map<String,String> params = new HashMap<>();
//                params.put("NAME",nameET.getText().toString());
//                params.put("EMAIL",emailET.getText().toString());
//                params.put("FEEDBACK",feedbackET.getText().toString());
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(stringRequest);


        final String feedbackId = myRef.push().getKey();
        final String name = nameET.getText().toString().trim();
        final String email = emailET.getText().toString().trim();
        final String feed = feedbackET.getText().toString().trim();
        final String contact = contactET.getText().toString().trim();

        //validation
        boolean error = false;
        if(name.trim().length()<6){
            Toast.makeText(getContext(), "Name must be atleast 6 characters!", Toast.LENGTH_SHORT).show();
            error = true;
        }
        else if(contact.length()!=11){
            Toast.makeText(getContext(), "Contact must contain 11 digits", Toast.LENGTH_SHORT).show();
            error = true;
        }
        else if(feed.length()<20){
            Toast.makeText(getContext(), "Write at least few words to explain your feedback", Toast.LENGTH_SHORT).show();
            error = true;
        }

        if(!error){
            new AlertDialog.Builder(getContext())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage("Do you want to send this feedback?")
                    .setTitle("Confirm feedback!")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Feedback feedback = new Feedback(feedbackId,name,email,feed, "",contact,Long.toString(System.currentTimeMillis()/1000));
                            myRef.child(feedbackId).setValue(feedback);
                            nameET.setText("");
                            emailET.setText("");
                            feedbackET.setText("");
                            contactET.setText("");
                            Toast.makeText(getContext(), "Successfully sent feedback", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
    }
}
