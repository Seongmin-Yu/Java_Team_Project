package com.example.ado.ahmacja;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.util.HashMap;
import java.util.List;

public class MapFragment extends Fragment implements MapView.MapViewEventListener,
        MapView.POIItemEventListener{

    MapView mapView;
    String API_KEY = "bbc70593defebc949948303a45e2b7ea";
    ImageView mButtonSearch;
    EditText mEditTextQuery;
    ImageButton mButtonCurrent;
    private HashMap<Integer, Item> mTagItemMap = new HashMap<>();

    public MapFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.fragment_map, container, false);

        mapView = layout.findViewById(R.id.map_view_fragment);


        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());

        mEditTextQuery = (EditText) layout.findViewById(R.id.editText_home_fragment);
        mEditTextQuery.setOnKeyListener(new EditMessageOnKeyListener());
        mButtonSearch = (ImageView) layout.findViewById(R.id.search_button_fragment);
        mButtonCurrent = layout.findViewById(R.id.current_location_button_fragment);

        mEditTextQuery.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    onSearchButton();
                    return true;
                }

                return false;
            }
        });

        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchButton();
            }
        });
        mButtonCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
            }
        });
        return layout;
    }
    public void onSearchButton() {
        String query = mEditTextQuery.getText().toString();
        if (query == null || query.length() == 0) {
            Toast.makeText(getActivity().getBaseContext(), "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }
//        mEditTextQuery.setInputType(InputType.TYPE_NULL);
        MapPoint.GeoCoordinate geoCoordinate = mapView.getMapCenterPoint().getMapPointGeoCoord();
        double latitude = geoCoordinate.latitude; // 위도
        double longitude = geoCoordinate.longitude; // 경도
        int radius = 10000;
        int page = 1;

        Searcher searcher = new Searcher();
        searcher.searchKeyword(getActivity().getApplicationContext(), query, latitude, longitude, radius, page, API_KEY, new OnFinishSearchListener() {
            @Override
            public void onSuccess(List<Item> itemList) {
                mapView.removeAllPOIItems();
                showResult(itemList);
            }

            @Override
            public void onFail() {
                //Toast.makeText(getBaseContext(), "API_KEY 제한 트래픽 초과!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onMapViewInitialized(MapView mapView) {
        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
    }

    private void showResult(List<Item> itemList) {
        //화면에 보여질 영역 설정을 위한 객체.
        MapPointBounds mapPointBounds = new MapPointBounds();
        //검색 api를 통해 호출받은 List의 크기만큼 반복.
        for (int i = 0; i < itemList.size(); i++) {
            //마커와 CalloutBallon을 설정하기 위한 옵션들.
            Item item = itemList.get(i);
            MapPOIItem poiItem = new MapPOIItem();
            poiItem.setTag(i);















            poiItem.setItemName(itemList.get(i).place_name);
            poiItem.setMarkerType(MapPOIItem.MarkerType.BluePin);
            poiItem.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
            //길 찾기 기능 설정을 위해 해당 POI(관심지점) 객체에 mapPoint(경위도 좌표 값)를 등록.
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(item.y, item.x);

            poiItem.setMapPoint(mapPoint);
            //...중략
            mapView.addPOIItem(poiItem);
            mTagItemMap.put(poiItem.getTag(), item);
        }
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds));
        //트랙킹모드 실행.
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving);
        MapPOIItem[] poiItems = mapView.getPOIItems();
        if (poiItems.length > 0) {
            mapView.selectPOIItem(poiItems[0], false);
        }



    }


    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {

        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.custom_marker_ballon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            if (poiItem == null) return null;
            Item item = mTagItemMap.get(poiItem.getTag());
            if (item == null) return null;
            ImageView imageViewBadge = (ImageView) mCalloutBalloon.findViewById(R.id.badge);
            TextView textViewTitle = (TextView) mCalloutBalloon.findViewById(R.id.title);
            textViewTitle.setText(item.place_name);
            TextView textViewDesc = (TextView) mCalloutBalloon.findViewById(R.id.desc);
            textViewDesc.setText(item.road_address_name);
            //imageViewBadge.setImageDrawable(createDrawableFromUrl(item.place_url));
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }

    }

    class EditMessageOnKeyListener implements View.OnKeyListener {


        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {


            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                //onSearchButton();
                return true;
            }

            return false;
        }
    }
}
