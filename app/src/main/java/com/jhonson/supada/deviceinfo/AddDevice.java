/**
 * AddDevice.java allows the user to add a new device.
 *
 * @author Supada Hegde
 * @version 1.0
 */

package com.jhonson.supada.deviceinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;

public class AddDevice extends AppCompatActivity implements View.OnClickListener {
    private EditText device;
    private EditText os;
    private EditText manufacturer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addphone);

        device = (EditText) findViewById(R.id.editText);

        os = (EditText) findViewById(R.id.editText2);

        manufacturer = (EditText) findViewById(R.id.editText3);

        Button button = (Button) findViewById(R.id.savebutton);
        button.setOnClickListener(this);
    }

    @Override
    //validating all 3 fields
    public void onClick(View v) {
        String strDevice = device.getText().toString();
        String strOs = os.getText().toString();
        String strMft = manufacturer.getText().toString();

        if (TextUtils.isEmpty(strDevice)) {
            device.setError("Please enter Device name");

        }
        if (TextUtils.isEmpty(strOs)) {
            os.setError("Please enter Operating system name");

        }
        if (TextUtils.isEmpty(strMft)) {
            manufacturer.setError("Please enter Manufacturer name");

        } else {
            Intent intent = new Intent(AddDevice.this, MainActivity.class);
            startActivity(intent);
        }

    }
}


