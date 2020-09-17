package tech01knowledge.blogspot.ecomtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button saveBtn;

    private EditText city;
    private EditText locality;
    private EditText flatNo;
    private EditText pincode;
    private EditText landmark;
    private EditText name;
    private EditText mobileNo;
    private EditText alternateMobileNo;
    private Spinner stateSpinner;


    private String [] stateList;
    private String selectedState;

    private Dialog loadingDialog;
    private boolean updateAddress = false;
    private AddressModel addressModel;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);          // back arrow button
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Add Address");

        //// LOading Dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ///Loading dialog

        stateList = getResources().getStringArray(R.array.state);

        city = findViewById(R.id.city);
        locality = findViewById(R.id.locality);
        flatNo = findViewById(R.id.flat_no);
        pincode = findViewById(R.id.pincode);
        landmark = findViewById(R.id.landmark);
        name = findViewById(R.id.name);
        mobileNo = findViewById(R.id.mobile_no);
        alternateMobileNo = findViewById(R.id.alternate_mobile_no);
        stateSpinner = findViewById(R.id.stateSpinner);

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,stateList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stateSpinner.setAdapter(spinnerAdapter);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedState = stateList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (getIntent().getStringExtra("INTENT").equals("update_address")) {
            updateAddress = true;
            position = getIntent().getIntExtra("index", -1);
            addressModel = DBqueries.addressModelList.get(position);

            city.setText(addressModel.getCity());
            locality.setText(addressModel.getLocality());
            flatNo.setText(addressModel.getFlatNo());
            landmark.setText(addressModel.getLandmark());
            name.setText(addressModel.getName());
            mobileNo.setText(addressModel.getMobileNo());
            alternateMobileNo.setText(addressModel.getAlternateMobileNo());
            pincode.setText(addressModel.getPincode());

            for (int i = 0; i < stateList.length; i++) {
                if (stateList[i].equals(addressModel.getState()));
                {
                    stateSpinner.setSelection(i);
                }
            }
//            saveBtn.setText("Update");
        } else {
            position = DBqueries.addressModelList.size();
        }
        saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(city.getText())) {
                    if (!TextUtils.isEmpty(locality.getText())) {
                        if (!TextUtils.isEmpty(flatNo.getText())) {
                            if (!TextUtils.isEmpty(pincode.getText() )&& pincode.getText().length() == 6) {
                                if (!TextUtils.isEmpty(name.getText())) {
                                    if (!TextUtils.isEmpty(mobileNo.getText()) && mobileNo.getText().length() == 10) {

                                        loadingDialog.show();

                                        final String fullAddress = flatNo.getText().toString() + " " + locality.getText().toString() + " " + landmark.getText().toString() + " " +city.getText().toString() + " " + selectedState;

                                        Map<String, Object> addAddress = new HashMap();

                                        addAddress.put("city_" + String.valueOf (position + 1), city.getText().toString());
                                        addAddress.put("locality_" + String.valueOf (position + 1),  locality.getText().toString());
                                        addAddress.put("flatNo_" + String.valueOf (position + 1),  flatNo.getText().toString());
                                        addAddress.put("pincode_" + String.valueOf (position + 1), pincode.getText().toString());
                                        addAddress.put("landmark_" + String.valueOf (position + 1),  landmark.getText().toString());
                                        addAddress.put("name_" + String.valueOf(position + 1), name.getText().toString());
                                        addAddress.put("mobile_no_" + String.valueOf (position + 1),  mobileNo.getText().toString());
                                        addAddress.put("alternate_mobile_no_" + String.valueOf (position + 1),  alternateMobileNo.getText().toString());
                                        addAddress.put("state_" + String.valueOf (position + 1),  selectedState);

                                        if (!updateAddress) {
                                            addAddress.put("list_size", (long) DBqueries.addressModelList.size() + 1);
                                            if (getIntent().getStringExtra("INTENT").equals("manage")){
                                                if (DBqueries.addressModelList.size() == 0) {
                                                    addAddress.put("selected_" + String.valueOf(position + 1), true);

                                                } else {
                                                    addAddress.put("selected_" + String.valueOf(position + 1), false);

                                                }
                                            } else {
                                                addAddress.put("selected_" + String.valueOf(position + 1), true);
                                            }
                                            if (DBqueries.addressModelList.size() > 0) {
                                                addAddress.put("selected_" + (DBqueries.selectedAddress + 1), false);
                                            }
                                        }
                                        FirebaseFirestore.getInstance().collection("USERS")
                                                .document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                                                .document("MY_ADDRESSES")
                                                .update(addAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    if(!updateAddress) {
                                                        if (DBqueries.addressModelList.size() > 0) {
                                                            DBqueries.addressModelList.get(DBqueries.selectedAddress).setSelected(false);
                                                        }
                                                        DBqueries.addressModelList.add(new AddressModel(true, city.getText().toString(),locality.getText().toString(), flatNo.getText().toString(),pincode.getText().toString(),landmark.getText().toString(),name.getText().toString(), mobileNo.getText().toString(), alternateMobileNo.getText().toString(), selectedState));

                                                        if (getIntent().getStringExtra("INTENT").equals("manage")){
                                                            if (DBqueries.addressModelList.size() == 0) {
                                                                DBqueries.selectedAddress = DBqueries.addressModelList.size() - 1;

                                                            }
                                                        } else {
                                                            DBqueries.selectedAddress = DBqueries.addressModelList.size() - 1;

                                                        }
                                                    } else {
                                                        DBqueries.addressModelList.set(position,new AddressModel(true, city.getText().toString(),locality.getText().toString(), flatNo.getText().toString(),pincode.getText().toString(),landmark.getText().toString(),name.getText().toString(), mobileNo.getText().toString(), alternateMobileNo.getText().toString(), selectedState));

                                                    }
                                                    if (getIntent().getStringExtra("INTENT").equals("deliveryIntent")) {
                                                        Intent deliveryIntent = new Intent(AddAddressActivity.this, DeliveryActivity.class);
                                                        startActivity(deliveryIntent);
                                                    } else {
                                                        MyAddressesActivity.refreshItem(DBqueries.selectedAddress, DBqueries.addressModelList.size() -1);
                                                    }
                                                    finish();
                                                } else {

                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(AddAddressActivity.this, error, Toast.LENGTH_SHORT).show();
                                                }
                                                loadingDialog.dismiss();
                                            }
                                        });
                                    } else {
                                        mobileNo.requestFocus();
                                        Toast.makeText(AddAddressActivity.this, "Please provide valid Mobile number", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    name.requestFocus();
                                }
                            } else {
                                pincode.requestFocus();
                                Toast.makeText(AddAddressActivity.this, "Please provide valid pincode", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            flatNo.requestFocus();
                        }
                    }else {
                        locality.requestFocus();
                    }
                } else {
                    city.requestFocus();
                }


            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
