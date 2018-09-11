package shopping.reloaded.com.shoppingapp.models;

public class Feedback {
    String id, name, email, feedback, reply, contact, timeUploaded;

    public Feedback(String id, String name, String email, String feedback, String reply, String contact, String timeUploaded) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.feedback = feedback;
        this.reply = reply;
        this.contact = contact;
        this.timeUploaded = timeUploaded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Name: " + name +"\nEmail: " + email+"\nFeedback: " + feedback;
    }

    public String getTimeUploaded() {
        return timeUploaded;
    }

    public void setTimeUploaded(String timeUploaded) {
        this.timeUploaded = timeUploaded;
    }
}
