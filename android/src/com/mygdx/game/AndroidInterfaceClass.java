package com.mygdx.game;

import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mygdx.game.controller.LoadingController;
import com.mygdx.game.controller.PlayController;
import com.mygdx.game.model.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static android.content.ContentValues.TAG;



public class AndroidInterfaceClass implements FirebaseServices {
    private FirebaseAnalytics mFirebaseAnalytics;
    DatabaseReference data;
    FirebaseDatabase database;
    DatabaseReference gameInfo;
    Integer turnPlayer;
    GameIdHolder gameIdHolder;
    private Player player;
    Integer playerId;
    private String id;

    public AndroidInterfaceClass(){
        database = FirebaseDatabase.getInstance("https://battleship-80dca-default-rtdb.firebaseio.com/");
        data = database.getReference();
        gameIdHolder = GameIdHolder.getInstance();

    }




    // Adds a player to the waitingRoom, input: A player object
    @Override
    public void addPlayer(Player player) {
        //this.gameIdHolder = gameIdHolder;
        //cheks if there is an excisting waitingRoom
        this.player = player;
        //checks if the waitingRoom exists and then adds a player child if it does, else creates a room and add a child
        this.addWaitingroomListener();
        //this.addWaitingroomLisenerOnce();

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
    public void changeTurn() {
        if (turnPlayer==0){
            turnPlayer=1;
            //leser feil player her. 
            data.child("GameState").child(gameIdHolder.gameId).child("GameInfo").child("Turn").setValue("1");
        }else{
            turnPlayer=0;
            data.child("GameState").child(gameIdHolder.gameId).child("GameInfo").child("Turn").setValue("0");
        }

    }

    @Override
    public Boolean addTurnListener(){
        data.child("GameState").child(gameIdHolder.gameId).child("GameInfo").child("Turn").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                turnPlayer = Integer.valueOf((String) snapshot.getValue());
                System.out.println("addturnListener in android: " + turnPlayer);
                PlayController.myTurn = turnPlayer.equals(playerId);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return turnPlayer.equals(playerId);
    }

    @Override
    public ArrayList<List<Integer>> getOpponentBoard() {
        return null;
    }

    @Override
    public void sendBoard(ArrayList<List<Integer>> board) {
        System.out.println("sendBoard from here " + "playerid: " + gameIdHolder.playerId + " " + board);

        data.child("GameState").child(gameIdHolder.gameId).child("GameInfo").child("Board").child("Player" + gameIdHolder.playerId).setValue(board);
    }

    @Override
    public void boardListener(){
        data.child("GameState").child(gameIdHolder.gameId).child("GameInfo").child("Board").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() > 1){
                    LoadingController.playersReady = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //create the gameId, this will be the same for the two players and the game
    @Override
    public void createGame(){
        this.id = this.generateGameId();
        this.gameInfo = data.child("GameState").child(id).child("GameInfo");
        gameInfo.child("GameId").setValue(id);
        gameInfo.child("Players").child("Player0").setValue("0");

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


    public void initializeGame() {
                data.child("GameState").child(gameIdHolder.gameId).child("GameInfo").child("Players").child("Player0").setValue(player.getName());
                data.child("GameState").child(gameIdHolder.gameId).child("GameInfo").child("Turn").setValue("0");
                data.child("WaitingRoom").child(player.getName()).removeValue();
                this.turnPlayer = 0;
                playerId = 0;
                LoadingController.playersAdded = true;
    }

    public void addWaitingroomListener(){
        System.out.println("addWaitingRoomListener metode");
        data.child("WaitingRoom").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("Inne i listener");
                String waitingRoomPlayerId = "";
                if(snapshot.exists()){
                    System.out.println("Anne skal inne her " + player.getName());

                    String pId="";
                    for (DataSnapshot player : snapshot.getChildren()){
                        pId = (String) player.getValue();
                    }
                    data.child("WaitingRoom").child(player.getName()).setValue(pId);
                    gameIdHolder.gameId = pId;
                    gameIdHolder.playerId = 0;
                    int waiting = (int) snapshot.getChildrenCount() +1;
                    if(waiting > 1){
                        initializeGame();
                    }
                }else{
                    System.out.println("else setning");
                    //if the WaitingRoom dose'nt exist, create it and then add the player

                    //generates GameIs when the WaitingRoom is created
                    createGame();

                    gameIdHolder.gameId = id;
                    gameIdHolder.playerId = 1;
                    System.out.println("playerId " + gameIdHolder.playerId);
                    DatabaseReference waitingRoom = data.child("WaitingRoom");
                    //creates a player child and gives the player the same id as the game
                    waitingRoom.child(player.getName()).setValue(id);
                    waitingRoomChildListener();
                }
                 }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void waitingRoomChildListener(){

        data.child("WaitingRoom").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                data.child("GameState").child(gameIdHolder.gameId).child("GameInfo").child("Players").child("Player1").setValue(player.getName());
                data.child("WaitingRoom").removeValue();
                turnPlayer = 0;
                playerId = 1;
                LoadingController.playersAdded = true;

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}