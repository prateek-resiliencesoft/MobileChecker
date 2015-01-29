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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;

import com.resiliencesoft.mobilelocator.R;


import android.support.v7.app.ActionBarActivity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends ActionBarActivity implements OnClickListener {
	TextView tvarrow1;
	EditText edname,edemail,edmessage,edcontact;
	String email,message,firstname;
	 HttpClient httpclient;
	 HttpPost httppost;
	 boolean internetactive;
	 Button feedback;
	 ImageView imageview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//Remove title bar and logo
		setContentView(R.layout.activity_feedback);
		AddFeedback();
		imageview=(ImageView)findViewById(R.id.imageView2);//find image
		imageview.setOnClickListener(this);
		edname=(EditText)findViewById(R.id.editText1);//find edittext
        edemail=(EditText)findViewById(R.id.editText2);//find edittext
        edmessage=(EditText)findViewById(R.id.editText3);	//find edittext	
	}
	
	public void AddFeedback()
	{
		feedback=(Button)findViewById(R.id.buttonSubmit);
		httppost=new HttpPost(HttpUrls.HttpFeedback);
		feedback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				firstname=edname.getText().toString();
				email=edemail.getText().toString();
				message=edmessage.getText().toString();
				if (firstname.equals("")||message.equals(""))					
				 {
				 Toast.makeText(FeedbackActivity.this, "Fields are empty",Toast.LENGTH_SHORT).show();
				 return;
				 } 				
			   else
				{ 
				 internetactive = isNetworkAvailable();
				}
				if (internetactive) {
					
				new getresult().execute();
				} else 
				{
									
				Toast.makeText(FeedbackActivity.this,"Internet Not Connected",Toast.LENGTH_SHORT).show();
				}								
				
			}
		});
	}

	protected boolean isNetworkAvailable() {
		// TODO Auto-generated method stub
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null&& activeNetworkInfo.isConnectedOrConnecting();
	}

	public class getresult extends AsyncTask<Void, Void, Void>
	{
		String result=null;

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
                  HttpParams params = new BasicHttpParams();				
				  params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,HttpVersion.HTTP_1_1);
				  httpclient = new DefaultHttpClient(params);				
				  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);	
				  nameValuePairs.add(new BasicNameValuePair("firstname", firstname));
				  nameValuePairs.add(new BasicNameValuePair("email", email));
				  nameValuePairs.add(new BasicNameValuePair("message", message));
				  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
				  result = httpclient.execute(httppost, new BasicResponseHandler());

			} 
			catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result1)
		{ 
			super.onPostExecute(result1);
//		   int duration = Toast.LENGTH_SHORT;
		   try {
			   Toast.makeText(FeedbackActivity.this,"Thanks For Your Feedback",Toast.LENGTH_SHORT).show();
			   edname.setText("");
			   edemail.setText("");
			   edmessage.setText("");
		} 
		   catch (Exception e) {
			// TODO: handle exception
		}
			
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feedback, menu);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(FeedbackActivity.this,LinkActivity.class);
		startActivity(intent);
		finish();
	}


	
}
