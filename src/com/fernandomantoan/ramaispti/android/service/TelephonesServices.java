package com.fernandomantoan.ramaispti.android.service;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fernandomantoan.ramaispti.android.activity.SearchActivity;
import com.fernandomantoan.ramaispti.android.entity.Person;
import com.fernandomantoan.ramaispti.android.support.WebClient;

import android.os.AsyncTask;

public class TelephonesServices extends AsyncTask<String, Object, String> {
	private final static String ENDPOINT = "http://pdi.pti.org.br/habitantes/telefones?nome=";
	
	private SearchActivity activity;
	private ArrayList<Person> people = new ArrayList<Person>();
	private String name;
	private String company;
	private String role;
	
	public TelephonesServices(SearchActivity activity, String name, String company, String role) {
		this.activity = activity;
		this.name = name;
		this.company = company;
		this.role = role;
	}
	
	@Override
	protected String doInBackground(String... params) {
		try {
			String parameters = "";
			if (this.company != null && !"".equals(this.company))
				parameters += "&empresa=" + URLEncoder.encode(this.company, "utf-8");
			if (this.role != null && !"".equals(this.role))
				parameters += "&funcao=" + URLEncoder.encode(this.role, "utf-8");
			
			String result = new WebClient(ENDPOINT + URLEncoder.encode(this.name, "utf-8") + parameters).get();
			JSONObject rootJSON = new JSONObject(result);
			JSONArray peopleJSONList = rootJSON.getJSONArray("pessoaList");
			
			people = new ArrayList<Person>();
			for (int i = 0; i < peopleJSONList.length(); i++) {
				Person person = new Person(peopleJSONList.getJSONObject(i));
				people.add(person);
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	@Override
    protected void onPreExecute() {
		activity.showLoading();
    }

    @Override
    protected void onPostExecute(String message) {
    	activity.requestEnd(people);
    }
}