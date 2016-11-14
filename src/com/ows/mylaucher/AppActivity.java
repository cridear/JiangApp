package com.ows.mylaucher;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class AppActivity extends Activity
{
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_app);
		listView = (ListView) findViewById(R.id.applist);
		new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				final ArrayList<AppInfo> appList = new ArrayList<AppInfo>(); //用来存储获取的应用信息数据
				List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
				for(int i=0;i<packages.size();i++) { 
			PackageInfo packageInfo = packages.get(i);
			AppInfo tmpInfo =new AppInfo(); 
			tmpInfo.packageName = packageInfo.packageName; 
			tmpInfo.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();	 
			tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
			appList.add(tmpInfo);
			
			}
				
				runOnUiThread(new Runnable()
				{
					
					@Override
					public void run()
					{
						listView.setAdapter(new AppAdapter(AppActivity.this, appList));		
						listView.setOnItemClickListener(new OnItemClickListener()
						{

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3)
							{
								startAPP(appList.get(arg2).packageName);	
								finish();
							}
						});
					}
				});
				
				
		}}).start();
		

	}
	
	
	public void startAPP(String appPackageName){
	    try{
	        Intent intent = this.getPackageManager().getLaunchIntentForPackage(appPackageName);
	        startActivity(intent);
	    }catch(Exception e){
	        Toast.makeText(this, "没有安装", Toast.LENGTH_LONG).show();
	    }
	
	

	}
}
	
		

	
	
	



