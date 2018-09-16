package com.clews.ameokja;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.*;

import java.util.HashMap;
import java.util.List;




public class DaumMapsActivity extends FragmentActivity
        implements MapView.MapViewEventListener,
        MapView.POIItemEventListener {

    MapView mapView;
    String API_KEY = "bbc70593defebc949948303a45e2b7ea";
    ImageView mButtonSearch;
    EditText mEditTextQuery;
    private HashMap<Integer, Item> mTagItemMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daum_maps_layout);
        mapView = findViewById(R.id.map_view);


        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());

        mEditTextQuery = (EditText) findViewById(R.id.editText_home);
        mButtonSearch = (ImageView) findViewById(R.id.search_button);

/*
        try {
            String query = mEditTextQuery.getText().toString();
            int radius = 10000; // 중심 좌표부터의 반경거리. 특정 지역을 중심으로 검색하려고 할 경우 사용. meter 단위 (0 ~ 10000)
            int page = 1; // 페이지 번호 (1 ~ 3). 한페이지에 15개
            MapPoint.GeoCoordinate geoCoordinate = mapView.getMapCenterPoint().getMapPointGeoCoord(); //좌표 정보 지오코딩.
            double latitude = geoCoordinate.latitude; // 위도
            double longitude = geoCoordinate.longitude; // 경도
            String apikey = " bbc70593defebc949948303a45e2b7ea";
            Searcher searcher = new Searcher(); // Searcher
//            Toast.makeText(getApplicationContext(), String.format(getString(R.string.app_name), query),Toast.LENGTH_SHORT).show();
            searcher.searchKeyword(getApplicationContext(), query, latitude, longitude, radius, page, apikey, new OnFinishSearchListener() {
                @Override
                public void onSuccess(List<Item> itemList) {
                    mapView.removeAllPOIItems(); // 기존 검색 결과 삭제.
                    showResult(itemList); // 검색 결과 보여줌.

                }

                @Override
                public void onFail() {
                    Log.w("오류: ", "오류");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
*/


        mButtonSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                onSearchButton();

            /*
        String query = mEditTextQuery.getText().toString();
        if (query == null || query.length() == 0) {
            Toast.makeText(getBaseContext(), "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        MapPoint.GeoCoordinate geoCoordinate = mapView.getMapCenterPoint().getMapPointGeoCoord();
        double latitude = geoCoordinate.latitude; // 위도
        double longitude = geoCoordinate.longitude; // 경도
        int radius = 10000;
        int page = 1;

        Searcher searcher = new Searcher();
        searcher.searchKeyword(getApplicationContext(), query, latitude, longitude, radius, page, API_KEY, new OnFinishSearchListener() {
            @Override
            public void onSuccess(List<Item> itemList) {
                mapView.removeAllPOIItems();
                showResult(itemList);
            }

            @Override
            public void onFail() {
                //Toast.makeText(getBaseContext(), "API_KEY 제한 트래픽 초과!", Toast.LENGTH_SHORT).show();
            }
        });*/


            }

        });

    }

    public void onSearchButton() {
        String query = mEditTextQuery.getText().toString();
        if (query == null || query.length() == 0) {
            Toast.makeText(getBaseContext(), "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        MapPoint.GeoCoordinate geoCoordinate = mapView.getMapCenterPoint().getMapPointGeoCoord();
        double latitude = geoCoordinate.latitude; // 위도
        double longitude = geoCoordinate.longitude; // 경도
        int radius = 10000;
        int page = 1;

        Searcher searcher = new Searcher();
        searcher.searchKeyword(getApplicationContext(), query, latitude, longitude, radius, page, API_KEY, new OnFinishSearchListener() {
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
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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

        System.out.println(itemList.get(0).place_name);

        System.out.println(itemList.get(1).place_name);
        System.out.println(itemList.get(2).place_name);



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
}



/*

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.EventListener;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.*;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


class Item {
    public String place_name;
    public String place_url;
    public String address_name;
    public String road_address_name;
    public String phone;
    public double x;
    public double y;
    public double distance;
    public String category_group_name;
    public String category_group_code;
    public String id;
    public String placeUrl;
}

//OnFinishSearchListener 인터페이스
interface OnFinishSearchListener {
    public void onSuccess(List<Item> itemList);

    public void onFail();
}

//Searcher 클래스
class Searcher {
    //API 콜백 URL
    public static final String LOCAL_KEYWORD_SEARCH_API_FORMAT = "https://dapi.kakao.com/v2/local/search/keyword.json?query=%s&y=%s&x=%s&radius=%d&page=%d&apikey=%s";
    private OnFinishSearchListener onFinishSearchListener;

//......중략

    public void searchKeyword(Context applicationContext, String query, double latitude, double longitude, int radius, int page, String apikey, OnFinishSearchListener onFinishSearchListener) {
        this.onFinishSearchListener = onFinishSearchListener;
//            if (searchTask != null) {
//                searchTask.cancel(true);
//                searchTask = null;
//            }
        String url = buildKeywordSearchApiUrlString(query, latitude, longitude, radius, page, apikey);
//            searchTask = new SearchTask();
//            searchTask.execute(url);
    }


    //UTF-8로 키워드 인코딩 필요.
    private String buildKeywordSearchApiUrlString(String query, double latitude, double longitude, int radius, int page, String apikey) {
        String encodedQuery = "";
        try {
            encodedQuery = URLEncoder.encode(query, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return String.format(Locale.ENGLISH, LOCAL_KEYWORD_SEARCH_API_FORMAT, encodedQuery, latitude, longitude, radius, page, apikey);
    }

    //API 호출을 위한 메서드
    private String fetchData(String urlString, Map<String, String> header) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(4000 */
/* milliseconds *//*
);
            conn.setConnectTimeout(7000 */
/* milliseconds *//*
);
            conn.setRequestMethod("GET"); // GET 방식으로  API 요청
            conn.setRequestProperty("Authorization", "KakaoAK " + "bbc70593defebc949948303a45e2b7ea"); // header 부분에 앱키 작성
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            @SuppressWarnings("resource")
            Scanner s = new Scanner(is);
            s.useDelimiter("\\A");
            String data = s.hasNext() ? s.next() : "";

            Log.w("data : ", data);

            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //JSON 파싱용 메서드
    private List<Item> parse(String jsonString) {
        List<Item> itemList = new ArrayList<Item>();
        try {
            JSONObject reader = new JSONObject(jsonString);
            JSONArray objects = reader.getJSONArray("documents");
            for (int i = 0; i < objects.length(); i++) {
                JSONObject object = objects.getJSONObject(i);
                //item 클래스에 json 데이터 할당.
                Item item = new Item();
                item.place_name = object.getString("place_name");
                //...중략
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return itemList;
    }


}

*/
/*
public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "bbc70593defebc949948303a45e2b7ea";

    ImageView searchButton = findViewById(R.id.search_button);
    EditText searchText = findViewById(R.id.editText_home);
    Searcher searcher = new Searcher();
    final MapView mapView = new MapView(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.map_view);
        viewGroup.addView(mapView);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithCONGCoord(37.53737528, 127.00557633));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(marker);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchText.getText().toString();

                if (query == null || query.length() == 0) {
                    Toast.makeText(getBaseContext(), "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                MapPoint.GeoCoordinate geoCoordinate = mapView.getMapCenterPoint().getMapPointGeoCoord();
                double latitude = geoCoordinate.latitude;
                double longitude = geoCoordinate.longitude;
                int radius = 10000;
                int page = 2;
                searcher.searchKeyword(getApplicationContext(), query, latitude, longitude, radius, page, API_KEY, new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        mapView.removeAllPOIItems();
                        showResult(itemList);
                    }

                    @Override
                    public void onFail() {

                    }
                });

            }
        });


    }
    private void showResult (List < Item > itemList) {
        //화면에 보여질 영역 설정을 위한 객체.
        MapPointBounds mapPointBounds = new MapPointBounds();
        //검색 api를 통해 호출받은 List의 크기만큼 반복.
        for (int i = 0; i < itemList.size(); i++) {
            //마커와 CalloutBallon을 설정하기 위한 옵션들.
            Item item = itemList.get(i);
            MapPOIItem poiItem = new MapPOIItem();
            poiItem.setTag(i);
            //길 찾기 기능 설정을 위해 해당 POI(관심지점) 객체에 mapPoint(경위도 좌표 값)를 등록.
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(item.y, item.x);
            poiItem.setMapPoint(mapPoint);
            //...중략
            mapView.addPOIItem(poiItem);
//            mTagItemMap.put(poiItem.getTag(), item);
        }
        //화면 이동.
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds));

        //mapView.setCalloutBalloonAdapter(new CustomCalloutBallAdapter());

    }

}*/

/*

    class CustomCalloutBallAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBallon;

        public CustomCalloutBallAdapter() {
            mCalloutBallon = getLayoutInflater().inflate(R.layout)
        }

        @Override
        public View getCalloutBalloon(MapPOIItem mapPOIItem) {
            return null;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }

    }
*/
