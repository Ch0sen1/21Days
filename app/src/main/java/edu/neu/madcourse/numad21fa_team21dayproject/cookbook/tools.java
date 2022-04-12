package edu.neu.madcourse.numad21fa_team21dayproject.cookbook;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class tools {
    public static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public static StorageReference mStorage = FirebaseStorage.getInstance().getReference();
}
