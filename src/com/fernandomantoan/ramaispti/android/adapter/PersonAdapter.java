package com.fernandomantoan.ramaispti.android.adapter;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fernandomantoan.ramaispti.android.R;
import com.fernandomantoan.ramaispti.android.entity.Person;
import com.fernandomantoan.ramaispti.android.util.WordUtil;

public class PersonAdapter extends BaseAdapter {
	private Activity activity;
	private List<Person> people;
	private int resourceViewId;

	public PersonAdapter(Activity activity, int resourceViewId,
			List<Person> people) {
		this.activity = activity;
		this.resourceViewId = resourceViewId;
		this.people = people;
	}

	@Override
	public int getCount() {
		return people.size();
	}

	@Override
	public Object getItem(int i) {
		return people.get(i);
	}

	@Override
	public long getItemId(int i) {
		return people.get(i).hashCode();
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		return layoutInflater(position, viewGroup);
	}

	private View layoutInflater(int position, ViewGroup parent) {

		Person person = people.get(position);

		LayoutInflater inflater = activity.getLayoutInflater();
		View view = inflater.inflate(resourceViewId, parent, false);

		TextView nameTV = (TextView) view.findViewById(R.id.name);
		
		// Se não suportar textAllCaps, deixa tudo maiúsculo manualmente
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH)
			nameTV.setText(person.getName().toUpperCase());
		else
			nameTV.setText(person.getName());

		TextView companyTV = (TextView) view.findViewById(R.id.company);
		companyTV.setText(person.getCompany().getName());
		
		TextView telephoneTV = (TextView) view.findViewById(R.id.telephone);
		String aux = person.getTelephone().replace("/", "\n");
		telephoneTV.setText(aux);
				
		TextView roleTV = (TextView) view.findViewById(R.id.role);
		if (person.getRole().getName() == null || "".equals(person.getRole().getName()))
			roleTV.setVisibility(View.GONE);
		else
			roleTV.setText(WordUtil.capitalizeString(person.getRole().getName()));
		
		if (person.getTelephone() == null || "".equals(person.getTelephone())) {
			view.setEnabled(false);
			view.setClickable(false);
		}

		return view;
	}
}