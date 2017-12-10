package dell.trialsecond;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by dell on 22-04-2017.
 */

public class HomeCirclesFirebaseAdapter extends FirebaseRecyclerAdapter<HomeCircles, HomeCirclesHolder> {
    private Context context;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    /**
     * @param modelClass      Firebase will marshall the data at a location into
     *                        an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list.
     *                        You will be responsible for populating an instance of the corresponding
     *                        view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location,
     *                        using some combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */
    public HomeCirclesFirebaseAdapter(Context context, Class<HomeCircles> modelClass, int modelLayout, Class<HomeCirclesHolder> viewHolderClass, DatabaseReference ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context=context;
        firebaseStorage= FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference().child("HomeCircles");
    }

    @Override
    protected void populateViewHolder(HomeCirclesHolder viewHolder, HomeCircles model, int position) {
        viewHolder.title.setText(model.getCircleTitle());
        Glide.with(viewHolder.circularImage.getContext()).using(new FirebaseImageLoader()
        ).load(storageReference).into(viewHolder.circularImage);
    }

}
