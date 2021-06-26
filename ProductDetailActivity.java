package internship.batch1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView name,price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imageView = findViewById(R.id.product_detail_image);
        name = findViewById(R.id.product_detail_name);
        price = findViewById(R.id.product_detail_price);

        Bundle bundle = getIntent().getExtras();
        String sName = bundle.getString("name");
        String sPrice = bundle.getString("price");
        int iImage = bundle.getInt("image");

        getSupportActionBar().setTitle(sName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name.setText(sName);
        price.setText(sPrice);
        imageView.setImageResource(iImage);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}