package com.example.checkzip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity extends AppCompatActivity {

    private EditText firstName, lastName, userName, userPassword, reenterPassword, userEmail, zipcode, securityQuestion, securityAnswer;
    private Button signupBtn;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    private LinearLayout layout;


    String firstname;
    String lastname;
    String username;
    String email;
    String password;
    String repassword;
    String zip;
    String question;
    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setupUIView();

        firebaseAuth = FirebaseAuth.getInstance();
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendEmailVerification();

                                //Toast.makeText(SignupActivity.this,"Registration successful",Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(SignupActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });


    }

    private void setupUIView() {
        firstName = (EditText) findViewById(R.id.firstNameText);
        lastName = (EditText) findViewById(R.id.lastNameText);
        userName = (EditText) findViewById(R.id.usernameText);
        userPassword = (EditText) findViewById(R.id.passwordText);
        reenterPassword = (EditText) findViewById(R.id.repasswordText);
        userEmail = (EditText) findViewById(R.id.emailText);
        zipcode = (EditText) findViewById(R.id.zipcodeText);
        signupBtn = (Button) findViewById(R.id.signMeUpBtn);
        userLogin = (TextView) findViewById(R.id.signupTV);
        securityQuestion = (EditText) findViewById(R.id.squestionTxt);
        securityAnswer = (EditText) findViewById(R.id.sAnswerTxt);
    }

    private Boolean validate() {
        Boolean result = false;
        firstname = firstName.getText().toString();
        lastname = lastName.getText().toString();
        username = userName.getText().toString();
        password = userPassword.getText().toString();
        repassword = reenterPassword.getText().toString();
        email = userEmail.getText().toString();
        zip = zipcode.getText().toString();
        question = securityQuestion.getText().toString();
        answer = securityAnswer.getText().toString();

        if (firstname.isEmpty() || lastname.isEmpty() || username.isEmpty() || password.isEmpty() || repassword.isEmpty() || email.isEmpty() || zip.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        } else if (password.compareTo(repassword) != 0) {
            Toast.makeText(this, "Password and Re-enter Password is not matched", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;

    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(firstname, lastname, username, email, zip, question, answer);
        myRef.setValue(userProfile);
    }

    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendUserData();
                        Toast.makeText(SignupActivity.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(SignupActivity.this, "Verification email has not been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


}
