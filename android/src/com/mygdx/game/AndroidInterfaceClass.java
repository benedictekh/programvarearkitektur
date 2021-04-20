package com.mygdx.game;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mygdx.game.controller.GameFinishedController;
import com.mygdx.game.controller.LoadingController;
import com.mygdx.game.controller.PlayController;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.ScoreBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;



public class AndroidInterfaceClass implements FirebaseServices {
    DatabaseReference data;
    FirebaseDatabase database;
    DatabaseReference gameInfo;
    Integer turnPlayer;
    GameCodeHolder gameCodeHolder;
    private Player player;
    Integer playerId;
    private String id;
    static ArrayList<List<Integer>> opponentBoard;
    static HashMap<String, Integer> scoreboard;

    public AndroidInterfaceClass(){
        database = FirebaseDatabase.getInstance("https://battleship-80dca-default-rtdb.firebaseio.com/");
        data = database.getReference();
        gameCodeHolder = GameCodeHolder.getInstance();

    }


    // Adds a player to the waitingRoom, input: A player object
    @Override
    public void addPlayer(Player player) {
        this.player = player;
        this.addWaitingroomListener();
        //this.addWaitingroomLisenerOnce();

    }

    @Override
    public void changeTurn() {
        if (turnPlayer==0){
            turnPlayer=1;
            //leser feil player her. 
            data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Turn").setValue("1");
        }else{
            turnPlayer=0;
            data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Turn").setValue("0");
        }
    }

    @Override
    public Boolean addTurnListener(){
        data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Turn").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                turnPlayer = Integer.valueOf((String) snapshot.getValue());
                System.out.println("addturnListener in android: " + turnPlayer);
                PlayController.myTurn = turnPlayer.equals(playerId);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {            }
        });
        return turnPlayer.equals(playerId);
    }



    @Override
    public ArrayList<List<Integer>> getOpponentBoard() {
        int opponentId = 0;
        if (gameCodeHolder.getPlayerId() == 0){
            opponentId = 1;
        }
        data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Board").child("Player" + opponentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                opponentBoard = new ArrayList<List<Integer>>();
                Iterable<DataSnapshot> snap = snapshot.getChildren();
                System.out.println("retrieve opponentBoard");
                for (DataSnapshot data : snap){
                    List<Integer> temp = new ArrayList<>();
                    Iterable<DataSnapshot> children = data.getChildren();
                    for (DataSnapshot child : children){
                        temp.add(Integer.parseInt(String.valueOf (child.getValue())));
                    }
                    System.out.println(temp);
                   opponentBoard.add(temp);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //System.out.println("opponent board i andoird " + opponentBoard);
        return opponentBoard;
    }

    @Override
    public void sendBoard(ArrayList<List<Integer>> board) {
        System.out.println("sendBoard from here " + "playerid: " + gameCodeHolder.getPlayerId() + " " + board);
        data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Board").child("Player" + gameCodeHolder.getPlayerId()).setValue(board);
        getOpponentBoard();
    }


    @Override
    public void boardListener(){
        data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Board").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() > 1){
                    LoadingController.playersReady = true;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {            }
        });
    }

    @Override
    public void sendShot(int x, int y, int newValue) {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(x, y, newValue));
        data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("LastShot").setValue(list);
    }

    @Override
    public void getOpponentsShot() {
        PlayController.lastShot = new ArrayList<>(Arrays.asList(0,0,0));
        data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("LastShot").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PlayController.lastShot = new ArrayList<>();
                Iterable<DataSnapshot> data = snapshot.getChildren();
                for(DataSnapshot value : data){
                    PlayController.lastShot.add(Integer.parseInt(String.valueOf(value.getValue())));
                }
                PlayController.shotChanged = true;
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
        gameInfo.child("LastShot").child("0").setValue("0");
        gameInfo.child("LastShot").child("1").setValue("0");
        gameInfo.child("LastShot").child("2").setValue("0");
        gameInfo.child("GameFinished").setValue("False");
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
                data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Players").child("Player0").setValue(player.getName());
                data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Turn").setValue("0");
                data.child("WaitingRoom").child(player.getName()).removeValue();
                this.turnPlayer = 0;
                playerId = 0;
                LoadingController.playersAdded = true;
    }

    public void addWaitingroomListener(){
        //checks if the waitingRoom exists and then adds a player child if it does, else creates a room and add a child
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
                    gameCodeHolder.setGameId(pId);
                    gameCodeHolder.setPlayerId(0);
                    int waiting = (int) snapshot.getChildrenCount() +1;
                    if(waiting > 1){
                        initializeGame();
                    }
                }else{
                    System.out.println("else setning");
                    //if the WaitingRoom dose'nt exist, create it and then add the player

                    //generates GameIs when the WaitingRoom is created
                    createGame();

                    gameCodeHolder.setGameId(id);
                    gameCodeHolder.setPlayerId(1);
                    System.out.println("playerId " + gameCodeHolder.getPlayerId());
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
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                data.child("GameState").child(gameCodeHolder.getGameId()).child("GameInfo").child("Players").child("Player1").setValue(player.getName());
                data.child("WaitingRoom").removeValue();
                turnPlayer = 0;
                playerId = 1;
                LoadingController.playersAdded = true;
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {            }
        });
    }


    @Override
    public void setScoreboard(ScoreBoard scoreboard){
        data.child("Scoreboard").child(scoreboard.getName()).setValue(scoreboard.getScore());
    }

    @Override
    public void retrieveScoreboard(){
        data.child("Scoreboard").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GameFinishedController.printScoreboard = new HashMap<String, Integer>();
                Iterable<DataSnapshot> data = snapshot.getChildren();
                for (DataSnapshot score : data){
                    GameFinishedController.printScoreboard.put(score.getKey(), Integer.parseInt(String.valueOf(score.getValue())));
                }
                System.out.println("RetrieveScoreboard androidInterfaceClass - in arrayList" + GameFinishedController.printScoreboard);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        System.out.println("andorid scoreboard before return " + scoreboard);
    }

    @Override
    public void gameFinished(){
        data.child(gameCodeHolder.getGameId()).child("GameInfo").child("GameFinished").setValue("True");
    }

    @Override
    public void gameFinsihedListener(){
        data.child(gameCodeHolder.getGameId()).child("GameInfo").child("GameFinished").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (((String) snapshot.getValue()).equals("True")){
                    PlayController.finishedGame = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}