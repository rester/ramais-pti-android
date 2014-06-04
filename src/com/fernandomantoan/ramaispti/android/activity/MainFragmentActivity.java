package com.fernandomantoan.ramaispti.android.activity;


import android.content.res.Configuration;
import android.os.Bundle;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fernandomantoan.ramaispti.android.R;
import com.fernandomantoan.ramaispti.android.fragment.SearchFragment;
import com.fernandomantoan.ramaispti.android.fragment.UsefulSearchFragment;


/**
 * 
 * Main Activity, handles drawer action
 * @author inovatic
 *
 */
public class MainFragmentActivity extends ActionBarActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private ActionBar actionBar;
	

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mServiceTitles;
	int selectedPosition = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTitle = mDrawerTitle = getTitle();
		mServiceTitles = getResources().getStringArray(R.array.services_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		
		//configurações da lista lateral
		
		// set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mServiceTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        // enable ActionBar app icon to behave as action to toggle nav drawer
      
    	actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	     
        
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
            	
        		actionBar.setTitle(mTitle);         	
        		supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                actionBar.setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu();
                
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }
 

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    
    }
	

	@Override
	public void onBackPressed() {

		if (selectedPosition != 0) {
	        selectItem(0);
	    } else {
	        super.onBackPressed();
	    }
	}	
	
	  /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    

	private void selectItem(int position){
		//update the main content by replacing fragments
			
		if(mServiceTitles[position].equals("Ramais")){
			
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
									
			ft.replace(R.id.content_frame, new SearchFragment()).commit();	
			
			mDrawerList.setItemChecked(position, true);
			setTitle(mServiceTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
			
		} else {
			Fragment fragment = new UsefulSearchFragment();
			Bundle args = new Bundle();
			args.putString("op", mServiceTitles[position]);
			fragment.setArguments(args);
			
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.content_frame, fragment).commit();
			
			//update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			setTitle(mServiceTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		}
		
		selectedPosition = position;
	}
	
	
	//update ActionBar title
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		actionBar.setTitle(mTitle);
	}
	
	
	  /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}
