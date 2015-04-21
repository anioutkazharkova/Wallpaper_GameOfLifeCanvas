package com.anioutkazharkova.gameoflifecanvas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
	private TextView tvCellsNumber,tvMax;
	//private CheckBox chbForm;
	
	int backColor=Utility.BackColor;
	int themeCode=Utility.ThemeCode;
	int cellsNumber=Utility.CellsNumber;
	int newCellsNumber=Utility.CellsNumber;
	int cellSize=Utility.CellSize;
	private SharedPreferences preferences;
	private int columns=0;
	private int rows=0;
	int maxCount=0;
	int form=Utility.Form;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		preferences = getApplicationContext().getSharedPreferences(Utility.PREFERENCES,
				getApplicationContext().MODE_PRIVATE);
		//loadPreferences();
		
		
		maxCount=getMaxCount();
		/*chbForm=(CheckBox)findViewById(R.id.chbForm);
		if (form==0)
			chbForm.setChecked(false);
		else 
			chbForm.setChecked(true);
		
		chbForm.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked)
					form=1;
				else
					form=0;
			}
		});
		*/
		tvMax=(TextView)findViewById(R.id.tvMaxNumber);
		tvMax.setText("Max: "+maxCount);
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
				
				maxCount=getMaxCount();
				tvMax.setText("Max: "+maxCount);
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
					if (newCellsNumber>maxCount)
					{
						newCellsNumber=maxCount-1;
						//etCells.setText(newCellsNumber);
						//ErrorDialog dialog=new ErrorDialog();
						//dialog.show(getFragmentManager(), "error");
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
	
	
	class ErrorDialog extends DialogFragment
	{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			
			AlertDialog.Builder builder=new Builder(getApplicationContext());
			
			builder.setMessage("Max: "+maxCount);
			builder.setPositiveButton("OK", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dismiss();
				}
			});
			
			return builder.create();
		}
	}
	
	private int getMaxCount()
	{
		columns = Utility.getDisplayWidth(this) /cellSize;
		rows = Utility.getDisplayHeight(this) / cellSize;
		return rows*columns;
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
		Utility.ThemeCode=themeCode;
		Utility.CurrentTheme=new Theme(themeCode);
		Utility.BackColor=backColor;
		Utility.CellsNumber=newCellsNumber;
		Utility.CellSize=cellSize;
		Utility.Form=form;
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
