package com.murataktay.dovizkurlari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread startActivityScreen = new Thread(){
            //run metodunun üzerine yazdık ve 2 saniye beklemesini sağladık
            @Override
            public void run(){
                try {
                    synchronized (this){
                        wait(2000);
                    }
                }catch (Exception e){
                   e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        startActivityScreen.start();
    }
}
