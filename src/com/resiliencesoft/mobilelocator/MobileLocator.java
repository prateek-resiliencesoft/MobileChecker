package com.resiliencesoft.mobilelocator;

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

import com.resiliencesoft.mobilelocator.R;


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
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
ImageView image;
WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);  //Remove title bar and logo
        setContentView(R.layout.activity_mobile_locator);       
        Search();                                       //method call
        editsearch=(EditText)findViewById(R.id.editText1);//find edittext
        tvmessage=(TextView)findViewById(R.id.textView3);//find textview
        image=(ImageView)findViewById(R.id.imageViewmobile);//find image
        image.setOnClickListener(this);//it will auto generate method in the class activity
       
        
    }
    
    public void Search()
    {
     	button=(Button)findViewById(R.id.buttonSearch);//find button
     	//click event on the button
    	button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tvmessage.setText("");
				SearchNumber = editsearch.getText().toString();//fetch data from edittext and store it in string
				httppost = new HttpPost(HttpUrls.HttpMobileSearch+ SearchNumber);//add edittext data with Url
				
				if (SearchNumber.equals(""))					
				{
				 Toast.makeText(MobileLocator.this, "Fields are empty",Toast.LENGTH_SHORT).show();	
				 return;
				} 
				 else if (SearchNumber.length() < 10 ||SearchNumber.length()>10 ) //check weather data is less or greater 
				{
				Toast.makeText(MobileLocator.this,"Please Enter 10 digit Mobile number",Toast.LENGTH_SHORT).show();
				return;
				} 
				 else
				{ 
				 internetactive = isNetworkAvailable();//check network
				}
				if (internetactive) 
				{					
				 new getresult().execute();
				}
				else 
			     {
				    Toast.makeText(MobileLocator.this,"Internet Not Connected",Toast.LENGTH_SHORT).show();
			    	}				
				
				
				
			}
		});
    	
    }

//this method check the the network
   protected Boolean isNetworkAvailable() {
		// TODO Auto-generated method stub
    	ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null&& activeNetworkInfo.isConnectedOrConnecting();
	}
    
    public class getresult extends AsyncTask<Void, Void, Void>
    {
      String result=null;
  // for progressdialog we have to use this method
      @Override 
      protected void onPreExecute() { 
           dialog = new ProgressDialog(MobileLocator.this); 
           dialog.setMessage("Searching...."); //Instead of searching you can write loading also or else
           dialog.setIndeterminate(true); 
           dialog.setCancelable(true); 
           dialog.show(); //runuithread can be call
           
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
				result = httpclient.execute(httppost, new BasicResponseHandler());//response message from server
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

	 //If we have to split the string so use this technique
			String[] parts = result.split(",");

				location=parts[0];  
			    signal=parts[1];
			    operator=parts[2].replace(parts[2].substring(parts[2].indexOf("</a")),"");
			    msg= location+"\n"+signal+"\n"+operator;
			    tvmessage.setText(msg);
			    //here we can write code for map in webview or mapview
			    dialog.dismiss();// it will destroy the dialog
			    editsearch.setText("");// it will clean the edittext when the response will arrived
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
		startActivity(new Intent(MobileLocator.this,LinkActivity.class));//it will move from this activity to LinkActivity
		finish();
	}
}
