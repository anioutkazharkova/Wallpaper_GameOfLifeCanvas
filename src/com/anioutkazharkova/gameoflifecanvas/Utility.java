package com.anioutkazharkova.gameoflifecanvas;

import android.content.Context;
import android.util.DisplayMetrics;

public class Utility {
	
	public static Theme CurrentTheme=new Theme(0);
	public static int ThemeCode=0;
	public static int BackColor=0;
	public static int Form=0;
	public static int CellsNumber=500;
	public static int CellSize=20;
	public static String PREFERENCES="GameLifePref";
	public static String BACK_PREF="backColor_pref";
	public static String THEME_PREF="themeCode_pref";
	public static String CELL_PREF= "cellsNumber_pref";
	public static String SIZE_PREF= "cellSize_pref";
	
	public static int getDisplayWidth(Context context)
	{
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		
		return displayMetrics.widthPixels;
	}
	
	public static int getDisplayHeight(Context context)
	{
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		
		return displayMetrics.heightPixels;
	}
	public static int DpToPx(Context context, int value) {
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		float scale = displayMetrics.density;
		int pixels = (int) (value * scale + 0.5f);
		return pixels;

	}
	
	
}
