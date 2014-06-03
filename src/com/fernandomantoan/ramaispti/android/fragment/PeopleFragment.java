package com.fernandomantoan.ramaispti.android.fragment;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.fernandomantoan.ramaispti.android.R;
import com.fernandomantoan.ramaispti.android.adapter.PersonAdapter;
import com.fernandomantoan.ramaispti.android.entity.Person;



/**
 * Lists the people found in the {@link SearchFragment}
 * 
 * @author fernando
 */
public class PeopleFragment extends Fragment {
	// ---------------------------------------------------------------------------------------------
	// Attributes
	// ---------------------------------------------------------------------------------------------
	private PersonAdapter adapter;
	private ListView list;
	// ---------------------------------------------------------------------------------------------
	// Main Activity Overrides
	// ---------------------------------------------------------------------------------------------
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.people, container, false);
		List<Person> people = getArguments().getParcelableArrayList("array");////ver se é null
		
		// Sort by name
		Collections.sort(people, new Comparator<Person>() {
			@Override
			public int compare(Person onePerson, Person anotherPerson) {
				return onePerson.getName().compareTo(anotherPerson.getName());
			}

		});


		adapter = new PersonAdapter(getActivity(), R.layout.person_item, people);
		this.list = (ListView) view.findViewById(R.id.people);
		this.list.setAdapter(adapter);
		this.list.setOnItemClickListener(itemClickListener);

		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(getActivity());
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

			final String [] arrayTel = person.getTelephone().split("/");

			if(arrayTel.length > 1){

    			NumberChoiceDialogFragment search = NumberChoiceDialogFragment.newInstance(arrayTel);
    			search.show(getFragmentManager(), "searchFragment");

			}else{

				Uri number = Uri.parse("tel:" + person.getDiableTelephone());
				Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
				startActivity(callIntent);
			}

		}
	};
}