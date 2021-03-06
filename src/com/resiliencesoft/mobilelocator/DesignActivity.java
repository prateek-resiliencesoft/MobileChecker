package com.resiliencesoft.mobilelocator;



import com.resiliencesoft.mobilelocator.R;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;


public class DesignActivity extends ActionBarActivity implements OnClickListener {
ImageView imageviewdesign;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); //Remove title bar and logo
		
		setContentView(R.layout.activity_design);   
		imageviewdesign=(ImageView)findViewById(R.id.imageView1Design);//find imageview
		imageviewdesign.setOnClickListener(this);//event on the imageview
		try 
		{   
			// in first request it will display this activity for some time and at the back time it will not be displayed 
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Intent intent=new Intent(DesignActivity.this, LinkActivity.class);
					DesignActivity.this.startActivity(intent);
					DesignActivity.this.finish(); //finish this activity in second request onwards
				}
			}, 3000);//wait for 3 seconds
			
		} 
		catch (Exception e)
		{
			// TODO: handle exception
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.design, menu);
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
		startActivity(new Intent(DesignActivity.this, LinkActivity.class));
		finish();
	}
}
