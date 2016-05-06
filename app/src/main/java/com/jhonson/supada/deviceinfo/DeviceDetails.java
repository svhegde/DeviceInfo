/**
 * DeviceDetails.java gives all the information about device and allows the user to checkIn/checkOut the device.
 *
 * @author Supada Hegde
 * @version 1.0
 */

package com.jhonson.supada.deviceinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.EditText;
import android.content.DialogInterface;
import android.text.InputType;
import android.app.AlertDialog;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DeviceDetails extends AppCompatActivity implements View.OnClickListener {
    private TextView device,os,mft,user,lastChkOut;
    Boolean isCheckedOutVar = false;
    int deviceId;
    Button btnCheckIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_details);
        Intent deviceIntent = getIntent();

        deviceId = deviceIntent.getIntExtra("id", 0);
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

        isCheckedOutVar = deviceIntent.getBooleanExtra("isCheckedOut", true);
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
      Intent intent = new Intent(DeviceDetails.this, MainActivity.class);
      initiateAlertWindow();
  }

    private void initiateAlertWindow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Name");

    // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

    // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                user = (TextView) findViewById(R.id.user);
                user.setText("Last user CheckedOut: " + m_Text);
                final String strDevice = device.getText().toString();
                final String strOs = os.getText().toString();
                final String strManufacturer = mft.getText().toString();
                final String strUser= user.getText().toString();

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                final String strLastCheckedOut = df.format(c.getTime());
                //final String strLastCheckedOut = lastChkOut.getText().toString();
                Device postD = new Device();
                postD.setIsCheckedOut(isCheckedOutVar);
                postD.setLastCheckedOutBy(strUser);
                postD.setLastCheckedOutDate(strLastCheckedOut);

                DeviceAPI.Factory.getInstance().updateDevice(deviceId, postD).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("RESPPP", response.message());
                        if (response.isSuccessful()) {
                            setContentView(R.layout.device_details);
                            device = (TextView) findViewById(R.id.device);
                            device.setText(strDevice);
                            os = (TextView) findViewById(R.id.os);
                            os.setText(strOs);
                            mft = (TextView) findViewById(R.id.mft);
                            mft.setText(strManufacturer);
                            user = (TextView) findViewById(R.id.user);
                            user.setText(strUser);
                            lastChkOut = (TextView) findViewById(R.id.lastChkOut);
                            lastChkOut.setText("Last Date Checked Out: " + strLastCheckedOut);
                            Log.d("POST", response.message());
                            Toast.makeText(DeviceDetails.this, " Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            int statusCode = response.code();

                            // handle request errors
                            ResponseBody errorBody = response.errorBody();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("RESP", t.getStackTrace().toString());
                        Log.e("POST ERROR", t.getMessage());
                    }
                });
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
