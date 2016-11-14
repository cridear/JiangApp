package com.ows.mylaucher;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

public class DialogactivityMainActivity extends Activity implements OnClickListener {
	private Button add;
	private Button edit;
	private Button call;
	private Button cancle;
	
	private String name;
	private String number;
	private int group;
	private int position;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogactivity_main);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.y = 150;  
	    getWindow().setAttributes(lp);
	    add =(Button) findViewById(R.id.add);
	    edit = (Button) findViewById(R.id.edit);
	    call = (Button) findViewById(R.id.call);
	    cancle = (Button) findViewById(R.id.cancle);
	    add.setOnClickListener(this);
	    edit.setOnClickListener(this);
	    call.setOnClickListener(this);
	    cancle.setOnClickListener(this);
	    Intent intent = getIntent(); 
	    
	    position = intent.getIntExtra("position",0);
	    name = MainActivity.instance.contacts.get(position).name;	    
	    number = MainActivity.instance.contacts.get(position).number;
	    Log.e("myhome", "533+"+number);
	    group = MainActivity.instance.contacts.get(position).group;
	    
	    
	    
	    
	    SpannableString msp = new SpannableString("给"+name+"打电话");   
	    msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 1, name.length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	    msp.setSpan(new ForegroundColorSpan(Color.parseColor("#EEE7CE")), 1, name.length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  
	    call.setText(msp);
	    //2A8FBD
	    
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dialogactivity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add:			
			startActivity(new Intent(this,AddActivity.class));
			finish();
			break;
		case R.id.edit:
			Intent intent = new Intent(this,EditActivity.class);			
			intent.putExtra("position", position);
			Log.e("myhome", "~~~~~~~~~~~");
			startActivity(intent);
			finish();
			break;
			
			
		case R.id.call:
			Log.e("myhome", "number"+number);			  
            startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number))); 
            finish();            
			break;
		case R.id.cancle:
			finish();
		default:
			break;
		}

		
	}

}
