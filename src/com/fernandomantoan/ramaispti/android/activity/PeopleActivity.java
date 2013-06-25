package com.fernandomantoan.ramaispti.android.activity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.fernandomantoan.ramaispti.android.R;
import com.fernandomantoan.ramaispti.android.adapter.PersonAdapter;
import com.fernandomantoan.ramaispti.android.entity.Person;

/**
 * Lists the people found in the {@link SearchActivity}
 * 
 * @author fernando
 */
public class PeopleActivity extends SherlockActivity {
	// ---------------------------------------------------------------------------------------------
    // Attributes
    // ---------------------------------------------------------------------------------------------
	private PersonAdapter adapter;
	private ListView list;
	// ---------------------------------------------------------------------------------------------
    // Main Activity Overrides
    // ---------------------------------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.people);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle bundle = (Bundle) getIntent().getBundleExtra("array");

		List<Person> people = bundle.getParcelableArrayList("array");
		
		// Sort by name
		Collections.sort(people, new Comparator<Person>() {
			@Override
			public int compare(Person onePerson, Person anotherPerson) {
				return onePerson.getName().compareTo(anotherPerson.getName());
			}

		});
		adapter = new PersonAdapter(this, R.layout.person_item, people);
		this.list = (ListView) findViewById(R.id.people);
		this.list.setAdapter(adapter);
		this.list.setOnItemClickListener(itemClickListener);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// ---------------------------------------------------------------------------------------------
	// Handlers
	// ---------------------------------------------------------------------------------------------
	/**
	 * When one person is selected, show the Dialer
	 */
	final OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long arg3) {
			Person person = (Person) adapter.getItemAtPosition(position);
			
			if (person.getTelephone() == null || "".equals(person.getTelephone()))
				return;
			Uri number = Uri.parse("tel:" + person.getDiableTelephone());
			Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
			startActivity(callIntent);
		}
	};
}