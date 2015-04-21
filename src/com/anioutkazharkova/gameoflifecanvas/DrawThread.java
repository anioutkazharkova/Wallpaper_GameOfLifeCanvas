package com.anioutkazharkova.gameoflifecanvas;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {
	private SurfaceHolder holder;
	private Context mContext;
	private LifeModel mLifeModel;
	int backColor=0;
	private Integer rows;
	private Integer columns;
	private Integer size;
	int divider=1;
	private int[] colors;
	private Paint mAlivePaint;
	private Paint mDeadPaint;

	public DrawThread(SurfaceHolder surfaceHolder,Context context,LifeModel mLifeModel,Object[] bunch)
	 {
		 this.holder=surfaceHolder;	 
		 mContext=context;
		 this.mLifeModel=mLifeModel;
		 divider=Utility.DpToPx(mContext, 1);
		 if (bunch!=null && bunch.length>0)
		 {
			 backColor=(Integer) bunch[0];
			 rows=(Integer)bunch[1];
			 columns=(Integer)bunch[2];
			 size=(Integer)bunch[3];
			 colors=(int[])bunch[4];
			 mAlivePaint=(Paint)bunch[5];
			 mDeadPaint=(Paint)bunch[6];
		 }
		 
	 }
	//DrawThread thread=new DrawThread(holder, mContext, mLifeModel,new Object[]{backColor,rows,columns,size,colors,mAlivePaint,mDeadPaint});
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (holder!=null)
		{
		Canvas canvas = null;

		//handler.removeCallbacks(drawrunnable);

		try {
			canvas = holder.lockCanvas();

			synchronized (holder) {
				drawPattern(canvas);
			}
			
		} finally {
			if (canvas != null)
				holder.unlockCanvasAndPost(canvas);
		}
		}
	}

	private void drawPattern(Canvas canvas) {
		// TODO Auto-generated method stub
		if (canvas != null) {
			Paint paint = null;
			Paint stroke=null;
			canvas.drawColor(mContext.getResources().getColor(
					backColor));
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					if (mLifeModel.isCellAlive(i, j)) {
						int color=new Random().nextInt(5);
						
						paint = mAlivePaint;
						paint.setColor(mContext.getResources().getColor(colors[color]));
						//stroke=mStrokePaint;
					} else
						{paint = mDeadPaint;
						stroke=null;
						}
					int x = (int) Math.round(i * size) + divider;
					int y = (int) Math.round(j * size) + divider;
					int x1 = x + size - divider;
					int y1 = y + size - divider;
					if (Theme.getForm()==0)
					{
					 Rect r = new Rect(x, y, x1, y1);
					 canvas.drawRect(r, paint);
					}
					else
					{
					canvas.drawCircle((float) (x + x1) / 2,
							(float) (y + y1) / 2, size / 2, paint);
					}
					/*if (stroke!=null)
					{
						canvas.drawCircle((float) (x + x1-1) / 2,
								(float) (y + y1-1) / 2, size / 2-1, stroke);
					}*/
					// 
				}
			}
		}
	}
}
