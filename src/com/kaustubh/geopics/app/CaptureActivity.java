package com.kaustubh.geopics.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaustubh.geopics.objects.GeoImage;

public class CaptureActivity extends Activity implements LocationListener {
	private static final String TAG = "CaptureActivity";
	private static final int CAMERA_PIC_REQUEST = 1337;
	private Button captureButton;
	private Button saveButton;
	private ImageView captureImageView;
	private Intent cameraIntent;
	private LocationManager locationManager;
	private GeoImage geoImage;
	private TextView longitudeTextView;
	private TextView latitudeTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);
		cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		captureButton = (Button) findViewById(R.id.captureButton);
		saveButton = (Button) findViewById(R.id.saveButton);
		captureImageView = (ImageView) findViewById(R.id.captureImageView);
		longitudeTextView = (TextView) findViewById(R.id.longitudeLabel);
		latitudeTextView = (TextView) findViewById(R.id.latitudeLabel);
		init();
		

	}

	private void init() {
		saveButton.setVisibility(View.INVISIBLE);
		initListeners();
	}

	private void initListeners() {
		captureButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clear();
				capture();
			}
		});
		
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				geoImage.print();
			}
		});
	}

	protected void clear() {
		latitudeTextView.setText("Latitude:");
		longitudeTextView.setText("Longitude:");
		saveButton.setVisibility(View.INVISIBLE);
	}

	private void capture() {
		startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_PIC_REQUEST) {
			Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
			showImage(thumbnail);
			createImageObject(thumbnail);
		}
	}

	private void showImage(Bitmap image) {
		captureImageView.setImageBitmap(image);
	}
	
	private void createImageObject(Bitmap image) {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Long.MAX_VALUE, Float.MAX_VALUE, (LocationListener) this);
		geoImage = new GeoImage(image);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		geoImage.setCoordinates(location.getLatitude(), location.getLongitude());
		latitudeTextView.setText("Latitude: " + String.valueOf(location.getLatitude()));
		longitudeTextView.setText("Longitude: " + String.valueOf(location.getLongitude()));
		saveButton.setVisibility(View.VISIBLE);
		locationManager.removeUpdates((LocationListener) this);
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(getApplicationContext(), "Please enable your network provider", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}
}
