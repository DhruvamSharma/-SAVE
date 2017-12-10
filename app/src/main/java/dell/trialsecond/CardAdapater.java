package dell.trialsecond;

import android.support.v7.widget.CardView;

/**
 * Created by dell on 17-04-2017.
 */

public interface CardAdapater {
    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();

    void addCardItem(CardItem cardItem);

}
