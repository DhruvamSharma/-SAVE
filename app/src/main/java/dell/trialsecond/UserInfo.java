package dell.trialsecond;

/**
 * Created by dell on 09-04-2017.
 */

public class UserInfo {
    private String name;
    private String email;



    public UserInfo(){}
    public UserInfo(String name,String email){
        this.name=name;
        this.email=email;


    }


    public void setUserName(String name)
    {
        this.name=name;
    }

    public void setUserEmail(String name)
    {
        this.email=name;
    }


    public String getUserName()
    {
        return name;
    }

    public String getUserEmail()
    {
        return email;
    }



}
