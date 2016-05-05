/**
 * DeviceDetails.java gives all the information about device and allows the user to checkIn/checkOut the device.
 *
 * @author Supada Hegde
 * @version 1.0
 */

package com.jhonson.supada.deviceinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.PopupWindow;
import android.view.LayoutInflater;
import android.view.Gravity;
import android.content.Context;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.content.DialogInterface;
import android.text.InputType;
import android.app.AlertDialog;


public class DeviceDetails extends AppCompatActivity implements View.OnClickListener {
    private TextView device,os,mft,user,lastChkOut;
    Boolean isCheckout;
    Button btnOkPopup;
    Button btnCheckIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_details);
        Intent deviceIntent = getIntent();

        String deviceVar = deviceIntent.getStringExtra("device");
        Log.d("device", deviceVar);
        device = (TextView) findViewById(R.id.device);
        device.setText("Device: " + deviceVar);

        String osVar = deviceIntent.getStringExtra("os");
        os = (TextView) findViewById(R.id.os);
        os.setText("OS: " + osVar);

        String mftVar = deviceIntent.getStringExtra("manufacturer");
        mft = (TextView) findViewById(R.id.mft);
        mft.setText("Manufacturer: " + mftVar);

        String userVar = deviceIntent.getStringExtra("user");
        if(userVar != null) {
            user = (TextView) findViewById(R.id.user);
            user.setText("Last user CheckedOut: " + userVar);
        }

        String lastChkOutVar = deviceIntent.getStringExtra("lastCheckOut");
        if(lastChkOutVar != null) {
            lastChkOut = (TextView) findViewById(R.id.lastChkOut);
            lastChkOut.setText("Last Date Checked Out: " + lastChkOutVar);
        }

        Boolean isCheckedOutVar = deviceIntent.getBooleanExtra("isCheckedOut", true);
        btnCheckIn = (Button) findViewById(R.id.checkInButton);
        btnCheckIn.setOnClickListener(this);
        if(!isCheckedOutVar) {
            btnCheckIn.setText("Check-Out");

        }
        else{
            btnCheckIn.setText("Check-In");
        }

    }


  @Override
        public void onClick(View v) {
      Button button = (Button) findViewById(R.id.checkInButton);
      initiateAlertWindow();

  }
    private void initiateAlertWindow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Name");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
