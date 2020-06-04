package tech01knowledge.blogspot.ecomtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


public class VIewAllActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridView gridView;
    public  static List<WishlistModel> wishlistModelList;

    public static  List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        gridView = findViewById(R.id.grid_view);

        int layout_code = getIntent().getIntExtra("layout_code", -1);

        if (layout_code == 0) {
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
//
//            List<WishlistModel> wishlistModelList = new ArrayList<>();
//            wishlistModelList.add(new WishlistModel(R.drawable.mob3, "Redmi 5", 1, "3", 32, "Rs.49,999", "Rs.59,999", "Cash on delivery"));
//            wishlistModelList.add(new WishlistModel(R.drawable.mob3, "Redmi 5", 0, "3", 32, "Rs.49,999", "Rs.59,999", "Cash on delivery"));
//            wishlistModelList.add(new WishlistModel(R.drawable.mob3, "Redmi 5", 2, "3", 32, "Rs.49,999", "Rs.59,999", "Cash on delivery"));
//            wishlistModelList.add(new WishlistModel(R.drawable.mob3, "Redmi 5", 4, "3", 32, "Rs.49,999", "Rs.59,999", "Cash on delivery"));
//            wishlistModelList.add(new WishlistModel(R.drawable.mob3, "Redmi 5", 1, "3", 32, "Rs.49,999", "Rs.59,999", "Cash on delivery"));
//            wishlistModelList.add(new WishlistModel(R.drawable.mob3, "Redmi 5", 2, "3", 32, "Rs.49,999", "Rs.59,999", "Cash on delivery"));
//            wishlistModelList.add(new WishlistModel(R.drawable.mob3, "Redmi 5", 4, "3", 32, "Rs.49,999", "Rs.59,999", "Cash on delivery"));
//            wishlistModelList.add(new WishlistModel(R.drawable.mob3, "Redmi 5", 1, "3", 32, "Rs.49,999", "Rs.59,999", "Cash on delivery"));
//            wishlistModelList.add(new WishlistModel(R.drawable.mob3, "Redmi 5", 2, "3", 32, "Rs.49,999", "Rs.59,999", "Cash on delivery"));
//            wishlistModelList.add(new WishlistModel(R.drawable.mob3, "Redmi 5", 4, "3", 32, "Rs.49,999", "Rs.59,999", "Cash on delivery"));
//            wishlistModelList.add(new WishlistModel(R.drawable.mob3, "Redmi 5", 1, "3", 32, "Rs.49,999", "Rs.59,999", "Cash on delivery"));
            WishlistAdapter adapter = new WishlistAdapter(wishlistModelList, false);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else if (layout_code == 1) {
            gridView.setVisibility(View.VISIBLE);
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob1, "Redmi 5", "S D 425", "Rs.9,999"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob2, "Redmi 5", "S D 425", "Rs.9,999"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob3, "Redmi 5", "S D 425", "Rs.9,999"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob4, "Redmi 5", "S D 425", "Rs.9,999"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob5, "Redmi 5", "S D 425", "Rs.9,999"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.bazzar, "Redmi 5", "S D 425", "Rs.9,999"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.close8, "Redmi 5", "S D 425", "Rs.9,999"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.alert, "Redmi 5", "S D 425", "Rs.9,999"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob5, "Redmi 5", "S D 425", "Rs.9,999"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.bazzar, "Redmi 5", "S D 425", "Rs.9,999"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.close8, "Redmi 5", "S D 425", "Rs.9,999"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.alert, "Redmi 5", "S D 425", "Rs.9,999"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob2, "Redmi 5", "S D 425", "Rs.9,999"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob3, "Redmi 5", "S D 425", "Rs.9,999"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.mob4, "Redmi 5", "S D 425", "Rs.9,999"));
            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(horizontalProductScrollModelList);
            gridView.setAdapter(gridProductLayoutAdapter);

        }
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
