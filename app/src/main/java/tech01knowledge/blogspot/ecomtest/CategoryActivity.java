package tech01knowledge.blogspot.ecomtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import static tech01knowledge.blogspot.ecomtest.DBqueries.lists;
import static tech01knowledge.blogspot.ecomtest.DBqueries.loadFragmentData;
import static tech01knowledge.blogspot.ecomtest.DBqueries.loadedCategoriesNames;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerview;
    private List<HomePageModel> homePageModelFakeList = new ArrayList<>();

    private HomePageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryRecyclerview = findViewById(R.id.category_recyclerview);


//        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();

//        sliderModelList.add(new SliderModel(R.mipmap.bell, "#077AE4"));
//        sliderModelList.add(new SliderModel(R.mipmap.rewards, "#077AE4"));
//        sliderModelList.add(new SliderModel(R.mipmap.greenemail, "#077AE4"));
//
//        sliderModelList.add(new SliderModel(R.mipmap.email, "#077AE4"));
//        sliderModelList.add(new SliderModel(R.mipmap.heart, "#077AE4"));
//        sliderModelList.add(new SliderModel(R.mipmap.cart, "#077AE4"));
//        sliderModelList.add(new SliderModel(R.mipmap.order,"#077AE4"));
//        sliderModelList.add(new SliderModel(R.mipmap.bell, "#077AE4"));
//
//        sliderModelList.add(new SliderModel(R.drawable.banner, "#077AE4"));
//        sliderModelList.add(new SliderModel(R.mipmap.greenemail, "#077AE4"));
//        sliderModelList.add(new SliderModel(R.mipmap.email, "#077AE4"));



//        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob1, "Redmi 5", "S D 425", "Rs.9,999"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob2, "Redmi 5", "S D 425", "Rs.9,999"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob3, "Redmi 5", "S D 425", "Rs.9,999"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob4, "Redmi 5", "S D 425", "Rs.9,999"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob5, "Redmi 5", "S D 425", "Rs.9,999"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.bazzar, "Redmi 5", "S D 425", "Rs.9,999"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.close8, "Redmi 5", "S D 425", "Rs.9,999"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.alert, "Redmi 5", "S D 425", "Rs.9,999"));

        LinearLayoutManager testingLayoutManager =  new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerview.setLayoutManager(testingLayoutManager);



//        homePageModelList.add(new HomePageModel(0, sliderModelList));
//        homePageModelList.add(new HomePageModel(1, R.drawable.ad, "#000000"));
//        homePageModelList.add(new HomePageModel(2,  "DEALS OF THE DAY", horizontalProductScrollModelList));
//        homePageModelList.add(new HomePageModel(3,  "DEALS OF THE DAY", horizontalProductScrollModelList));
//
//        homePageModelList.add(new HomePageModel(0, sliderModelList));
//        homePageModelList.add(new HomePageModel(1, R.drawable.banner, "#ffff00"));
//
//        homePageModelList.add(new HomePageModel(2,  "DEALS OF THE DAY", horizontalProductScrollModelList));
//        homePageModelList.add(new HomePageModel(2,  "DEALS OF THE DAY", horizontalProductScrollModelList));
//
//
//        homePageModelList.add(new HomePageModel(0, sliderModelList));
//        homePageModelList.add(new HomePageModel(1, R.drawable.ad, "#ff0000"));
        adapter = new HomePageAdapter(homePageModelFakeList);


        int listPosition=0;
        for(int x =0;x<loadedCategoriesNames.size();x++){
            if(loadedCategoriesNames.get(x).equals(title.toUpperCase())){
                listPosition=x;
            }
        }
        if(listPosition==0){
            loadedCategoriesNames.add(title.toUpperCase());
            lists.add(new ArrayList<HomePageModel>()); ///.toUpperCase()
            adapter = new HomePageAdapter(lists.get(loadedCategoriesNames.size()-1));
            DBqueries.loadFragmentData(categoryRecyclerview, this,loadedCategoriesNames.size()-1,title);
        }else{
            adapter = new HomePageAdapter(lists.get(listPosition));
        }
        categoryRecyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.main_search_icon) {
            Intent searchIntent = new Intent(this, SearchActivity.class);
            startActivity(searchIntent);
            return true;
        } else if (id == R.id.home) {
            finish();
            return true;
        } else if (id == R.id.main_cart_icon) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
