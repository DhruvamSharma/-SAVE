package dell.trialsecond;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.firebase.ui.auth.AuthUI.EMAIL_PROVIDER;
import static com.firebase.ui.auth.AuthUI.GOOGLE_PROVIDER;
import static com.firebase.ui.auth.AuthUI.IdpConfig;
import static com.firebase.ui.auth.AuthUI.getInstance;

public class MainActivity extends AppCompatActivity {
    String name;
    CoordinatorLayout layout;
    Toolbar toolbar;
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    List<IdpConfig> providers;




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(!name.equals(HomeFragment.getName())) {
                        selectedFragment = HomeFragment.newInstance();
                        name = HomeFragment.  getName();
                        toolbar.onFinishTemporaryDetach();
                    }
                    else
                        return true;

                    break;
                case R.id.navigation_dashboard:
                    if (!name.equals(DashboardFragment.getName())) {
                        selectedFragment = DashboardFragment.newInstance();
                        toolbar.onStartTemporaryDetach();
                        name = DashboardFragment.getName();

                    }
                    else return true;

                    break;
                case R.id.navigation_notifications:
                    if (!name.equals(NotificationFragment.getName())) {
                        toolbar.onFinishTemporaryDetach();
                        selectedFragment = NotificationFragment.newInstance();
                        name = NotificationFragment.getName();



                    }
                    else return true;
                    break;
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

           // transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            layout=(CoordinatorLayout) findViewById(R.id.container);
            layout.setOnTouchListener(new Guestures(MainActivity.this) {
                public void onSwipeTop(){
                    Toast.makeText(MainActivity.this,"hello",Toast.LENGTH_SHORT).show();
                }
            });

            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        toolbar.bringToFront();

        providers = new ArrayList<>();

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mFirebaseDatabase.setPersistenceEnabled(true);
        mDatabaseReference= mFirebaseDatabase.getReference().child("users");
        mDatabaseReference.keepSynced(true);
        mFirebaseAuth= FirebaseAuth.getInstance();
                mAuthStateListener=new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser firebaseUser=mFirebaseAuth.getCurrentUser();
                        if(firebaseUser==null)
                        {
                            providers.add(new IdpConfig.Builder(EMAIL_PROVIDER).build());
                            providers.add(new IdpConfig.Builder(GOOGLE_PROVIDER).build());
                            startActivityForResult(
                                    getInstance()
                                            .createSignInIntentBuilder()
                                            .setIsSmartLockEnabled(true)
                                            .setProviders(EMAIL_PROVIDER,GOOGLE_PROVIDER)
                                            .setTheme(R.style.FirebaseUILoginTheme)
                                            .setLogo(R.drawable.ic_person_black_24dp)
                                            .build(),
                                    1);
                        }


                    }
                };








        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP)
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.frame_layout, HomeFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();

        name=HomeFragment.getName();








        /*FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/


    }



    public void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onPause() {
        super.onPause();
            if(mAuthStateListener==null) {

                mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
            }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode== ResultCodes.OK)
            {

                FirebaseUser firebaseUser=mFirebaseAuth.getCurrentUser();
                assert firebaseUser != null;
                UserInfo userInfo=new UserInfo(firebaseUser.getDisplayName(),firebaseUser.getEmail());
                mDatabaseReference.push().setValue(userInfo);
                Toast.makeText(MainActivity.this,"UserAdded",Toast.LENGTH_SHORT).show();

                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });






            }
            else
                {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Toast.makeText(getApplicationContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(getApplicationContext(),"No network",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(getApplicationContext(),"Unknown Error",Toast.LENGTH_SHORT).show();
                }
            }


        }
        }




}
