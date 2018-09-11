package shopping.reloaded.com.shoppingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import shopping.reloaded.com.shoppingapp.R;
import shopping.reloaded.com.shoppingapp.models.Feedback;

public class FeedbackAdapter extends BaseAdapter {

    ArrayList<Feedback> arrayList;
    Context context;

    public FeedbackAdapter(ArrayList<Feedback> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
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
        View view = inflater.inflate(R.layout.feedback_item_layout,null);

        TextView name, email, feedback, reply, date, contact;
        name = view.findViewById(R.id.feedback_name);
        email = view.findViewById(R.id.feedback_email);
        feedback = view.findViewById(R.id.feedback_only);
        reply = view.findViewById(R.id.replyTV);
        date = view.findViewById(R.id.date);
        contact = view.findViewById(R.id.feedback_contact);

        name.setText(arrayList.get(position).getName());

        if(arrayList.get(position).getEmail().equalsIgnoreCase("")){
            email.setText("(no email provided!)");
        }else{
            email.setText(arrayList.get(position).getEmail());
        }

        feedback.setText(arrayList.get(position).getFeedback());
        reply.setText(arrayList.get(position).getReply());
        contact.setText(arrayList.get(position).getContact());


        int timeStamp = Integer.parseInt(arrayList.get(position).getTimeUploaded());
        java.util.Date time = new java.util.Date((long)timeStamp*1000);
        StringBuilder sb = new StringBuilder(time.toString());
        String properTime = sb.delete(19,29).toString();
        date.setText(properTime);

        return view;
    }
}
