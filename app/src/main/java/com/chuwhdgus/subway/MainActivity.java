package com.chuwhdgus.subway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.icu.text.IDNA;
import android.os.AsyncTask;
import android.os.Bundle;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public String dataKey ="tBMbl2LjPjgsvzBDaQXaq%2BRbHoIq3oPLqoeL0gACeNltCphhcofQnOljv6AyelSLK2DMm2qFUkVdGKwCoYg45w%3D%3D";
    private String requestUrl = "http://openapi.tago.go.kr/openapi/service/SubwayInfoService/getKwrdFndSubwaySttnList?serviceKey=tBMbl2LjPjgsvzBDaQXaq%2BRbHoIq3oPLqoeL0gACeNltCphhcofQnOljv6AyelSLK2DMm2qFUkVdGKwCoYg45w%3D%3D&subwayStationName=%EC%84%9C%EC%9A%B8&";
    ArrayList<info> list = new ArrayList<info>();
    info subway = new info();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager =  new LinearLayoutManager(this);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //AsyncTask

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();

    }

    public class MyAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {

            try {
                boolean b_subwatRouteName = false;
                boolean b_subwayStationNm = false;
                boolean b_endSubWayStationNm = false;
                boolean b_depTime = false;
                boolean b_arrTime = false;

                URL url = new URL(requestUrl);
                InputStream is = url.openStream();
                XmlPullParserFactory factory =  XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new InputStreamReader(is,"UTF-8"));

                int eventType = parser.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    switch (eventType){
                        case XmlPullParser.START_DOCUMENT:
                            break;
                         case XmlPullParser.END_DOCUMENT:
                             break;
                         case XmlPullParser.END_TAG:
                             if(parser.getName().equals("items") && subway !=null ){
                                 list.add(subway);
                             }
                             break;
                         case XmlPullParser.START_TAG:
                             if(parser.getName().equals("subwayRouteName"))
                                 b_subwatRouteName = true;
                             if(parser.getName().equals("subwayStationName"))
                                 b_subwayStationNm = true;
                             if(parser.getName().equals("arrTime"))
                                 b_arrTime = true;
                             if(parser.getName().equals("depTime"))
                                 b_depTime = true;
                             if(parser.getName().equals("endsubwayStationName"))
                                 b_endSubWayStationNm = true;
                             break;
                          case XmlPullParser.TEXT:
                              if(b_subwatRouteName){
                                  subway.subwayRouteName = parser.getText();
                                  b_subwatRouteName = false;
                              }
                              if(b_subwayStationNm){
                                  subway.subwayStationNm = parser.getText();
                                  b_subwayStationNm = false;
                              }
                              if(b_arrTime){
                                  subway.arrTime = parser.getText();
                                  b_arrTime = false;
                              }
                              if(b_depTime){
                                  subway.depTime = parser.getText();
                                  b_depTime = false;
                              }
                              if(b_endSubWayStationNm){
                                  subway.endSubWayStationNm = parser.getText();
                                  b_endSubWayStationNm = false;
                              }
                    }
                    eventType = parser.next();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //어답터 연결
            MyAdapter adapter = new MyAdapter(getApplicationContext(), list);
            recyclerView.setAdapter(adapter);
        }
    }
}