package com.bung.bungapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bung.bungapp.core.ApiCaller;
import com.bung.bungapp.core.Callback;
import com.bung.bungapp.core.LocationClient;
import com.bung.bungapp.model.ChickenStoreResult;
import com.bung.bungapp.model.Party;
import com.bung.bungapp.util.Async;
import com.bung.bungapp.util.DialogUtils;
import com.bung.bungapp.util.GeoUtils;
import com.bung.bungapp.util.ThemeUtils;
import com.bung.bungapp.util.ViewUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rok on 2015. 11. 14..
 */
public class MapFragment extends com.google.android.gms.maps.MapFragment implements OnMapReadyCallback,
        GoogleMap.OnCameraChangeListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.InfoWindowAdapter,
        LocationClient.Listener {

    public static final String EXTRA_ENABLE_SEARCH = "enable_search";

    private final int DEFAULT_ZOOM_LEVEL = 16;
    private final int INITIAL_ZOOM_LEVEL = 14;

    private LocationClient mLocationClient;
    private CameraPosition mPreviousPosition;
    private OnEventListener mListener;

    private Map<Marker, Party> mMarkers;
    private Map<Marker, ChickenStoreResult.ResultsEntity> mChickenMarkers;
    private List<Party> mParties;

    private boolean isSearchEnable = false;
    private boolean isExtraInitiated = false;

    public MapFragment() {

    }

    public static MapFragment newInstance(Bundle bundle) {
        MapFragment mapFragment = new MapFragment();
        mapFragment.setArguments(bundle);
        return mapFragment;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);

        if (args != null) {
//            ArrayList<Party> parties = args.getParcelableArrayList(EXTRA_KEY_BUNGS);
//            mInitialOptions = args.getParcelable(EXTRA_KEY_MAP_OPTION);
//            isSearchEnable = args.getBoolean(EXTRA_ENABLE_SEARCH, false);
//
//            if (parties != null) {
//                setParties(parties);
//            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.getMapAsync(this);
        super.setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_my_location) {
            GoogleMap map = getMap();
            if (map != null) {
                Location myLocation = map.getMyLocation();
                LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM_LEVEL));
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setParties(List<Party> parties) {
        mParties = parties;
        isExtraInitiated = false;
        initMarkers();
    }

    public void setChickenStore(ChickenStoreResult result) {
        if (result != null && result.getResults() != null) {
            if (mChickenMarkers == null) mChickenMarkers = new HashMap<>();
            for (Marker marker : mChickenMarkers.keySet()) {
                marker.remove();
            }
            mChickenMarkers.clear();

            if (!isDetached()) {
                for (ChickenStoreResult.ResultsEntity resultsEntity : result.getResults()) {
                    ChickenStoreResult.ResultsEntity.GeometryEntity.LocationEntity location = resultsEntity.getGeometry().getLocation();
                    LatLng latLng = new LatLng(location.getLat(), location.getLng());
                    MarkerOptions options = new MarkerOptions()
                            .position(latLng)
                            .title(resultsEntity.getName())
                            .snippet(resultsEntity.getVicinity())
                            .visible(false);

                    Marker marker = getMap().addMarker(options);
                    mChickenMarkers.put(marker, resultsEntity);
                    updateMarkers(null);
                }
            }
        }
    }

    public void clearExtras() {
        isExtraInitiated = true;
        isSearchEnable = true;
        mParties = null;
        GoogleMap map = getMap();
        if (map != null) {
            map.clear();
            mMarkers = null;
            mPreviousPosition = null;
        }
        updateLocation(true);
    }

    public void updateLocation(boolean force) {
        GoogleMap map = getMap();
        if (map != null && map.getMyLocation() != null) {
            Location myLocation = map.getMyLocation();
            onLocationUpdate(myLocation);

        } else {
            if (mLocationClient == null) {
                initLocationClient(getActivity());
            }
            mLocationClient.start(force);
        }
    }

    public void setOnEventListener(OnEventListener listener) {
        this.mListener = listener;
    }

    private synchronized void initMarkers() {
        if (!isExtraInitiated) {

            GoogleMap map = getMap();
            if (map != null) {
                isExtraInitiated = true;

                map.clear();
                if (mMarkers == null) {
                    mMarkers = new HashMap<>();
                }
                mMarkers.clear();

                if (mParties != null) {
                    for (Party party : mParties) {
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(party.getLatLng())
                                .title(party.getName())
                                .snippet(party.getDescription());

                        Marker marker = map.addMarker(markerOptions);
                        mMarkers.put(marker, party);
                    }
                }
            } else {
                Async.main(this::initMarkers, 1000);
            }
        }
    }

    public synchronized void initSearch() {
        isSearchEnable = true;

        LatLng latLng = null;
        Location lastKnownLocation = LocationClient.getLastKnownLocation();
        if (getMap() != null) {
            latLng = getMap().getCameraPosition().target;
        } else if (lastKnownLocation != null) {
            latLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        }
        if (latLng != null) {
            ApiCaller.getChickenStores(latLng.latitude, latLng.longitude, new Callback<ChickenStoreResult>() {
                @Override
                public void onSuccess(ChickenStoreResult result) {
                    setChickenStore(result);
                }

                @Override
                public void onFailure() {

                }
            });
        }
    }

    private synchronized void initLocationClient(@NonNull Context context) {
        mLocationClient = LocationClient.with(context).listener(this).build();
    }

    @Override
    public synchronized void onMapReady(GoogleMap map) {

        Context context = getActivity();
        if (mListener != null) mListener.onMapLoaded(map);

        int paddingTop = ViewUtils.getStatusBarHeight(context) + ThemeUtils.getDimension(context, android.R.attr.actionBarSize);
        map.setPadding(0, paddingTop, 0, 0);
        map.setMyLocationEnabled(true);
        map.setOnCameraChangeListener(this);
        map.setOnInfoWindowClickListener(this);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.getUiSettings().setMyLocationButtonEnabled(false);
        Location lastKnownLocation = LocationClient.getLastKnownLocation();

        if (lastKnownLocation != null) {
            LatLng initialLatLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLatLng, INITIAL_ZOOM_LEVEL));

            CameraPosition cameraPosition = new CameraPosition(initialLatLng, DEFAULT_ZOOM_LEVEL, 0, 0);
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        } else {
            LatLng seoul = new LatLng(127.025064, 37.516569);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, DEFAULT_ZOOM_LEVEL));
        }

        getMap().setInfoWindowAdapter(this);
        initMarkers();
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

        boolean updateMarker = mPreviousPosition == null
                || (int) mPreviousPosition.zoom != (int) cameraPosition.zoom
                || GeoUtils.calculateDistanceInMeter(cameraPosition.target, mPreviousPosition.target) > 500;
        boolean updateNearbyStation = isSearchEnable && updateMarker;

        if (updateMarker) {
            updateMarkers(cameraPosition);
            mPreviousPosition = cameraPosition;
        }
        if (updateNearbyStation) {
            LatLng latLng = cameraPosition.target;

//            LatLngBounds bounds = getMap().getProjection().getVisibleRegion().latLngBounds;
//            int radius = (int) (GeoUtils.getRadius(latLng, bounds));
//
//            if (radius > 3000) {
//                if (mListener != null && getActivity() != null) {
//                    mListener.onErrorMessage("지도를 더 확대하세요");
//                }
//            } else {
////                if (radius > 1000) radius = 1000;
//            }
                initSearch();
        }
    }

    private void updateMarkers(CameraPosition cameraPosition) {
        if (mMarkers != null && getMap() != null) {
            boolean visible = true;
            Bitmap partyBitmap = null;
            Bitmap chickenBitmap = null;

            //getMap().clear();
            if (cameraPosition == null) {
                cameraPosition = getMap().getCameraPosition();
            }
            if (isSearchEnable) {
                LatLngBounds bounds = getMap().getProjection().getVisibleRegion().latLngBounds;
                int radius = (int) (GeoUtils.getRadius(cameraPosition.target, bounds));
                if (radius > 5000) {
                    visible = false;
                }
            } else if (cameraPosition.zoom < 12) {
                visible = false;
            }

            int size = (int) (cameraPosition.zoom * 2.5);
            partyBitmap = getScaledIcon(R.drawable.sword, size);
            chickenBitmap = getScaledIcon(R.drawable.chicken_beer, size);

            LatLngBounds bounds = getMap().getProjection().getVisibleRegion().latLngBounds;

            // Draw Chicken Markers
            if (mChickenMarkers != null) {
                Collection<Marker> inBoundStoreMarkers = mChickenMarkers.keySet();
                for (Marker marker : inBoundStoreMarkers) {
                    if (bounds.contains(marker.getPosition())) try {
                        boolean isInfoWindowShown = marker.isInfoWindowShown();
                        marker.setVisible(visible);
                        if (chickenBitmap != null) {
                            marker.setIcon(BitmapDescriptorFactory.fromBitmap(chickenBitmap));
                        }
                        if (isInfoWindowShown) {
                            marker.showInfoWindow();
                        }
                    } catch (Exception e) {

                    }
                }
            }

            // Draw Party Markers
            Collection<Marker> inBoundMarkers = mMarkers.keySet();
            for (Marker marker : inBoundMarkers) {
                if (bounds.contains(marker.getPosition())) {
                    boolean isInfoWindowShown = marker.isInfoWindowShown();
                    marker.setVisible(visible);
                    if (partyBitmap != null) {
                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(partyBitmap));
                    }
                    if (isInfoWindowShown) {
                        marker.showInfoWindow();
                    }
                }
            }


        }
    }

    private Bitmap getScaledIcon(@DrawableRes int drawableRes, int dp) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableRes);
        int pixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
        return Bitmap.createScaledBitmap(bitmap, pixel, pixel, false);
    }

    public void focusParty(Party party) {
        if (mMarkers != null) {
            Marker found = null;
            for (Map.Entry<Marker, Party> markerPartyEntry : mMarkers.entrySet()) {
                if (markerPartyEntry.getValue().getId() == party.getId()) {
                    found = markerPartyEntry.getKey();
                    break;
                }
            }
            if (found != null) {
                LatLng position = found.getPosition();
                GoogleMap map = getMap();
                if (map != null) {
                    final Marker finalFound = found;
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(position, DEFAULT_ZOOM_LEVEL), new GoogleMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            finalFound.showInfoWindow();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }
            }
        }

    }

    @Override
    public void onLocationUpdate(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        GoogleMap map = getMap();
        if (map != null) {
            CameraPosition cameraPosition = new CameraPosition(latLng, DEFAULT_ZOOM_LEVEL, 0, 0);
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            boolean updateMarker = mPreviousPosition == null || (int) mPreviousPosition.zoom != (int) cameraPosition.zoom;
            if (updateMarker) {
                updateMarkers(cameraPosition);
            }
        }

        if (mListener != null && getActivity() != null) {
            mListener.onLocationUpdate(location);
        }
        LocationClient.setLastKnownLocation(location);
    }

    @Override
    public void onError(String failReason, ConnectionResult connectionResult) {
        if (mListener != null && getActivity() != null) {
            mListener.onLocationUpdate(null);
            mListener.onErrorMessage(failReason);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        ChickenStoreResult.ResultsEntity resultsEntity = mChickenMarkers.get(marker);

        if (resultsEntity != null) {
            DialogUtils.openChickenPopup(getActivity(), resultsEntity.getPlace_id());
            return;
        }

        Party party = mMarkers.get(marker);
        if (party != null) {
            DialogUtils.openPartyPopup(getActivity(), party);
        }

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Party party = mMarkers.get(marker);
        if (party != null) {
            View infoWindow = View.inflate(getActivity(), R.layout.view_partywindow, null);
            TextView title = (TextView) infoWindow.findViewById(R.id.title);
            TextView owner = (TextView) infoWindow.findViewById(R.id.owner);
            TextView participantCount = (TextView) infoWindow.findViewById(R.id.participant_count);

            title.setText(party.getName());
            owner.setText(party.getCreator().getName());
            participantCount.setText(String.format("(%d/%d)", party.getNumber_of_participants(), party.getLimit()));
            return infoWindow;
        }
        ChickenStoreResult.ResultsEntity resultsEntity = mChickenMarkers.get(marker);

        if (resultsEntity != null) {
            View infoWindow = View.inflate(getActivity(), R.layout.view_chickenwindow, null);
            TextView title = (TextView) infoWindow.findViewById(R.id.title);
            TextView location = (TextView) infoWindow.findViewById(R.id.location);
            TextView rating = (TextView) infoWindow.findViewById(R.id.rating);

            title.setText(resultsEntity.getName());
            location.setText(resultsEntity.getVicinity());
            rating.setText("");
            return infoWindow;
        }

        return null;
    }

    public interface OnEventListener {
        void onMapLoaded(GoogleMap map);

        void onLocationUpdate(Location location);

        void onErrorMessage(String error);
    }
}
