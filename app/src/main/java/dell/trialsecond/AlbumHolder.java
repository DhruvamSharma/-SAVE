package dell.trialsecond;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by dell on 09-04-2017.
 */

public class AlbumHolder extends RecyclerView.ViewHolder {
    TextView title, count;
    ImageView thumbnail;
    private ImageButton imageButton;
    private Animation alphaAnimation;
    private float pixelDensity;
    private boolean flag = true;
    private LinearLayout revealView, layoutButtons;
    public AlbumHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.title);
        count = (TextView) view.findViewById(R.id.count);
        thumbnail = (ImageView) view.findViewById(R.id.imageView);
        imageButton=(ImageButton)view.findViewById(R.id.launchTwitterAnimation);
        pixelDensity = view.getResources().getDisplayMetrics().density;
        revealView=(LinearLayout)view.findViewById(R.id.linearView);
        layoutButtons=(LinearLayout)view.findViewById(R.id.layoutButtons);
        alphaAnimation = AnimationUtils.loadAnimation(view.getContext(), R.anim.alpha_anim);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  /*
         MARGIN_RIGHT = 16;
         FAB_BUTTON_RADIUS = 28;
         */
                int x = thumbnail.getRight();
                int y = thumbnail.getBottom();
                x -= ((28 * pixelDensity) + (16 * pixelDensity));

                int hypotenuse = (int) Math.hypot(thumbnail.getWidth(), thumbnail.getHeight());

                if (flag) {

                    imageButton.setBackgroundResource(R.drawable.rounded_cancel_button);
                    imageButton.setImageResource(R.mipmap.image_cancel);

                    FrameLayout.LayoutParams parameters = (FrameLayout.LayoutParams)
                            revealView.getLayoutParams();
                    parameters.height = thumbnail.getHeight();
                    revealView.setLayoutParams(parameters);

                    Animator anim = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        anim = ViewAnimationUtils.createCircularReveal(revealView, x, y, 0, hypotenuse);
                    }
                    assert anim != null;
                    anim.setDuration(400);

                    anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            layoutButtons.setVisibility(View.VISIBLE);
                            layoutButtons.startAnimation(alphaAnimation);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });

                    revealView.setVisibility(View.VISIBLE);
                    anim.start();

                    flag = false;
                } else {

                    imageButton.setImageResource(R.drawable.ic_pets_white_24dp);
                    imageButton.setBackgroundResource(R.drawable.rounded_button);


                    Animator anim = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        anim = ViewAnimationUtils.createCircularReveal(revealView, x, y, hypotenuse, 0);
                    }
                    assert anim != null;
                    anim.setDuration(400);

                    anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            revealView.setVisibility(View.GONE);
                            layoutButtons.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });

                    anim.start();
                    flag = true;
                }
            }
        });
    }
}
