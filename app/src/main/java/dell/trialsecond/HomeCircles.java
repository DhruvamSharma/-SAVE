package dell.trialsecond;

import android.support.v7.widget.RecyclerView;

/**
 * Created by dell on 22-04-2017.
 */

public class HomeCircles extends RecyclerView.AdapterDataObserver{
    String title;
    public HomeCircles(){}
    public HomeCircles(String title){
        this.title=title;

    }
    public void setCircleTitle(String title){
        this.title=title;
    }
    public String getCircleTitle(){
        return title;
    }

}
