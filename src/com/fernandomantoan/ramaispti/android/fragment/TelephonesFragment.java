package com.fernandomantoan.ramaispti.android.fragment;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.fernandomantoan.ramaispti.android.R;
import com.fernandomantoan.ramaispti.android.adapter.TelephoneAdapter;
import com.fernandomantoan.ramaispti.android.entity.LegalEntity;


/**
 * Fragment that appears in the "content_frame", shows the telephone list
 * @author inovatic
 *
 */
public class TelephonesFragment extends Fragment /*implements SearchView.OnQueryTextListener*/{
	
	private TelephoneAdapter adapter;
	private ListView list;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View view = inflater.inflate(R.layout.telephone_list, container, false);
		
		List<LegalEntity> entities = getArguments().getParcelableArrayList("array");//lista de telefones úteis
		
		Collections.sort(entities, new Comparator<LegalEntity>() {
			@Override
			public int compare(LegalEntity oneEntity, LegalEntity anotherEntity) {
				return oneEntity.getEntityName().compareTo(anotherEntity.getEntityName());
			}

		});
		
		adapter = new TelephoneAdapter(getActivity(), R.layout.telephone_item, entities);
		this.list = (ListView) view.findViewById(R.id.telephones_list);
		this.list.setAdapter(adapter);
		this.list.setOnItemClickListener(itemClickListener);
		
		setHasOptionsMenu(true);
		
		return view;
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
			LegalEntity entity = (LegalEntity) adapter.getItemAtPosition(position);
			
			if (entity.getTelephone() == null || "".equals(entity.getTelephone()))
				return;
			
			final String [] arrayTel = entity.getTelephone().split("/");

			if(arrayTel.length > 1){

    			NumberChoiceDialogFragment search = NumberChoiceDialogFragment.newInstance(arrayTel);
    			search.show(getFragmentManager(), "searchFragment");

			}else{

				Uri number = Uri.parse("tel:" + entity.getDiableTelephone());
				Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
				startActivity(callIntent);
			}
						
		}
	};
	
	
	

}
