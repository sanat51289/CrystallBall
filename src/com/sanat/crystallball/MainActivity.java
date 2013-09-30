package com.sanat.crystallball;

import com.sanat.crystallball.R;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;


import com.sanat.crystallball.ShakeDetector.OnShakeListener;

public class MainActivity extends Activity {
	
	private CrystallBall mCrystallBall = new CrystallBall();
	private TextView mAnswerLabel;
	private SensorManager mSensorManager;
	private ImageView mCrystalBallImage;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;
//	private Button mGetAnswerButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Assigning the views from the layout file
		mAnswerLabel = (TextView) findViewById(R.id.textView1);
//		mGetAnswerButton = (Button) findViewById(R.id.button1);
		mCrystalBallImage = (ImageView) findViewById(R.id.imageView1);
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); 
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mShakeDetector = new ShakeDetector(new OnShakeListener() {
			
			@Override
			public void onShake() {
				
				handleNewAnswer();
			}
		});
		
		
//		mGetAnswerButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
				
				
//			}
//		});
	}
	
	@Override 
	public void onResume() //check the activity lifecycle
	{
		super.onResume();
		mSensorManager.registerListener(mShakeDetector, mAccelerometer, 
				SensorManager.SENSOR_DELAY_UI);
		
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		mSensorManager.unregisterListener(mShakeDetector);
	}
	private void animateCrystallBall(){
		
		mCrystalBallImage.setImageResource(R.drawable.ball_animation);
		AnimationDrawable ballAnimation = (AnimationDrawable) mCrystalBallImage.getDrawable();
		if(ballAnimation.isRunning())
		{
			ballAnimation.stop();
		}
		ballAnimation.start();
	}
	
	private void animateAnswer()
	{
		AlphaAnimation fadeInAnimation = new AlphaAnimation(0, 1);
		fadeInAnimation.setDuration(1500);
		fadeInAnimation.setFillAfter(true);
		
		mAnswerLabel.setAnimation(fadeInAnimation);
	}

	private void playSound()
	{
		MediaPlayer player = MediaPlayer.create(this, R.raw.crystal_ball);
		player.start();
		player.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void handleNewAnswer() {
		animateCrystallBall();
		String answer = mCrystallBall.getAnAnswer();
		mAnswerLabel.setText(answer);
		animateAnswer();
		playSound();
	}

}
