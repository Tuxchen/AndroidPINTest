package com.example.pintestactivity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Dialog startDialog;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeText = (TextView) findViewById(R.id.text);

        startDialog = new Dialog(this);
        startDialog.setContentView(R.layout.dialog_layout);
        startDialog.setOwnerActivity(this);
        startDialog.setCancelable(false);
        startDialog.show();

        final EditText pin = (EditText) startDialog.findViewById(R.id.pin);
        Button btn_confirm = (Button) startDialog.findViewById(R.id.confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.post(new Runnable() {
                   public void run() {
                       long start = System.currentTimeMillis();
                       String origPin = pin.getText().toString();
                       String testPin = "";
                       boolean solved = false;

                       for(int i = 0; i < 10000; i++) {
                           if(i < 10) {
                               testPin = "000" + i;
                           }
                           else if(i > 10 && i < 100) {
                               testPin = "00" + i;
                           }
                           else if(i > 100 && i < 1000) {
                               testPin = "0" + i;
                           }
                           else {
                               testPin = "" + i;
                           }

                           if(testPin.equals(origPin)) {
                               solved = true;
                               break;
                           }
                       }

                       long end = System.currentTimeMillis();
                       long duration = end - start;
                       String msg = "";

                       if(solved) {
                           msg = "Dauer: " + duration + "ms (geknackt)";
                           Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                       }
                       else {
                           msg = "Dauer: " + duration + "ms (nicht geknackt)";
                           Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                       }

                       welcomeText.setText(msg);
                   }
                });

                startDialog.dismiss();
            }
        });
    }
}
