package dell.trialsecond;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by dell on 22-04-2017.
 */

public class HomeCirclesHolder extends RecyclerView.ViewHolder {

    TextView title;
    ImageView circularImage;
    public HomeCirclesHolder(View itemView) {
        super(itemView);
        title=(TextView)itemView.findViewById(R.id.title);
        circularImage=(ImageView)itemView.findViewById(R.id.imageCircle);
    }
}
