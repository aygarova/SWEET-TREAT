package com.example.shopapp.supermarket;

import android.content.res.Resources;

import com.example.shopapp.R;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

public class SupermarketInfo {

    public void findSupermarket (String city, MapView mapView, Resources resources) {
        mapView.getOverlays().clear();

        switch (city) {
            case "София":
                someSupermarketInSofia(mapView, resources);
                break;
            case "Пловдив":
                someSupermarketInPlovdiv(mapView, resources);
                break;
            case "Бургас":
                someSupermarketInBurgas(mapView, resources);
                break;
            case "Варна":
                someSupermarketInVarna(mapView, resources);
                break;
            case "Русе":
                someSupermarketInRuse(mapView, resources);
                break;
        }
    }

    private void setMarkers(double latitude, double longitude, String name, MapView mapView, Resources resources) {
        ArrayList<Marker> markers = new ArrayList<>();
        GeoPoint point = new GeoPoint(latitude, longitude);
        Marker marker = new Marker(mapView);
        marker.setPosition(point);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setIcon(resources.getDrawable(R.drawable.logo));
        marker.setTitle(name);
        markers.add(marker);
        mapView.getOverlays().addAll(markers);
    }

    public void someSupermarketInSofia(MapView mapView, Resources resources) {
        setMarkers(42.6587829,23.3450155,"Фантастико F6", mapView, resources);
        setMarkers( 42.635909,23.374579 ,"Lidl-Младост", mapView, resources);
        setMarkers(42.713982,23.353853 , "Кауфланд - Хаджи Димитър", mapView, resources);
        setMarkers(42.674085,23.335867, "Фантастико F36", mapView, resources);
        setMarkers(42.780165,23.4199935, "Кауфланд София - Надежда", mapView, resources);
    }

    public void someSupermarketInPlovdiv(MapView mapView, Resources resources) {
        setMarkers(42.18238612582057,24.84318324075821, "Билла Родопи", mapView, resources);
        setMarkers(42.122425,24.740151 , "Кауфланд - Христо Ботев", mapView, resources);
        setMarkers(42.143748,24.783366  , "Метро 1", mapView, resources);
        setMarkers(42.147387,24.784296, "Кауфланд - Тракия", mapView, resources);
        setMarkers(42.124869,24.720357 , "Била - Остромила", mapView, resources);
    }

    public void someSupermarketInBurgas(MapView mapView, Resources resources) {
        setMarkers(42.463513,27.410213, "Кауфланд - Меден рудник", mapView, resources);
        setMarkers(42.532183,27.460973, "Кауфланд - Изгрев", mapView, resources);
        setMarkers(42.524014,27.443826, "Лидл - Славейков", mapView, resources);
        setMarkers(42.458653,27.415037, "Лидл - Меден рудник", mapView, resources);
        setMarkers(42.531804,27.462747 , "Лидл - Изгрев", mapView, resources);
    }

    public void someSupermarketInVarna(MapView mapView, Resources resources) {
        setMarkers(43.245330,27.858535, "Лидл - Дъбов дол", mapView, resources);
        setMarkers(43.177527,27.905771, "Лидл - Аспарухово", mapView, resources);
        setMarkers(43.235194,27.881459, "Кауфланд - Възраждане", mapView, resources);
        setMarkers(43.241727,27.850866, "Кауфланд - Варненчик", mapView, resources);
        setMarkers(43.180086,27.897543, "Била - Аспарухово", mapView, resources);
    }

    public void someSupermarketInRuse(MapView mapView, Resources resources) {
        setMarkers(43.856274,25.959381, "Лидл - Център", mapView, resources);
        setMarkers(43.832145,25.968492, "Кауфланд - Дружба", mapView, resources);
        setMarkers(43.845295,25.966186, "Кауфланд - Ялта", mapView, resources);
        setMarkers(43.830777,25.950947, "Била - Мидия-Енос", mapView, resources);
        setMarkers( 43.846597,25.977282, "Лидл - Дунав", mapView, resources);

    }
}



