package com.mobile.sextus;

import org.json.JSONException;
import org.json.JSONObject;


public class Bike {
	String 		company;
	String		model;
	String		location;
	double		price;
	String		date;
	String		description;
	String		picture;
	String		link;
	
	public Bike( JSONObject bike ) {
		try {
			company 		= bike.getString( "Company" );
			model 			= bike.getString( "Model" );
			location 		= bike.getString( "Location" );
			price 			= bike.getDouble( "Price" );
			date 				= bike.getString( "Date" );
			description = bike.getString( "Description" );
			picture 		= bike.getString( "Picture" );
			link 				= bike.getString( "Link" );
		}
		catch ( JSONException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
