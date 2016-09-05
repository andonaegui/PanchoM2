package de.derandroidpro.navdrawer_tutorial;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
//import com.loopj.android.http.*;


public class MainActivity extends Activity {
	
	
	public Fragment1_class fragment1;
	public Fragment2_class fragment2;
	public Fragment3_class fragment3;
	public FragmentManager fragmanager;
	public FragmentTransaction fragtrans;
	

	public DrawerLayout drawerlayout;
	public ActionBarDrawerToggle drawertoggle;
	
	public ListView drawerlist;
//	public String[] listentxt = {"Angular 1","Angular 2","Angular 3"};
    private ActionBar actionbar;
    private ArrayAdapter<String> drawerlistadapter;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		startComponents();
		startEvent();
	}

    private void getDrawerOptions() {
        //

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://api.androidhive.info/contacts/", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONArray options = null;
                try {
                        String response = new String(responseBody, "UTF-8");
                        JSONObject opt_obj = new JSONObject(response);
                        options = opt_obj.getJSONArray("contacts");
                        setDrawerOptions(options);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                try {
                    String response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {

                }
            }
        });
    }

    private void setDrawerOptions(JSONArray options) throws JSONException {
        ArrayList<String> items = new ArrayList<String>();
        for (int i=0;i<options.length();i++) {
            JSONObject item = (JSONObject) options.get(i);
            items .add(item.getString("name"));
        }
        drawerlistadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        drawerlist.setAdapter(drawerlistadapter);
    }

    private void startComponents() {
        drawerlayout = (DrawerLayout) findViewById(R.id.ganzesLayout);

        fragment1 = (Fragment1_class) Fragment.instantiate(this, Fragment1_class.class.getName(), null);
        fragment2 = (Fragment2_class) Fragment.instantiate(this, Fragment2_class.class.getName(), null);
        fragment3 = (Fragment3_class) Fragment.instantiate(this, Fragment3_class.class.getName(), null);
        drawerlist = (ListView) findViewById(R.id.drawerliste);
    }

	private void startEvent() {


        drawertoggle = new ActionBarDrawerToggle(MainActivity.this, drawerlayout, R.drawable.ic_drawer, R.string.open, R.string.close);
        drawerlayout.setDrawerListener(drawertoggle);

        actionbar = getActionBar();
        assert actionbar != null;
        actionbar.setHomeButtonEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);

        fragmanager = getFragmentManager();
        fragtrans = fragmanager.beginTransaction();
        fragtrans.add(R.id.HauptLayout, fragment1);
        fragtrans.commit();

        setNavigationListeners();
        getDrawerOptions();
    }

    private void setNavigationListeners() {

        drawerlist.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2,
                                    long arg3) {

                switch (drawerlist.getPositionForView(view)) {
                    case 0: {

                        fragmanager = getFragmentManager();
                        fragtrans = fragmanager.beginTransaction();
                        fragtrans.replace(R.id.HauptLayout, fragment1);
                        fragtrans.commit();



                        Toast.makeText(getApplicationContext(), "Angular 1", Toast.LENGTH_SHORT).show();
                        drawerlayout.closeDrawers();
                        break;
                    }


                    case 1: {
                        fragmanager = getFragmentManager();
                        fragtrans = fragmanager.beginTransaction();
                        fragtrans.replace(R.id.HauptLayout, fragment2);
                        fragtrans.commit();

                        Toast.makeText(getApplicationContext(), "Angular 2", Toast.LENGTH_SHORT).show();
                        drawerlayout.closeDrawers();
                        break;
                    }



                    case 2: {
                        fragmanager = getFragmentManager();
                        fragtrans = fragmanager.beginTransaction();
                        fragtrans.replace(R.id.HauptLayout, fragment3);
                        fragtrans.commit();
                        Toast.makeText(getApplicationContext(), "Angular 3", Toast.LENGTH_SHORT).show();
                        drawerlayout.closeDrawers();
                        break;
                    }

                }



            }
        });
    }


    @Override
	public void onConfigurationChanged(Configuration newConfig) {
		drawertoggle.onConfigurationChanged(newConfig);
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		drawertoggle.syncState();
		super.onPostCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		if (drawertoggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
