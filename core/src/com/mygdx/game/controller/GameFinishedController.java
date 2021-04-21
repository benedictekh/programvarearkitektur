package com.mygdx.game.controller;

import com.mygdx.game.Battleships;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.ScoreBoard;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GameFinishedController extends Controller{

    private ScoreBoard scoreboard;
    public static HashMap<String, Integer> printScoreboard;

    public GameFinishedController(Player player){
        super(player);

        scoreboard = new ScoreBoard(player);
        printScoreboard = new HashMap<>();
        this.updateScoreboard();
    }

    public HashMap<String, Integer> getScoreboard(){
        return printScoreboard;
    }


    public void updateScoreboard(){
        scoreboard.calculateScore();
        //Kommenter tilbake
        Battleships.firebaseConnector.setScoreboard(scoreboard);
        Battleships.firebaseConnector.retrieveScoreboard();
        try{
            TimeUnit.SECONDS.sleep(3);
        }catch(Exception e){
            e.printStackTrace();
        }
        this.sortScoreboard();
        //sende tilbake til view
        System.out.println("Controller: " + printScoreboard);
    }

    private void sortScoreboard(){
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(printScoreboard.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        if (temp.size() >= 5){
            for (int i = 0; i < 5 ; i++) {
                temp.put(list.get(i).getKey(), list.get(i).getValue());
            }
        }else {
            for (Map.Entry<String, Integer> aa : list) {
                temp.put(aa.getKey(), aa.getValue());
            }
        }
        printScoreboard = temp;
    }

    /*
    private void sortScoreboard(){
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        if (temp.size() >= 10){
            for (int i = 0; i < 10 ; i++) {
                temp.put("isa", 52);
            }
        }
        printScoreboard = temp;
    }

     */

    @Override
    public void update(float dt) {

    }
}
