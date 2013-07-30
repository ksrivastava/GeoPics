package com.kaustubh.geopics.objects;

import android.graphics.Bitmap;
import android.util.Log;

public class GeoImage {
	private static final String TAG = "GeoImage";
	private Bitmap image;
	private double latitude;
	private double longitude;
	
	public GeoImage() {
		// TODO Auto-generated constructor stub
	}
	
	public GeoImage(Bitmap image) {
		this.image = image;
	}

	public void setCoordinates(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		
	}

	public void print() {
		String location = "Latitude: " + latitude + " Longitude: " + longitude;
		Log.d(TAG, location);
	}

}
