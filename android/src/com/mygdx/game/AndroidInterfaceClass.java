package com.mygdx.game;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.badlogic.gdx.Game;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static android.content.ContentValues.TAG;



public class AndroidInterfaceClass implements FirebaseServices {
    private FirebaseAnalytics mFirebaseAnalytics;
    DatabaseReference data;
    FirebaseDatabase database;
    DatabaseReference gameInfo;
    ArrayList<String> players;
    //ArrayList<String> gameId;
    Integer turnPlayer = 0;
    String gameId;
    GameIdHolder gameIdHolder;

    public AndroidInterfaceClass(){
        database = FirebaseDatabase.getInstance("https://battleship-80dca-default-rtdb.firebaseio.com/");
        data = database.getReference();
        gameIdHolder = GameIdHolder.getInstance();

    }


    private Player player;

    // Adds a player to the waitingRoom, input: A player object
    @Override
    public void addPlayer(Player player) {
        //this.gameIdHolder = gameIdHolder;
        //cheks if there is an excisting waitingRoom
        this.player = player;
        //checks if the waitingRoom exists and then adds a player child if it does, else creates a room and add a child
        this.addWaitingroomLisenerOnce();

    }

    @Override
    public void playersListener(String playerGameId){

    }

    // Initializes a new game when there are 2 players in the waitingRoom, move the players form waitingRoom and to the existing game
    /*
    @Override
    public void playersListener(String playerGameId){
        System.out.println(playerGameId);
        data.child("GameState").child(playerGameId).child("GameInfo").child("Players").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                players = new ArrayList<>();
                gameId = new ArrayList<>();
                for (DataSnapshot s : snapshot.getChildren()) {
                    String iden = (String) snapshot.getValue();
                    gameId.add(iden);
                    String player = snapshot.getKey();
                    players.add(player);
                }
                System.out.println("player 0: " + players.get(0)  + " gameId: " + gameId.get(0));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

*/
    public void initializeGame() {
        data.child("WaitingRoom").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                players = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String player = snapshot.getKey();
                    players.add(player);

                    System.out.println("Player: " + player);
                }

                data.child("GameState").child(gameIdHolder.gameId).child("GameInfo").child("Players").child("Player0").setValue(players.get(0));
                data.child("GameState").child(gameIdHolder.gameId).child("GameInfo").child("Players").child("Player1").setValue(players.get(1));

                data.child("WaitingRoom").removeValue();
                data.child("GameState").child(gameIdHolder.gameId).child("GameInfo").child("Turn").setValue(players.get(0));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public String turnListener(String gameID) {
        final String[] player = new String[1];
        gameInfo.child("Turn").addValueEventListener(new ValueEventListener() {
            // Read from the database
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                player[0] = dataSnapshot.getValue().toString();
                Log.d(TAG, "Turn: " + player[0]);
                //isMyTurn();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return player[0];
    }
   /*
    public boolean isMyTurn(){
        return true;
    }
*/
    @Override
    public String changeTurn() {
        String name;
        if (turnPlayer==0){
            turnPlayer=1;
            data.child("GameState").child(gameIdHolder.gameId).child("GameInfo").child("Turn").setValue(this.players.get(1));
             //data.child("GameState").child(this.gameId.get(0)).child("GameInfo").child("Turn").setValue(this.players.get(1));
             name = this.players.get(1);
        }else{
            turnPlayer=0;
            data.child("GameState").child(gameIdHolder.gameId).child("GameInfo").child("Turn").setValue(this.players.get(0));
            //data.child("GameState").child(this.gameId.get(0)).child("GameInfo").child("Turn").setValue(this.players.get(0));
            name = this.players.get(0);
        }
        return name;
    }



    private String id;

    //create the gameId, this will be the same for the two players and the game
    @Override
    public void createGame(){
        this.id = this.generateGameId();
        this.gameInfo = data.child("GameState").child(id).child("GameInfo");
        gameInfo.child("GameId").setValue(id);

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

//endre til listenrvalueEvent


    public void addWaitingroomLisenerOnce(){
        data.child("WaitingRoom").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String waitingRoomPlayerId = "";
                if(snapshot.exists()){

                    String playerId="";
                    for (DataSnapshot player : snapshot.getChildren()){
                        playerId = (String) player.getValue();
                    }
                    data.child("WaitingRoom").child(player.getName()).setValue(playerId);
                    gameIdHolder.gameId = playerId;
                    System.out.println("GameID: " + gameIdHolder.gameId);

                    int waiting = (int) snapshot.getChildrenCount() +1;

                    Log.d(TAG, "The number of players waiting is: " + waiting);
                    if(waiting > 1){
                        initializeGame();
                    }
                }else{
                    //if the WaitingRoom dose'nt exist, create it and then add the player

                    //generates GameIs when the WaitingRoom is created
                    createGame();

                    gameIdHolder.gameId = id;
                    DatabaseReference waitingRoom = data.child("WaitingRoom");
                    //creates a player child and gives the player the same id as the game
                    waitingRoom.child(player.getName()).setValue(id);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void addPlayerToGameState(){

    }

    public void addWaitingroomLisener(){
        data.child("WaitingRoom").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String waitingRoomPlayerId = "";
                if(snapshot.exists()){

                    String playerId="";
                    for (DataSnapshot player : snapshot.getChildren()){
                        playerId = (String) player.getValue();
                    }
                    data.child("WaitingRoom").child(player.getName()).setValue(playerId);
                    gameIdHolder.gameId = playerId;

                }else{
                    //if the WaitingRoom dose'nt exist, create it and then add the player

                    //generates GameIs when the WaitingRoom is created
                    createGame();

                    gameIdHolder.gameId = id;
                    DatabaseReference waitingRoom = data.child("WaitingRoom");
                    //creates a player child and gives the player the same id as the game
                    waitingRoom.child(player.getName()).setValue(id);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }



}