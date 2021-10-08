package com.vartyr.littlebirdblock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vartyr.littlebirdblock.models.DailyGratitudeObject;
import com.vartyr.littlebirdblock.utils.logger;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            doConnectDB();
            getAllGratitudeObjectFromDBForUserGivenUID();
        }

    }

    // TODO: put this in a "session manager"
    public void handleUserSignOut(){
        // get out of the firebase instance
        FirebaseAuth.getInstance().signOut();

        // take them back to login page
        // TODO: create some navigation singleton / manager class
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //region UI Interactions
    public void press_logout(View view) {
        handleUserSignOut();
    }

    public void testAdd(View view) {
        logger.simplelog("testing addition");
        test_addNewObject();
    }

    //endregion


    //region Fragment view handing
    private void createGratitudeViewFragment(ArrayList<String> gratitudeList){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentGratitudeItemRecyclerView fragment = new FragmentGratitudeItemRecyclerView();
        fragment.setData(gratitudeList);
        transaction.replace(R.id.sample_content_fragment, fragment);
        transaction.commit();
    }

    //endregion

    //region Gratitude Object CRUD

    // test - Add Object into DB
    public void test_addNewObject(){
        doInsertNewGratitudeIntoDB(
                doCreateNewGratitudeObject("testobject, im geeling food today"));
    }

    private DailyGratitudeObject doCreateNewGratitudeObject(String text){
        // TODO: Obviously put more stuff in the constructor, right?
        return new DailyGratitudeObject(text);
    }


    // TODO: Create a separate class for handling DB connections
    private void doConnectDB(){
        // TODO: Use something else in the near future
        if (db == null) {
            db = FirebaseFirestore.getInstance();
            logger.simplelog("connecting to db...");
        }
    }

    // TODO: we should kick off a task for this
    private void getAllGratitudeObjectFromDBForUserGivenUID() {

        ArrayList<String> gratitudeEntries = new ArrayList<String>();

        db.collection("daily-gratitude").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        String contentString = doc.getString("text");
                        logger.simplelog("DocumentSnapshot data: " + contentString);
                        gratitudeEntries.add(contentString);
                    }
                    createGratitudeViewFragment(gratitudeEntries);
                } else {
                    logger.simplelog("get failed with " + task.getException());
                }
            }
        });
    }


    private void doInsertNewGratitudeIntoDB(DailyGratitudeObject obj){
        if (db != null) {
            db.collection("daily-gratitude")
                    .add(obj.CreateAndReturnObjectMapFromObject())
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            logger.simplelog("DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            logger.simplelog("Error adding document: " + e);
                        }
                    });
        } else {
            // TODO: Do not do the operation, retry later
            logger.simplelog("eh oh...");
        }


    }

    //endregion


}