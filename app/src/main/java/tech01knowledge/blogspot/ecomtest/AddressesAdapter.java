package tech01knowledge.blogspot.ecomtest;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tech01knowledge.blogspot.ecomtest.DeliveryActivity.SELECT_ADDRESS;
import static tech01knowledge.blogspot.ecomtest.MyAccountFragment.MANAGE_ADDRESS;
import static tech01knowledge.blogspot.ecomtest.MyAddressesActivity.refreshItem;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.ViewHolder> {

    private List<AddressModel> addressModelList;
    private int MODE;
    private int preSelectedPosition;
    private boolean refresh = false;
    private Dialog loadingDialog;

    public AddressesAdapter(List<AddressModel> addressModelList, int MODE, Dialog loadingDialog) {
        this.addressModelList = addressModelList;
        this.MODE = MODE;
        preSelectedPosition = DBqueries.selectedAddress;
        this.loadingDialog = loadingDialog;
    }

    @NonNull
    @Override
    public AddressesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.addresses_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressesAdapter.ViewHolder viewHolder, int position) {

       String city = addressModelList.get(position).getCity();
       String locality  = addressModelList.get(position).getLocality();
       String flatNo  = addressModelList.get(position).getFlatNo();
       String pincode  = addressModelList.get(position).getPincode();
       String landmark  = addressModelList.get(position).getLandmark();
       String name  = addressModelList.get(position).getName();
       String mobileNo  = addressModelList.get(position).getMobileNo();
       String alternateMobileNo  = addressModelList.get(position).getAlternateMobileNo();
       String state  = addressModelList.get(position).getState();
       boolean selected = addressModelList.get(position).getSelected();

       viewHolder.setData(name, city, pincode, selected, position, mobileNo, alternateMobileNo, flatNo, locality, state, landmark);


    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView fullname;
        private TextView address;
        private TextView pincode;
        private ImageView icon;
        private LinearLayout optionContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fullname = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            pincode = itemView.findViewById(R.id.pincode);
            icon = itemView.findViewById(R.id.icon_view);
            optionContainer = itemView.findViewById(R.id.option_container);

        }

        private void setData(String username, String city, String userPincode, Boolean selected, final int position, String mobileNo, String alternateMobileNo, String flatNo, String locality, String state, String landmark) {
            if (alternateMobileNo.equals("")) {
                fullname.setText(username + " - " + mobileNo);
            } else {
                fullname.setText(username + " - " + mobileNo + " or " + alternateMobileNo);

            }

            if (landmark.equals("")) {
                address.setText(flatNo +" " + locality +" " + city +" " + state);

            } else {
                address.setText(flatNo +" " + locality +" " + landmark +" " + city +" " + state);
            }
            pincode.setText(userPincode);

            if (MODE == SELECT_ADDRESS) {

                icon.setImageResource(R.mipmap.check2);
                if (selected){
                    icon.setVisibility(View.VISIBLE);
                    optionContainer.setVisibility(View.INVISIBLE);
                    preSelectedPosition = position;
                } else {
                    optionContainer.setVisibility(View.INVISIBLE);
                    icon.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (preSelectedPosition != position) {
                        addressModelList.get(position).setSelected(true);
                        addressModelList.get(preSelectedPosition).setSelected(false);
                        refreshItem(preSelectedPosition,position);
                        preSelectedPosition = position;
                        DBqueries.selectedAddress = position;
                        }
                    }
                });

            } else if (MODE == MANAGE_ADDRESS) {
                optionContainer.setVisibility(View.GONE);
                icon.setImageResource(R.mipmap.dot);   /// changed
                optionContainer.getChildAt(0).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { /////////edit address
                        Intent addAddressIntent = new Intent(itemView.getContext(), AddAddressActivity.class);
                        addAddressIntent.putExtra("INTENT", "update_address");
                        addAddressIntent.putExtra("index", position);
                        itemView.getContext().startActivity(addAddressIntent);
                    }
                });

                optionContainer.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { ///////remove address
                        loadingDialog.show();

                        Map<String, Object> address = new HashMap<>();
                        int x = 0;
                        int selected = -1;
                        for (int i=0; i < addressModelList.size() +1; i++) {
                            if (i != position) {
                                x++;
                                address.put("city_" + x, addressModelList.get(i).getCity());
                                address.put("locality_"+ x, addressModelList.get(i).getLocality());
                                address.put("flatNo_" + x, addressModelList.get(i).getFlatNo());
                                address.put("pincode_" + x, addressModelList.get(i).getPincode());
                                address.put("landmark_" + x, addressModelList.get(i).getLandmark());
                                address.put("name_" + x, addressModelList.get(i).getName());
                                address.put("mobile_no_" + x, addressModelList.get(i).getMobileNo());
                                address.put("alternate_mobile_no_" + x, addressModelList.get(i).getAlternateMobileNo());
                                address.put("state_" + x, addressModelList.get(i).getState());

                                if (addressModelList.get(position).getSelected()) {
                                    if (position -1 >= 0) {
                                        if (x == position) {
                                            address.put("selected_" + x, true);
                                            selected = x;

                                        } else {
                                            address.put("selected_" + x, addressModelList.get(i).getSelected());
                                        }
                                    } else {
                                        if (x == 1) {
                                            address.put("selected_" + x, true);
                                            selected = x;

                                        } else {
                                            address.put("selected_" + x, addressModelList.get(i).getSelected());

                                        }
                                    }
                                } else {
                                    address.put("selected_" + x, addressModelList.get(i).getSelected());
                                    if (addressModelList.get(i).getSelected()){
                                        selected = x;
                                    }

                                }

                            }
                        }
                        address.put("list_size", x);
                        final int finalSelected = selected;
                        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_ADDRESSES")
                                .set(address).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    DBqueries.addressModelList.remove(position);
                                    if (finalSelected != -1) {
                                        DBqueries.selectedAddress = finalSelected - 1;
                                        DBqueries.addressModelList.get(finalSelected - 1).setSelected(true);
                                    }
                                    else if (DBqueries.addressModelList.size() == 0) {
                                        DBqueries.selectedAddress = -1;
                                    }
                                    notifyDataSetChanged();
                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(itemView.getContext(), error, Toast.LENGTH_SHORT).show();
                                }
                                loadingDialog.dismiss();
                            }
                        });
                        refresh = false;

                    }
                });
                icon.setImageResource(R.mipmap.more1);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        optionContainer.setVisibility(View.VISIBLE);
                        if (refresh) {
                            refreshItem(preSelectedPosition, preSelectedPosition);
                        } else {
                            refresh = true;
                        }
                        preSelectedPosition = position;
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refreshItem(preSelectedPosition, preSelectedPosition);
                        preSelectedPosition = -1;
                    }
                });
            }
        }
    }
}
