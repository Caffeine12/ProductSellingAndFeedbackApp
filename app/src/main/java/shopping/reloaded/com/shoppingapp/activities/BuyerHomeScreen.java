package shopping.reloaded.com.shoppingapp.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import shopping.reloaded.com.shoppingapp.R;
import shopping.reloaded.com.shoppingapp.adapters.ViewPagerAdapter;
import shopping.reloaded.com.shoppingapp.fragments.FoodFragment;
import shopping.reloaded.com.shoppingapp.fragments.FeedbackFragment;
import shopping.reloaded.com.shoppingapp.fragments.PopularFragment;
import shopping.reloaded.com.shoppingapp.fragments.Search_Feedback;

public class BuyerHomeScreen extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_home_screen);
        initialize();

        setDataViewManager();
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setDataViewManager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // add tabs
        viewPagerAdapter.addFragment(new PopularFragment(),"PRODUCTS");
        viewPagerAdapter.addFragment(new FeedbackFragment(),"FEEDBACK");
        viewPagerAdapter.addFragment(new Search_Feedback(),"Search Feedback");


        viewPager.setAdapter(viewPagerAdapter);
    }


    private void initialize() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
    }
}
