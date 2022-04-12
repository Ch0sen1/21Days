package edu.neu.madcourse.numad21fa_team21dayproject.fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import edu.neu.madcourse.numad21fa_team21dayproject.R;
import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.User;
import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.tools;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = "PHOTO";

    private final static int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView profilePic;
    private TextView userNameText;


    private TextView userLv;
    private TextView userStr;
    private TextView userInt;
    private TextView userAgi;

    private ProgressBar user_LV;
    private ProgressBar user_Str;
    private ProgressBar user_Int;
    private ProgressBar user_Agi;


    private String fireBasePic;

    private String currentPhotoPath;
    private StorageReference storageReference;
    private StorageReference profilePicReference;
    private DatabaseReference mUser;
    private DatabaseReference mDatabase;

    private User curUser;


    public ProfileFragment() {
        // Required empty public constructor
    }


    public void setUser(User user){
        this.curUser = user;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profilePic = view.findViewById(R.id.profileImg);
        userNameText = view.findViewById(R.id.userName1);

        userLv = view.findViewById(R.id.userLevel);
        userStr = view.findViewById(R.id.user_str);
        userInt = view.findViewById(R.id.user_int);
        userAgi = view.findViewById(R.id.user_agi);

        user_LV = view.findViewById(R.id.progressbar_lv);
        user_Str = view.findViewById(R.id.progressbar_str);
        user_Int = view.findViewById(R.id.progressbar_Intelligent);
        user_Agi = view.findViewById(R.id.progressbar_userAgility);


        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = tools.mDatabase;
        mUser = mDatabase.child("users");

        try {
            fetchUserDataOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Log.e("Profile", curUser.getPicPath());



        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        return view;
    }

    private void fetchUserDataOnScreen() throws IOException {
        try {
            userNameText.setText(curUser.getUserName());
            userLv.setText("Lv " + String.valueOf(curUser.getUserLevel()));
            userStr.setText("Strength: " + String.valueOf(curUser.getUserStr()));
            userInt.setText("Intelligent: " + String.valueOf(curUser.getUserIntelligent()));
            userAgi.setText("Agility: " + String.valueOf(curUser.getUserAgility()));

            user_LV.setProgress(curUser.getUserLevel());
            user_Str.setProgress(curUser.getUserStr());
            user_Int.setProgress(curUser.getUserIntelligent());
            user_Agi.setProgress(curUser.getUserAgility());

            // TODO: read file from firebase storage
            fireBasePic = curUser.getPicPath();
            Log.e("Profile", "pictures/"+fireBasePic);
            profilePicReference = FirebaseStorage.getInstance().getReference().child("pictures/").child(fireBasePic);
            File localfile = File.createTempFile("tempfile",".jpg");
            profilePicReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    profilePic.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if (resultCode == Activity.RESULT_OK){
                File f = new File(currentPhotoPath);
                profilePic.setImageURI(Uri.fromFile(f));
                Log.d(TAG,"The url is" + Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                getActivity().sendBroadcast(mediaScanIntent);

                uploadImageToFireBase(f.getName(), contentUri);
                mUser.child(curUser.getUserName()).child("picPath").setValue(f.getName());

            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void uploadImageToFireBase(String name, Uri contentUri){
        final StorageReference image = storageReference.child("pictures/" + name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, "onSuccess: Uploaded Image URL is" + uri);
                    }
                });
                Toast.makeText(getActivity(), "Upload Success", Toast.LENGTH_SHORT);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Upload Failed", Toast.LENGTH_SHORT);
            }
        });
    }





}