package com.vartyr.littlebirdblock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.vartyr.littlebirdblock.models.DailyGratitudeObject;
import com.vartyr.littlebirdblock.utils.logger;


public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doConnectDB();
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

    // TODO: Will retrieve all the gratitudes for a specific user id
    private void getAllGratitudeObjectFromDBForUserGivenUID() {

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