package com.example.graduationproject;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
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

        boolean initem= false, inrnum=false,incode=false,inname=false;
        String item= null, rnum=null,code=null,name=null;


        try{
            URL url = new URL("http://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=rmmgDUVQo9EoerLv7PxQp1CP0aiSxgG8Y3MsrzCQUU7F1exTKhM3rUEckr1cXp8G3yCdkcfWNDc0mHCKrgTjbg%3D%3D&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest"
            ); //검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");

            while (parserEvent != XmlPullParser.END_DOCUMENT){
                switch(parserEvent){
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if(parser.getName().equals("rnum")){ //title 만나면 내용을 받을수 있게 하자
                            inrnum = true;
                        }
                        if(parser.getName().equals("code")){ //address 만나면 내용을 받을수 있게 하자
                            incode = true;
                        }
                        if(parser.getName().equals("name")){ //address 만나면 내용을 받을수 있게 하자
                            inname = true;
                        }
                        if(parser.getName().equals("message")){ //message 태그를 만나면 에러 출력
                            status1.setText(status1.getText()+"에러");
                            //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if(inrnum){ //isTitle이 true일 때 태그의 내용을 저장.
                            rnum = parser.getText();
                            inrnum = false;
                        }
                        if(incode){ //isAddress이 true일 때 태그의 내용을 저장.
                            code = parser.getText();
                            incode = false;
                        }
                        if(inname){ //isMapx이 true일 때 태그의 내용을 저장.
                            name = parser.getText();
                            inname = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){
                            status1.setText(status1.getText()+"번호 : "+ rnum +"\n 코드: "+ code +"\n 이름 : " + name
                                    +"\n");
                            initem = false;
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