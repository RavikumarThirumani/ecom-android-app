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
public class MyWishlistFragment extends Fragment {


    public MyWishlistFragment() {
        // Required empty public constructor
    }

    private RecyclerView wishlistRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wishlist, container, false);

        wishlistRecyclerView = view.findViewById(R.id.my_wishlist_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        wishlistRecyclerView.setLayoutManager(linearLayoutManager);

        List<WishlistModel> wishlistModelList = new ArrayList<>();
//        wishlistModelList.add(new WishlistModel(R.drawable.mob3,"Redmi 5",1, "3",32,"Rs.49,999","Rs.59,999","Cash on delivery"));
//        wishlistModelList.add(new WishlistModel(R.drawable.mob3,"Redmi 5",0, "3",32,"Rs.49,999","Rs.59,999","Cash on delivery"));
//        wishlistModelList.add(new WishlistModel(R.drawable.mob3,"Redmi 5",2, "3",32,"Rs.49,999","Rs.59,999","Cash on delivery"));
//        wishlistModelList.add(new WishlistModel(R.drawable.mob3,"Redmi 5",4, "3",32,"Rs.49,999","Rs.59,999","Cash on delivery"));
//        wishlistModelList.add(new WishlistModel(R.drawable.mob3,"Redmi 5",1, "3",32,"Rs.49,999","Rs.59,999","Cash on delivery"));

        WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModelList,true);
        wishlistRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
        return view;
    }

}
