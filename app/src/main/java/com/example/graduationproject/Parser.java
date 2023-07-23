package com.example.graduationproject;


import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
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
     *
     * @throws Exception
     */
    public ArrayList<DTO> apiParserSearch() throws Exception {
        URL url = new URL(getURLParam(null));

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        xpp.setInput(bis, "utf-8");

        String tag = null;
        int event_type = xpp.getEventType();

        ArrayList<DTO> list = new ArrayList<DTO>();

        String mapx = null, mapy= null,title=null;
        while (event_type != XmlPullParser.END_DOCUMENT) {
            if (event_type == XmlPullParser.START_TAG) {
                tag = xpp.getName();
            } else if (event_type == XmlPullParser.TEXT) {
                /**
                 * 약국의 주소만 가져와본다.
                 */
                if(tag.equals("mapx")){
                    mapx = xpp.getText();
                    System.out.println(mapx);
                }else if(tag.equals("mapy")){
                    mapy = xpp.getText();
                }else if(tag.equals("title")){
                    title = xpp.getText();
                }
            } else if (event_type == XmlPullParser.END_TAG) {
                tag = xpp.getName();
                if (tag.equals("item")) {
                    DTO entity = new DTO();
                    entity.setmapx(Double.valueOf(mapx));
                    entity.setmapy(Double.valueOf(mapy));
                    entity.settitle(title);

                    list.add(entity);
                }
            }
            event_type = xpp.next();
        }
        System.out.println(list.size());

        return list;
    }




    private String getURLParam(String search){
        Intent intent = getIntent();
        mapx=intent.getDoubleExtra("mapx");
        mapy=intent.getDoubleExtra("mapy")
        String url = PHARM_URL+"mapX="+mapx+"&mapy="+mapy+"&radius=1000&listYN=Y";
        if(search != null){
            url = url+"&yadmNm"+search;
        }
        return url;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new Parser();
    }

}