package com.example.express;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="Main" ;
    private EditText com,no;
    private Button select;
    private TextView result;
    private String json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        onClick();
    }

    private void onClick() {
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comStr=com.getText().toString().trim();
                String noStr=no.getText().toString().trim();
                getJson(comStr,noStr);
                parseJson();
            }
        });
    }

    private void parseJson() {
        Gson gson=new Gson();
        Data data=gson.fromJson(json,Data.class);
        if (data!=null){
            String dataStr="";
            for (int i = 0; i < data.getResult().getList().size(); i++) {
                dataStr+=data.getResult().getList().get(i).getRemark()+"\n";
            }
            result.setText(dataStr);
        }
    }

    private void getJson(final String comStr, final String noStr) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        json=DataUtils.getRequest1(comStr,noStr);
                        //Log.d(TAG, "onClick: "+json);
                    }
                }
        ).start();
    }

    private void initViews() {
        com= (EditText) findViewById(R.id.com);
        no= (EditText) findViewById(R.id.no);
        select= (Button) findViewById(R.id.select);
        result= (TextView) findViewById(R.id.result);
    }
}
