package com.anioutkazharkova.lifewallpaper;

import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class LifeWallpaperService extends WallpaperService {

	@Override
	public Engine onCreateEngine() {
		// TODO Auto-generated method stub
		return new LifeEngine(getApplicationContext());
	}

	class LifeEngine extends Engine {
		private int rows;
		private int columns;
		private int cells = Utility.CellsNumber;
		private int size = Utility.CellSize; // Width and height of cell
		private LifeModel mLifeModel = null;
		private int divider = 1;

		int backColor = R.color.smockie;

		private Paint mDeadPaint;
		private Paint mAlivePaint;
		private Paint mStrokePaint;

		private boolean canLive = true;
		private int lastPosition = -1;
		private long lastClick = 0;
		private Context mContext;
		private boolean isVisible = true;
		OnChangeLiveListener callback;
		Object sync = new Object();
		int[] colors;

		public LifeEngine() {
		}

		public LifeEngine(Context context) {
			mContext = context;

			init(context);
		}

		private final Runnable drawrunnable = new Runnable() {
			public void run() {
				draw();
			}

		};
		private SharedPreferences preferences;

		private void loadPreferences() {
			preferences = mContext.getSharedPreferences(Utility.PREFERENCES,
					mContext.MODE_PRIVATE);
			backColor = (preferences.getInt(Utility.BACK_PREF, 0) == 0) ? R.color.smockie
					: R.color.black;
			int themeCode = preferences.getInt(Utility.THEME_PREF, 0);
			Utility.CurrentTheme = new Theme(themeCode);
			cells = preferences.getInt(Utility.CELL_PREF, cells);
			size = preferences.getInt(Utility.SIZE_PREF, size);
		}

		private void draw() {
			// TODO Auto-generated method stub
			final SurfaceHolder holder = getSurfaceHolder();

			Canvas canvas = null;

			handler.removeCallbacks(drawrunnable);

			try {
				try {
					canvas = holder.lockCanvas(null);
				} catch (Exception e) {

				}
				if (null == canvas) {
					return;
				}
				System.gc();
				synchronized (holder) {
					drawPattern(canvas);
				}

			} finally {
				if (canvas != null)
					try {
						holder.unlockCanvasAndPost(canvas);
					} catch (Exception e) {

					}
			}

			handler.removeCallbacks(drawrunnable);
			if (isVisible) {
				handler.postDelayed(drawrunnable, 100);
				// startLive();
			}
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			super.onVisibilityChanged(visible);

			isVisible = visible;

			if (visible) {
				// loadPreferences();
				reset(mContext);
				// init(mContext);
				setColors();
				setDeadColor();
				synchronized (sync) {
					DrawThread thread = new DrawThread(getSurfaceHolder(),
							mContext, mLifeModel, new Object[] { backColor,
									rows, columns, size, colors, mAlivePaint,
									mDeadPaint });
					thread.start();
					// mLifeModel = new LifeModel(rows, columns, cells);
					// handler.post(drawrunnable);

				}
				startLive();
			} else {
				handler.removeCallbacks(drawrunnable);
			}

		}

		private void drawPattern(Canvas canvas) {
			// TODO Auto-generated method stub
			if (canvas != null) {
				Paint paint = null;
				Paint stroke = null;

				canvas.drawColor(mContext.getResources().getColor(
						Utility.BackColor == 0 ? R.color.smockie
								: R.color.black));
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < columns; j++) {
						if (mLifeModel.isCellAlive(i, j)) {
							int color = new Random().nextInt(5);

							paint = mAlivePaint;
							paint.setColor(mContext.getResources().getColor(
									colors[color]));
							stroke = mStrokePaint;
						} else {
							mDeadPaint
									.setColor(mContext
											.getResources()
											.getColor(
													Utility.BackColor == 0 ? R.color.smockie
															: R.color.black));
							paint = mDeadPaint;
							stroke = null;
						}
						int x = (int) Math.round(i * size) + divider;
						int y = (int) Math.round(j * size) + divider;
						int x1 = x + size - divider;
						int y1 = y + size - divider;
						if (Utility.Form == 0) {
							Rect r = new Rect(x, y, x1, y1);
							canvas.drawRect(r, paint);
						} else {
							canvas.drawCircle((float) (x + x1) / 2,
									(float) (y + y1) / 2, size / 2, paint);
						}
						/*
						 * if (stroke!=null) { canvas.drawCircle((float) (x +
						 * x1-1) / 2, (float) (y + y1-1) / 2, size / 2-1,
						 * stroke); }
						 */
						//
					}
				}
			}
		}

		private int height = 0;
		private int width = 0;

		private Handler handler;

		@Override
		public void onTouchEvent(MotionEvent motionEvent) {
			// TODO Auto-generated method stub
			int action = motionEvent.getAction();
			int position = -1;
			if (action == MotionEvent.ACTION_DOWN) {
				canLive = false;
				stopLive();
			}
			if (action == MotionEvent.ACTION_MOVE
					|| action == MotionEvent.ACTION_DOWN) {
				float currentXPosition = motionEvent.getX();
				float currentYPosition = motionEvent.getY();

				position = getPosition((int) currentXPosition,
						(int) currentYPosition);

				if ((position == lastPosition && (SystemClock.elapsedRealtime() - lastClick) >= 500)
						|| position != lastPosition) {
					lastClick = SystemClock.elapsedRealtime();
					mLifeModel.changeAlive(position);
					draw();
				}

			}
			// xor(motionEvent.getX(), motionEvent.getY());
			if (action == MotionEvent.ACTION_UP) {
				canLive = true;
				startLive();
			}
			lastPosition = position;

			super.onTouchEvent(motionEvent);
		}

		private int getPosition(int x, int y) {

			int curColumn = y / size;
			int curRow = x / size;

			return curRow * columns + curColumn;
		}

		public void next() {
			try {
				mLifeModel.next();
			} catch (Exception e) {

			}
			draw();
		}

		public void startLive() {
			handler.postDelayed(mUpdateGeneration, 1500);
		}

		public void stopLive() {
			handler.removeCallbacks(mUpdateGeneration);
		}

		private Runnable mUpdateGeneration = new Runnable() {
			public void run() {
				next();

				handler.postDelayed(mUpdateGeneration, 800);
			}
		};

		public void changeLiveStatus(int position) {
			mLifeModel.changeAlive(position);
		}

		private void setColors() {

			colors = Utility.CurrentTheme.getColors();
		}

		public void reset(Context context) {
			size = Utility.CellSize;
			cells = Utility.CellsNumber;
			columns = Utility.getDisplayWidth(context) / size;
			rows = Utility.getDisplayHeight(context) / size;
			size = Utility.DpToPx(context, Utility.CellSize);
			mLifeModel = new LifeModel(rows, columns, cells);

		}

		public void init(Context context) {

			mContext = context;
			// loadPreferences();
			columns = Utility.getDisplayWidth(context) / size;
			rows = Utility.getDisplayHeight(context) / size;
			size = Utility.DpToPx(context, size);
			divider = Utility.DpToPx(context, 1);
			mLifeModel = new LifeModel(rows, columns, cells);

			mAlivePaint = new Paint();
			mAlivePaint.setStyle(Style.FILL);
			mAlivePaint.setAntiAlias(true);

			mDeadPaint = new Paint();
			mDeadPaint.setStyle(Style.FILL_AND_STROKE);
			mDeadPaint.setColor(mContext.getResources().getColor(
					Utility.BackColor == 0 ? R.color.smockie : R.color.black));
			mDeadPaint.setAntiAlias(true);

			//mDeadPaint.setColor(mContext.getResources().getColor(backColor));
			setColors();
		}

		private synchronized void setDeadColor() {
			mDeadPaint.setColor(mContext.getResources().getColor(Utility.BackColor==0?R.color.smockie:R.color.black));

		}

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);
			handler = new Handler();
		}

		@Override
		public void onDestroy() {
			super.onDestroy();

		}

		@Override
		public void onOffsetsChanged(float xOffset, float yOffset,
				float xOffsetStep, float yOffsetStep, int xPixelOffset,
				int yPixelOffset) {

			super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep,
					xPixelOffset, yPixelOffset);
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);

			this.width = width;
			this.height = height;
		}

		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			super.onSurfaceCreated(holder);
			/*
			 * loadPreferences(); synchronized (sync) { init(mContext); }
			 */

			// mLifeModel = new LifeModel(rows, columns, cells);
			handler.post(drawrunnable);
			startLive();

		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			super.onSurfaceDestroyed(holder);
			stopLive();
			isVisible = false;

			handler.removeCallbacks(drawrunnable);

		}
	}

}
