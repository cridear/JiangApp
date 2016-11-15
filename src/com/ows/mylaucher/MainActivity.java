package com.ows.mylaucher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.ows.mylaucher.AppAdapter.AppCallback;
import com.ows.mylaucher.Myadapter.Callback;
import com.ows.mylaucher.db.DatabaseContext;
import com.ows.mylaucher.db.PersonSQLiteOpenHelper;

public class MainActivity extends Activity implements OnClickListener,SpeechSynthesizerListener,Callback,PassMessage,AppCallback{
	private ListView listView;
	private TextClock textClock;
	private TextView textView;
	private Camera m_Camera;
	private Myadapter myadapter;
	boolean isCharging;
	private SpeechSynthesizer mSpeechSynthesizer;
	private static final String SAMPLE_DIR_NAME = "baiduTTS";
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_OFFLINE_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
    private static final String LICENSE_FILE_NAME = "temp_license";
    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";
    private String mSampleDirPath;
    private TextView battary,lock,light,left1,left2,left3,left4;
    private BatteryReceiver batteryReceiver;
    MyLinkedList contacts;
    private ArrayList<Contactbean> savelst;
    static public MainActivity instance;
    private AsyncQueryHandler asyncQueryHandler; // �첽��ѯ���ݿ������
    SQLiteDatabase mSQLiteDatabase;
    PersonSQLiteOpenHelper personSQLiteOpenHelper;
    private Context context;
    private boolean initspeak;
    ArrayList<AppInfo> appList=new ArrayList<AppInfo>();
    
    
    
    
    
    // ͼƬ�б� 
	 ArrayList<String> picList = new ArrayList<String>();  
	 // �õ�sd����·�� 
	  String imagePath = Environment.getExternalStorageDirectory().toString()+ "/han";   
	  String[][] MIME_MapTable={ 
			  //{��׺����MIME����} 
	            {".3gp",    "video/3gpp"}, 
	            {".apk",    "application/vnd.android.package-archive"}, 
	            {".asf",    "video/x-ms-asf"}, 
	            {".avi",    "video/x-msvideo"}, 
	            {".bin",    "application/octet-stream"}, 
	            {".bmp",    "image/bmp"}, 
	            {".c",  "text/plain"}, 
	            {".class",  "application/octet-stream"}, 
	            {".conf",   "text/plain"}, 
	            {".cpp",    "text/plain"}, 
	            {".doc",    "application/msword"}, 
	            {".docx",   "application/vnd.openxmlformats-officedocument.wordprocessingml.document"}, 
	            {".xls",    "application/vnd.ms-excel"},  
	            {".xlsx",   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}, 
	            {".exe",    "application/octet-stream"}, 
	            {".gif",    "image/gif"}, 
	            {".gtar",   "application/x-gtar"}, 
	            {".gz", "application/x-gzip"}, 
	            {".h",  "text/plain"}, 
	            {".htm",    "text/html"}, 
	            {".html",   "text/html"}, 
	            {".jar",    "application/java-archive"}, 
	            {".java",   "text/plain"}, 
	            {".jpeg",   "image/jpeg"}, 
	            {".jpg",    "image/jpeg"}, 
	            {".js", "application/x-javascript"}, 
	            {".log",    "text/plain"}, 
	            {".m3u",    "audio/x-mpegurl"}, 
	            {".m4a",    "audio/mp4a-latm"}, 
	            {".m4b",    "audio/mp4a-latm"}, 
	            {".m4p",    "audio/mp4a-latm"}, 
	            {".m4u",    "video/vnd.mpegurl"}, 
	            {".m4v",    "video/x-m4v"},  
	            {".mov",    "video/quicktime"}, 
	            {".mp2",    "audio/x-mpeg"}, 
	            {".mp3",    "audio/x-mpeg"}, 
	            {".mp4",    "video/mp4"}, 
	            {".mpc",    "application/vnd.mpohun.certificate"},        
	            {".mpe",    "video/mpeg"},   
	            {".mpeg",   "video/mpeg"},   
	            {".mpg",    "video/mpeg"},   
	            {".mpg4",   "video/mp4"},    
	            {".mpga",   "audio/mpeg"}, 
	            {".msg",    "application/vnd.ms-outlook"}, 
	            {".ogg",    "audio/ogg"}, 
	            {".pdf",    "application/pdf"}, 
	            {".png",    "image/png"}, 
	            {".pps",    "application/vnd.ms-powerpoint"}, 
	            {".ppt",    "application/vnd.ms-powerpoint"}, 
	            {".pptx",   "application/vnd.openxmlformats-officedocument.presentationml.presentation"}, 
	            {".prop",   "text/plain"}, 
	            {".rc", "text/plain"}, 
	            {".rmvb",   "audio/x-pn-realaudio"}, 
	            {".rtf",    "application/rtf"}, 
	            {".sh", "text/plain"}, 
	            {".tar",    "application/x-tar"},    
	            {".tgz",    "application/x-compressed"},  
	            {".txt",    "text/plain"}, 
	            {".wav",    "audio/x-wav"}, 
	            {".wma",    "audio/x-ms-wma"}, 
	            {".wmv",    "audio/x-ms-wmv"}, 
	            {".wps",    "application/vnd.ms-works"}, 
	            {".xml",    "text/plain"}, 
	            {".z",  "application/x-compress"}, 
	            {".zip",    "application/x-zip-compressed"}, 
	            {"",        "*/*"}   
	        }; 
   
    
    
    
    
    
    
    
    
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_main);
        context = this;
        instance = this;
        //flash(true);
        //setFlashlightEnabled(true);
        contacts = new MyLinkedList();
        listView = (ListView) findViewById(R.id.lv);
        battary = (TextView) findViewById(R.id.textView1);
        textClock = (TextClock) findViewById(R.id.digitalClockDate);
        textView = (TextView) findViewById(R.id.txt2);
        lock = (TextView) findViewById(R.id.lock);
        lock.setOnClickListener(this);
        light = (TextView) findViewById(R.id.light);
        light.setOnClickListener(this);
        light.setTag(false);
        left1= (TextView) findViewById(R.id.left1);
        left2= (TextView) findViewById(R.id.left2);
        left3= (TextView) findViewById(R.id.left3);
        left4= (TextView) findViewById(R.id.left4);
        left1.setOnClickListener(this);
        left2.setOnClickListener(this);
        left3.setOnClickListener(this);
        left4.setOnClickListener(this);
        
        batteryReceiver=new BatteryReceiver();
        asyncQueryHandler = new MyAsyncQueryHandler(getContentResolver());  
        IntentFilter filter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);//ע��BroadcastReceiver  
        
        init();
        
        
        
      /*   
        // ��ô��
        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;*/
        
        
        
        
        
        
        
        
        
        
        
        
        
		        
        
        
        
       /* mSQLiteDatabase = openOrCreateDatabase(MyApp.getDATABASE_NAME(), Context.MODE_PRIVATE, null);
        if (!CommonLibCall.checkDataBase(MyApp.getDATABASE_NAME())) {
        	mSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS config (id integer primary key autoincrement, s varchar(60), rt varchar(60),st varchar(60), ru varchar(60), v varchar(60),i varchar(60))");  	        	
        	
    		for (int i = 0; i < 10; i++)
    		{
    			//mSQLiteDatabase.execSQL("insert into"+" "+"Contact"+"(ID,Word) values" +"("+i+",'"+(i+1)+"'"+");");			
    		}		
    		//mSQLiteDatabase.close();
    		Log.v("ccy", "�������ݿ���ɣ�����");	
		}*/
        
        
        
				
        
        
        
      new Thread(new Runnable() {
			
			@Override
			public void run() {			
				initialEnv();        
		        initialTts();	
		        initspeak = true;
		      
		          
		        
			}
		}).start();      
        
        
        textClock.setFormat12Hour("yyyy��MM��dd  EEEE");
       
        
        //listView.setAdapter(new Myadapter(als, this));
        
          
          listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			//	startAPPFromPackageName(MainActivity.this,"com.android.bbk.lockscreen3");
				/*String name =als.get(arg2)[0];
				speak(name);*/

				
			}
        	  
		});
          
         /* IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
          Intent batteryStatus = context.registerReceiver(null, ifilter);
          //����Զ������״̬,����ڳ�磬���Զ�����usb���ǽ�����
           
          // �Ƿ��ڳ��
          int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
          isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                               status == BatteryManager.BATTERY_STATUS_FULL;
          
        	  if (isCharging) {
        		  Toast.makeText(this, "���ڳ��", Toast.LENGTH_LONG).show();}*/
			
		

         
          
        
    }
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	/*int a = Calendar.getInstance().get(Calendar.YEAR);*/
    	int b = Calendar.getInstance().get(Calendar.MONTH);
    	int c = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    	/*int d = LunarCalendar.solarToLunar(a, b, c)[1];
    	int e = LunarCalendar.solarToLunar(a, b, c)[2];
    	
    	Log.e("laucher", "a:"+a);*/
    	
		textView.setText("ũ��  "+ MyLunarDateFormat.Month2��(b)+ MyLunarDateFormat.Day2��(c));
    }
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
    	switch (item.getItemId())
    	{
    	case R.id.action_settings:			  
               startActivity(new Intent(Settings.ACTION_SETTINGS));			
    		break;
    	case R.id.applist:
    		   startActivity(new Intent(this,AppActivity.class));
    	default:
    		break;
    	}

    	return super.onOptionsItemSelected(item);
    }    
    
  

    private void flash(boolean bool){
    	if(bool){
    		PackageManager pm= this.getPackageManager();
    		FeatureInfo[]  features=pm.getSystemAvailableFeatures();
    		for(FeatureInfo f : features)
    		{
    		  if(PackageManager.FEATURE_CAMERA_FLASH.equals(f.name))   //�ж��豸�Ƿ�֧�������
    		  {
    		  if ( null == m_Camera )  
    		    {  
    		        m_Camera = Camera.open();      
    		    }  

    		    Camera.Parameters parameters = m_Camera.getParameters();               
    		    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);    
    		    m_Camera.setParameters( parameters );              
    		    m_Camera.startPreview();
    		
    		  }
    		}
    		}else{
    		if ( m_Camera != null )  
    		   {  
    		       m_Camera.stopPreview();  
    		       m_Camera.release();  
    		       m_Camera = null;  
    		   }
    		
    		}
    		}
    	
    	

    private void initialTts() {
        this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        this.mSpeechSynthesizer.setContext(this);
        this.mSpeechSynthesizer.setSpeechSynthesizerListener(this);
        // �ı�ģ���ļ�·�� (��������ʹ��)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/"
                + TEXT_MODEL_NAME);
        // ��ѧģ���ļ�·�� (��������ʹ��)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/"
                + SPEECH_FEMALE_MODEL_NAME);
		// ������Ȩ�ļ�·��,��δ���ý�ʹ��Ĭ��·��.������ʱ��Ȩ�ļ�·����LICENCE_FILE_NAME���滻����ʱ��Ȩ�ļ���ʵ��·��������ʹ����ʱlicense�ļ�ʱ��Ҫ�������ã������[Ӧ�ù���]�п�ͨ����ʽ������Ȩ������Ҫ���øò��������齫���д���ɾ�����������棩
		// ����ϳɽ��������ʱ��Ȩ�ļ���Ҫ���ڵ���ʾ��˵��ʹ������ʱ��Ȩ�ļ�����ɾ����ʱ��Ȩ���ɡ�
		//this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/"+ LICENSE_FILE_NAME);
        // ���滻Ϊ����������ƽ̨��ע��Ӧ�õõ���App ID (������Ȩ)
        
        
        
        
        this.mSpeechSynthesizer.setAppId("8564434");
        // ���滻Ϊ����������ƽ̨ע��Ӧ�õõ���apikey��secretkey (������Ȩ)
        
        this.mSpeechSynthesizer.setApiKey("1NyzXSpn5SMfiNRGhxmgBPia", "47ecfcceefdd60fabc3b2545e0e98a46");
        // �����ˣ��������棩�����ò���Ϊ0,1,2,3���������������˻ᶯ̬���ӣ���ֵ����ο��ĵ������ĵ�˵��Ϊ׼��0--��ͨŮ����1--��ͨ������2--�ر�������3--���������������
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // ����Mixģʽ�ĺϳɲ���
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // ��Ȩ���ӿ�(ֻ��ͨ��AuthInfo���м�����Ȩ�Ƿ�ɹ���)
        // AuthInfo�ӿ����ڲ��Կ������Ƿ�ɹ����������߻���������Ȩ�����������Ȩ�ɹ��ˣ�����ɾ��AuthInfo���ֵĴ��루�ýӿ��״���֤ʱ�ȽϺ�ʱ��������Ӱ������ʹ�ã��ϳ�ʹ��ʱSDK�ڲ����Զ���֤��Ȩ��
        AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.MIX);

        if (authInfo.isSuccess()) {
            toPrint("auth success");
            Log.e("myhome", "auth success");
        } else {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            toPrint("auth failed errorMsg=" + errorMsg);            
            Log.e("jiang", "auth failed errorMsg=" + errorMsg);
            
        }

        // ��ʼ��tts
        mSpeechSynthesizer.initTts(TtsMode.MIX);
        // ��������Ӣ����Դ���ṩ����Ӣ�ĺϳɹ��ܣ�
        int result =
 mSpeechSynthesizer.loadEnglishModel(mSampleDirPath + "/"
				+ ENGLISH_TEXT_MODEL_NAME, mSampleDirPath + "/"
				+ ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        int result2 =
 mSpeechSynthesizer.loadEnglishModel(mSampleDirPath + "/"
				+ TEXT_MODEL_NAME, mSampleDirPath + "/"
				+ SPEECH_OFFLINE_FEMALE_MODEL_NAME);
        
        toPrint("loadEnglishModel result=" + result);
    }
    

    private void initialEnv() {
        if (mSampleDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
        }
        makeDir(mSampleDirPath);
        copyFromAssetsToSdcard(false, SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, SPEECH_OFFLINE_FEMALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, TEXT_MODEL_NAME, mSampleDirPath + "/" + TEXT_MODEL_NAME);
        copyFromAssetsToSdcard(false, LICENSE_FILE_NAME, mSampleDirPath + "/" + LICENSE_FILE_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_TEXT_MODEL_NAME);
    }    

    private void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * ��sample������Ҫ����Դ�ļ�������SD����ʹ�ã���Ȩ�ļ�Ϊ��ʱ��Ȩ�ļ�����ע����ʽ��Ȩ��
     * 
     * @param isCover �Ƿ񸲸��Ѵ��ڵ�Ŀ���ļ�
     * @param source
     * @param dest
     */
    private void copyFromAssetsToSdcard(boolean isCover, String source, String dest) {
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = getResources().getAssets().open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    private void toPrint(String str) {
        Message msg = Message.obtain();
        msg.obj = str;
        //this.mHandler.sendMessage(msg);
    }

    private void print(Message msg) {
        String message = (String) msg.obj;
        if (message != null) {
        //    Log.w(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            scrollLog(message);
        }
    }
    private void scrollLog(String message) {
        Spannable colorMessage = new SpannableString(message + "\n");
        colorMessage.setSpan(new ForegroundColorSpan(0xff0000ff), 0, message.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
         /*mShowText.append(colorMessage);
        Layout layout = mShowText.getLayout();
        if (layout != null) {
            int scrollAmount = layout.getLineTop(mShowText.getLineCount()) - mShowText.getHeight();
            if (scrollAmount > 0) {
                mShowText.scrollTo(0, scrollAmount + mShowText.getCompoundPaddingBottom());
            } else {
                mShowText.scrollTo(0, 0);
            }
        }*/
    }
	@Override
	public void onError(String arg0, SpeechError arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSpeechFinish(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSpeechProgressChanged(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSpeechStart(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSynthesizeDataArrived(String arg0, byte[] arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSynthesizeFinish(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSynthesizeStart(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.lock:
		startAPPFromPackageName(MainActivity.this,"com.android.bbk.lockscreen3");
		break;
	case R.id.light:
		if ((Boolean) light.getTag()) {
			flash(false);
			light.setTag(false);
			light.setBackgroundColor(Color.BLACK);
		}
		else {
			flash(true);
			light.setTag(true);
			light.setBackgroundColor(Color.MAGENTA);
			
		}
		
	case R.id.left1:
		//listView.setSelection(contacts.prethegroup(0));
		if (contacts!=null&&contacts.size()>0) {
			myadapter = new Myadapter(contacts, MainActivity.this,MainActivity.this);
            listView.setAdapter(myadapter);
		}
		
		break;
	case R.id.left2:
		//listView.setSelection(contacts.prethegroup(1));
		startAPP("com.cootek.smartdialer");
		
		
		break;
	case R.id.left3:
		if (picList!=null&&picList.size()>0)
		{
			listView.setAdapter(new HanAppAdapter(this, picList, this));
		}
		else {			
			
			File mfile = new File(imagePath);
			  File[] files = mfile.listFiles(); 
			  Log.e("jiang", "imagefile"+files);
			  
			  if (files!=null&&files.length>0) {
			 // �����е��ļ�����ArrayList��,����������ͼƬ��ʽ���ļ� 
			 for (int i = 0; i < files.length; i++) { 
			  File file = files[i];			  
			   picList.add(file.getPath());
			 }
			 
			 Collections.sort(picList);
				 listView.setAdapter(new HanAppAdapter(this, picList, this));	
			}
			 
				
		}
		break;
		
		
	case R.id.left4:
		//listView.setSelection(contacts.prethegroup(2));
		//Log.e("myhome", "659");
		
		if (appList!=null&&appList.size()>0) {
			listView.setAdapter(new AppAdapter(MainActivity.this, appList,this));
			
		}
		else {
			new Thread(new Runnable()
			{
				
				@Override
				public void run()
				{
					appList = new ArrayList<AppInfo>(); //�����洢��ȡ��Ӧ����Ϣ����
					//List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
					Intent it = new Intent(Intent.ACTION_MAIN);
					it.addCategory(Intent.CATEGORY_LAUNCHER);
					List<ResolveInfo> packages = getPackageManager().queryIntentActivities(it,0);
					
					for(int i=0;i<packages.size();i++) { 
						ResolveInfo packageInfo = packages.get(i);
				AppInfo tmpInfo =new AppInfo(); 
				//tmpInfo.packageName = packageInfo.packageName; 
				tmpInfo.packageName = packageInfo.activityInfo.packageName;
				//tmpInfo.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();	 
				tmpInfo.appName = null;
				try {

tmpInfo.appName = getPackageManager().getApplicationLabel(
					
		getPackageManager().getApplicationInfo(packageInfo.activityInfo.packageName,
					
					                            PackageManager.GET_META_DATA)).toString();
					
					        } catch (NameNotFoundException e) {
					
					            e.printStackTrace();
					
					        }

						
				//tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
				tmpInfo.appIcon = packageInfo.activityInfo.loadIcon(getPackageManager());
				appList.add(tmpInfo);
				
				}
					
					runOnUiThread(new Runnable()
					{
						
						@Override
						public void run()
						{
							listView.setAdapter(new AppAdapter(MainActivity.this, appList,MainActivity.this));		
							/*listView.setOnItemClickListener(new OnItemClickListener()
							{

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3)
								{
									startAPP(appList.get(arg2).packageName);
									
								}
							});
*/						}
					});
					
					
			}}).start();
		}
		
		break;
		
		}
		
		
		
		
		
	}
	
	
	private void speak(String text) {
     
        //��Ҫ�ϳɵ��ı�text�ĳ��Ȳ��ܳ���1024��GBK�ֽڡ�
        int result = this.mSpeechSynthesizer.speak(text);
        if (result < 0) {
            toPrint("error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }
	
	/** 
	 * ͨ��packagename����Ӧ�� 
	 * @param context 
	 * @param packagename 
	 * */  
	public static void startAPPFromPackageName(Context context,String packagename){  
	    Intent intent=isexit(context,packagename);   
	     if(intent==null){    
	           Log.i("MyLaucher","APP not found!");    
	       }    
	     context.startActivity(intent);    
	}  
	  
	/** 
	 * ͨ��packagename�ж�Ӧ���Ƿ�װ 
	 * @param context 
	 * @param packagename 
	 *  
	 * @return ��ת��Ӧ����activity Intent 
	 * */  
	  
	public static Intent isexit(Context context,String pk_name){  
	    PackageManager packageManager = context.getPackageManager();   
	    Intent it= packageManager.getLaunchIntentForPackage(pk_name);  
	    return it;  
	}
	
	
	private class BatteryReceiver extends BroadcastReceiver{
	    @Override
	    public void onReceive(Context context, Intent intent) {
	      int current=intent.getExtras().getInt("level");//��õ�ǰ����
	      int total=intent.getExtras().getInt("scale");//����ܵ���
	      int percent=current*100/total;
	      if (percent<15) {
	    	  battary.setText("���ڵĵ�����"+percent+"%, �����ϳ�磡");
	    	  battary.setBackgroundColor(Color.parseColor("#EF9C94"));
	    	  
	    	  

	    	  
	               
	    	  
	    	  
		}
	      else if (percent<50) {
	    	  battary.setText("���ڵĵ�����"+percent+"%, ���Գ�硣");
	    	  battary.setBackgroundColor(Color.parseColor("#CEE6E6"));	    	  
	    	  battary.setTextColor(Color.BLACK);
		}
	      else {
	    	  battary.setText("���ڵĵ�����"+percent+"%");
	    	  battary.setBackgroundColor(Color.parseColor("#D6E6AD"));	    	  
	    	  battary.setTextColor(Color.BLACK);
		}
	      
	    }
	  }
@Override
protected void onDestroy() {
	unregisterReceiver(batteryReceiver);
	super.onDestroy();
}


/** 
 * ��ʼ�����ݿ��ѯ���� 
 */  
private void init() { 
	 
	
    Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // ��ϵ��Uri��  
    // ��ѯ���ֶ�  
    String[] projection = { ContactsContract.CommonDataKinds.Phone._ID,  
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,  
            ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",  
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,  
            ContactsContract.CommonDataKinds.Phone.PHOTO_ID,  
            ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY };  
    // ����sort_key�����ԃ  
    asyncQueryHandler.startQuery(0, null, uri, projection, null, null,  
            "sort_key COLLATE LOCALIZED asc");  

}  

/** 
 *  
 * @author Administrator 
 *  
 */  
private class MyAsyncQueryHandler extends AsyncQueryHandler {	
        	

    public MyAsyncQueryHandler(ContentResolver cr) {  
        super(cr);
       
    }  

    @Override  
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {  
        if (cursor != null && cursor.getCount() > 0) {  
        	
       	 
        	
            /*contactIdMap = new HashMap<Integer, ContactBean>();  
            list = new ArrayList<ContactBean>();*/  
            cursor.moveToFirst(); // �α��ƶ�����һ��  
            for (int i = 0; i < cursor.getCount(); i++) {  
                cursor.moveToPosition(i);  
                String name = cursor.getString(1);  
                String number = cursor.getString(2);  
                /*String sortKey = cursor.getString(3);  
                int contactId = cursor.getInt(4);  
                Long photoId = cursor.getLong(5);  
                String lookUpKey = cursor.getString(6);*/  

               /* if (contactIdMap.containsKey(contactId)) {  
                    // �޲���  
                } else {  */
                    // ������ϵ�˶���
                Contactbean contact = new Contactbean();
                contact.name = name;
                contact.number = number;
                contact.group = 0;
                contacts.add(contact);
                
               

                      
                //}  
            }  
            if (contacts.size() > 0) {
            	
            	initData(contacts.toString(),"/sdcard/Test/", "log.txt");
            	
            	Log.e("myhome", contacts.toString());
            	myadapter = new Myadapter(contacts, MainActivity.this,MainActivity.this);
               listView.setAdapter(myadapter);  
            }  
        cursor.close();
        }  

        super.onQueryComplete(token, cookie, cursor);  
    }  

}
@Override
public void click(View v) {
	
	
	switch (v.getId()) {		
	case R.id.name:
	case R.id.number:
		Intent intent = new Intent(this,DialogactivityMainActivity.class);
		/*Contactbean contactbean = contacts.get((Integer) v.getTag());
		intent.putExtra("name", contactbean.name);		
		intent.putExtra("number", contactbean.number);		
		intent.putExtra("group", contactbean.group);*/
		intent.putExtra("position", (Integer) v.getTag());		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
		startActivity(intent);
		break;
	case R.id.voice:				
		speak(contacts.get((Integer) v.getTag()).name);
		break;	
	case R.id.hori:
		openFile(new File(picList.get(((Integer) v.getTag()))));		
		break;
		
	case R.id.movie:
		String a =picList.get(((Integer) v.getTag())).replaceAll(Pattern.compile("[^/]*$").split(picList.get((Integer) v.getTag()))[0],"").replace(".mp4","");
		Log.e("jiang", "aaaaaaaaaa"+a);
		speak(rd(a));				
	break;
	
	default:
		break;
	}
	
	

	
}

class MyLinkedList extends ArrayList<Contactbean>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
	@Override
	public boolean add(Contactbean contact) {
		hashMap.put(contact.group, hashMap.get(contact.group)==null?1:hashMap.get(contact.group)+1);	
		return super.add(contact);
	}
	@Override
	public void add(int rank, Contactbean contact)
	{		
		hashMap.put(contact.group, hashMap.get(contact.group)==null?1:hashMap.get(contact.group)+1);	
		super.add(rank, contact);
	}	
	@Override
	public boolean remove(Object contact)
	{	
		hashMap.put(((Contactbean) contact).group, hashMap.get(((Contactbean) contact).group)-1);
		return super.remove(contact);
	}	
	public int prethegroup(int group){
		int precount =0;
		for (int i = group-1; i >= 0; i--)
		{
			Log.e("myhome", "for:i:"+i);
			precount +=(hashMap.get(i)==null?0:hashMap.get(i));			
			Log.e("myhome", "count:i:"+precount);
		}
		return precount;		
	}
	public int getRanks(int group) {
		return hashMap.get(group)==null?0:hashMap.get(group);
		}
	
	
	
	
}

public Contactbean getContactbean() {
	return new Contactbean();


}
@Override
public void passMessage(int fun, Bundle bundle) {
	switch (fun) {
	//��
	case 0:
		if (bundle!=null) {
			Contactbean contactbean = new Contactbean();
			contactbean.name = bundle.getString("addName");
			contactbean.number = bundle.getString("addNumber");
			contactbean.group = bundle.getInt("addGroup");
			contacts.add(bundle.getInt("addposition")+contacts.prethegroup(bundle.getInt("addGroup")), contactbean);			
			myadapter.notifyDataSetChanged();
			initData(contacts.toString(),"/sdcard/Test/", "log.txt");
			
		}
		break;
		//ɾ
	case 1:
		break;
		//��
	case 2:
		if (bundle!=null) {
			Log.e("myhome", "717"+bundle.getInt("del"));			
			contacts.remove(contacts.get(bundle.getInt("del")));
			Log.e("myhome", "717"+bundle.getInt("delsuccess"));
			Contactbean contactbean = new Contactbean();
			contactbean.name = bundle.getString("addName");
			contactbean.number = bundle.getString("addNumber");
			contactbean.group = bundle.getInt("addGroup");
			if (bundle.getInt("addposition",1)+contacts.prethegroup(bundle.getInt("addGroup"))==contacts.size()) {
				contacts.add(contactbean);			
				Log.e("myhome", "728");}
			else {				
				contacts.add(bundle.getInt("addposition",1)+contacts.prethegroup(bundle.getInt("addGroup")), contactbean);
				Log.e("myhome", "731");
			}
			
			Log.e("myhome", "724");
			myadapter.notifyDataSetChanged();
			initData(contacts.toString(),"/sdcard/Test/", "log.txt");
			
		}
		
		
		
		
		break;
	case 3:
		break;
	default:
		break;
	}

	
}

public void createfile(String str,String url)throws IOException{ 

	//д���ݵ�SD�е��ļ�  
	
	 try{   
	  
	       FileOutputStream fout = new FileOutputStream(url);   
	       byte [] bytes = str.getBytes();   
	  
	       fout.write(bytes);   
	       fout.close();   
	     }  
	  
	      catch(Exception e){   
	        e.printStackTrace();   
	       }   
	   }





private void initData(String content,String filePath, String fileName) {
    //String filePath = "/sdcard/Test/";
    //String fileName = "log.txt";
    
    writeTxtToFile(content, filePath, fileName);
}
 
// ���ַ���д�뵽�ı��ļ���
public void writeTxtToFile(String strcontent, String filePath, String fileName) {
    //�����ļ���֮���������ļ�����Ȼ�����
    makeFilePath(filePath, fileName);
    
    String strFilePath = filePath+fileName;
    // ÿ��д��ʱ��������д
    String strContent = strcontent + "\r\n";
    try {
        File file = new File(strFilePath);
        if (!file.exists()) {
            Log.d("TestFile", "Create the file:" + strFilePath);
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rwd");
        raf.seek(file.length());
        raf.write(strContent.getBytes());
        raf.close();
    } catch (Exception e) {
        Log.e("TestFile", "Error on write File:" + e);
    }
}
 
// �����ļ�
public File makeFilePath(String filePath, String fileName) {
    File file = null;
    makeRootDirectory(filePath);
    try {
        file = new File(filePath + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return file;
}
 
// �����ļ���
public static void makeRootDirectory(String filePath) {
    File file = null;
    try {
        file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
    } catch (Exception e) {
        Log.i("error:", e+"");
    }
}
/*
public class PowerConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) { 
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                            status == BatteryManager.BATTERY_STATUS_FULL;
        if (initspeak&&status == BatteryManager.BATTERY_STATUS_FULL) {
        	runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					//speak("������");	
					
				}
			});
        	
		}
       
        
        
    
        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
    }
}
*/


public void startAPP(String appPackageName){
    try{
        Intent intent = this.getPackageManager().getLaunchIntentForPackage(appPackageName);
        startActivity(intent);
    }catch(Exception e){
        Toast.makeText(this, "û�а�װ", Toast.LENGTH_LONG).show();
    }



}
@Override
public void appclick(View v) {
	
	switch (v.getId()){
	case R.id.img:
		speak(appList.get((Integer) v.getTag()).appName);
		break;
	case R.id.txt:
		startAPP(appList.get((Integer) v.getTag()).packageName);
		break;
		
		
		
	}

	
}


private void openFile(File file){ 
     
    Intent intent = new Intent(); 
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
    //����intent��Action���� 
    intent.setAction(Intent.ACTION_VIEW); 
    //��ȡ�ļ�file��MIME���� 
    String type = getMIMEType(file); 
    //����intent��data��Type���ԡ� 
    intent.setDataAndType(/*uri*/Uri.fromFile(file), type); 
    //��ת 
    startActivity(intent);
} 


/**
 * �����ļ���׺����ö�Ӧ��MIME���͡�
 * @param file
 */ 
private String getMIMEType(File file) { 
     
    String type="*/*"; 
    String fName = file.getName(); 
    //��ȡ��׺��ǰ�ķָ���"."��fName�е�λ�á� 
    int dotIndex = fName.lastIndexOf("."); 
    if(dotIndex < 0){ 
        return type; 
    } 
    /* ��ȡ�ļ��ĺ�׺��*/ 
    String end=fName.substring(dotIndex,fName.length()).toLowerCase(); 
    if(end=="")return type; 
    //��MIME���ļ����͵�ƥ������ҵ���Ӧ��MIME���͡� 
    for(int i=0;i<MIME_MapTable.length;i++){ //MIME_MapTable??��������һ�������ʣ����MIME_MapTable��ʲô�� 
        if(end.equals(MIME_MapTable[i][0])) 
            type = MIME_MapTable[i][1]; 
    }        
    return type; 
} 
 


private String rd(String str){		
	 String pat="\\d+";
	 Pattern p=Pattern.compile(pat);
	 Matcher m=p.matcher(str);
	 return m.replaceAll("");
	}	

        
}
