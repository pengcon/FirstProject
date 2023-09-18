package com.myapp.graduationproject;


import androidx.appcompat.app.AppCompatActivity;

import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class Parser extends AppCompatActivity {

    public final static String PHARM_URL = "https://apis.data.go.kr/B551011/KorService1/locationBasedList1?serviceKey=rmmgDUVQo9EoerLv7PxQp1CP0aiSxgG8Y3MsrzCQUU7F1exTKhM3rUEckr1cXp8G3yCdkcfWNDc0mHCKrgTjbg%3D%3D&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&";
    public final static String KEY = "[SERVICE KEY]";
    double mapx;
    double mapy;


    public Parser() {

        try {
            apiParserSearch();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * @throws Exception
     */
    public ArrayList<DTO> apiParserSearch() throws Exception {
        URL url = new URL("https://apis.data.go.kr/B551011/KorService1/locationBasedList1?serviceKey=rmmgDUVQo9EoerLv7PxQp1CP0aiSxgG8Y3MsrzCQUU7F1exTKhM3rUEckr1cXp8G3yCdkcfWNDc0mHCKrgTjbg%3D%3D&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&mapX=126.6968421&mapY=37.4258293&radius=000&listYN=Y");

        XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
        XmlPullParser parser = parserCreator.newPullParser();
        parser.setInput(url.openStream(), null);
//        String tag = null;
//        int event_type = xpp.getEventType();

        ArrayList<DTO> list = new ArrayList<DTO>();
        boolean inmapx = false, inmapy = false, intitle = false;
        String mapx = null, mapy = null, title = null;
        try {
//            URL url = new URL("https://apis.data.go.kr/B551011/KorService1/locationBasedList1?serviceKey=rmmgDUVQo9EoerLv7PxQp1CP0aiSxgG8Y3MsrzCQUU7F1exTKhM3rUEckr1cXp8G3yCdkcfWNDc0mHCKrgTjbg%3D%3D&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&mapX=126.981611&mapY=37.568477&radius=1000&listYN=Y");
            //검색 URL부분


            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("mapx")) { //title 만나면 내용을 받을수 있게 하자
                            inmapx = true;
                        }
                        if (parser.getName().equals("mapy")) { //address 만나면 내용을 받을수 있게 하자
                            inmapy = true;
                        }
                        if (parser.getName().equals("title")) { //address 만나면 내용을 받을수 있게 하자
                            intitle = true;
                        }


                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (inmapx) { //isTitle이 true일 때 태그의 내용을 저장.
                            mapx = parser.getText();
                            inmapx = false;
                        }
                        if (inmapy) { //isAddress이 true일 때 태그의 내용을 저장.
                            mapy = parser.getText();
                            inmapy = false;
                        }
                        if (intitle) { //isMapx이 true일 때 태그의 내용을 저장.
                            title = parser.getText();
                            intitle = false;
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {

                            DTO entity = new DTO();
                            entity.setmapx(Double.valueOf(mapx));
                            entity.setmapy(Double.valueOf(mapy));
                            entity.settitle(title);

                            list.add(entity);

                        }
                        parserEvent = parser.next();
                }
            }
        } catch (XmlPullParserException e) {

        }
        return list;
    }
}