package com.fernandomantoan.ramaispti.android.fragment;

import java.text.Normalizer;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.fernandomantoan.ramaispti.android.R;
import com.fernandomantoan.ramaispti.android.entity.LegalEntity;
import com.fernandomantoan.ramaispti.android.service.UsefulTelephonesServices;
import com.fernandomantoan.ramaispti.android.util.Network;

public class UsefulSearchFragment extends Fragment {

	private String option;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		option = getArguments().getString("op");

		if (!Network.isNetworkAvailable(getActivity().getApplicationContext())) {
			Toast.makeText(getActivity(), R.string.internet_required, Toast.LENGTH_LONG).show();
			return;
		}

		setHasOptionsMenu(true);

		option = this.normalizar(option);
		new UsefulTelephonesServices(UsefulSearchFragment.this,option).execute();

	}


	public void requestEnd(ArrayList<LegalEntity> entities) {

		if (entities != null && entities.size() != 0){

			Bundle bundle = new Bundle();
			bundle.putParcelableArrayList("array", entities);

			TelephonesFragment telephones = new TelephonesFragment();
			telephones.setArguments(bundle);
			FragmentTransaction ft = getFragmentManager().beginTransaction();

			ft.replace(R.id.content_frame, telephones);

			ft.commit();

		}else {
			Toast.makeText(getActivity(), R.string.no_results, Toast.LENGTH_LONG).show();
		}
	}

	
	@SuppressLint("NewApi")
	public String normalizar (String s){
		String str;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			/* Use Normalizer normally */
			str = Normalizer.normalize(s, Normalizer.Form.NFD);
			str = str.replaceAll("[^\\p{ASCII}]", "");
			str = str.replaceAll(" ", "%20");
			return str.toLowerCase();
		} else {
			
			str = s;
			str = str.replaceAll("[çÇ]+", "c");
			str = str.replaceAll("[ãÃáÁ]+", "a");
			str = str.replaceAll("[éÉ]+", "e");
			str = str.replaceAll("[íÍ]+", "i");
			str = str.replaceAll("[õÕóÓ]+", "o");
			str = str.replaceAll("[úÚ]+", "u");
						
			str = str.replaceAll("[^\\p{ASCII}]", "");
			str = str.replaceAll(" ", "%20");
			Log.d("Teste", str);
			return str.toLowerCase();
		} 

	}

	


}
