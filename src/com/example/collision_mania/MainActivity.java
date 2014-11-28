package com.example.collision_mania;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class MainActivity extends Activity {

	Button start,start1,startTri,startTri1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		start = (Button)findViewById(R.id.button1);
		start1 = (Button)findViewById(R.id.button2);
		startTri = (Button)findViewById(R.id.button3);
		startTri1 = (Button)findViewById(R.id.button4);
		
		TranslateAnimation anim = new TranslateAnimation(-500,0, 0, 0); //first 0 is start point, 150 is end point horizontal
		anim.setDuration(3000); // 1000 ms = 1second
		anim.setFillAfter(true);
		
		start.startAnimation(anim);
		start1.startAnimation(anim);
		startTri.startAnimation(anim);
		startTri1.startAnimation(anim);
		
		
		start.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(MainActivity.this, GamePlay.class);
				myIntent.putExtra("Level", "0");
				MainActivity.this.startActivity(myIntent);
			}
		});
		start1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(MainActivity.this, GamePlay_arc_circle.class);
				myIntent.putExtra("Level", "1");
				MainActivity.this.startActivity(myIntent);
			}
		});
		startTri.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(MainActivity.this, GamePlay_tri_trans.class);
				myIntent.putExtra("Level", "1");
				MainActivity.this.startActivity(myIntent);
			}
		});
		startTri1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(MainActivity.this, GamePlay_arc_tri.class);
				myIntent.putExtra("Level", "1");
				MainActivity.this.startActivity(myIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
