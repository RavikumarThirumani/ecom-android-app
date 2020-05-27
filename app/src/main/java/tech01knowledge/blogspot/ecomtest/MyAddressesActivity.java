package tech01knowledge.blogspot.ecomtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import static tech01knowledge.blogspot.ecomtest.DeliveryActivity.SELECT_ADDRESS;

public class MyAddressesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView myAddressesRecyclerview;
    private static AddressesAdapter addressesAdapter;

    private Button deliverHereBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);          // back arrow button
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");

        myAddressesRecyclerview = findViewById(R.id.addrresses_recyclerview);
        deliverHereBtn = findViewById(R.id.deliver_here_btn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        myAddressesRecyclerview.setLayoutManager(layoutManager);

        List<AddressModel> addressModelList = new ArrayList<>();
        addressModelList.add(new AddressModel("Ravi Kumar", "dfgnvifybvfv idgfifvg yfgewydfg uygweyfgugwf yufdufy","848401",true));
        addressModelList.add(new AddressModel("Ravi Kumar", "dfgnvifybvfv idgfifvg yfgewydfg uygweyfgugwf yufdufy","848401",false));

        addressModelList.add(new AddressModel("Ravi Kumar", "dfgnvifybvfv idgfifvg yfgewydfg uygweyfgugwf yufdufy","848401", false));

        addressModelList.add(new AddressModel("Ravi Kumar", "dfgnvifybvfv idgfifvg yfgewydfg uygweyfgugwf yufdufy","848401", false));

        addressModelList.add(new AddressModel("Ravi Kumar", "dfgnvifybvfv idgfifvg yfgewydfg uygweyfgugwf yufdufy","848401", false));

        addressModelList.add(new AddressModel("Ravi Kumar", "dfgnvifybvfv idgfifvg yfgewydfg uygweyfgugwf yufdufy","848401", false));

        int mode = getIntent().getIntExtra("MODE",-1);
        if (mode == SELECT_ADDRESS) {
            deliverHereBtn.setVisibility(View.VISIBLE);
        } else deliverHereBtn.setVisibility(View.GONE);
        addressesAdapter = new AddressesAdapter(addressModelList,mode);
        myAddressesRecyclerview.setAdapter(addressesAdapter);
        ((SimpleItemAnimator)myAddressesRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();
    }

    public static void refreshItem(int deselect, int slecect) {
        addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(slecect);
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
