package internship.batch1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    TextView createAccount;
    Button login;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passwordPattern = "(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,10}";

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sp = getSharedPreferences(ConstantUrl.PREF,MODE_PRIVATE);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        createAccount = findViewById(R.id.login_create_account);
        login = findViewById(R.id.login_button);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(LoginActivity.this,SignupActivity.class);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().trim().equals("")) {
                    email.setError("Email Id Required");
                }
                else if(!email.getText().toString().trim().matches(emailPattern)){
                    email.setError("Valid Email Id Required");
                }
                else if (password.getText().toString().trim().equals("")) {
                    password.setError("Password Required");
                }
                else if(!password.getText().toString().trim().matches(passwordPattern)){
                    password.setError("Strong Password Required");
                }
                else {
                    if(new ConnectionDetector(LoginActivity.this).isConnectingToInternet()){
                        new loginData().execute();
                    }
                    else{
                        new ConnectionDetector(LoginActivity.this).connectiondetect();
                    }
                    /*if(email.getText().toString().trim().equals("admin@gmail.com") && password.getText().toString().trim().equalsIgnoreCase("ADmin@007")){
                        System.out.println("Login Successfully");
                        Log.d("LOGIN", "Login Successfully");
                        Log.e("LOGIN", "Login Successfully");

                        //Toast.makeText(LoginActivity.this,"Login Successfully",Toast.LENGTH_SHORT).show();
                        new CommonMethod(LoginActivity.this,"Login Successfully");
                        //Snackbar.make(view,"Login Successfully", Snackbar.LENGTH_LONG).show();
                        new CommonMethod(view,"Login Successfully");
                        *//*Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);*//*
                        new CommonMethod(LoginActivity.this,HomeActivity.class);
                    }
                    else{
                        new CommonMethod(LoginActivity.this,"Login Unsuccessfully");
                        new CommonMethod(view,"Login Unsuccessfully");
                    }*/
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            //onBackPressed();
            alertMethod();
        }
        return super.onOptionsItemSelected(item);
    }

    private void alertMethod() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Exit Alert!");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Are You Sure Want To Exit!");

        builder.setPositiveButton(Html.fromHtml("<font color='#F31101'>No</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton(Html.fromHtml("<font color='#238A28'>Yes</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });

        builder.setNeutralButton("Rate Us", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new CommonMethod(LoginActivity.this,"Rate Us");
                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        alertMethod();
    }

    private class loginData extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("email",email.getText().toString());
            hashMap.put("password",password.getText().toString());
            return new MakeServiceCall().MakeServiceCall(ConstantUrl.URL+"login.php",MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if(object.getBoolean("Status")==true){
                    new CommonMethod(LoginActivity.this,object.getString("Message"));
                    JSONArray array = object.getJSONArray("response");
                    for(int i=0;i<array.length();i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        sp.edit().putString(ConstantUrl.ID,jsonObject.getString("id")).commit();
                        sp.edit().putString(ConstantUrl.NAME,jsonObject.getString("name")).commit();
                        sp.edit().putString(ConstantUrl.EMAIL,jsonObject.getString("email")).commit();
                        sp.edit().putString(ConstantUrl.CONTACT,jsonObject.getString("contact")).commit();
                        sp.edit().putString(ConstantUrl.PASSWORD,jsonObject.getString("password")).commit();
                        sp.edit().putString(ConstantUrl.DOB,jsonObject.getString("dob")).commit();
                        sp.edit().putString(ConstantUrl.GENDER,jsonObject.getString("gender")).commit();
                        sp.edit().putString(ConstantUrl.CITY,jsonObject.getString("city")).commit();
                        sp.edit().putString(ConstantUrl.STATE,jsonObject.getString("state")).commit();
                        new CommonMethod(LoginActivity.this,HomeActivity.class);
                    }
                }
                else{
                    new CommonMethod(LoginActivity.this,object.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}