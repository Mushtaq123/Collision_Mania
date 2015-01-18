package com.example.collision_mania;

import java.util.Random;

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

public class Arcade extends Activity {
	
	private ImageView mImageView1;
	private ImageView mImageView2;
	Button player1, player2,start ;
	TextView tv,s1,s2;
	
	boolean clicked,startClicked,end ;
	static int score1 = 0,score2 = 0;
	static int noOfRounds = 0;
	CountDownTimer timer;
	int i ;
	
	long startTime;
	long endTime;
	
	ArcTranslate anim ;
	ArcTranslate anim1;
	Long T;
	
	float x1,y1,x2,y2;

	Random rn = new Random();
	
	MediaPlayer mp,mp1 ;
	
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
		setContentView(R.layout.game_play);
		
		mImageView1 = (ImageView) findViewById(R.id.icon1);
		mImageView2 = (ImageView) findViewById(R.id.icon2);
		Random generator = new Random(); 
		i = generator.nextInt(3) + 1;
		
		if(i==1)
		{
			mImageView1.setImageResource(R.drawable.b128);
			mImageView2.setImageResource(R.drawable.b128);
		}
		
		if(i==2)
		{
			mImageView1.setImageResource(R.drawable.b128);
			mImageView2.setImageResource(R.drawable.b128);
		}
		
		if(i==3)
		{
			mImageView1.setImageResource(R.drawable.triangle);
			mImageView2.setImageResource(R.drawable.triangle1);
		}
		
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
					startTime = System.currentTimeMillis();
					
					if(i==1)
					{
						translate.run();
					}
					
					try
					{
						//mp.setDataSource(getApplicationContext(), R.raw.backmusic);
		                //mp.prepare();
						mp.start();
					}
					catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						
					} 
					if(i==2)
					{
						float diff = ( mImageView2.getTop()- mImageView1.getTop());
						diff = diff/2;
						anim =  new ArcTranslate(3000,Animation.ABSOLUTE,0,Animation.ABSOLUTE,diff,Animation.ABSOLUTE,mImageView1.getX(),1);
						mImageView1.startAnimation(anim);
						
						anim1 = new ArcTranslate(3000,Animation.ABSOLUTE,0,Animation.ABSOLUTE,-diff,Animation.ABSOLUTE,mImageView1.getX(),-1);
						mImageView2.startAnimation(anim1);
						T = anim.getDuration();
						
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
					}
					
					if(i==3)
					{
						translate1.run();
					}
					
					/*Animation anim = new ArcTranslate(3000,Animation.ABSOLUTE,0,Animation.ABSOLUTE,mImageView1.getBottom()+100,Animation.ABSOLUTE,mImageView1.getX(),1);
					mImageView1.startAnimation(anim);
					
					Animation anim1 = new ArcTranslate(3000,Animation.ABSOLUTE,0,Animation.ABSOLUTE,-mImageView1.getBottom()-100,Animation.ABSOLUTE,mImageView1.getX(),-1);
					mImageView2.startAnimation(anim1);*/
					
					noOfRounds++;
					clicked = false;
					startClicked = true;
				}
				if(startClicked && clicked && !end)
				{
					try{
						mp1.pause();
						mp1.seekTo(0);
						}
						catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							
						} 
					mImageView1.setY(mImageView1.getTop());
					mImageView2.setY(mImageView2.getTop());
					
					Random generator = new Random();
					i = generator.nextInt(3) + 1;
					
					if(i==1)
					{
						mImageView1.setImageResource(R.drawable.b128);
						mImageView2.setImageResource(R.drawable.b128);
					}
					
					if(i==2)
					{
						mImageView1.setImageResource(R.drawable.b128);
						mImageView2.setImageResource(R.drawable.b128);
					}
					
					if(i==3)
					{
						mImageView1.setImageResource(R.drawable.triangle);
						mImageView2.setImageResource(R.drawable.triangle1);
					}
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
				
				if(!clicked && startClicked && !end)
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
					boolean b = true;
					if(i==1)
						{
							stopObjects1();
							b = victory1(); 
						}
					if(i==2)
					{
						stopObjects2();
						b = victory2(x1,y1,x2,y2); 
					}
					if(i==3)
					{
						stopObjects3();
						b = victory3(); 
					}
					endTime = System.currentTimeMillis();
					clicked  = true;
					if(b)
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
				
				if(!clicked && startClicked && !end)
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
					boolean b = true;
					if(i==1)
						{
							stopObjects1();
							b = victory1(); 
						}
					if(i==2)
					{
						stopObjects2();
						b = victory2(x1,y1,x2,y2); 
					}
					if(i==3)
					{
						stopObjects3();
						b = victory3(); 
					}
					endTime = System.currentTimeMillis();
					clicked  = true;
					if(b)
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
			
			
			float diff = ( mImageView2.getTop()- mImageView1.getTop());
			diff = diff/2;
			int time = rn.nextInt(3001)+ 2000; // generating time of animation
			//T = time;
			float translation = getResources()
					.getDimension(R.dimen.translation);
			mImageView1.animate().setDuration(time)
					.setInterpolator(new LinearInterpolator())
					.translationYBy(diff);
			mImageView2.animate().setDuration(time)
			.setInterpolator(new LinearInterpolator())
			.translationYBy(-diff);
			//tv.setVisibility(View.INVISIBLE);
			tv.setText("time "+time);
			/*if(pause)
				try {
					this.wait(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			timer = new CountDownTimer(time+1000, 1000) {

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
			
		}
	};
	
	Runnable translate1 = new Runnable() {
		public void run() {
			
			long time = rn.nextInt(3001)+ 2000; // generating time of animation
			T = time;
			float diff = ( mImageView2.getTop()- mImageView1.getTop());
			diff = diff/2;
			float translation = getResources()
					.getDimension(R.dimen.translation);
			mImageView1.animate().setDuration(time)
					.setInterpolator(new LinearInterpolator())
					.translationYBy(diff).rotationBy(-720.0f);
			mImageView2.animate().setDuration(time)
			.setInterpolator(new LinearInterpolator())
			.translationYBy(-diff).rotationBy(720.0f);
			//tv.setVisibility(View.INVISIBLE);
			tv.setText("time"+time);
			/*if(pause)
				try {
					this.wait(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			timer = new CountDownTimer(time+1000, 1000) {

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
		}
	};
	
	void stopObjects1()
	{
		timer.cancel();
		Float f = mImageView1.getTranslationY();
		Log.i("Try",Float.toString(f) );
		Float f1 = mImageView2.getTranslationY();
		Log.i("Try",Float.toString(f1) );
		mImageView1.animate().cancel();
		mImageView2.animate().cancel();
		//tv.setText("x"+mImageView1.getTranslationY()+"  "+(mImageView2.getTop()- mImageView1.getTop())+" "+mImageView1.getHeight());
		tv.setVisibility(View.INVISIBLE);
		//player2.setText("x"+mImageView1.getTranslationY()+"  "+(mImageView2.getTop()- mImageView1.getTop())+" ");
	}
	boolean victory1()
	{
		
		
		float ih=mImageView1.getMeasuredHeight();//height of imageView
		float iw=mImageView1.getMeasuredWidth();//width of imageView
		float iH=mImageView1.getDrawable().getIntrinsicHeight();//original height of underlying image
		float iW=mImageView1.getDrawable().getIntrinsicWidth();//original width of underlying image

		if (ih/iH<=iw/iW) iw=iW*ih/iH;//rescaled width of image within ImageView
		else ih= iH*iw/iW;//rescaled height of image within ImageView
		float diff = ( mImageView2.getTop()- mImageView1.getTop());
		float trans = mImageView1.getTranslationY()-mImageView2.getTranslationY();
		if(trans+ih>diff ) return true;
		else return false;
	}
	void stopObjects2()
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
	boolean victory2(float x1,float y1,float x2, float y2)
	{
		float ih=mImageView1.getMeasuredHeight();//height of imageView
		float iw=mImageView1.getMeasuredWidth();//width of imageView
		float iH=mImageView1.getDrawable().getIntrinsicHeight();//original height of underlying image
		float iW=mImageView1.getDrawable().getIntrinsicWidth();//original width of underlying image

		if (ih/iH<=iw/iW) iw=iW*ih/iH;//rescaled width of image within ImageView
		else ih= iH*iw/iW;//rescaled height of image within ImageView
		float diff = ( mImageView2.getTop()- mImageView1.getTop());
		float trans = y1-y2;
		float trans2 = x1-x2;
		if(trans+ih>=diff && trans2<=ih  ) return true;
		
		//if(y1-y2>=2*mImageView1.getBottom())return true;
		else return false;
	}
	void stopObjects3()
	{
		timer.cancel();
		Float f = mImageView1.getTranslationY();
		Log.i("Try",Float.toString(f) );
		Float f1 = mImageView2.getTranslationY();
		Log.i("Try",Float.toString(f1) );
		mImageView1.animate().cancel();
		mImageView2.animate().cancel();
		//tv.setText("x"+mImageView1.getTranslationY()+"  "+(mImageView2.getTop()- mImageView1.getTop())+" "+mImageView1.getHeight());
		tv.setVisibility(View.INVISIBLE);
		//player2.setText("x"+mImageView1.getTranslationY()+"  "+(mImageView2.getTop()- mImageView1.getTop())+" ");
	}
	boolean victory3()
	{
		//float r = mImageView2.getRotation();		
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
		r = r%(2*Math.PI);
		
		double pi = Math.PI;
		float val = (2*ih)/3;
		float corr = ih/6;
		corr = (float)(corr*Math.cos(r));
		
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
		
		float diff = ( mImageView2.getTop()- mImageView1.getTop());
		float trans = mImageView1.getTranslationY()-mImageView2.getTranslationY()-2*corr;
		
		Log.i("Try",Float.toString(ih) );
		Log.i("Try",Float.toString(val) );
		Log.i("Try",Double.toString(r) );
		if(trans+2*val>=diff ) return true;
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
