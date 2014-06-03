package com.fernandomantoan.ramaispti.android.service;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.fernandomantoan.ramaispti.android.entity.LegalEntity;
import com.fernandomantoan.ramaispti.android.fragment.UsefulSearchFragment;
import com.fernandomantoan.ramaispti.android.support.WebClient;

public class UsefulTelephonesServices extends AsyncTask<String, Object, String>{

	private final static String ENDPOINT = "http://pdi.pti.org.br/habitantes/telefones?util=sim";
	
	private ArrayList<LegalEntity> entities = new ArrayList<LegalEntity>();
	private UsefulSearchFragment useful;
	private String option;
	private String result;
	public UsefulTelephonesServices(UsefulSearchFragment useful, String option){
		this.useful = useful;
		this.option = ENDPOINT.concat("&empresa="+option);
	}
	
	
	
	@Override
	protected String doInBackground(String... arg0) {
		
		try{	
			this.result = new WebClient(this.option).get();	
			JSONObject rootJSON = new JSONObject(result);   
			JSONArray entitiesJSONList = rootJSON.getJSONArray("pessoaList");		
				
			
			for(int i = 0; i < entitiesJSONList.length(); i++){
				LegalEntity entity = new LegalEntity(entitiesJSONList.getJSONObject(i));
				entities.add(entity);
			}
					
		}catch(Exception e){
			
		}
		
		Log.i("antes return", "nao  funcionou");
		return null;
	}

	 @Override
	    protected void onPostExecute(String message) {
	    	useful.requestEnd(entities);
	    }	
	
}
