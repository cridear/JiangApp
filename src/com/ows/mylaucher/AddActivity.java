package com.ows.mylaucher;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddActivity extends Activity implements OnClickListener{
	ArrayAdapter<Integer> spinnerAdapter;
	ArrayAdapter<Integer> spinnerAdapter2;
	ArrayList<Integer> grouplist;
	ArrayList<Integer> ranklist;
	EditText nameTextView;
	EditText numberTextView;
	private Spinner groupSpinner;
	private Spinner rankSpinner;
	private String nameString;
	private String numberString;
	private int groupString;
	private int rankString;
	private int ranks;
	private PassMessage passMessage;
	Button sure;
	Button cancel;
	int position;
	
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.y = 150;  
	    getWindow().setAttributes(lp);
		passMessage = MainActivity.instance;
		Intent intent = getIntent();
		position = intent.getIntExtra("position",0);
		Contactbean contactbean = MainActivity.instance.contacts.get(position);
		Log.e("myhome", "+39~~~~~~~~~~~~");
		nameString = contactbean.name;
		numberString = contactbean.number;
		groupString = contactbean.group;		
		rankString = position - MainActivity.instance.contacts.prethegroup(groupString);
		Log.e("myhome", "+44~~~~~~~~~~~~");
		ranks = MainActivity.instance.contacts.getRanks(groupString);
		Log.e("myhome", ""+ranks);
		groupSpinner = (Spinner) findViewById(R.id.group);
		groupSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				int changeRanks = MainActivity.instance.contacts.getRanks(arg2);
				ranklist = new ArrayList<Integer>();
				for (int i = 0; i < changeRanks; i++) {
					ranklist.add(i);
				}
				spinnerAdapter2= new ArrayAdapter<Integer>(AddActivity.this, android.R.layout.simple_spinner_item, ranklist.toArray(new Integer[ranklist.size()]));
				//设置样式
				spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				rankSpinner.setAdapter(spinnerAdapter2);				

				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

				
			}
			
		});
		rankSpinner= (Spinner) findViewById(R.id.rank);
		nameTextView = (EditText) findViewById(R.id.name2);
		numberTextView = (EditText) findViewById(R.id.number2);
		sure = (Button) findViewById(R.id.sure);
		cancel = (Button) findViewById(R.id.cancle);
		sure.setOnClickListener(this);
		cancel.setOnClickListener(this);
		nameTextView.setText("");
		numberTextView.setText("");
		grouplist = new ArrayList<Integer>();
		grouplist.add(0);
		grouplist.add(1);
		grouplist.add(2);
		ranklist = new ArrayList<Integer>();
		for (int i = 0; i < ranks; i++) {
			ranklist.add(i);
		}
		
		Log.e("myhome","integer0"+grouplist.toString());
		Log.e("myhome","integer"+(grouplist.toArray(new Integer[grouplist.size()])).toString());
		
		
		
		spinnerAdapter= new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, grouplist.toArray(new Integer[grouplist.size()]));
	        //设置样式
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        //加载适配器
		
		spinnerAdapter2= new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, ranklist.toArray(new Integer[ranklist.size()]));
        //设置样式
	spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
		
		groupSpinner.setAdapter(spinnerAdapter);
		groupSpinner.setSelection(0);
		rankSpinner.setAdapter(spinnerAdapter2);
		rankSpinner.setSelection(0);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sure:			
			Bundle bundle = new Bundle();			
			bundle.putString("addName",nameTextView.getEditableText().toString());
			bundle.putString("addNumber",numberTextView.getEditableText().toString());
			bundle.putInt("addGroup",groupSpinner.getSelectedItemPosition());
			bundle.putInt("addposition",rankSpinner.getSelectedItemPosition()==-1?0:rankSpinner.getSelectedItemPosition());			
			passMessage.passMessage(0, bundle);
			finish();
			break;
		case R.id.cancle:
			finish();
			break;

		default:
			break;
		}

		
	}

}
