package com.mygdx.game;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;


public class AndroidInterfaceClass implements FirebaseServices {
    private FirebaseAnalytics mFirebaseAnalytics;
    DatabaseReference myRef;
    FirebaseDatabase database;

    public AndroidInterfaceClass(){
        database = FirebaseDatabase.getInstance("https://battleship-80dca-default-rtdb.firebaseio.com/");
        myRef = database.getReference("GameState");
    }


    @Override
    public String getPlayerName() {
        return null;
    }

    @Override
    public void getPlayerScore(String player) {
        myRef.child(player).child("Score").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }

    @Override
    public void setPlayerScore(String player, int score) {
        myRef.child(player).child("Score").setValue(score);
    }

    @Override
    public void playerScoreValueListener(String player) {
        myRef.child(player).child("Score").addValueEventListener(new ValueEventListener() {
            // Read from the database

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Integer value = dataSnapshot.getValue(Integer.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    //check if the player exists and get refrence
    //vet ikke hva sharedPrefrences gjør enda
    //SharedPreferences preferences = getSharedPreferences("PREFS", 0);
    //playerName = preferences.getString("playerName", "");
		/*if (!playerName.equals("")){
			//må ha referanse i firebase til dette
			playerRef = database.getReference("players/" + playerName);
			//addEventListener;
			playerRef.setValue("");
		}*/
    //playerRef = database.getReference();
    //testWriteDatabase();
    //må få inn playerName fra core

	/*public void test(String hei){
		System.out.println(hei);
	}*/
/*
    public void testWriteDatabase() {
        String playerId = "1";
        String playerName = "Anne";
        playerRef.child("GameState").child("Player1").child("Score").setValue("1");
    }

    public void testReadDatabase() {
        //String player2 = dataSnapshot
    }
*/

}