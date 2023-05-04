package com.example.shopapp.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telecom.Call;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shopapp.Adapter.MyAdapters;
import com.example.shopapp.BuildConfig;
import com.example.shopapp.models.RecipeModel;
import com.example.shopapp.repository.Database;
import com.example.shopapp.supermarket.SupermarketInfo;
import com.example.shopapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

public class MyProfile extends AppCompatActivity {

    private MapView mapView;
    private SupermarketInfo map = new SupermarketInfo();
    private String city;
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private IMapController mapController;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private ScaleGestureDetector scaleGestureDetector;
    private Database database = new Database();
    private double mMinZoomLevel = 1.0;
    private double mMaxZoomLevel = 19.0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sign_in);

        getSupportActionBar().setTitle("Моя профил");

        Button exitButton = findViewById(R.id.button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("email");
                editor.remove("password");
                editor.apply();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        Button button = findViewById(R.id.buttonAddRecipe);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfile.this, Recipe.class);
                startActivity(intent);
                finish();
            }
        });

        Button buttonAllRecipes = findViewById(R.id.buttonAllRecipes);
        buttonAllRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<RecipeModel> myObjectList = new ArrayList<>();
                Query query = firestore.collection("recipes").whereEqualTo("userKey", firebaseAuth.getCurrentUser().getUid());
                query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            Intent intent = new Intent(MyProfile.this, EmptyRecipeCatalog.class);
                            startActivity(intent);
                            finish();
                        }
                        myObjectList.clear();

                        if (snapshot.isEmpty()) {
                            Intent intent = new Intent(MyProfile.this, EmptyRecipeCatalog.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(MyProfile.this, RecipeCatalog.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                });
            }
        });
        setUpMap();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MyProfile.this, "Няма резрешение до местоположението", Toast.LENGTH_SHORT).show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }
    private void setUpMap() {
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        mapView = (MapView) findViewById(R.id.mapview);

        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        mapController = mapView.getController();
        mapController.setZoom(10.0);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onLocationChanged(Location location) {
                GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                mapController.setCenter(startPoint);
                Marker marker = new Marker(mapView);
                marker.setPosition(startPoint);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                marker.setIcon(getResources().getDrawable(R.drawable.location));
                mapView.getOverlays().add(marker);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapView.setMinZoomLevel(1.0);
        mapView.setMaxZoomLevel(19.0);

        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        Spinner citySpinner = (Spinner) findViewById(R.id.city_spinner);

        List<String> cities = new ArrayList<>();
        cities.add("София");
        cities.add("Пловдив");
        cities.add("Варна");
        cities.add("Бургас");
        cities.add("Русе");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = parent.getItemAtPosition(position).toString();
                map.findSupermarket(city, mapView, getResources());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                double zoomLevel = mapView.getZoomLevelDouble();
                double scaleFactor = detector.getScaleFactor();

                zoomLevel += Math.log(scaleFactor) / Math.log(2);
                zoomLevel = Math.max(mMinZoomLevel, Math.min(zoomLevel, mMaxZoomLevel));

                mapView.getController().setZoom(zoomLevel);

                return true;
            }
        }
    }
