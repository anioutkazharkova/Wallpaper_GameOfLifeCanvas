package com.anioutkazharkova.gameoflifecanvas;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity implements OnChangeLiveListener {
	
	private Handler mHandler;
	private LifeModelView lifeView;
	boolean canLive = true;
	int lastPosition = -1;
	long lastClick = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mHandler = new Handler();
		lifeView = (LifeModelView) findViewById(R.id.lifeView);		
		
		lifeView.setCallback(this);
		startLive();
	}

	public void startLive() {
		mHandler.postDelayed(mUpdateGeneration, 1000);
	}

	public void stopLive() {
		mHandler.removeCallbacks(mUpdateGeneration);
	}

	private Runnable mUpdateGeneration = new Runnable() {
		public void run() {
			lifeView.next();

			mHandler.postDelayed(mUpdateGeneration, 1000);
		}
	};
	
}
