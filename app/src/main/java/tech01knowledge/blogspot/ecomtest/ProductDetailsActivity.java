package tech01knowledge.blogspot.ecomtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static tech01knowledge.blogspot.ecomtest.MainActivity.showCart;


public class ProductDetailsActivity extends AppCompatActivity {

    /*********** PRODUCT DESCRIPTION ******/
    private ConstraintLayout productDetailsOnlyContainer;
    private ConstraintLayout productDetailsTabsContainer;
    private ViewPager productImagesViewPager;
    private TabLayout viewpagerIndicator;
    private TextView productOnlyDescriptionBody;
    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();

    private String productDescription;
    private String productOtherDetails;

    /*********** PRODUCT DESCRIPTION ******/

    private TextView productTitle;
    private TextView averageRatingMiniView;
    private TextView totalRatingMiniView;
    private TextView productPrice;
    private TextView cuttedPrice;
    private ImageView codIndicator;
    private TextView tvCodIndicator;
    private TabLayout viewPagerIndicator;
    private TextView averageRating;

    private Button coupenRedeemBtn;

    private TextView rewardTitle;
    private TextView rewardBody;


    private TabLayout productDetailsTablayout;

    private Button buyNowBtn;

    /*********** RATINGS LAYOUT ******/
    private LinearLayout rateNowContainer;
    private TextView totalRatings;
    private LinearLayout ratingsNoContainer;
    private LinearLayout ratingsProgressBarContainer;
    private TextView totalRatingsFig;

    /*********** RATINGS LAYOUT ******/



    private FloatingActionButton addToWishlisttBtn;
    private static boolean ALREDY_ADDED_TO_WISHLIST = false;

    private FirebaseFirestore firebaseFirestore;
    // Coupen Dialog
    public static TextView coupenTitle;
    public static TextView coupenExpiryDate;
    public static TextView coupenBody;
    private static RecyclerView coupenRecyclerView;
    private static LinearLayout selectedCoupen;
    // coupen Dialg
    private ViewPager productsDetailsViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);          // sets back button

        productImagesViewPager = findViewById(R.id.product_images_viewpager);
        viewpagerIndicator = findViewById(R.id.viewpager_indicator);
        buyNowBtn = findViewById(R.id.buy_now_btn);
        addToWishlisttBtn= findViewById(R.id.add_to_wishlist_btn);
        productsDetailsViewpager = findViewById(R.id.product_details_viewpager);
        productDetailsTablayout = findViewById(R.id.product_details_tablayout);
        coupenRedeemBtn = findViewById(R.id.coupon_redemtion_btn);
        productTitle = findViewById(R.id.product_title);
        averageRatingMiniView = findViewById(R.id.tv_product_rating_miniview);
        productPrice = findViewById(R.id.product_price);
        cuttedPrice = findViewById(R.id.cutted_price);
        codIndicator = findViewById(R.id.cod_indicator_imageview);
        tvCodIndicator = findViewById(R.id.tv_cod_indicator);
        rewardTitle = findViewById(R.id.reward_title);
        rewardBody = findViewById(R.id.reward_body);
        productDetailsTabsContainer=findViewById(R.id.product_details_tab_container);
        productDetailsOnlyContainer=findViewById(R.id.product_details_container);
        productOnlyDescriptionBody=findViewById(R.id.product_details_body);
        totalRatings=findViewById(R.id.total_ratings);
        ratingsNoContainer=findViewById(R.id.ratings_numbers_container);
        totalRatingsFig=findViewById(R.id.total_ratings_figure);
        ratingsProgressBarContainer=findViewById(R.id.ratings_progressbar_container);
        averageRating=findViewById(R.id.average_rating);


        firebaseFirestore = FirebaseFirestore.getInstance();
        final List<String> productImages = new ArrayList<>();

        firebaseFirestore.collection("PRODUCTS").document("GaHjO4b6mMK0ypXvwDRX").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();

                            for (long x = 1; x < (long) documentSnapshot.get("no_of_product_images") + 1; x++) {
                                productImages.add(documentSnapshot.get("product_image_" + x).toString());
                            }

                            ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                            // I think here one more line of code will take place
                            productImagesViewPager.setAdapter(productImagesAdapter);
                            productTitle.setText(documentSnapshot.get("product_title").toString());
                            averageRatingMiniView.setText(documentSnapshot.get("average_rating").toString());
                            totalRatingMiniView.setText("(" + (long) documentSnapshot.get("total_ratings") + ") ratings");
                            productPrice.setText("Rs." + documentSnapshot.get("product_price").toString() + "/-");
                            cuttedPrice.setText("Rs." + documentSnapshot.get("cutted_price").toString() + "/-");

                            if((boolean) documentSnapshot.get("COD")) {
                                codIndicator.setVisibility(View.VISIBLE);
                                tvCodIndicator.setVisibility(View.VISIBLE);
                            } else {
                                codIndicator.setVisibility(View.INVISIBLE);
                                tvCodIndicator.setVisibility(View.INVISIBLE);
                            }

                            rewardTitle.setText((long)documentSnapshot.get("free_coupons") + documentSnapshot.get("free_coupon_title").toString());
                            rewardBody.setText(documentSnapshot.get("free_coupon_body").toString());

                            if((boolean)documentSnapshot.get("use_tab_layout")){
                                productDetailsTabsContainer.setVisibility(View.VISIBLE);
                                productDetailsOnlyContainer.setVisibility(View.GONE);
                                productDescription = documentSnapshot.get("product_description").toString();
                                productOtherDetails = documentSnapshot.get("product_other_details").toString();
                                for(long x = 1;x<(long)documentSnapshot.get("total_spec_titles")+1;x++){
                                    productSpecificationModelList.add(new ProductSpecificationModel(0,documentSnapshot.get("spec_title_"+x).toString()));
                                    for(long y =1;y<(long)documentSnapshot.get("spec_title_"+x+"_total_fields")+1;y++){
                                        productSpecificationModelList.add(new ProductSpecificationModel(1,documentSnapshot.get("spec_title_"+x+"_field_"+y+"_name").toString(),documentSnapshot.get("spec_title_"+x+"_field_"+y+"_value").toString()));

                                    }
                                }
                            }else{
                                productDetailsTabsContainer.setVisibility(View.GONE);
                                productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                                productOnlyDescriptionBody.setText(documentSnapshot.get("product_description").toString());
                            }
                            totalRatings.setText((long)documentSnapshot.get("total_ratings")+"ratings");
                            for(int x=0;x<5;x++){
                                TextView ratings = (TextView) ratingsNoContainer.getChildAt(x);
                                ratings.setText(String.valueOf((long)documentSnapshot.get((5-x)+"_star")));
                                ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                                int maxProgress = Integer.parseInt(String.valueOf((long)documentSnapshot.get("total_ratings")));
                                progressBar.setMax(maxProgress);
                                progressBar.setProgress(Integer.parseInt(String.valueOf((long)documentSnapshot.get((5-x)+"_star"))));
                            }
                            totalRatingsFig.setText(String.valueOf((long)documentSnapshot.get("total_ratings")));
                            averageRating.setText(documentSnapshot.get("average_rating").toString());
                            productsDetailsViewpager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTablayout.getTabCount(), productDescription, productOtherDetails, productSpecificationModelList));


                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();

                        }
                    }
                });


//        List<Integer> productImages = new ArrayList<>();
//        productImages.add(R.drawable.mob3);
//        productImages.add(R.drawable.ad);
//        productImages.add(R.drawable.mob4);
//        productImages.add(R.drawable.mob2);

        viewpagerIndicator.setupWithViewPager(productImagesViewPager, true);

        addToWishlisttBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ALREDY_ADDED_TO_WISHLIST) {
                    ALREDY_ADDED_TO_WISHLIST = false;
                    addToWishlisttBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));


                } else {
                    ALREDY_ADDED_TO_WISHLIST = true;
                    addToWishlisttBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                }
            }
        });

        productsDetailsViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTablayout));
        productDetailsTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productImagesViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // rating layout

        rateNowContainer = findViewById(R.id.rate_now_container);

        for (int x =0;x<rateNowContainer.getChildCount();x++) {
            final int starPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setRating(starPosition);
                }
            });
        }

        //rating

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deliveryIntent = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                startActivity(deliveryIntent);
            }
        });

        // Coupen Dialog

        final Dialog checkCoupenPriceDialog = new Dialog(ProductDetailsActivity.this);
        checkCoupenPriceDialog.setContentView(R.layout.coupen_redeem_dialog);
        checkCoupenPriceDialog.setCancelable(true);
        checkCoupenPriceDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView toggleRecyclerView = checkCoupenPriceDialog.findViewById(R.id.toggle_recyclerview);
        coupenRecyclerView = checkCoupenPriceDialog.findViewById(R.id.coupens_recyclerview);
        selectedCoupen = checkCoupenPriceDialog.findViewById(R.id.selected_coupen);

        coupenTitle = checkCoupenPriceDialog.findViewById(R.id.coupen_title);
        coupenExpiryDate = checkCoupenPriceDialog.findViewById(R.id.coupen_validity);
        coupenBody = checkCoupenPriceDialog.findViewById(R.id.coupen_body);

        TextView originalPrice = checkCoupenPriceDialog.findViewById(R.id.original_price);
        TextView discountedPrice = checkCoupenPriceDialog.findViewById(R.id.discount_price);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        coupenRecyclerView.setLayoutManager(layoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("Cashback", "TIll 23 MAR 2020","GET 20% CASHBACK ON ANY PRODUCT ABOVE Rs.200/- AND BELOW Rs.3000/-"));
        rewardModelList.add(new RewardModel("Discount", "TIll 23 MAR 2020","GET 20% CASHBACK ON ANY PRODUCT ABOVE Rs.200/- AND BELOW Rs.3000/-"));
        rewardModelList.add(new RewardModel("But 1 GET 1 FREE", "TIll 23 MAR 2020","GET 20% CASHBACK ON ANY PRODUCT ABOVE Rs.200/- AND BELOW Rs.3000/-"));
        rewardModelList.add(new RewardModel("Cashback", "TIll 23 MAR 2020","GET 20% CASHBACK ON ANY PRODUCT ABOVE Rs.200/- AND BELOW Rs.3000/-"));
        rewardModelList.add(new RewardModel("Cashback", "TIll 23 MAR 2020","GET 20% CASHBACK ON ANY PRODUCT ABOVE Rs.200/- AND BELOW Rs.3000/-"));
        rewardModelList.add(new RewardModel("Cashback", "TIll 23 MAR 2020","GET 20% CASHBACK ON ANY PRODUCT ABOVE Rs.200/- AND BELOW Rs.3000/-"));
        rewardModelList.add(new RewardModel("Cashback", "TIll 23 MAR 2020","GET 20% CASHBACK ON ANY PRODUCT ABOVE Rs.200/- AND BELOW Rs.3000/-"));
        rewardModelList.add(new RewardModel("Cashback", "TIll 23 MAR 2020","GET 20% CASHBACK ON ANY PRODUCT ABOVE Rs.200/- AND BELOW Rs.3000/-"));
        rewardModelList.add(new RewardModel("Discount", "TIll 23 MAR 2020","GET 20% CASHBACK ON ANY PRODUCT ABOVE Rs.200/- AND BELOW Rs.3000/-"));
        rewardModelList.add(new RewardModel("But 1 GET 1 FREE", "TIll 23 MAR 2020","GET 20% CASHBACK ON ANY PRODUCT ABOVE Rs.200/- AND BELOW Rs.3000/-"));
        rewardModelList.add(new RewardModel("Cashback", "TIll 23 MAR 2020","GET 20% CASHBACK ON ANY PRODUCT ABOVE Rs.200/- AND BELOW Rs.3000/-"));

        MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(rewardModelList,true);
        coupenRecyclerView.setAdapter(myRewardsAdapter);
        myRewardsAdapter.notifyDataSetChanged();

        toggleRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogRecyclerView();
            }
        });

        // Coupen Dialog
        coupenRedeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkCoupenPriceDialog.show();
            }
        });


    }

    public static void showDialogRecyclerView() {
        if (coupenRecyclerView.getVisibility() == View.GONE) {
            coupenRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupen.setVisibility(View.GONE);
        } else {
            coupenRecyclerView.setVisibility(View.GONE);
            selectedCoupen.setVisibility(View.VISIBLE);
        }

    }
    private void setRating(int starPosition) {
        for (int x=0;x<rateNowContainer.getChildCount();x++) {
            ImageView starBtn = (ImageView)rateNowContainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
             if (x <= starPosition) {
                 starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
             }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.seaech_and_cart_icon, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.main_search_icon) {
            return true;
        } else if (id == R.id.main_cart_icon) {
            Intent cartIntent = new Intent(ProductDetailsActivity.this,MainActivity.class);
            showCart = true;
            startActivity(cartIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
