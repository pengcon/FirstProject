package com.myapp.graduationproject;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.net.URL;

public class API extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);



        StrictMode.enableDefaults();

        TextView status1 = (TextView)findViewById(R.id.result); //파싱된 결과확인!
        //addr1:대주소,addr2:상세주소 dist:거리 title:위치 이름
        boolean inresult= false, inname=false,informatted_phone_number=false,inrating=false;
        String result= null, name=null,formatted_phone_number=null,rating=null;


        try{
            URL url = new URL("https://maps.googleapis.com/maps/api/place/details/xml?place_id=ChIJN1t_tDeuEmsRUsoyG83frY4&fields=name%2Crating%2Cformatted_phone_number&key=AIzaSyAV_sjZngj87Gf9WsEnEI6TTgCDS8poR5w"
            ); //검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");

            while (parserEvent != XmlPullParser.END_DOCUMENT){
                switch(parserEvent){
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if(parser.getName().equals("name")){ //title 만나면 내용을 받을수 있게 하자
                            inname = true;
                        }
                        if(parser.getName().equals("formatted_phone_number")){ //address 만나면 내용을 받을수 있게 하자
                            informatted_phone_number = true;
                        }
                        if(parser.getName().equals("rating")){ //address 만나면 내용을 받을수 있게 하자
                            inrating = true;
                        }
                        if(parser.getName().equals("message")){ //message 태그를 만나면 에러 출력
                            status1.setText(status1.getText()+"에러");
                            //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if(inname){ //isTitle이 true일 때 태그의 내용을 저장.
                            name = parser.getText();
                            inname = false;
                        }
                        if(informatted_phone_number){ //isAddress이 true일 때 태그의 내용을 저장.
                            formatted_phone_number = parser.getText();
                            informatted_phone_number = false;
                        }
                        if(inrating){ //isMapx이 true일 때 태그의 내용을 저장.
                            rating = parser.getText();
                            inrating = false;
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("result")){
                            status1.setText(status1.getText()+"이름 "+ name +"\n 번호: "+ formatted_phone_number +"\n 평점 : " + rating
                                    +"\n");
                            inresult = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch(Exception e){
            status1.setText("에러가..났습니다...");
        }
    }
}