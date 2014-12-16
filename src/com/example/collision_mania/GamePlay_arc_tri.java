package com.example.collision_mania;
import java.io.IOException;
import java.util.Random;

import com.example.collision_mania.ArcTranslate;
import com.example.collision_mania.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class GamePlay_arc_tri extends Activity {
	
	private ImageView mImageView1;
	private ImageView mImageView2;
	
	//private ImageView newImageView1;
	//private ImageView newImageView2;
	
	Button player1, player2,start ;
	TextView tv,s1,s2;
	
	boolean clicked,startClicked,end ;
	static int score1 = 0,score2 = 0;
	static int noOfRounds = 0;
	CountDownTimer timer;
	
	Random rn = new Random();
	
	ArcTranslate anim ;
	ArcTranslate anim1;
	float x1,y1,x2,y2;
	
	MediaPlayer mp,mp1 ;
	
	long startTime;
	long endTime;
	long T;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		String value;
		Intent intent = getIntent();
		Bundle extras = getIntent().getExtras();
		//if (extras != null) {
		    value = extras.getString("Level");
		//}
		Log.i("Level ",value);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_play_tri);
		
		mImageView1 = (ImageView) findViewById(R.id.icon1);
		mImageView2 = (ImageView) findViewById(R.id.icon2);
		
		
	//	newImageView1.setVisibility(View.INVISIBLE);
		//newImageView2.setVisibility(View.INVISIBLE);
		
		player1 = (Button) findViewById(R.id.button1);
		player2 = (Button) findViewById(R.id.button2);
		start = (Button) findViewById(R.id.button3);
		tv = (TextView) findViewById(R.id.textView1);
		s1 = (TextView) findViewById(R.id.score1);
		s2 = (TextView) findViewById(R.id.score2);
		
		
		mp = MediaPlayer.create(getApplicationContext(), R.raw.backmusic);
		mp1 = MediaPlayer.create(getApplicationContext(), R.raw.collision);
		
		score1 = 0;score2 = 0;
		end = false;
		start.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!startClicked && !end)
				{	
					//translate.run();
					
					startTime = System.currentTimeMillis();
					try
					{
						//mp.setDataSource(getApplicationContext(), R.raw.backmusic);
		                //mp.prepare();
						mp.start();
					}
					catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						
					} 
					float diff = ( mImageView2.getTop()- mImageView1.getTop());
					diff = diff/2;
					anim =  new ArcTranslate(3000,Animation.ABSOLUTE,0,Animation.ABSOLUTE,diff,Animation.ABSOLUTE,mImageView1.getX(),1);
					mImageView1.animate().setDuration(3000)
					.setInterpolator(new LinearInterpolator()).rotationBy(-720.0f);
					mImageView1.startAnimation(anim);
					//mImageView1.animate().rotationBy(15000.0f);
					
					anim1 = new ArcTranslate(3000,Animation.ABSOLUTE,0,Animation.ABSOLUTE,-diff,Animation.ABSOLUTE,mImageView1.getX(),-1);
					mImageView2.animate().setDuration(3000)
					.setInterpolator(new LinearInterpolator()).rotationBy(720.0f);
					mImageView2.startAnimation(anim1);
					T = anim1.getDuration();
					
					timer = new CountDownTimer(T+1000, 1000) {

					     public void onTick(long millisUntilFinished) {
					         //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
					     }

					     public void onFinish() {
					    	 
					        if(!clicked)
					        	{
					        	tv.setText("Game drawn");
					        clicked = true;
					        startClicked = true;
					        try
							{
								mp.pause();
								mp.seekTo(0);
								mp1.start();
							}
							catch (IllegalStateException e) {
								// TODO Auto-generated catch block
								
							} 
					        	}
					     }
					  }.start();
					
					
					noOfRounds++;
					clicked = false;
					startClicked = true;
				}
				if(startClicked && clicked && !end)
				{
					anim.cancel();
					anim1.cancel();
					try{
						mp1.pause();
						mp1.seekTo(0);
						}
						catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							
						} 
					mImageView1.setRotation(0);
					mImageView2.setRotation(0);
					mImageView1.setY(mImageView1.getTop());
					mImageView2.setY(mImageView2.getTop());
					
					player1.setText("PLAYER 1");
					player2.setText("PLAYER 2");
					
					player1.setBackgroundResource(android.R.drawable.btn_default);
					player2.setBackgroundResource(android.R.drawable.btn_default);
					
					tv.setVisibility(View.VISIBLE);
					
					startClicked = false;
					clicked = false;
					if(score1==10 || score2==10) 
					{
						checkEnd();
						end = true;
					}
				}
			}
		});

		
		player1.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				if(!clicked && startClicked &&!end)
				{
					try
					{
						mp.pause();
						mp.seekTo(0);
						mp1.start();
					}
					catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						
					} 
					
					y1 = anim.myY;
					y2 = anim1.myY;
					x1 = anim.myX;
					x2 = anim1.myX;
					
					Log.i("myValues",Float.toString(mImageView1.getBottom()));
					
					Log.i("myValues",Float.toString(x1));
					Log.i("myValues",Float.toString(y1));
					
					Log.i("myValues",Float.toString(x2));
					Log.i("myValues",Float.toString(y2));
					stopObjects();
					endTime = System.currentTimeMillis();
					clicked  = true;
					//if(victory())
					if(victory2(x1,y1,x2,y2))
					{
						player1.setBackgroundColor(0xFF00FF00);
						player1.setText("YOU WON");
						score1++;
						s1.setText(""+score1);
					}
					else
					{
						player1.setBackgroundColor(0xffff0000);
						player1.setText("YOU LOST");
						score2++;
						s2.setText(""+score2);
					}
				}
				return false;
			}
		});
		
		
		player2.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				if(!clicked && startClicked &&!end)
				{
					try
					{
						mp.pause();
						mp.seekTo(0);
						mp1.start();
					}
					catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						
					} 
					
					y1 = anim.myY;
					y2 = anim1.myY;
					x1 = anim.myX;
					x2 = anim1.myX;
					
					Log.i("myValues",Float.toString(mImageView1.getBottom()));
					
					Log.i("myValues",Float.toString(x1));
					Log.i("myValues",Float.toString(y1));
					
					Log.i("myValues",Float.toString(x2));
					Log.i("myValues",Float.toString(y2));
					stopObjects();
					endTime = System.currentTimeMillis();
					clicked  = true;
					//if(victory())
					if(victory2(x1,y1,x2,y2))
					{
						player2.setBackgroundColor(0xFF00FF00);
						player2.setText("YOU WON");
						
						score2++;
						s2.setText(""+score2);
					}
					else
					{
						player2.setBackgroundColor(0xffff0000);
						player2.setText("YOU LOST");
						score1++;
						s1.setText(""+score1);
					}
				}
				return false;
			}
		});
		
	}
	/*public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		if (hasFocus) {
			translate.run();
		}
	}*/
	
	Runnable translate = new Runnable() {
		public void run() {
			
			int time = rn.nextInt(3001)+ 2000; // generating time of animation
			
			float translation = getResources()
					.getDimension(R.dimen.translation);
			mImageView1.animate().setDuration(time)
					.setInterpolator(new LinearInterpolator())
					.translationYBy(translation);
			mImageView2.animate().setDuration(time)
			.setInterpolator(new LinearInterpolator())
			.translationYBy(-translation);
			//tv.setVisibility(View.INVISIBLE);
			tv.setText("time"+time);
			/*if(pause)
				try {
					this.wait(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
		}
	};
	void stopObjects()
	{
		timer.cancel();
		/*Float f = mImageView1.getTranslationY();
		Log.i("Try",Float.toString(f) );
		Float f1 = mImageView2.getTranslationY();
		Log.i("Try",Float.toString(f1) );
		//player2.setText("x"+mImageView1.getTranslationY()+"  "+(mImageView2.getTop()- mImageView1.getTop())+" ");
		//tv.setText("x"+mImageView1.getTranslationY()+"  "+(mImageView2.getTop()- mImageView1.getTop())+" "+mImageView1.getHeight());*/
		mImageView1.animate().cancel();
		mImageView2.animate().cancel();
		anim.pause();
		anim1.pause();
		
		tv.setVisibility(View.INVISIBLE);
		
		//mImageView1.setVisibility(View.INVISIBLE);
		//mImageView2.setVisibility(View.INVISIBLE);
		
		//newImageView1.setVisibility(View.VISIBLE);newImageView2.setVisibility(View.VISIBLE);
		
		//mImageView1.setX(x1);
		//mImageView1.setY(y1);
		//mImageView2.setX(x2);
		//mImageView2.setX(y2);
		
	}
	boolean victory()
	{
		
		
		float ih=mImageView1.getMeasuredHeight();//height of imageView
		float iw=mImageView1.getMeasuredWidth();//width of imageView
		float iH=mImageView1.getDrawable().getIntrinsicHeight();//original height of underlying image
		float iW=mImageView1.getDrawable().getIntrinsicWidth();//original width of underlying image

		if (ih/iH<=iw/iW) iw=iW*ih/iH;//rescaled width of image within ImageView
		else ih= iH*iw/iW;//rescaled height of image within ImageView
		float diff = ( mImageView2.getTop()- mImageView1.getTop());
		float trans = mImageView1.getTranslationY()-mImageView2.getTranslationY();
		if(trans+ih>=diff ) return true;
		else return false;
	}
	boolean victory2(float x1,float y1,float x2, float y2)
	{
		float ih=mImageView1.getMeasuredHeight();//height of imageView
		float iw=mImageView1.getMeasuredWidth();//width of imageView
		float iH=mImageView1.getDrawable().getIntrinsicHeight();//original height of underlying image
		float iW=mImageView1.getDrawable().getIntrinsicWidth();//original width of underlying image

		if (ih/iH<=iw/iW) iw=iW*ih/iH;//rescaled width of image within ImageView
		else ih= iH*iw/iW;//rescaled height of image within ImageView
		
		double r = 4*Math.PI;
		long t = (endTime-startTime);
		if(t>T) t = T;
		r = r*(t)/(T); // angle rotated till now 
		double r1 = r+Math.PI/6;
		r = r%(2*Math.PI);
		r1 = r1%(2*Math.PI);
		
		double pi = Math.PI;
		float val = (2*ih)/3;
		float val1 = val;
		float corr = ih/6;
		corr = (float)(corr*Math.cos(r));
		
		float corr1 = ih/6;
		corr1 = (float)(corr1*Math.sin(r));
		
		Log.i("Try",Float.toString(val) );
		
		if(r>=0 && r<(pi/3)) val = (float)(val*Math.cos(r));
		else if(r>=(pi/3) && r<((2*pi)/3)) val = (float)(val*Math.cos(2*pi/3-r));
		else if(r>=(2*pi)/3 && r<pi)val = (float)(val*Math.cos(r-2*pi/3));
		else if(r>=(pi) && r<((4*pi)/3))
		{
			val = (float)(val*Math.cos(4*pi/3-r));
			Log.i("n",Double.toString(r) );
		}
		else if(r>=(4*pi)/3 && r<((5*pi)/3))val = (float)(val*Math.cos(r-4*pi/3));
		else if(r>=(5*pi)/3 && r<(2*pi))val = (float)(val*Math.cos(2*pi-r));
		
		if(r1>=0 && r1<(pi/3)) val1 = (float)(val1*Math.cos(r1));
		else if(r1>=(pi/3) && r1<((2*pi)/3)) val1 = (float)(val1*Math.cos(2*pi/3-r1));
		else if(r1>=(2*pi)/3 && r1<pi)val1 = (float)(val1*Math.cos(r1-2*pi/3));
		else if(r1>=(pi) && r1<((4*pi)/3))
		{
			val1 = (float)(val1*Math.cos(4*pi/3-r1));
			Log.i("n",Double.toString(r1) );
		}
		else if(r1>=(4*pi)/3 && r1<((5*pi)/3))val1 = (float)(val1*Math.cos(r1-4*pi/3));
		else if(r1>=(5*pi)/3 && r1<(2*pi))val1 = (float)(val1*Math.cos(2*pi-r1));
		
		float diff = ( mImageView2.getTop()- mImageView1.getTop());
		float trans = y1-y2-2*corr;
		float trans2 = x1-x2-2*corr1;
		
		Log.i("Try",Float.toString(ih) );
		Log.i("Try",Float.toString(val) );
		Log.i("Try",Double.toString(r) );
		if(trans+2*val>=diff && trans2<=2*val1  ) return true;
		
		//if(y1-y2>=2*mImageView1.getBottom())return true;
		else return false;
	}
	
	void checkEnd()
	{
		if(score1==10)
			{
				mImageView1.setVisibility(View.INVISIBLE);
				mImageView2.setVisibility(View.INVISIBLE);
				tv.setText("PLAYER 1 WINS THE GAME!");
			}
		if(score2==10)
		{
			mImageView1.setVisibility(View.INVISIBLE);
			mImageView2.setVisibility(View.INVISIBLE);
			tv.setText("PLAYER 2 WINS THE GAME!");
		}
	}

}
