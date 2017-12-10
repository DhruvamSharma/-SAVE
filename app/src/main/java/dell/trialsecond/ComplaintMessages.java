package dell.trialsecond;

import android.support.v7.widget.RecyclerView;

/**
 * Created by dell on 08-04-2017.
 */

public class ComplaintMessages extends RecyclerView.AdapterDataObserver {

    private String name;
    private String contact;
    private String email;
    private String location;
    private String complaint;
    private String photoVideoUrl;

    public ComplaintMessages(){

    }
    public ComplaintMessages(String name, String contact, String email, String location, String complaint, String photoVideoUrl){
        this.name=name;
        this.contact=contact;
        this.email=email;
        this.location=location;
        this.photoVideoUrl=photoVideoUrl;
        this.complaint=complaint;
    }

    public String getNameA(){
        return name;
    }

    public void setNameA(String name)
    {
        this.name=name;
    }

    public String getComplaint(){
        return complaint;
    }

    public void setComplaint(String name)
    {
        this.complaint=name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String name)
    {
        this.email=name;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String name)
    {
        this.location=name;
    }

    public String getPhotoVideoUrl(){
        return photoVideoUrl;
    }

    public void setPhotoVideoUrl(String name)
    {
        this.photoVideoUrl=name;
    }

    public String getContact(){
        return contact;
    }

    public void setContact(String name)
    {
        this.contact=name;
    }


}
