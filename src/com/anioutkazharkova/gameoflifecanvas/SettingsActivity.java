package com.anioutkazharkova.gameoflifecanvas;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsActivity extends Activity {

	private Spinner spThemes;
	private Spinner spBacks;
	private EditText etCells;
	private SeekBar sbCellSize;
	private TextView tvCellsNumber;
	
	int backColor=0;
	int themeCode=0;
	int cellsNumber=200;
	int newCellsNumber=200;
	int cellSize=20;
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		preferences = getApplicationContext().getSharedPreferences(Utility.PREFERENCES,
				getApplicationContext().MODE_PRIVATE);
		loadPreferences();
		
		spThemes = (Spinner) findViewById(R.id.spThemes);
		spBacks= (Spinner) findViewById(R.id.spBackground);
		etCells=(EditText)findViewById(R.id.etCellNumber);
		sbCellSize=(SeekBar)findViewById(R.id.sbCellSize);
		tvCellsNumber=(TextView)findViewById(R.id.tvCellsNumber);
		tvCellsNumber.setText(String.format("%d x %d", cellSize,cellSize));
		sbCellSize.setProgress(cellSize/5);
		sbCellSize.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (progress>1)
				{
					cellSize=progress*5;
					tvCellsNumber.setText(String.format("%d x %d", progress*5,progress*5));
				}
				else 
					sbCellSize.setProgress(2);
			}
		});
		
		etCells.setText(cellsNumber+"");
		
		etCells.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (s.toString()!="")
				{
					try{
					newCellsNumber=Integer.parseInt(s.toString());
					}
					catch(Exception e)
					{
						
					}
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		sbCellSize=(SeekBar)findViewById(R.id.sbCellSize);
		
		spBacks.setAdapter(new BackgroundAdapter(getApplicationContext(),
				R.layout.color_item,new String[2]));
		
		spBacks.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				backColor=arg2;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		
		});
		spBacks.setSelection(backColor);

		spThemes.setAdapter(new ThemeAdapter(getApplicationContext(),
				R.layout.color_item, getResources().getStringArray(R.array.theme_colors)));
		spThemes.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				themeCode=arg2;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		
		});
		
		spThemes.setSelection(themeCode);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId())
		{
		case R.id.saveItem:
			savePreferences();
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void savePreferences()
	{
		Editor editor =preferences.edit();
		editor.putInt(Utility.BACK_PREF, backColor);
		editor.putInt(Utility.THEME_PREF, themeCode);
		editor.putInt(Utility.CELL_PREF, newCellsNumber);
		editor.putInt(Utility.SIZE_PREF, cellSize);
		editor.apply();
		
	}
	private void loadPreferences()
	{
		
		backColor=preferences.getInt(Utility.BACK_PREF, backColor);
		themeCode=preferences.getInt(Utility.THEME_PREF,themeCode);
		cellsNumber=preferences.getInt(Utility.CELL_PREF, cellsNumber);
		newCellsNumber=cellsNumber;
		cellSize=preferences.getInt(Utility.SIZE_PREF, cellSize);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.save_menu, menu);
		return true;
	}
}
