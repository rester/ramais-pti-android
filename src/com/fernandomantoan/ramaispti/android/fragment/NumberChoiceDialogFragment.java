package com.fernandomantoan.ramaispti.android.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class NumberChoiceDialogFragment extends DialogFragment{
	
	static final String TELEFONES  = "telefones";
	
	static NumberChoiceDialogFragment newInstance(String [] arrayTel) {
		NumberChoiceDialogFragment f = new NumberChoiceDialogFragment();

	    Bundle args = new Bundle();
	    args.putStringArray(TELEFONES, arrayTel);
	    f.setArguments(args);

	    return f;
	}
	
	 @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			        
	        builder
			.setTitle("Escolha para qual número deseja ligar")
			.setItems(getArguments().getStringArray(TELEFONES), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// The 'which' argument contains the index position
					// of the selected item
					Uri number = Uri.parse("tel:" + getArguments().getStringArray(TELEFONES)[which]);
					Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
					startActivity(callIntent); 

				}
			});

	        // Create the AlertDialog object and return it
	        return builder.create();
	    }

}
