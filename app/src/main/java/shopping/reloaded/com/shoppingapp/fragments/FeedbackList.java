package shopping.reloaded.com.shoppingapp.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import shopping.reloaded.com.shoppingapp.R;
import shopping.reloaded.com.shoppingapp.adapters.FeedbackAdapter;
import shopping.reloaded.com.shoppingapp.models.Feedback;

public class FeedbackList extends Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef;

    ListView listView;
    FeedbackAdapter adapter;
    ArrayList<Feedback> arrayList;
    Dialog mydialog;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feedback_list,null);

        listView = view.findViewById(R.id.feedback_listview);
        arrayList = new ArrayList<>();
        adapter = new FeedbackAdapter(arrayList,getContext());
        listView.setAdapter(adapter);

        mydialog = new Dialog(getContext());

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("feedbacks");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot feedbackSnapshot : dataSnapshot.getChildren()){
                    if(feedbackSnapshot!=null){
                        String id = feedbackSnapshot.child("id").getValue().toString();
                        String name = feedbackSnapshot.child("name").getValue().toString();
                        String email = feedbackSnapshot.child("email").getValue().toString();
                        String feedback = feedbackSnapshot.child("feedback").getValue().toString();
                        String reply = feedbackSnapshot.child("reply").getValue().toString();
                        String contact = feedbackSnapshot.child("contact").getValue().toString();
                        String time = feedbackSnapshot.child("timeUploaded").getValue().toString();

                        if(reply.equalsIgnoreCase("")){
                            reply = "(No reply)";
                        }

                        Feedback feed = new Feedback(id,name,email,feedback,reply,contact,time);
                        arrayList.add(feed);
                        adapter.notifyDataSetChanged();

                        Collections.sort(arrayList, new Comparator<Feedback>() {
                            @Override
                            public int compare(Feedback m1, Feedback m2) {
                                long value =  Long.parseLong(m2.getTimeUploaded())  - Long.parseLong(m1.getTimeUploaded());
                                return (int)value;
                            }
                        });
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Cant send feedback. Try Again!", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                setDialogWidth();
                final EditText editText = mydialog.findViewById(R.id.replyText);
                Button button = mydialog.findViewById(R.id.replyButton);
                Button deleteFeedback = mydialog.findViewById(R.id.deleteFeedbackButton);

                if(arrayList.get(position).getReply().equalsIgnoreCase("(No reply)")){
                    editText.setText("");
                }else{
                    editText.setText(arrayList.get(position).getReply());
                }

                deleteFeedback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(getContext())
                                .setTitle("Confirm!")
                                .setMessage("Do you really want to delete this feedback completely?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String id = arrayList.get(position).getId();
                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("feedbacks").child(id);
                                        databaseReference.removeValue();
                                        adapter.notifyDataSetChanged();

                                        dialog.dismiss();
                                        mydialog.dismiss();
                                        Toast.makeText(getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();

                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(editText.getText().toString().trim().length()==0){
                            Toast.makeText(getContext(), "Nothing is replied", Toast.LENGTH_SHORT).show();
                        }else{
                            String id = arrayList.get(position).getId();
                            String name = arrayList.get(position).getName();
                            String email = arrayList.get(position).getEmail();
                            String feedback = arrayList.get(position).getFeedback();
                            String contact = arrayList.get(position).getContact();
                            String time = arrayList.get(position).getTimeUploaded();
                            Feedback fb = new Feedback(id,name,email,feedback,editText.getText().toString().trim(),contact,time);

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("feedbacks").child(id);
                            databaseReference.setValue(fb);

                            Toast.makeText(getContext(), "Replied Successfully", Toast.LENGTH_SHORT).show();

                            mydialog.dismiss();
                        }
                    }
                });
                mydialog.show();
            }
        });

        return view;
    }

    private void setDialogWidth() {
        mydialog.setContentView(R.layout.reply_layout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mydialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        mydialog.getWindow().setAttributes(lp);
    }
}
