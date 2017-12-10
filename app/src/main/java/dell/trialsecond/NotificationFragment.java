package dell.trialsecond;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.internal.zzs.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    TextInputLayout contactLayout;
    TextInputLayout nameLayout;
    EditText contact;
    TextInputLayout ComplaintLayout;
    EditText complaint;
    TextInputLayout emailLayout;
    EditText email;
    TextInputLayout locationLayout;
    EditText name;
    LocationManager locationManager;
    LocationListener locationListener;
    ImageView coordinates;
    boolean isImage;
    EditText location;
    ImageView imageView;
    Uri fileUri;
    TextView textView;
    ViewGroup viewGroup;
    CardView imageCard;
    CardView videoCard;
    AlertDialog alertDialog;
    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 12345;


    private String filePath = null;

    private VideoView vidPreview;

    int type;
    String request;
    private FloatingActionButton floatingActionButton;
    private FloatingActionButton picChoose;
    private FloatingActionButton videoChoose;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mComplaintDatabaseReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private ChildEventListener mChildEventListener;


    public NotificationFragment() {
        // Required empty public constructor
    }

    public static String getName() {
        return "NotificationFragment";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.fragment_notification, container, false);
        imageCard=(CardView)v.findViewById(R.id.view3);
        videoCard=(CardView)v.findViewById(R.id.view4);
        picChoose=(FloatingActionButton)v.findViewById(R.id.picChoose);
        videoChoose=(FloatingActionButton)v.findViewById(R.id.videoChoose);
        //Firebase



        mFirebaseStorage=FirebaseStorage.getInstance();
        mStorageReference=mFirebaseStorage.getReference().child("complaintPicVideo");

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mComplaintDatabaseReference=mFirebaseDatabase.getReference().child("complaintMessages");



        contactLayout = (TextInputLayout) v.findViewById(R.id.fNameLayout);
        contactLayout.setErrorEnabled(true);





        coordinates = (ImageView) v.findViewById(R.id.image8);
        coordinates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

        emailLayout = (TextInputLayout) v.findViewById(R.id.tNameLayout);
        emailLayout.setErrorEnabled(true);

        email = (EditText) v.findViewById(R.id.tName);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length()>0){
                    floatingActionButton.setEnabled(true);
                }
                else floatingActionButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        vidPreview = (VideoView) v.findViewById(R.id.videoView);


        imageView = (ImageView) v.findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    TransitionManager.beginDelayedTransition(viewGroup, new TransitionSet().addTransition(new ChangeBounds()).addTransition(new android.support.transition.Fade()));
                    ViewGroup.LayoutParams params=imageView.getLayoutParams();

                    params.height=expanded ?ViewGroup.LayoutParams.MATCH_PARENT:ViewGroup.LayoutParams.WRAP_CONTENT;
                    imageView.setLayoutParams(params);
                    imageView.setScaleType(expanded?ImageView.ScaleType.CENTER_CROP:ImageView.ScaleType.FIT_CENTER);
                    expanded=true;

                }*/
                call();
            }
        });


        nameLayout = (TextInputLayout) v.findViewById(R.id.ageLayout);
        nameLayout.setErrorEnabled(true);
        contact = (EditText) v.findViewById(R.id.fName);
        contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length()>0){
                    floatingActionButton.setEnabled(true);
                }
                else floatingActionButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        ComplaintLayout = (TextInputLayout) v.findViewById(R.id.llNameLayout);
        ComplaintLayout.setErrorEnabled(true);
        complaint = (EditText) v.findViewById(R.id.llName);
        complaint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length()>0){
                    floatingActionButton.setEnabled(true);
                }
                else floatingActionButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        locationLayout = (TextInputLayout) v.findViewById(R.id.ageLayoutt);
        locationLayout.setErrorEnabled(true);

        //Displaying EditText Error
        name = (EditText) v.findViewById(R.id.age);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length()>0){
                    floatingActionButton.setEnabled(true);
                }
                else floatingActionButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //Displaying both TextInputLayout and EditText Errors


        location = (EditText) v.findViewById(R.id.agee);
        location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length()>0){
                    floatingActionButton.setEnabled(true);
                }
                else floatingActionButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        floatingActionButton = (FloatingActionButton) v.findViewById(R.id.send);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* int i = 0;

                if (name.getText().toString().equals("") | location.getText().toString().equals("") | contact.getText().toString().equals("") | complaint.getText().toString().equals("")) {
                    if (name.getText().toString().equals(""))
                        nameLayout.setError("Name is required!");
                    else
                        nameLayout.setError(null);
                    if (contact.getText().toString().equals(""))
                        contactLayout.setError("Contact is required!");
                    else
                        contactLayout.setError(null);
                    if (location.getText().toString().equals(""))
                        locationLayout.setError("Location is required!");
                    else
                        locationLayout.setError(null);
                    if (complaint.getText().toString().equals(""))
                        ComplaintLayout.setError("Complaint is required!");
                    else
                        ComplaintLayout.setError(null);
                } else {
                    nameLayout.setError(null);
                    contactLayout.setError(null);
                    locationLayout.setError(null);
                    ComplaintLayout.setError(null);
                    emailLayout.setError(null);
                    //i++;
                }


                if (!contact.getText().toString().equals(""))
                    if (contact.getText().toString().length() <= 9)
                        contact.setError("Please type a valid number");
                    else {
                        name.setError(null);
                        // i++;
                    }

                if (!email.getText().toString().equals(""))
                    if (!email.getText().toString().contains("@") || !email.getText().toString().contains(".com"))
                        email.setError("Please type a valid email");
                    else {
                        email.setError(null);
                        // i++;
                    }

               if(fileUri==null)
               {
                   Toast.makeText(getContext(),"Please select image or video",Toast.LENGTH_SHORT).show();
               }
               else
                new UploadFileToServer().execute();*/
                String complaintID=mComplaintDatabaseReference.push().getKey();
                ComplaintMessages complaintMessages=new ComplaintMessages(name.getText().toString(),contact.getText().toString(),email.getText().toString(),location.getText().toString(),complaint.getText().toString(),fileUri.toString());
                mComplaintDatabaseReference.child(complaintID).setValue(complaintMessages);
                finishUp();


                contact.setText("");
                complaint.setText("");
                location.setText("");
                name.setText("");
                email.setText("");
                imageView.setImageResource(R.drawable.fab);
                Toast.makeText(getContext(),"Complaint Successfully Made",Toast.LENGTH_SHORT).show();


            }
        });

        request = getRequestResult();

        viewGroup = container;


        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("users");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserInfo userInfo=dataSnapshot.getValue(UserInfo.class);
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




        setHasOptionsMenu(true);
        return v;
    }

    private void finishUp() {
        StorageReference storageReference=mStorageReference.child(fileUri.getLastPathSegment());
        storageReference.putFile(fileUri).addOnSuccessListener(getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl=taskSnapshot.getDownloadUrl();
                assert downloadUrl != null;
                mComplaintDatabaseReference.child("photoVideoUrl").setValue(downloadUrl);
                Toast.makeText(getContext(),"Url added",Toast.LENGTH_SHORT).show();



            }
        });
    }

    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && requestCode == PERMISSIONS_MULTIPLE_REQUEST) {
            boolean cameraPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;

            if (cameraPermission && readExternalFile) {

            }
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content),
                    "Please Grant Permissions to upload profile photo",
                    Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestPermissions(
                                    new String[]{android.Manifest.permission
                                            .READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA},
                                    PERMISSIONS_MULTIPLE_REQUEST);
                        }
                    }).show();
        }


        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 124) {
            // Now user should be able to use camera

            callLocation();
        } else {
            // Your app will not have this permission. Turn off all functions
            // that require this permission or it will force close like your
            // original question

        }
    }

    public void call() {


        alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Picture or Video")
                .setMessage("Please select the option")
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (request.equals("finish")) {

                        } else if (request.equals("okPermissionGranted")) {
                            callPicCamera();
                        } else if (request.equals("PermissionAlreadyGiven")) {
                            callPicCamera();
                        } else {
                            Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("Video", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (request.equals("finish")) {

                        } else if (request.equals("okPermissionGranted")) {
                            callVideoCamera();
                        } else if (request.equals("PermissionAlreadyGiven")) {
                            callVideoCamera();
                        } else {
                            Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }

    public String getRequestResult() {




        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(getActivity(),
                        android.Manifest.permission.CAMERA) + ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) + ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(
                    new String[]{android.Manifest.permission
                            .READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_MULTIPLE_REQUEST);

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (getActivity(), android.Manifest.permission.CAMERA) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale
                    (getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

                Snackbar.make(getActivity().findViewById(android.R.id.content),
                        "Please Grant Permissions to upload photo/video",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestPermissions(
                                        new String[]{android.Manifest.permission
                                                .READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSIONS_MULTIPLE_REQUEST);
                            }
                        }).show();
            } else
                {

                    return "okPermissionGranted";
                }
        }
        else
            {
            // write your logic code if permission already granted
            return "PermissionAlreadyGiven";
        }
        return "finish";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //imageView.setImageBitmap((Bitmap) data.getExtras().get("data"));

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // successfully captured the image
                // launching upload activity
                isImage = true;

                filePath = fileUri.getPath();
                launchUploadActivity(true);


            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(getContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to capture image
                Toast.makeText(getContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // video successfully recorded
                // launching upload activity
                isImage = false;
                filePath = fileUri.getPath();
                textView.setText("Play the video");
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vidPreview.start();
                    }
                });
                launchUploadActivity(false);

            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled recording
                Toast.makeText(getContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to record video
                Toast.makeText(getContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void launchUploadActivity(boolean isImage) {
        if (filePath != null) {
            // Displaying the image or video on the screen
            previewMedia(isImage);
        } else {
            Toast.makeText(getContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }

    }

    private void previewMedia(boolean isImage) {
        // Checking whether captured media is image or video
        if (isImage) {
            videoChoose.setVisibility(View.GONE);
            videoCard.setVisibility(View.GONE);
            vidPreview.setVisibility(View.GONE);
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

            imageView.setImageBitmap(bitmap);
        } else {
            picChoose.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            imageCard.setVisibility(View.GONE);
            videoCard.setVisibility(View.VISIBLE);
            vidPreview.setVisibility(View.VISIBLE);
            vidPreview.setVideoPath(filePath);
            // start playing
            vidPreview.start();
        }

    }


    void callPicCamera() {
        alertDialog.hide();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

    }

    void callVideoCamera() {
        alertDialog.hide();
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);

        // set video quality
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);// set the image file
        // name

        // start the video capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }

  /*  @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        // get the file url
        assert savedInstanceState != null;
        fileUri = savedInstanceState.getParcelable("file_uri");
    }*/

    void callLocation() {

       // startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));


        Toast.makeText(getContext(), "Please wait, fill other blanks in the meanwhile", Toast.LENGTH_SHORT).show();


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

    }


    public void getLocation() {
        final AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
        ab.setTitle("Sit back and RELAX");
        ab.setMessage("Do you want us to add your current location?");
        ab.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                locationManager = (LocationManager)getContext(). getSystemService(Context.LOCATION_SERVICE);
                locationListener = new MyLocationListener();



                callLocation();

            }
        });
        ab.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = ab.create();
        dialog.show();

    }



    /*---------- Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            location.append("\n");
           /* Toast.makeText(
                    getBaseContext(),
                    "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                            + loc.getLongitude(), Toast.LENGTH_SHORT).show();*/
            String longitude = "Longitude: " + loc.getLongitude();
            Log.v("........", longitude);
            String latitude = "Latitude: " + loc.getLatitude();
            Log.v(".......", latitude);

        /*------- To get city name from coordinates -------- */
            String cityName = "";
            Geocoder gcd = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(),
                        loc.getLongitude(), 1);
                if (addresses.size() > 0) {
                    System.out.println(addresses.get(0).getLocality());
                    cityName = addresses.get(0).getLocality();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            String s;
            if (cityName.equals("")){
                s = longitude + "\n" + latitude;
            }
            else{
                s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                        + cityName;
            }

            location.append(s);
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }


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

    /**
     * ------------ Helper Methods ----------------------
     * */

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider",getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private File getOutputMediaFile(int type1) {

        type=type1;
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state))
        {
            Log.d("myAppName", "Error: external storage is unavailable");
            return null;
        }
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
        {
            Log.d("myAppName", "Error: external storage is read only.");
            return null;
        }
        Log.d("myAppName", "External storage is not read only or unavailable");

        return fileMaking();





    }


    File fileMaking()
    {
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), dell.trialsecond.Config.IMAGE_DIRECTORY_NAME
        );

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e(TAG+".............", "Oops! Failed create "
                        + dell.trialsecond.Config.IMAGE_DIRECTORY_NAME + " directory");

                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }




}
