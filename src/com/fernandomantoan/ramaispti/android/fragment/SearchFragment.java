package com.fernandomantoan.ramaispti.android.fragment;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.fernandomantoan.ramaispti.android.R;
import com.fernandomantoan.ramaispti.android.entity.Person;
import com.fernandomantoan.ramaispti.android.service.TelephonesServices;
import com.fernandomantoan.ramaispti.android.util.Network;


/**
 * Search Activity, handles the search form
 * 
 * @author fernando
 */
public class SearchFragment extends Fragment {
	// ---------------------------------------------------------------------------------------------
    // Attributes
    // ---------------------------------------------------------------------------------------------
    private EditText nameEditText;
    private EditText companyEditText;
    private EditText roleEditText;
    
    private ProgressDialog progressDialog;
    
    //private static final int CONTENT_VIEW_ID = 666;
    
    // ---------------------------------------------------------------------------------------------
    // Main Activity Overrides
    // ---------------------------------------------------------------------------------------------
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	
    	setHasOptionsMenu(true);
    }
    
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.search_form, container, false);
		initUIItems(view);
		return view;
    }
 
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	// TODO Auto-generated method stub
    	inflater = new MenuInflater(getActivity().getApplicationContext());
    	inflater.inflate(R.menu.search, menu);
    	super.onCreateOptionsMenu(menu, inflater);
    	
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    	case R.id.action_search:
    		return doSearch();
   		default:   	
   			return super.onOptionsItemSelected(item);
    	}
    }
       
    
	/**
	 * Called when the webservice request ends, if there are results show them, if not show a message
	 * 
	 * @param people
	 */
	public void requestEnd(ArrayList<Person> people) {
    	hideLoading();
    	if (people == null || people.size() == 0)
    		Toast.makeText(getActivity(), R.string.no_results, Toast.LENGTH_LONG).show();
    	else {
    		Bundle bundle = new Bundle();
    		bundle.putParcelableArrayList("array", people);
    	
    		PeopleFragment peopleA = new PeopleFragment();
    		peopleA.setArguments(bundle);
    		
    		FragmentTransaction ft = getFragmentManager().beginTransaction();
    		    		
    		ft.replace(R.id.content_frame, peopleA);
    		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);	
    		ft.addToBackStack(null);
    		ft.commit();
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
    protected void initUIItems(View view) {
        this.nameEditText = (EditText) view.findViewById(R.id.name);
        this.companyEditText = (EditText) view.findViewById(R.id.company);
        this.roleEditText = (EditText) view.findViewById(R.id.role);
        
        this.nameEditText.setOnEditorActionListener(searchActionListener);
        this.companyEditText.setOnEditorActionListener(searchActionListener);
        this.roleEditText.setOnEditorActionListener(searchActionListener);
    }
    /**
     * Show the ProgressDialog while the request is running
     */
    public void showLoading() {
    	progressDialog = new ProgressDialog(getActivity());
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
			if (!Network.isNetworkAvailable(getActivity().getApplicationContext())) {
				Toast.makeText(getActivity(), R.string.internet_required, Toast.LENGTH_LONG).show();
				return false;
			}
			new TelephonesServices(SearchFragment.this, nameEditText.getText().toString(), companyEditText.getText().toString(), roleEditText.getText().toString()).execute();
			return true;
		} else {
			Toast.makeText(getActivity(), R.string.required, Toast.LENGTH_SHORT).show();
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