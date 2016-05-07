/**
 * AddDevice.java allows the user to add a new device.
 *
 * @author Supada Hegde
 * @version 1.0
 */

package com.jhonson.supada.deviceinfo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;
import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDevice extends AppCompatActivity implements View.OnClickListener {
    private EditText device;
    private EditText os;
    private EditText manufacturer;
    public int listCount;
    DatabaseHelper myDb = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addphone);
        Intent mainIntent = getIntent();

        device = (EditText) findViewById(R.id.editText);

        os = (EditText) findViewById(R.id.editText2);

        manufacturer = (EditText) findViewById(R.id.editText3);

        Button button = (Button) findViewById(R.id.savebutton);
        button.setOnClickListener(this);
    }

    @Override
    //validating all 3 fields
    public void onClick(View v) {
        final String strDevice = device.getText().toString();
        final String strOs = os.getText().toString();
        final String strMft = manufacturer.getText().toString();

        if (TextUtils.isEmpty(strDevice)) {
            device.setError("Please enter Device name");

        }
        if (TextUtils.isEmpty(strOs)) {
            os.setError("Please enter Operating system name");

        }
        if (TextUtils.isEmpty(strMft)) {
            manufacturer.setError("Please enter Manufacturer name");

        } else {
            final Intent intent = new Intent(AddDevice.this, MainActivity.class);
            listCount = myDb.numberOfRows();
            Device postD = new Device();
            postD.setDevice(strDevice);
            postD.setOs(strOs);
            postD.setManufacturer(strMft);

            intent.putExtra("device", strDevice);
            intent.putExtra("os", strOs);
            intent.putExtra("mft", strMft);
            DeviceAPI.Factory.getInstance().postDevice(postD).enqueue(new Callback<Device>() {
                @Override
                public void onResponse(Call<Device> call, Response<Device> response) {
                    if (response.isSuccessful()) {
                        Device deviceResp = response.body();
                        Boolean ins = myDb.insertDevice(
                                Integer.toString(listCount),
                                strDevice,
                                strOs, strMft,
                                null, null, "false"
                        );
                    } else {
                        int statusCode = response.code();

                        // handle request errors
                        ResponseBody errorBody = response.errorBody();
                    }
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Device> call, Throwable t) {
                    Log.e("POST ERROR", t.getMessage());
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        (new AlertDialog.Builder(this))
                .setTitle("Confirm action")
                .setMessage("You will loose all the data. Do you want to proceed?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent in = new Intent(AddDevice.this, MainActivity.class);
                        startActivity(in);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}


