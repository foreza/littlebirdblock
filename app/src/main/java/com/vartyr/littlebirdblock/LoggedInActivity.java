package com.vartyr.littlebirdblock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.vartyr.littlebirdblock.utils.logger;


public class LoggedInActivity extends AppCompatActivity {

    FirebaseFirestore db = null;


    public class DailyGratitudeObject {
        String text = "";
        String date_added = util_getCurrentTimeStampAsString();
        String date_last_modified = util_getCurrentTimeStampAsString();

        // default constructor
        public DailyGratitudeObject(String t){
            text = t;
        }

        public Map<String, Object> CreateAndReturnObjectMapFromObject(){
            Map<String, Object> gratObj = new HashMap<>();
            gratObj.put("text", text);
            gratObj.put("date_added", date_added);
            gratObj.put("date_last_modified", date_last_modified);
            gratObj.put("user_id", "rhyEHzVbVSXbnxAnsdrZ3CVqseJ3"); // TODO: hardcoded user_id, change later
            return gratObj;
        }
    }

    // TODO: Move all util methods into their own class
    private static String util_getCurrentTimeStampAsString(){
        return String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        doConnectDB();
    }



    public void testAdd(View view) {
        logger.simplelog("testing addition");
        test_addNewObject();
    }


    public void test_addNewObject(){
        doInsertNewGratitudeIntoDB(
                doCreateNewGratitudeObject("testobject, im geeling food today"));
    }

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

    private DailyGratitudeObject doCreateNewGratitudeObject(String text){
        // TODO: Obviously put more stuff in the constructor, right?
        return new DailyGratitudeObject(text);
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
}