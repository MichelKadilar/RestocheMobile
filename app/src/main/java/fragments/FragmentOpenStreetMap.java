package fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.restoche.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FragmentOpenStreetMap extends Fragment {

    private MapView mapView;
    private IMapController mapController;

    String[] permissions = new String[]{
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    /*@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.fragment_open_street_map);
        mapView = findViewById(R.id.MAP_OSM);
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        GeoPoint startPoint = new GeoPoint(43.65020, 7.00517);
        IMapController mapController = mapView.getController();
        mapController.setZoom(10.0);
        mapController.setCenter(startPoint);

        List<OverlayItem> l = new ArrayList<>();
        OverlayItem overlayItem = new OverlayItem("Michel's office", "my office", new GeoPoint(5.0, 5.00));
        Drawable m = overlayItem.getMarker(0);
        l.add(overlayItem);

        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(getApplicationContext(), l,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {

                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(int index, OverlayItem item) {
                        return false;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);
        mapView.getOverlays().add(mOverlay);
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (checkPermissions()) {
            super.onCreateView(inflater, container, savedInstanceState);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            View v = inflater.inflate(R.layout.fragment_open_street_map, container, true);
            mapView = v.findViewById(R.id.MAP_OSM);
            Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(requireActivity().getApplicationContext()));
            mapView.setTileSource(TileSourceFactory.MAPNIK);
            mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
            GeoPoint startPoint = new GeoPoint(43.65020, 7.00517);
            IMapController mapController = mapView.getController();
            mapController.setZoom(10.0);
            mapView.setMultiTouchControls(true);
            mapController.setCenter(startPoint);

            List<OverlayItem> l = new ArrayList<>();
            OverlayItem overlayItem = new OverlayItem("Michel's office", "my office", new GeoPoint(5.0, 5.00));
            Drawable m = overlayItem.getMarker(0);
            l.add(overlayItem);

            ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<>(getContext(), l,
                    new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {

                        @Override
                        public boolean onItemSingleTapUp(int index, OverlayItem item) {
                            return true;
                        }

                        @Override
                        public boolean onItemLongPress(int index, OverlayItem item) {
                            return false;
                        }
                    });
            mOverlay.setFocusItemsOnTap(true);
            mapView.getOverlays().add(mOverlay);

            // Inflate the layout for this fragment
            return v;
        } else return null;
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
}