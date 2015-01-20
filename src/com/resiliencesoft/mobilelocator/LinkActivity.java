package com.resiliencesoft.mobilelocator;

import com.resiliencesoft.mobilelocator.R;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class LinkActivity extends ActionBarActivity implements OnClickListener {
 Button search,feedback,help;
 ImageView imageview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); //Remove title bar and logo
		setContentView(R.layout.activity_link);
		
		search=(Button)findViewById(R.id.buttonSearch);
		search.setOnClickListener(this);
		feedback=(Button)findViewById(R.id.button2);
		feedback.setOnClickListener(this);
		help=(Button)findViewById(R.id.button3);
		help.setOnClickListener(this);
		imageview=(ImageView)findViewById(R.id.imageView1);
		imageview.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.link, menu);
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
		try 
		{
			switch(v.getId())
			{ 
			case R.id.buttonSearch:
				Intent intent=new Intent(LinkActivity.this, MobileLocator.class);
				startActivity(intent);
				finish();
			 break;
			 
			 case R.id.button2:
				 Intent intent1=new Intent(LinkActivity.this, FeedbackActivity.class);
				 startActivity(intent1);
				 finish();
				break;
			 case R.id.button3:
				startActivity(new Intent(LinkActivity.this, HelpActivity.class));
				 finish();
			  break;
			 
			}
			
		 } 
		catch (Exception e) {
			// TODO: handle exception
		}
	}
}
