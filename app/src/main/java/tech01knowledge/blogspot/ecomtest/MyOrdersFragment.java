package tech01knowledge.blogspot.ecomtest;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersFragment extends Fragment {


    public MyOrdersFragment() {
        // Required empty public constructor
    }

    private RecyclerView myOrdersRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        myOrdersRecyclerView = view.findViewById(R.id.my_orders_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        myOrdersRecyclerView.setLayoutManager(layoutManager);

        List<MyOrderItemModel> myOrderItemModelList = new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.mob4, "Redmi Note 5(GOLD",3,
                "Delivered on Monday 17th Feb 2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.mob3, "Redmi Note 5(GOLD",2,
                "Delivered on Monday 17th Feb 2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.mob5, "Redmi Note 5(GOLD",1,
                "Cancelled"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.mob2, "Redmi Note 5(GOLD",4,
                "Delivered on Monday 17th Feb 2020"));

        MyOrderAdapter myOrderAdapter = new MyOrderAdapter(myOrderItemModelList);
        myOrdersRecyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();
        return view;
    }

}
