package com.fernandomantoan.ramaispti.android.activity;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.fernandomantoan.ramaispti.android.R;
import com.fernandomantoan.ramaispti.android.entity.Person;
import com.fernandomantoan.ramaispti.android.service.TelephonesServices;
import com.fernandomantoan.ramaispti.android.util.Network;

/**
 * Search Activity, handles the search form
 * 
 * @author fernando
 */
public class SearchActivity extends SherlockActivity {
	// ---------------------------------------------------------------------------------------------
    // Attributes
    // ---------------------------------------------------------------------------------------------
    private EditText nameEditText;
    private EditText companyEditText;
    private EditText roleEditText;
    
    private ProgressDialog progressDialog;
    // ---------------------------------------------------------------------------------------------
    // Main Activity Overrides
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_form);
        this.initUIItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.search, menu);
        menu.findItem(R.id.action_search).setOnMenuItemClickListener(menuSearchClick);
        return true;
    }
	// ---------------------------------------------------------------------------------------------
	// Handlers
	// ---------------------------------------------------------------------------------------------
    /**
     * Called when actionbar menu is pressed
     */
	final OnMenuItemClickListener menuSearchClick = new OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			return doSearch();
		}
	};
	/**
	 * Called when the webservice request ends, if there are results show them, if not show a message
	 * 
	 * @param people
	 */
	public void requestEnd(ArrayList<Person> people) {
    	hideLoading();
    	if (people == null || people.size() == 0)
    		Toast.makeText(this, R.string.no_results, Toast.LENGTH_LONG).show();
    	else {
    		Bundle bundle = new Bundle();
    		bundle.putParcelableArrayList("array", people);
    		
    		Intent intent = new Intent(SearchActivity.this, PeopleActivity.class);
    		intent.putExtra("array", bundle);
    		
    		startActivity(intent);
    	}
    }
	/**
	 * imeOptions listener, handle the "actionSearch" type
	 */
	final OnEditorActionListener searchActionListener = new OnEditorActionListener() {
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_SEARCH) {
				doSearch();
			}
			return true;
		}
	};
    // ---------------------------------------------------------------------------------------------
    // UI
    // ---------------------------------------------------------------------------------------------
	/**
	 * Initialize the EditTexts and set listeners for them
	 */
    protected void initUIItems() {
        this.nameEditText = (EditText) findViewById(R.id.name);
        this.companyEditText = (EditText) findViewById(R.id.company);
        this.roleEditText = (EditText) findViewById(R.id.role);
        
        this.nameEditText.setOnEditorActionListener(searchActionListener);
        this.companyEditText.setOnEditorActionListener(searchActionListener);
        this.roleEditText.setOnEditorActionListener(searchActionListener);
    }
    /**
     * Show the ProgressDialog while the request is running
     */
    public void showLoading() {
    	progressDialog = new ProgressDialog(SearchActivity.this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setTitle(R.string.loading);
		progressDialog.setMessage(getString(R.string.searching));
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();
    }
    /**
     * Hide the ProgressDialog when the request is over
     */
    public void hideLoading() {
    	try {
			progressDialog.dismiss();
		} catch (Exception e) {}
    }
    // ---------------------------------------------------------------------------------------------
    // Business Logic
    // ---------------------------------------------------------------------------------------------
    /**
     * Do the search in the webservice, after veryfing if {@link #validate()} returned true.
     * @return
     */
    protected boolean doSearch() {
    	if (validate()) {
			if (!Network.isNetworkAvailable(getApplicationContext())) {
				Toast.makeText(SearchActivity.this, R.string.internet_required, Toast.LENGTH_LONG).show();
				return false;
			}
			new TelephonesServices(SearchActivity.this, nameEditText.getText().toString(), companyEditText.getText().toString(), roleEditText.getText().toString()).execute();
			return true;
		} else {
			Toast.makeText(SearchActivity.this, R.string.required, Toast.LENGTH_SHORT).show();
			return false;
		}
    }
    // ---------------------------------------------------------------------------------------------
    // Validation
    // ---------------------------------------------------------------------------------------------
    /**
     * Verify if the required fields are all filled
     * @return
     */
    public boolean validate() {
    	if (this.nameEditText.getText().toString() == null || "".equals(this.nameEditText.getText().toString().trim())) {
    		return false;
    	}
    	return true;
    }
}