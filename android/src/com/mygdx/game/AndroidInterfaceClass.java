package com.mygdx.game;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mygdx.game.model.Player;

import java.util.ArrayList;
import java.util.Iterator;

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

    private Player player;

    // Adds a player to the waitingRoom, input: A player object
    @Override
    public void addPlayer(Player player) {
        //cheks if there is an excisting waitingRoom
        this.player = player;
        //checks if the waitingRoom exists and then adds a player child if it does, else creates a room and add a child
        this.addWaitingroomLisenerOnce();
    }

    // Initializes a new game when there are 2 players in the waitingRoom, move the players form waitingRoom and to the existing game

    public void initializeGame(ArrayList<String> players) {
        System.out.println("spillet har startet");

        DatabaseReference gameinfo = data.child("GameState").child(this.gameId).child("GameInfo");
        gameinfo.child("PlayerId").child(players.get(0)).setValue("id");
        gameinfo.child("PlayerId").child(players.get(1)).setValue("id");
        //we know the game has two players, and they will have the same id as the game id.
        //move the players from WaitingRoom to GameState

    }
    //create the gameId, this will be the same for the two players and the game
    @Override
    public void createGame(){
        this.gameId = this.generateGameId();
        DatabaseReference gameinfo = data.child("GameState").child(this.gameId).child("GameInfo");
        gameinfo.child("GameId").setValue(this.gameId);


        /*
        DatabaseReference gameIdRef = data.child("GameState").child("GameId");
        gameIdRef.setValue(this.gameId);

         */
    }

    //generates a random game Id
    private String generateGameId(){
        String possibleChar="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String gameId="";
        for(int i=0;i<20;i++){
            gameId+=possibleChar.charAt((int)Math.floor(Math.random()*possibleChar.length()));
        }
        return gameId;
    }

    private String gameId;

    @Override
    public void addWaitingroomLisenerOnce(){
        data.child("WaitingRoom").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String waitingRoomPlayerId = "";
                if(snapshot.exists()){
                    System.out.println("SPILLER TO KOMMER HER" + player.getName());

                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.


                    //if it is not added, we can add the child in the waitingroom
                    //må finne ut om vi skal legge til hele objektet eller bare navnet?
                    //henter iden til spilleren som allerede er lagt til og gir denne spilleren samme id
                    System.out.println("PLAYER " +player.getName() + gameId);
                    String playerId="";
                    for (DataSnapshot player : snapshot.getChildren()){
                        playerId = (String) player.getValue();
                        System.out.println(playerId);
                    }
                    data.child("WaitingRoom").child(player.getName()).setValue(playerId);

                    int waiting = (int) snapshot.getChildrenCount() +1;
                    Log.d(TAG, "The number of players waiting is: " + waiting);
                    if(waiting > 1){
                        //retrieve the players in the waitingRoom, and move them to initalizeGame
                        ArrayList<String> players = new ArrayList<>();
                        for (DataSnapshot player : snapshot.getChildren()){
                            players.add(player.getKey());
                        }
                        initializeGame(players);
                    }
                }else{
                    //if the WaitingRoom dose'nt exist, create it and then add the player
                    data.setValue("WaitingRoom");
                    System.out.println("KOM INN HER" + player.getName());
                    //generates GameIs when the WaitingRoom is created

                    createGame();

                    DatabaseReference waitingRoom = data.child("WaitingRoom");
                    //creates a player child and gives the player the same id as the game
                    waitingRoom.child(player.getName()).setValue(gameId);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    /*
    // Observer of the waitingRoom, runs initializeGame when there are 2 player in the waitingRoom
    //begge spillerne kan ikke starte et spill, bare en av spillerne kan opprette et spill
    //burde ikke ha en listener, bare hente det her akkurat når den er lagt til!
    @Override
    public void waitingRoomListener() {
        data.child("WaitingRoom").addValueEventListener(new ValueEventListener() {
            // Read from the database
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //checks if WaitingRoom exists
                String waitingRoomPlayerId = "";
                if(dataSnapshot.exists()){
                    System.out.println("KOM INN HER - 2 " + player.getName());

                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.


                    //fungerer ikke foreløpig, men vi trenger en validering for at spilleren ikke er lagt til fra før av
                    /*
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> childrens = children.iterator();
                    //checks if the player is added in the waitingRoom

                    while(childrens.hasNext()){
                        System.out.println("CHILDREN TIL WAITINGROOM: " + childrens.next());
                        //System.out.println("GAMEID" + childrens.next().getKey());
                        waitingRoomPlayerId = (String) childrens.next().getValue(String.class);
                        System.out.println("GAMEID" + childrens.next().getValue(String.class));
                        if (childrens.next().getKey().equals(player.getName())){
                            System.out.println("The child is already added");
                            break;
                        }
                    }



                    //if it is not added, we can add the child in the waitingroom
                    //må finne ut om vi skal legge til hele objektet eller bare navnet?
                    //henter iden til spilleren som allerede er lagt til og gir denne spilleren samme id
                    System.out.println("PLAYER " +player.getName() + gameId);
                    data.child("WaitingRoom").child(player.getName()).setValue(gameId);

                    int waiting = (int) dataSnapshot.getChildrenCount();
                    Log.d(TAG, "The number of players waiting is: " + waiting);
                    if(waiting > 1){
                        initializeGame();
                    }
                }else{
                    //if the WaitingRoom dose'nt exist, create it and then add the player
                    data.setValue("WaitingRoom");
                    System.out.println("KOM INN HER" + player.getName());
                    //generates GameIs when the WaitingRoom is created
                    createGame();
                    DatabaseReference waitingRoom = data.child("WaitingRoom");
                    //creates a player child and gives the player the same id as the game
                    waitingRoom.child(player.getName()).setValue(gameId);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
*/

}