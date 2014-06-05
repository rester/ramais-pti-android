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
import com.fernandomantoan.ramaispti.android.entity.LegalEntity;
import com.fernandomantoan.ramaispti.android.util.WordUtil;

public class TelephoneAdapter extends BaseAdapter{
	
	
	private Activity activity;
	private List<LegalEntity> entity;
	private int resourceViewId;

	public TelephoneAdapter(Activity activity, int resourceViewId,
			List<LegalEntity> entities) {
		this.activity = activity;
		this.resourceViewId = resourceViewId;
		this.entity = entities;
	}

	@Override
	public int getCount() {
		return entity.size();
	}

	@Override
	public Object getItem(int i) {
		return entity.get(i);
	}

	@Override
	public long getItemId(int i) {
		return entity.get(i).hashCode();
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		return layoutInflater(position, viewGroup);
	}

	private View layoutInflater(int position, ViewGroup parent) {

		LegalEntity en = entity.get(position);

		LayoutInflater inflater = activity.getLayoutInflater();
		View view = inflater.inflate(resourceViewId, parent, false);

		TextView nameTV = (TextView) view.findViewById(R.id.name);
		
		// Se não suportar textAllCaps, deixa tudo maiúsculo manualmente
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH)
			nameTV.setText(en.getEntityName().toUpperCase());
		else
			nameTV.setText(en.getEntityName());

		TextView telephoneTV = (TextView) view.findViewById(R.id.telephone);
		
		String aux = en.getTelephone().replace("/", "\n");
		telephoneTV.setText(aux);
		
		TextView roleTV = (TextView) view.findViewById(R.id.role);
		if (en.getRole().getName() == null || "".equals(en.getRole().getName()))
			roleTV.setVisibility(View.GONE);
		else
			roleTV.setText(WordUtil.capitalizeString(en.getRole().getName()));
		
		if (en.getTelephone() == null || "".equals(en.getTelephone())) {
			view.setEnabled(false);
			view.setClickable(false);
		}

		return view;
	}


}
