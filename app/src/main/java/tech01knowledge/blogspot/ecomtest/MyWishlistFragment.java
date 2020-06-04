package tech01knowledge.blogspot.ecomtest;


import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static tech01knowledge.blogspot.ecomtest.DBqueries.loadWishList;
import static tech01knowledge.blogspot.ecomtest.DBqueries.wishlistModelList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyWishlistFragment extends Fragment {


    public MyWishlistFragment() {
        // Required empty public constructor
    }

    private RecyclerView wishlistRecyclerView;
    private Dialog loadingDialog;
    public static WishlistAdapter wishlistAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wishlist, container, false);
        //// LOading Dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        ///Loading dialog

        wishlistRecyclerView = view.findViewById(R.id.my_wishlist_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        wishlistRecyclerView.setLayoutManager(linearLayoutManager);

//        wishlistModelList.add(new WishlistModel(R.drawable.mob3,"Redmi 5",1, "3",32,"Rs.49,999","Rs.59,999","Cash on delivery"));
//        wishlistModelList.add(new WishlistModel(R.drawable.mob3,"Redmi 5",0, "3",32,"Rs.49,999","Rs.59,999","Cash on delivery"));
//        wishlistModelList.add(new WishlistModel(R.drawable.mob3,"Redmi 5",2, "3",32,"Rs.49,999","Rs.59,999","Cash on delivery"));
//        wishlistModelList.add(new WishlistModel(R.drawable.mob3,"Redmi 5",4, "3",32,"Rs.49,999","Rs.59,999","Cash on delivery"));
//        wishlistModelList.add(new WishlistModel(R.drawable.mob3,"Redmi 5",1, "3",32,"Rs.49,999","Rs.59,999","Cash on delivery"));
        if (wishlistModelList.size() == 0) {
            DBqueries.wishList.clear();
            loadWishList(getContext(),loadingDialog, true);
        } else {
            loadingDialog.dismiss();
        }

        wishlistAdapter = new WishlistAdapter(wishlistModelList,true);
        wishlistRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
        return view;
    }

}
