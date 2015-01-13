package com.example.mobilelocator;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;


import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MobileLocator extends ActionBarActivity implements OnClickListener {
TextView tvarrow,tvmessage;
Button button;
EditText editsearch;
HttpPost httppost;
String SearchNumber,location,signal,operator,msg;
Boolean internetactive;
HttpClient httpclient;
ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_locator);
        Search();
        editsearch=(EditText)findViewById(R.id.editText1);
        tvmessage=(TextView)findViewById(R.id.textView3);
       tvarrow=(TextView)findViewById(R.id.textView1);	   
        tvarrow.setOnClickListener(this);
    }
    
    public void Search()
    {
    	button=(Button)findViewById(R.id.buttonSubmit);
    	button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tvmessage.setText("");
				SearchNumber = editsearch.getText().toString();
				httppost = new HttpPost(HttpUrls.HttpMobileSearch+SearchNumber);
				
				if (SearchNumber.equals(""))					
				{
				 Toast.makeText(MobileLocator.this, "Fields are empty",Toast.LENGTH_SHORT).show();
				 return;
				} 
				else if (SearchNumber.length() < 10) 
				{
				Toast.makeText(MobileLocator.this,"Password Size is less than 10",Toast.LENGTH_SHORT).show();
				} 
				else
				{ 
				 internetactive = isNetworkAvailable();
				}
				if (internetactive) {
					
				new getresult().execute();
				} else 
				{
									
				Toast.makeText(MobileLocator.this,"Internet Not Connected",Toast.LENGTH_SHORT).show();
				}				
				
				
				
			}
		});
    	
    }


   protected Boolean isNetworkAvailable() {
		// TODO Auto-generated method stub
    	ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null&& activeNetworkInfo.isConnectedOrConnecting();
	}
    
    public class getresult extends AsyncTask<Void, Void, Void>
    {
      String result=null;
  
      @Override 
      protected void onPreExecute() { 
           dialog = new ProgressDialog(MobileLocator.this); 
           dialog.setMessage("Searching...."); 
           dialog.setIndeterminate(true); 
           dialog.setCancelable(true); 
           dialog.show(); //runuithread me call ker sakte hai
           
        } 
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			
			try {
				HttpParams params = new BasicHttpParams();
				
				params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,HttpVersion.HTTP_1_1);
				httpclient = new DefaultHttpClient(params);
				
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);	
				
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
				result = httpclient.execute(httppost, new BasicResponseHandler());
			} 
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;			
		}
    	
		 @Override
		 protected void onPostExecute(Void result1)
		 {
			 super.onPostExecute(result1);
				int duration = Toast.LENGTH_SHORT;	
//				tvmessage.setText(result);
				String[] parts = result.split(",");
//				String[] find = parts[0].split(":"); 
				location=parts[0];
//		    String[] sig=parts[1].split(":");
			    signal=parts[1];
//			    String[] opr=parts[2].split(":");
			    operator=parts[2].replace(parts[2].substring(parts[2].indexOf("</a")), "");
			    msg=location+"\n"+" "+signal+"\n"+" "+operator;
			    tvmessage.setText(msg);
			    //here we can write code for map in webview
			    dialog.dismiss();
		 }
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mobile_locator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		startActivity(new Intent(MobileLocator.this,LinkActivity.class));
		finish();
	}
}
