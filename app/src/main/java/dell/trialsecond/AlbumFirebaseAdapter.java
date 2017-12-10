package dell.trialsecond;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by dell on 09-04-2017.
 */

class AlbumFirebaseAdapter extends FirebaseRecyclerAdapter<ComplaintMessages, AlbumHolder> {
    private Context context;




    AlbumFirebaseAdapter(Context context, int modelLayout, Class<ComplaintMessages> modelClass, Class<AlbumHolder> viewHolderClass, DatabaseReference ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;

    }

    @Override
    protected void populateViewHolder(final AlbumHolder viewHolder, ComplaintMessages model, int position) {
        viewHolder.title.setText(model.getNameA());
        viewHolder.count.setText(model.getLocation());

        Glide.with(viewHolder.thumbnail.getContext()).load(model.getPhotoVideoUrl()).into(viewHolder.thumbnail);


    }





}
