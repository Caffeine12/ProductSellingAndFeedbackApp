package shopping.reloaded.com.shoppingapp.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import shopping.reloaded.com.shoppingapp.R;
import shopping.reloaded.com.shoppingapp.adapters.ViewPagerAdapter;
import shopping.reloaded.com.shoppingapp.fragments.FeedbackList;
import shopping.reloaded.com.shoppingapp.fragments.ProductEdit;
import shopping.reloaded.com.shoppingapp.fragments.Product_Add;

public class SellerHomeScreen extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home_screen);
        initialize();

        setDataViewManager();
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initialize() {
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tabLayout2);
        viewPager = findViewById(R.id.viewPager2);
    }

    private void setDataViewManager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // add tabs
        viewPagerAdapter.addFragment(new Product_Add(),"ADD PRODUCT");
        viewPagerAdapter.addFragment(new ProductEdit(),"PRODUCTS");
        viewPagerAdapter.addFragment(new FeedbackList(),"FEEDBACKS");

        viewPager.setAdapter(viewPagerAdapter);
    }
}
