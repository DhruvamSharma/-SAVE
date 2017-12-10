package dell.trialsecond;


import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {


    private ImageButton closeButton;
    Animation alphaAppear, alphaDisappear;
    int x, y, width, height, hypotenuse;
    float pixelDensity;
    RelativeLayout revealView;
    LinearLayout layoutButtons;
    CoordinatorLayout coordinatorLayout;


    private RecyclerView recyclerView;
    private AlbumFirebaseAdapter adapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mComplaintDatabaseReference;

    TextView name;
    TextView email;
    View v;
    UserInfo userInfo;
    FloatingActionButton floatingActionButton;







    public DashboardFragment() {
        // Required empty public constructor
    }

    public static String getName()
    {
        return "DashboardFragment";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v =  inflater.inflate(R.layout.fragment_dashboard, container, false);

        setHasOptionsMenu(true);
        //initCollapsingToolbar();
        name=(TextView)v.findViewById(R.id.name);
        email=(TextView)v.findViewById(R.id.email);


        coordinatorLayout=(CoordinatorLayout)v.findViewById(R.id.main_content);



        pixelDensity = getResources().getDisplayMetrics().density;

        revealView = (RelativeLayout)v. findViewById(R.id.linearView);
        layoutButtons = (LinearLayout)v. findViewById(R.id.layoutButtons);
        closeButton = (ImageButton)v. findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeTwitter();
            }
        });

        alphaAppear = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_anim);
        alphaDisappear = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_disappear);
        floatingActionButton=(FloatingActionButton)v.findViewById(R.id.launchTwitterAnimation);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchTwitter();
            }
        });



        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("users");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                userInfo=dataSnapshot.getValue(UserInfo.class);
                if(!userInfo.getUserName().equals(""))
                    name.setText(userInfo.getUserName());
                if(!userInfo.getUserEmail().equals(""))
                    email.setText(userInfo.getUserEmail());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



     /*   recyclerView = (RecyclerView)v. findViewById(R.id.recycler_view);
        //Firebase Database
        mFirebaseDatabase=FirebaseDatabase.getInstance();

        mComplaintDatabaseReference=mFirebaseDatabase.getReference().child("complaintMessages");


        adapter = new AlbumFirebaseAdapter(getContext(),R.layout.album_card,ComplaintMessages.class,AlbumHolder.class,mComplaintDatabaseReference);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);*/


      /*  try {
            Glide.with(getContext()).load(R.drawable.profile_pic).into((ImageView)v. findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        // Inflate the layout for this fragment
        return v;
    }

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

  /*  private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) v.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) v.findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }*/


    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
 /*   public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }*/

   @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_notification:

                break;

            case R.id.menu_settings:

                break;
        }
        return true;
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    //.......Animations..........

    public void launchTwitter() {

        /*
         MARGIN_RIGHT = 16dp
         FAB_BUTTON_RADIUS = 28dp
         */
        width = coordinatorLayout.getWidth();
        height = coordinatorLayout.getHeight();

        x = width / 2;
        y = height / 2;
        hypotenuse = (int) Math.hypot(x, y);

        x = (int) (x - ((16 * pixelDensity) + (28 * pixelDensity)));

       /* CoordinatorLayout.LayoutParams parameters = (CoordinatorLayout.LayoutParams)
                revealView.getLayoutParams();
        parameters.height = coordinatorLayout.getHeight();
        revealView.setLayoutParams(parameters);*/

        floatingActionButton.animate()
                .translationX(-x)
                .translationY(-y)
                .setDuration(200)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {

                        Animator anim = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            anim = ViewAnimationUtils.createCircularReveal(revealView, width / 2, height / 2, 28 * pixelDensity, hypotenuse);
                        }
                        assert anim != null;
                        anim.setDuration(350);
                        anim.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                layoutButtons.setVisibility(View.VISIBLE);
                                closeButton.setVisibility(View.VISIBLE);
                                layoutButtons.startAnimation(alphaAppear);
                                closeButton.startAnimation(alphaAppear);
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                        floatingActionButton.setVisibility(View.GONE);
                        revealView.setVisibility(View.VISIBLE);
                        anim.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }

    public void closeTwitter() {

        alphaDisappear.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animator anim = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    anim = ViewAnimationUtils.createCircularReveal(revealView, width / 2, height / 2, hypotenuse, 28 * pixelDensity);
                }
                assert anim != null;
                anim.setDuration(350);
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        revealView.setVisibility(View.GONE);
                        floatingActionButton.setVisibility(View.VISIBLE);
                       floatingActionButton.animate()
                                .translationX(0f)
                                .translationY(0f)
                                .setDuration(200);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                anim.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        layoutButtons.setVisibility(View.GONE);
        closeButton.setVisibility(View.GONE);
        layoutButtons.startAnimation(alphaDisappear);
        closeButton.startAnimation(alphaDisappear);
    }

}
