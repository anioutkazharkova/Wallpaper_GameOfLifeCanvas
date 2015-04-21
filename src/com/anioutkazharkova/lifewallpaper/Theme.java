package com.anioutkazharkova.lifewallpaper;


public class Theme {
	
	private int code;
	private int[] Colors;
	private int figure;
	private int schemeColor;
	
	
	public Theme()
	{
		
	}
	public Theme(int code)
	{
		this.setCode(code);
		initColors(this.code);
		setSchemeColor(this.code);
	}
	
	public int[] getColors()
	{
		return Colors;
	}
	
	private int setSchemeColor(int code)
	{
		schemeColor=0;
		switch(code)
		{
		case 0:
			schemeColor=R.color.sunflower;
			break;
		case 1:
			schemeColor=R.color.raspberi;
			break;
		case 2:
			schemeColor=R.color.peter_river;
		break;
		case 3:
			schemeColor=R.color.nephritis;
			break;
		case 4:
			schemeColor=0;
			break;
		}
		return schemeColor;
	}
	
	private int[] initColors(int code)
	{
		Colors=new int[5];
		switch(code)
		{
		case 0:
		{
			Colors[1]=R.color.energy;
			Colors[0]=R.color.turbo_yellow;
			Colors[2]=R.color.sunflower;
			Colors[3]=R.color.orange;
			Colors[4]=R.color.carrot;
		}
		break;
		case 1:
		{
			Colors[1]=R.color.red_accent;
			Colors[0]=R.color.read_red;
			Colors[2]=R.color.raspberi;
			Colors[3]=R.color.valencia;
			Colors[4]=R.color.pomegranate;
		}
		break;
		
	case 2:
	{
		Colors[1]=R.color.peter_river;
		Colors[0]=R.color.light_blue;
		Colors[2]=R.color.pic_blue;
		Colors[3]=R.color.blue;
		Colors[4]=R.color.indigo;
	}
	break;

	case 3:
	{
		Colors[1]=R.color.chateu_green;
		Colors[0]=R.color.fern;
		Colors[2]=R.color.grean_sea;
		Colors[3]=R.color.nephritis;
		Colors[4]=R.color.emerald;
	}
	break;
	case 4:
	{
		Colors[1]=R.color.red_accent;
		Colors[0]=R.color.purple;
		Colors[2]=R.color.peter_river;
		Colors[3]=R.color.sunflower;
		Colors[4]=R.color.emerald;
	}
	break;
	}
		return Colors;
	}



public static int getForm()
{
	return 0;
}


public int getCode() {
	return code;
}
public void setCode(int code) {
	this.code = code;
}
public int getSchemeColor() {
	return schemeColor;
}
}
