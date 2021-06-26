package internship.batch1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ProductList> arrayList;
    ProductAdapter adapter;

    String[] productNameArray = {"Beet", "Green Chilli", "Red Chilli", "Apple", "Banana", "Blue Berries", "Grapes", "Strawberry"};
    int[] productImageArray = {R.drawable.beet, R.drawable.green_chilli, R.drawable.red_chillies, R.drawable.apple, R.drawable.banana, R.drawable.blue_blueberries, R.drawable.grapes, R.drawable.strawberry};
    String[] priceArray = {"50", "20", "30", "200", "40", "150", "100", "40"};
    String[] unitArray = {"250 GM", "100 GM", "100 GM", "1 KG", "12 Item", "500 GM", "1 KG", "1 Item"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        getSupportActionBar().setTitle("Products");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.product_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        arrayList = new ArrayList<>();
        for (int i = 0; i < productNameArray.length; i++) {
            ProductList list = new ProductList();
            list.setName(productNameArray[i]);
            list.setImage(productImageArray[i]);
            list.setPrice(priceArray[i]);
            list.setUnit(unitArray[i]);
            arrayList.add(list);
        }
        adapter = new ProductAdapter(ProductActivity.this, arrayList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {

        Context context;
        ArrayList<ProductList> arrayList;

        public ProductAdapter(ProductActivity productActivity, ArrayList<ProductList> arrayList) {
            this.context = productActivity;
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product, parent, false);
            return new MyHolder(view);
        }

        public class MyHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView name, price;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.custom_product_image);
                name = itemView.findViewById(R.id.custom_product_name);
                price = itemView.findViewById(R.id.custom_product_price);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.imageView.setImageResource(arrayList.get(position).getImage());
            holder.name.setText(arrayList.get(position).getName());
            holder.price.setText(getResources().getString(R.string.price_symbol) + arrayList.get(position).getPrice() + "/" + arrayList.get(position).getUnit());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,ProductDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name",arrayList.get(position).getName());
                    bundle.putString("price",getResources().getString(R.string.price_symbol) + arrayList.get(position).getPrice() + "/" + arrayList.get(position).getUnit());
                    bundle.putInt("image",arrayList.get(position).getImage());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }
}