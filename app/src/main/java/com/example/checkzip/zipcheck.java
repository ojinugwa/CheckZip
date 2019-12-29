
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

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class zipcheck extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button logout;

    public static TextView age;
    private EditText zipcodeText;
    Button fetch;
    Menu signOut;

    String zipcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zipcheck);

        firebaseAuth = FirebaseAuth.getInstance();
        //logout = (Button)findViewById(R.id.signoutBtn);
        signOut = findViewById(R.id.signOutBtn);


        age = (TextView) findViewById(R.id.testAge);
        zipcodeText = (EditText) findViewById(R.id.zipcodeTxt);
        fetch = (Button) findViewById(R.id.fetchdataBtn);
        zipcode = zipcodeText.getText().toString().trim();

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                zipcode = zipcodeText.getText().toString().trim();

                if (validateSearchBarZipCode(zipcode) == true) {
                    fetchData process = new fetchData();
                    process.setZipcode(zipcode);
                    process.execute();
                } else {
                    Context context = zipcheck.this;
                    String textToShow = "Enter a Valid 5 digit Zip Code";
                    Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();

                }
            }
        });
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
            Context context = zipcheck.this;
            String textToShow = "Signing Out";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();

            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(zipcheck.this, MainActivity.class));
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
