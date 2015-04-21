package com.anioutkazharkova.gameoflifecanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class LifeModelView extends View {

	private int rows;
	private int columns;
	private int cells = 300;
	private int size = 30; // Width and height of cell
	private LifeModel mLifeModel = null;
	private int divider = 1;

	private Paint mDeadPaint;
	private Paint mAlivePaint;

	private boolean canLive = true;
	private int lastPosition = -1;
	private long lastClick = 0;
	private Context mContext;
	
	OnChangeLiveListener callback;

	public LifeModelView(Context context) {
		super(context);
		init(context);
	}

	public LifeModelView(Context context, AttributeSet attr) {
		super(context, attr);
		init(context);
	}

	public LifeModelView(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
		init(context);
	}

	public void init(Context context) {

		mContext = context;
		columns = Utility.getDisplayWidth(context) / size;
		rows = Utility.getDisplayHeight(context) / size;
		size = Utility.DpToPx(context, size);
		divider = Utility.DpToPx(context, 1);
		mLifeModel = new LifeModel(rows, columns, cells);
		mAlivePaint = new Paint();
		mAlivePaint.setStyle(Style.FILL_AND_STROKE);
		mAlivePaint.setColor(Color.RED);

		mDeadPaint = new Paint();
		mDeadPaint.setStyle(Style.FILL_AND_STROKE);
		mDeadPaint.setColor(Color.WHITE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = null;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (mLifeModel.isCellAlive(i, j)) {
					paint = mAlivePaint;
				} else
					paint = mDeadPaint;
				int x = (int) Math.round(i * size) + divider;
				int y = (int) Math.round(j * size) + divider;
				int x1 = x + size - divider;
				int y1 = y + size - divider;
				Rect r = new Rect(x, y, x1, y1);

				canvas.drawRect(r, paint);
			}
		}

	}
	
	public void setCallback(OnChangeLiveListener callback)
	{
		this.callback=callback;
	}

	public boolean onTouchEvent(MotionEvent motionEvent) {
		int action = motionEvent.getAction();
		int position = -1;
		if (action == MotionEvent.ACTION_DOWN) {
			canLive = false;
			if (callback!=null)
				callback.stopLive();
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

			}

		}
		// xor(motionEvent.getX(), motionEvent.getY());
		if (action == MotionEvent.ACTION_UP) {
			canLive = true;
			if (callback!=null)
				callback.startLive();
		}
		lastPosition = position;
		// touchX = touchY = -1;
		this.invalidate();
		return true;
	}

	private int getPosition(int x, int y) {
		
		int curColumn = y / size;
		int curRow = x / size;
		
		return curRow * columns + curColumn; 
	}

	public void next() {
		mLifeModel.next();
		this.invalidate();
	}

	public void changeLiveStatus(int position) {
		mLifeModel.changeAlive(position);
	}
}
