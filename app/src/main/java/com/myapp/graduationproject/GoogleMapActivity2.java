package com.myapp.graduationproject;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import androidx.appcompat.widget.SearchView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class GoogleMapActivity2 extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
{
    // creating a variable
    // for search view.
    SearchView searchView;
    private RecyclerView recyclerView;
    private ArrayList<MarketItem> testDataSet ;
    private CustomAdapter customAdapter;
    double mapxx;
    double mapyy;
    private ArrayList<Marker> markers;

    private GoogleApiClient mGoogleApiClient = null;
    private GoogleMap mGoogleMap = null;
    private Marker currentMarker = null;
    private Marker searchMarker = null;

    private static final String TAG = "googlemap_example";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
    private static final int UPDATE_INTERVAL_MS = 1000;  // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초

    private AppCompatActivity mActivity;
    boolean askPermissionOnceAgain = false;
    boolean mRequestingLocationUpdates = false;
    Location mCurrentLocatiion;
    boolean mMoveMapByUser = true;
    boolean mMoveMapByAPI = true;
    LatLng currentPosition;
    private Place place;
    private PlacesClient placesClient;
    LocationRequest locationRequest = new LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL_MS)
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Places.initialize(getApplicationContext(),"AIzaSyAV_sjZngj87Gf9WsEnEI6TTgCDS8poR5w");
        placesClient=Places.createClient(this);
        StrictMode.enableDefaults();
        markers = new ArrayList<>();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_goolge_map2);

        //===== 테스트를 위한 더미 데이터 생성 ===================

        //========================================================
        recyclerView = new RecyclerView(this);
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);

        recyclerView.setLayoutManager(gridLayoutManager);  // LayoutManager 설정
         customAdapter = new CustomAdapter();
        recyclerView.setAdapter(customAdapter);

//        ArrayList<String> testDataSet = new ArrayList<>();
//        for (int i = 0; i<20; i++) {
//            testDataSet.add("TEST DATA" + i);
//        }
//        CustomAdapter customAdapter = new CustomAdapter(testDataSet);
//        recyclerView.setAdapter(customAdapter); // 어댑터 설정




//서치 뷰


        Log.d(TAG, "onCreate");
        mActivity = this;




        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();



        SupportMapFragment mapFragment = (SupportMapFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);




        //자동완성 기능



        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getLatLng());
                LatLng latLng = place.getLatLng();

                mapxx=latLng.longitude;
                mapyy=latLng.latitude;
                if(latLng != null){
                    if (searchMarker != null) searchMarker.remove();
                    searchMarker= mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(place.getName())); // 마커 추가하기
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    parsing(mapxx,mapyy);

                }






            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

//--------------------------------------------------------------------------


        // at last we calling our map fragment to update.
        mapFragment.getMapAsync(this);
    }






    @Override
    public void onResume() {

        super.onResume();

        if (mGoogleApiClient.isConnected()) {

            Log.d(TAG, "onResume : call startLocationUpdates");
            if (!mRequestingLocationUpdates) startLocationUpdates();
        }


        //앱 정보에서 퍼미션을 허가했는지를 다시 검사해봐야 한다.
        if (askPermissionOnceAgain) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                askPermissionOnceAgain = false;

                checkPermissions();
            }
        }

    }

    private void startLocationUpdates() {

        if (!checkLocationServicesStatus()) {

            Log.d(TAG, "startLocationUpdates : call showDialogForLocationServiceSetting");
            showDialogForLocationServiceSetting();
        }else {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                Log.d(TAG, "startLocationUpdates : 퍼미션 안가지고 있음");
                return;
            }


            Log.d(TAG, "startLocationUpdates : call FusedLocationApi.requestLocationUpdates");
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
            mRequestingLocationUpdates = true;

            mGoogleMap.setMyLocationEnabled(true);

        }

    }



    private void stopLocationUpdates() {

        Log.d(TAG,"stopLocationUpdates : LocationServices.FusedLocationApi.removeLocationUpdates");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mRequestingLocationUpdates = false;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d(TAG, "onMapReady :");

        mGoogleMap = googleMap;


        //런타임 퍼미션 요청 대화상자나 GPS 활성 요청 대화상자 보이기전에
        //지도의 초기위치를 서울로 이동
        setDefaultLocation();

        //mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener(){

            @Override
            public boolean onMyLocationButtonClick() {

                Log.d( TAG, "onMyLocationButtonClick : 위치에 따른 카메라 이동 활성화");


                parsing(mapxx,mapyy);
                mMoveMapByAPI = true;
                return true;
            }
        });
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                Log.d( TAG, "onMapClick :");
            }
        });

        mGoogleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {

            @Override
            public void onCameraMoveStarted(int i) {

                if (mMoveMapByUser == true && mRequestingLocationUpdates){

                    Log.d(TAG, "onCameraMove : 위치에 따른 카메라 이동 비활성화");
                    mMoveMapByAPI = false;
                }

                mMoveMapByUser = true;

            }
        });


        mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {

            @Override
            public void onCameraMove() {


            }
        });
    }


    @Override
    public void onLocationChanged(Location location) {

        currentPosition
                = new LatLng( location.getLatitude(), location.getLongitude());

        Log.d(TAG, "onLocationChanged : ");

        String markerTitle = getCurrentAddress(currentPosition);
        String markerSnippet = "위도:" + String.valueOf(location.getLatitude())
                + " 경도:" + String.valueOf(location.getLongitude());

        //현재 위치에 마커 생성하고 이동
        setCurrentLocation(location, markerTitle, markerSnippet);

        mCurrentLocatiion = location;
        //파싱
        mapxx=location.getLongitude();
        mapyy=location.getLatitude();

//        parsing(mapxx,mapyy);
//        StrictMode.enableDefaults();
//
//        TextView status1 = (TextView)findViewById(R.id.result); //파싱된 결과확인!
//        //addr1:대주소,addr2:상세주소 dist:거리 title:위치 이름
//        boolean initem= false, inmapx=false,inmapy=false,intitle=false;
//        String item= null,title=null;
//        Double mapx=null,mapy=null;
//
//        try{
//            URL url = new URL("https://apis.data.go.kr/B551011/KorService1/locationBasedList1?serviceKey=rmmgDUVQo9EoerLv7PxQp1CP0aiSxgG8Y3MsrzCQUU7F1exTKhM3rUEckr1cXp8G3yCdkcfWNDc0mHCKrgTjbg%3D%3D&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&mapX=126.6968421&mapY=37.4258293&radius=1500&listYN=Y"
//            ); //검색 URL부분
//
//            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
//            XmlPullParser parser = parserCreator.newPullParser();
//
//            parser.setInput(url.openStream(), null);
//
//            int parserEvent = parser.getEventType();
//            System.out.println("파싱시작합니다.");
//
//            while (parserEvent != XmlPullParser.END_DOCUMENT){
//                switch(parserEvent){
//                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
//                        if(parser.getName().equals("mapx")){ //title 만나면 내용을 받을수 있게 하자
//                            inmapx = true;
//                        }
//                        if(parser.getName().equals("mapy")){ //address 만나면 내용을 받을수 있게 하자
//                            inmapy = true;
//                        }
//                        if(parser.getName().equals("title")){ //address 만나면 내용을 받을수 있게 하자
//                            intitle = true;
//                        }
//                        if(parser.getName().equals("message")){ //message 태그를 만나면 에러 출력
//                            status1.setText(status1.getText()+"에러");
//                            //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
//                        }
//                        break;
//
//                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
//                        if(inmapx){ //isTitle이 true일 때 태그의 내용을 저장.
//                            mapx = Double.parseDouble(parser.getText());
//                            inmapx = false;
//                        }
//                        if(inmapy){ //isAddress이 true일 때 태그의 내용을 저장.
//                            mapy = Double.parseDouble(parser.getText());
//                            inmapy = false;
//                        }
//                        if(intitle){ //isMapx이 true일 때 태그의 내용을 저장.
//                            title = parser.getText();
//                            intitle = false;
//                        }
//
//                        break;
//                    case XmlPullParser.END_TAG:
//                        if(parser.getName().equals("item")){
//
//                            initem = false;
//                            MarkerOptions markerOptions = new MarkerOptions();
//                            markerOptions
//                                    .position(new LatLng(mapy,mapx))
//                                    .title(title)
//                                    .snippet("경도 = "+mapy+", 위도 = "+mapx+" 평점= 4.0");
//                            mGoogleMap.addMarker(markerOptions);
//
//                        }
//                        break;
//                }
//                parserEvent = parser.next();
//            }
//        } catch(Exception e){
//            status1.setText("에러가..났습니다...");
//        }
    }






    @Override
    protected void onStart() {

        if(mGoogleApiClient != null && mGoogleApiClient.isConnected() == false){

            Log.d(TAG, "onStart: mGoogleApiClient connect");
            mGoogleApiClient.connect();
        }

        super.onStart();
    }

    @Override
    protected void onStop() {

        if (mRequestingLocationUpdates) {

            Log.d(TAG, "onStop : call stopLocationUpdates");
            stopLocationUpdates();
        }

        if ( mGoogleApiClient.isConnected()) {

            Log.d(TAG, "onStop : mGoogleApiClient disconnect");
            mGoogleApiClient.disconnect();
        }

        super.onStop();
    }


    @Override
    public void onConnected(Bundle connectionHint) {


        if ( mRequestingLocationUpdates == false ) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

                if (hasFineLocationPermission == PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(mActivity,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                } else {

                    Log.d(TAG, "onConnected : 퍼미션 가지고 있음");
                    Log.d(TAG, "onConnected : call startLocationUpdates");
                    startLocationUpdates();
                    mGoogleMap.setMyLocationEnabled(true);
                }

            }else{

                Log.d(TAG, "onConnected : call startLocationUpdates");
                startLocationUpdates();
                mGoogleMap.setMyLocationEnabled(true);
            }
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.d(TAG, "onConnectionFailed");
        setDefaultLocation();
    }


    @Override
    public void onConnectionSuspended(int cause) {

        Log.d(TAG, "onConnectionSuspended");
        if (cause == CAUSE_NETWORK_LOST)
            Log.e(TAG, "onConnectionSuspended(): Google Play services " +
                    "connection lost.  Cause: network lost.");
        else if (cause == CAUSE_SERVICE_DISCONNECTED)
            Log.e(TAG, "onConnectionSuspended():  Google Play services " +
                    "connection lost.  Cause: service disconnected");
    }

    private void parsing(double mapxx,double mapyy) {
        StrictMode.enableDefaults();

        TextView status1 = (TextView)findViewById(R.id.result); //파싱된 결과확인!
        //addr1:대주소,addr2:상세주소 dist:거리 title:위치 이름
        boolean initem= false, inmapx=false,inmapy=false,intitle=false, infirstimage=false,intel=false,infirstimage2=false,inaddr1=false,inaddr2=false;
        String item= null,title=null, firstimage=null,firstimage2=null, tel=null,addr1=null,addr2=null;
        Double mapx=null,mapy=null;



        try{

            URL url = new URL("https://apis.data.go.kr/B551011/KorService1/locationBasedList1?serviceKey=rmmgDUVQo9EoerLv7PxQp1CP0aiSxgG8Y3MsrzCQUU7F1exTKhM3rUEckr1cXp8G3yCdkcfWNDc0mHCKrgTjbg%3D%3D&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&mapX="+mapxx+"&mapY="+mapyy+"&radius=1500&listYN=Y"
            ); //검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");
            testDataSet = new ArrayList<MarketItem>();
            while (parserEvent != XmlPullParser.END_DOCUMENT){
                switch(parserEvent){
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if(parser.getName().equals("addr1")){ //address 만나면 내용을 받을수 있게 하자
                            inaddr1 = true;
                        }
//                        if(parser.getName().equals("addr2")){ //address 만나면 내용을 받을수 있게 하자
//                            inaddr2 = true;
//                        }
                        if(parser.getName().equals("firstimage")){ //address 만나면 내용을 받을수 있게 하자
                            infirstimage = true;
                        }

                        if(parser.getName().equals("firstimage2")){ //address 만나면 내용을 받을수 있게 하자
                            infirstimage2 = true;
                        }
                        if(parser.getName().equals("mapx")){ //title 만나면 내용을 받을수 있게 하자
                            inmapx = true;
                        }
                        if(parser.getName().equals("mapy")){ //address 만나면 내용을 받을수 있게 하자
                            inmapy = true;
                        }
                        if(parser.getName().equals("tel")){ //address 만나면 내용을 받을수 있게 하자
                            intel = true;
                        }
                        if(parser.getName().equals("title")){ //address 만나면 내용을 받을수 있게 하자
                            intitle = true;
                        }

                        if(parser.getName().equals("message")){ //message 태그를 만나면 에러 출력
                            status1.setText(status1.getText()+"에러");
                            //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if(inaddr1){ //isTitle이 true일 때 태그의 내용을 저장.
                            addr1 = parser.getText();
                            inaddr1 = false;
                        }
//                        if(inaddr2){ //isTitle이 true일 때 태그의 내용을 저장.
//                            addr2 = parser.getText();
//                            inaddr2 = false;
//                        }

                        if(infirstimage){ //isTitle이 true일 때 태그의 내용을 저장.
                            firstimage = parser.getText();
                            infirstimage = false;
                        }

                        if(infirstimage2){ //isTitle이 true일 때 태그의 내용을 저장.
                            firstimage2 = parser.getText();
                            infirstimage2 = false;
                        }
                        if(inmapx){ //isTitle이 true일 때 태그의 내용을 저장.
                            mapx = Double.parseDouble(parser.getText());
                            inmapx = false;
                        }
                        if(inmapy){ //isAddress이 true일 때 태그의 내용을 저장.
                            mapy = Double.parseDouble(parser.getText());
                            inmapy = false;
                        }
                        if(intel){ //isAddress이 true일 때 태그의 내용을 저장.
                            tel = parser.getText();
                            intel = false;
                        }

                        if(intitle){ //isMapx이 true일 때 태그의 내용을 저장.
                            title = parser.getText();
                            intitle = false;
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){

                            initem = false;
                            Log.d("값들","DD"+title+addr1+tel+mapx+mapy);

                            Bitmap bitmap=null;
                            Bitmap bitmap2=null;
                            if(firstimage!=null) {
                                URL imageurl = new URL(firstimage); // imageAddress는 이미지주소를 담고 있는 문자열 변수입니다.
                                InputStream is = imageurl.openConnection().getInputStream();
                                bitmap = BitmapFactory.decodeStream(is);

                            }

                            if(firstimage2!=null) {
                                URL imageurl2 = new URL(firstimage); // imageAddress는 이미지주소를 담고 있는 문자열 변수입니다.
                                InputStream is2 = imageurl2.openConnection().getInputStream();
                                 bitmap2 = BitmapFactory.decodeStream(is2);

                            }

                        if( (bitmap!=null) && (title!=null) && (addr1!=null) ) {
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions
                                    .position(new LatLng(mapy, mapx))
                                    .title(title)
                                    .snippet("경도 = " + mapy + ", 위도 = " + mapx + " 평점= 4.0");
                            Marker marker = mGoogleMap.addMarker(markerOptions);
                            markers.add(marker);




                            testDataSet.add(new MarketItem(bitmap, title, addr1));
                            customAdapter.setFriendList(testDataSet);
                        }

                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch(Exception e){
//            status1.setText("에러가..났습니다...");
            Log.d(TAG,"오류");
        }



    }




    public String getCurrentAddress(LatLng latlng) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }

    }


    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {

        mMoveMapByUser = false;


        if (currentMarker != null) currentMarker.remove();


        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);


        currentMarker = mGoogleMap.addMarker(markerOptions);


        if ( mMoveMapByAPI ) {

            Log.d( TAG, "setCurrentLocation :  mGoogleMap moveCamera "
                    + location.getLatitude() + " " + location.getLongitude() ) ;
            // CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLatLng, 15);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
            mGoogleMap.moveCamera(cameraUpdate);
        }
    }


    public void setDefaultLocation() {

        mMoveMapByUser = false;


        //디폴트 위치, Seoul
        LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
        String markerTitle = "위치정보 가져올 수 없음";
        String markerSnippet = "위치 퍼미션과 GPS 활성 요부 확인하세요";


        if (currentMarker != null) currentMarker.remove();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = mGoogleMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mGoogleMap.moveCamera(cameraUpdate);

    }


    //여기부터는 런타임 퍼미션 처리을 위한 메소드들
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissions() {
        boolean fineLocationRationale = ActivityCompat
                .shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasFineLocationPermission == PackageManager
                .PERMISSION_DENIED && fineLocationRationale)
            showDialogForPermission("앱을 실행하려면 퍼미션을 허가하셔야합니다.");

        else if (hasFineLocationPermission
                == PackageManager.PERMISSION_DENIED && !fineLocationRationale) {
            showDialogForPermissionSetting("퍼미션 거부 + Don't ask again(다시 묻지 않음) " +
                    "체크 박스를 설정한 경우로 설정에서 퍼미션 허가해야합니다.");
        } else if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED) {


            Log.d(TAG, "checkPermissions : 퍼미션 가지고 있음");

            if ( mGoogleApiClient.isConnected() == false) {

                Log.d(TAG, "checkPermissions : 퍼미션 가지고 있음");
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(permsRequestCode, permissions, grantResults);
        if (permsRequestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION && grantResults.length > 0) {

            boolean permissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

            if (permissionAccepted) {


                if (mGoogleApiClient.isConnected() == false) {

                    Log.d(TAG, "onRequestPermissionsResult : mGoogleApiClient connect");
                    mGoogleApiClient.connect();
                }


            } else {

                checkPermissions();
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void showDialogForPermission(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(GoogleMapActivity2.this);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.create().show();
    }

    private void showDialogForPermissionSetting(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(GoogleMapActivity2.this);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                askPermissionOnceAgain = true;

                Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + mActivity.getPackageName()));
                myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
                myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mActivity.startActivity(myAppSettings);
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.create().show();
    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(GoogleMapActivity2.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d(TAG, "onActivityResult : 퍼미션 가지고 있음");


                        if ( mGoogleApiClient.isConnected() == false ) {

                            Log.d( TAG, "onActivityResult : mGoogleApiClient connect ");
                            mGoogleApiClient.connect();
                        }
                        return;
                    }
                }

                break;
        }
    }


}