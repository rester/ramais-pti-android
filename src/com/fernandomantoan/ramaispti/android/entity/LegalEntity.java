package com.fernandomantoan.ramaispti.android.entity;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class LegalEntity implements Parcelable{

	private static final String PTI_PREFIX_1 = "3576";
	private static final String PTI_PREFIX_2 = "3529";
	
	
	private String entityName;
	private String telephone;
	private Company company;
	private Role role;
	
	
	public LegalEntity(String entityName, String telephone, Company company, Role role){
		
		this.entityName = entityName;
		this.telephone = telephone;
        this.company = company;
        this.role = role;
	}
	
	
	public LegalEntity(JSONObject jsonObject) throws JSONException {
	        this(jsonObject.getString("nome"), jsonObject.getString("telefone"),
	                new Company(jsonObject.getJSONObject("empresa").getString("nome")),
	                new Role(jsonObject.getJSONObject("funcao").getString("nome")));
	}
	
	public LegalEntity(Parcel parcel){
		this.readFromParcel(parcel);
	}
	
	
	public String getEntityName() {
		return entityName;
	}


	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public Company getCompany() {
		return company;
	}


	public void setCompany(Company company) {
		this.company = company;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(entityName);
		dest.writeString(telephone);
		dest.writeString(company.getName());
		dest.writeString(role.getName());	
	}

	private void readFromParcel(Parcel parcel) {
		this.entityName = parcel.readString();
		this.telephone = parcel.readString();
		this.company = new Company(parcel.readString());
		this.role = new Role(parcel.readString());
	}
	
	public String getDiableTelephone() {
		String fullTelephone = "";
		if (telephone.length() == 4) {
			Log.i("telefone", telephone);
			if(telephone.startsWith("2")){
				fullTelephone = PTI_PREFIX_2 + telephone;
			}else {
				fullTelephone = PTI_PREFIX_1 + telephone;	
			}
			
		} else {
			fullTelephone = telephone;
		}
		
		return fullTelephone;
	}
	
	public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
		@Override
		public Person createFromParcel(Parcel source) {
			return new Person(source);
		};
		@Override
		public Person[] newArray(int size) {
			return new Person[size];
		}
	};
}
