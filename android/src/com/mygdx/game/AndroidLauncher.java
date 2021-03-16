package com.mygdx.game;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mygdx.game.battleships;

public class AndroidLauncher extends AndroidApplication implements FirebaseServices{



	// request codes we use when invoking an external activity


	// tag for debug logging
	private static final String TAG = "Firebase Test";

	// Firebase Analytics
	private FirebaseAnalytics mFirebaseAnalytics;
	DatabaseReference playerRef;
	FirebaseDatabase database;
	private String playerName="";

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		database = FirebaseDatabase.getInstance();

		//check if the player exists and get refrence
		//vet ikke hva sharedPrefrences gjør enda
		SharedPreferences preferences = getSharedPreferences("PREFS", 0);
		playerName = preferences.getString("playerName", "");
		if (!playerName.equals("")){
			//må ha referanse i firebase til dette
			playerRef = database.getReference("players/" + playerName);
			//addEventListener;
			playerRef.setValue("");
		}

		//må få inn playerName fra core

		initialize(new battleships(this), config);

	}

	/*public void test(String hei){
		System.out.println(hei);
	}*/




	@Override
	public String getPlayerName() {
		return null;
	}
}
