package com.fernandomantoan.ramaispti.android.entity;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fernando on 21/06/13.
 */
public class Person implements Parcelable {
	
	private static final String PTI_PREFIX = "3576";
	
    private String name;
    private String telephone;
    private Company company;
    private Role role;

    public Person(String name, String telephone, Company company, Role role) {
        this.name = name;
        this.telephone = telephone;
        this.company = company;
        this.role = role;
    }

    public Person(JSONObject jsonObject) throws JSONException {
        this(jsonObject.getString("nome"), jsonObject.getString("telefone"),
                new Company(jsonObject.getJSONObject("empresa").getString("nome")),
                new Role(jsonObject.getJSONObject("funcao").getString("nome")));
    }
    
    public Person(Parcel parcel) {
    	this.readFromParcel(parcel);
    }

    public String getName() {
        return this.name;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Company getCompany() {
        return this.company;
    }

    public Role getRole() {
        return this.role;
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(telephone);
		dest.writeString(company.getName());
		dest.writeString(role.getName());
	}
	
	private void readFromParcel(Parcel parcel) {
		this.name = parcel.readString();
		this.telephone = parcel.readString();
		this.company = new Company(parcel.readString());
		this.role = new Role(parcel.readString());
	}
	
	public String getDiableTelephone() {
		String fullTelephone = "";
		if (telephone.length() == 4) {
			fullTelephone = PTI_PREFIX + telephone;
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