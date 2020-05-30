package tech01knowledge.blogspot.ecomtest;


import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static tech01knowledge.blogspot.ecomtest.DBqueries.categoryModelList;
import static tech01knowledge.blogspot.ecomtest.DBqueries.firebaseFirestore;
import static tech01knowledge.blogspot.ecomtest.DBqueries.lists;
import static tech01knowledge.blogspot.ecomtest.DBqueries.loadCategories;
import static tech01knowledge.blogspot.ecomtest.DBqueries.loadFragmentData;
import static tech01knowledge.blogspot.ecomtest.DBqueries.loadedCategoriesNames;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewHomeFragment extends Fragment {




    public NewHomeFragment() {
        // Required empty public constructor
    }
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    public static SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private List<categoryModel> categoryModelFakeList = new ArrayList<>();
    private RecyclerView homePageRecyclerView;
    private List<HomePageModel> homePageModelFakeList = new ArrayList<>();

    private HomePageAdapter adapter;

    private ImageView noInternetConnection;


    ////
//    private ViewPager bannerSliderViewPager;
//    private List<SliderModel> sliderModelList;   ############### delete this code
//
//    private int currentPage = 2;
//
//    private Timer timer;
//
//    final private  long DELAY_TIME = 3000;
//    final private long PERIOD_TIME = 3000;

    //////++++ sssssstrip
//    private ImageView stripAdImage;
//    private ConstraintLayout stripAdContainer;
//
//
//    ////////Strip ad layout

    //horizontal  product layout

    //    private TextView horizontalLayoutTitle;
    //
    //    private Button horizontalViewAllBtn;
    //
    //    private RecyclerView horizontalRecyclerView;1


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_home, container, false);
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        noInternetConnection = view.findViewById(R.id.no_internet_connection);
        categoryRecyclerView = view.findViewById(R.id.category_recyclerview);

        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary), getContext().getResources().getColor(R.color.colorPrimary), getContext().getResources().getColor(R.color.colorPrimary));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // or else recyclerview
        categoryRecyclerView.setLayoutManager(layoutManager);
        homePageRecyclerView = view.findViewById(R.id.home_page_recyclerview);

        LinearLayoutManager testingLayoutManager =  new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLayoutManager);

        categoryModelFakeList.add(new categoryModel("null",""));
        categoryModelFakeList.add(new categoryModel("null",""));
        categoryModelFakeList.add(new categoryModel("null",""));
        categoryModelFakeList.add(new categoryModel("null",""));
        categoryModelFakeList.add(new categoryModel("null",""));
        categoryModelFakeList.add(new categoryModel("null",""));
        categoryModelFakeList.add(new categoryModel("null",""));
        categoryModelFakeList.add(new categoryModel("null",""));
        categoryModelFakeList.add(new categoryModel("null",""));
        categoryModelFakeList.add(new categoryModel("null",""));
        //
        List<SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));

        List<HorizontalProductScrollModel> horizontalProductScrollModelFakeList = new ArrayList<>();
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));

        homePageModelFakeList.add(new HomePageModel(0,sliderModelFakeList));
        homePageModelFakeList.add(new HomePageModel(1,"",""));
        homePageModelFakeList.add(new HomePageModel(2,"","#ffffff",horizontalProductScrollModelFakeList,new ArrayList<WishlistModel>()));
        homePageModelFakeList.add(new HomePageModel(3,"","#ffffff",horizontalProductScrollModelFakeList));

        //
        categoryAdapter = new CategoryAdapter(categoryModelFakeList);
        adapter = new HomePageAdapter(homePageModelFakeList);
     // 1*   categoryRecyclerView.setAdapter(categoryAdapter);

//        adapter = new HomePageAdapter(lists.get(0));
     //2*   homePageRecyclerView.setAdapter(adapter);

        connectivityManager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
//
        if (networkInfo != null && networkInfo.isConnected() == true) {
            noInternetConnection.setVisibility(View.GONE);

//3*
            if (categoryModelList.size() == 0) {
                loadCategories(categoryRecyclerView, getContext());
            } else {
                categoryAdapter.notifyDataSetChanged();
            }
        categoryRecyclerView.setAdapter(categoryAdapter); //repasted 1*
            if (lists.size() == 0) {
                loadedCategoriesNames.add("HOME");
                lists.add(new ArrayList<HomePageModel>());

                loadFragmentData(homePageRecyclerView, getContext(),0, "Home");
            } else {
                adapter = new HomePageAdapter(lists.get(0));
                adapter.notifyDataSetChanged();
            }

        homePageRecyclerView.setAdapter(adapter); //2*
        } else {
            Glide.with(this).load(R.drawable.alert).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
        }


        //   ****** i changed few lines check videom i if any prob

        ///
//        bannerSliderViewPager = view.findViewById(R.id.banner_slider_view_pager);   #####delete

//        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();
//
//        sliderModelList.add(new SliderModel(R.mipmap.bell, "#077AE4"));
//        sliderModelList.add(new SliderModel(R.mipmap.rewards, "#077AE4"));
//
//        sliderModelList.add(new SliderModel(R.mipmap.greenemail, "#077AE4"));
//        sliderModelList.add(new SliderModel(R.mipmap.email, "#077AE4"));
//        sliderModelList.add(new SliderModel(R.mipmap.heart, "#077AE4"));
//        sliderModelList.add(new SliderModel(R.mipmap.cart, "#077AE4"));
//        sliderModelList.add(new SliderModel(R.mipmap.order,"#077AE4"));
//        sliderModelList.add(new SliderModel(R.mipmap.bell, "#077AE4"));
//        sliderModelList.add(new SliderModel(R.drawable.banner, "#077AE4"));
//
//        sliderModelList.add(new SliderModel(R.mipmap.greenemail, "#077AE4"));
//        sliderModelList.add(new SliderModel(R.mipmap.email, "#077AE4"));


//        SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);`                                    ########delete
//        bannerSliderViewPager.setAdapter(sliderAdapter);
//        bannerSliderViewPager.setClipToPadding(false);
//        bannerSliderViewPager.setPageMargin(20);
//        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                currentPage = position;
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                if (state == ViewPager.SCROLL_STATE_IDLE) {
//                    pageLooper();
//                }
//
//            }
//        };
//
//        bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);
//        /////
//
//        startBannerSlideShow();
//
//
//        // I think error here
//
//        bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                pageLooper();
//                stopBannerSlideShow();
//                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    stopBannerSlideShow();
//                }
//                return false;
//            }
//        });

//        // Strip ad                                                   ######delete
//        stripAdImage = view.findViewById(R.id.strip_ad_image);
//        stripAdContainer = view.findViewById(R.id.banner_container);
//
//        stripAdImage.setImageResource(R.drawable.ad);
//        stripAdImage.setBackgroundColor(Color.parseColor("#000000"));
//        //strip


        // Horizontal  Product Layout

//        horizontalLayoutTitle = view.findViewById(R.id.horizontal_scroll_layout_title);                       #######Delete
//        horizontalViewAllBtn = view.findViewById(R.id.horizontal_scrool_view_all_btn);
//        horizontalRecyclerView = view.findViewById(R.id.horizontal_scroll_layout_recyclerview);

//        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob1, "Redmi 5", "S D 425", "Rs.9,999"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob2, "Redmi 5", "S D 425", "Rs.9,999"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob3, "Redmi 5", "S D 425", "Rs.9,999"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob4, "Redmi 5", "S D 425", "Rs.9,999"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob5, "Redmi 5", "S D 425", "Rs.9,999"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.bazzar, "Redmi 5", "S D 425", "Rs.9,999"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.close8, "Redmi 5", "S D 425", "Rs.9,999"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.alert, "Redmi 5", "S D 425", "Rs.9,999"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.bazzar, "Redmi 5", "S D 425", "Rs.9,999"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.close8, "Redmi 5", "S D 425", "Rs.9,999"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.alert, "Redmi 5", "S D 425", "Rs.9,999"));


//        HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        horizontalRecyclerView.setLayoutManager(linearLayoutManager);
//                                                                                          ####delete
//        horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
//        horizontalProductScrollAdapter.notifyDataSetChanged();

        //

//        // Grid Product Layout
//
//        TextView gridLayoutTitle = view.findViewById(R.id.grid_product_layout_title);
//        Button gridLayoutViewAllBtn = view.findViewById(R.id.horizontal_scrool_view_all_btn);             ##delete
//        GridView gridView =  view.findViewById(R.id.grid_product_layout_gridview);
//
//        gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModelList));
//
//        // Grid Product Layout

        ///



//        homePageModelList.add(new HomePageModel(0, sliderModelList));
//        homePageModelList.add(new HomePageModel(1, R.drawable.ad, "#000000"));
//        homePageModelList.add(new HomePageModel(2,  "DEALS OF THE DAY", horizontalProductScrollModelList));
//        homePageModelList.add(new HomePageModel(1, R.drawable.banner, "#ffff00"));
//        homePageModelList.add(new HomePageModel(3,  "DEALS OF THE DAY", horizontalProductScrollModelList));
//
//
//        homePageModelList.add(new HomePageModel(1, R.drawable.banner, "#ffff00"));
//
//        homePageModelList.add(new HomePageModel(2,  "DEALS OF THE DAY", horizontalProductScrollModelList));
//        homePageModelList.add(new HomePageModel(2,  "DEALS OF THE DAY", horizontalProductScrollModelList));







        ///

        // refresh layout

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                categoryModelList.clear();
                lists.clear();
                loadedCategoriesNames.clear();
            if (networkInfo != null && networkInfo.isConnected() == true) {
                noInternetConnection.setVisibility(View.GONE);
                categoryAdapter = new CategoryAdapter(categoryModelFakeList);
                adapter = new HomePageAdapter(homePageModelFakeList);
                categoryRecyclerView.setAdapter(categoryAdapter);
                homePageRecyclerView.setAdapter(adapter);
                loadCategories(categoryRecyclerView,getContext());
                loadedCategoriesNames.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                loadFragmentData(homePageRecyclerView, getContext(),0, "Home");
            } else {

                Glide.with(getContext()).load(R.drawable.alert).into(noInternetConnection);
                noInternetConnection.setVisibility(View.VISIBLE);
            }
            }
        });
        return view;
    }
//    //
//    private void pageLooper() {
//        if (currentPage == sliderModelList.size() -2) {
//            currentPage = 2;
//            bannerSliderViewPager.setCurrentItem(currentPage, false);
//        }
//
//        if (currentPage == 1) {                                                                   #####delete
//            currentPage = sliderModelList.size() -3;
//            bannerSliderViewPager.setCurrentItem(currentPage, false);
//        }
//    }
//
//    private void startBannerSlideShow() {
//        final Handler handler = new Handler();
//        final Runnable update = new Runnable() {
//            @Override
//            public void run() {
//                if (currentPage >= sliderModelList.size()) {
//                    currentPage = 1;
//                }
//                bannerSliderViewPager.setCurrentItem(currentPage++  ,true);
//            }
//        };
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(update);
//
//            }
//        }, DELAY_TIME, PERIOD_TIME);
//    }
//
//    private void stopBannerSlideShow() {
//        timer.cancel();
//    }



    //

}
