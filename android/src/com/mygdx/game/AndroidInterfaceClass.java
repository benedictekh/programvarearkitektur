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
import com.mygdx.game.model.Player;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class AndroidInterfaceClass implements FirebaseServices {
    private FirebaseAnalytics mFirebaseAnalytics;
    DatabaseReference data;
    FirebaseDatabase database;

    public AndroidInterfaceClass(){
        database = FirebaseDatabase.getInstance("https://battleship-80dca-default-rtdb.firebaseio.com/");
        data = database.getReference();
    }

    // Get the score of a player, input: Player1 or Player2
    @Override
    public void getPlayerScore(String player) {
        data.child("GameState").child(player).child("Score").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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

    // Updates the score of a player, input: Player1 or Player2, new score
    @Override
    public void setPlayerScore(String player, int score) {
        data.child("GameState").child(player).child("Score").setValue(score);
    }

    // Observer of a players score, input: Player1 or Player2
    @Override
    public void playerScoreValueListener(String player) {
        data.child("GameState").child(player).child("Score").addValueEventListener(new ValueEventListener() {
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

    // Adds a player to the waitingRoom, input: A player object
    @Override
    public void addPlayer(Player player) {

    }

    // Initializes a new game when there are 2 players in the waitingRoom
    @Override
    public void initializeGame() {

    }

    // Observer of the waitingRoom, runs initializeGame when there are 2 player in the waitingRoom
    @Override
    public void waitingRoomListener() {
        data.child("WaitingRoom").addValueEventListener(new ValueEventListener() {
            // Read from the database
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int waiting = (int) dataSnapshot.getChildrenCount();
                Log.d(TAG, "The number of players waiting is: " + waiting);
                if(waiting > 1){
                    initializeGame();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


}