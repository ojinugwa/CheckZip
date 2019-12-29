package com.example.checkzip;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private FirebaseAuth firebaseAuth;
    public static TextView info;
    private EditText zipcodeText;
    Button fetch;
    String zipcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        zipcodeText = (EditText) findViewById(R.id.zipcodeTxt);
        fetch = (Button) findViewById(R.id.fetchdataBtn);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Adding Fragments
        viewPagerAdapter.AddFragment(new WeatherFragment(), "Weather");
        viewPagerAdapter.AddFragment(new RepFragment(), "Official Elect");

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zipcode = zipcodeText.getText().toString().trim();

                if (validateSearchBarZipCode(zipcode) == true) {
                    fetchData process = new fetchData();
                    process.setZipcode(zipcode);
                    process.execute();
                    startActivity(new Intent(Home.this, zipcheck.class));
                } else {
                    Context context = Home.this;
                    String textToShow = "Enter a Valid 5 digit Zip Code";
                    Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();

                }
            }
        });


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //Adding the Icons
        tabLayout.getTabAt(0).setIcon(R.drawable.weather_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.rep_icon);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu item) {
        getMenuInflater().inflate(R.menu.main, item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.signOutBtn) {
            Context context = Home.this;
            String textToShow = "Signing Out";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();

            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(Home.this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This Method is used to validate the entry in the search Text Area
     * It takes the string and ensures it is only 5 characters
     * It Checks if individual characters are between 0 to 9, using ASCII code
     * If valid, returns true, if input isn't a valid zip-code, returns false
     */
    protected boolean validateSearchBarZipCode(String searchBoxContent) {

        if (searchBoxContent.length() != 5) {
            return false;
        } else {
            char[] brokenZip = searchBoxContent.toCharArray();
            for (int i = 0; i < brokenZip.length; i++) {
                if (((int) brokenZip[i] < 48) || ((int) brokenZip[i] > 57)) {
                    return false;
                }
            }
        }
        return true;
    }

}
