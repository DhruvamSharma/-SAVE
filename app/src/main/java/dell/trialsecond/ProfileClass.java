package dell.trialsecond;

/**
 * Created by dell on 14-04-2017.
 */

public class ProfileClass {
    private String complaint;
    private int resourceId;

    public ProfileClass(){}
    public ProfileClass(String complaint,int resourceId)
    {
        this.complaint=complaint;
        this.resourceId=resourceId;
    }
    public void setComplaint(String complaint){
        this.complaint=complaint;
    }
    public void setResourceId(int resourceId)
    {
        this.resourceId=resourceId;
    }
    public String getComplaint()
    {return complaint;}
    public int getResourceId()
    {return resourceId;}
}
