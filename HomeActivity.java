package internship.batch1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String[] categoryNameArray = {"Vegetables", "Fruits", "Berries", "Chilli"};
    int[] categoryImageArray = {R.drawable.vegetable, R.drawable.fruits, R.drawable.berries, R.drawable.chilli};

    ArrayList<CategoryList> arrayCategoryList;

    SearchView searchView;
    //EditText searchEdit;
    CategoryAdapter adapter;
    //AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> categoryNameArrayList;

    RecyclerView productRecyclerview;
    ArrayList<ProductList> productArrayList;
    ProductAdapter productAdapter;

    String[] productNameArray = {"Beet", "Green Chilli", "Red Chilli", "Apple", "Banana", "Blue Berries", "Grapes", "Strawberry"};
    int[] productImageArray = {R.drawable.beet, R.drawable.green_chilli, R.drawable.red_chillies, R.drawable.apple, R.drawable.banana, R.drawable.blue_blueberries, R.drawable.grapes, R.drawable.strawberry};
    String[] priceArray = {"50", "20", "30", "200", "40", "150", "100", "40"};
    String[] unitArray = {"250 GM", "100 GM", "100 GM", "1 KG", "12 Item", "500 GM", "1 KG", "1 Item"};

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sp = getSharedPreferences(ConstantUrl.PREF, MODE_PRIVATE);
        /*searchEdit = findViewById(R.id.home_search);

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().equalsIgnoreCase("")){
                    adapter.filter("");
                }
                else{
                    adapter.filter(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/

        searchView = findViewById(R.id.home_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().equalsIgnoreCase("")) {
                    adapter.filter("");
                    productAdapter.filter("");
                } else {
                    adapter.filter(newText);
                    productAdapter.filter(newText);
                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.home_category);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        arrayCategoryList = new ArrayList<>();
        categoryNameArrayList = new ArrayList<>();
        for (int i = 0; i < categoryNameArray.length; i++) {
            CategoryList list = new CategoryList();
            list.setCategoryName(categoryNameArray[i]);
            list.setCategoryImage(categoryImageArray[i]);
            arrayCategoryList.add(list);
            categoryNameArrayList.add(categoryNameArray[i]);
        }
        adapter = new CategoryAdapter(HomeActivity.this, arrayCategoryList);
        //CategoryAdapter adapter = new CategoryAdapter(HomeActivity.this,categoryNameArray,categoryImageArray);
        recyclerView.setAdapter(adapter);

        productRecyclerview = findViewById(R.id.home_product);
        productRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        productRecyclerview.setItemAnimator(new DefaultItemAnimator());

        productArrayList = new ArrayList<>();
        for (int i = 0; i < productNameArray.length; i++) {
            ProductList list = new ProductList();
            list.setName(productNameArray[i]);
            list.setImage(productImageArray[i]);
            list.setPrice(priceArray[i]);
            list.setUnit(unitArray[i]);
            productArrayList.add(list);
        }
        productAdapter = new ProductAdapter(HomeActivity.this, productArrayList);
        productRecyclerview.setAdapter(productAdapter);

        /*autoCompleteTextView = findViewById(R.id.home_search);
        ArrayAdapter autoAdapter = new ArrayAdapter(HomeActivity.this, android.R.layout.simple_list_item_1, categoryNameArrayList);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(autoAdapter);*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finishAffinity();
        }
        if (id == R.id.menu_message) {
            new CommonMethod(HomeActivity.this, ChatActivity.class);
        }
        if (id == R.id.menu_logout) {
            //sp.edit().remove(ConstantUrl.ID).commit();
            sp.edit().clear().commit();
            new CommonMethod(HomeActivity.this, LoginActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        //super.onBackPressed();
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyHolder> {

        Context context;
        ArrayList<CategoryList> arrayCategoryList;
        ArrayList<CategoryList> searchList;

        public CategoryAdapter(HomeActivity homeActivity, ArrayList<CategoryList> arrayCategoryList) {
            this.context = homeActivity;
            this.arrayCategoryList = arrayCategoryList;
            searchList = new ArrayList<>();
            searchList.addAll(arrayCategoryList);
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_category, parent, false);
            return new MyHolder(view);
        }

        public void filter(String s) {
            s = s.toLowerCase(Locale.getDefault());
            arrayCategoryList.clear();
            if (s.length() == 0) {
                arrayCategoryList.addAll(searchList);
            } else {
                for (CategoryList cat : searchList) {
                    if (cat.getCategoryName().toLowerCase(Locale.getDefault()).contains(s)) {
                        arrayCategoryList.add(cat);
                    }
                }
            }
            notifyDataSetChanged();
        }

        public class MyHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView name;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.custom_category_name);
                imageView = itemView.findViewById(R.id.custom_category_image);
            }

        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.name.setText(arrayCategoryList.get(position).getCategoryName());
            holder.imageView.setImageResource(arrayCategoryList.get(position).getCategoryImage());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new CommonMethod(context, ProductActivity.class);
                    /*if(position==0){
                        new CommonMethod(context,ProductActivity.class);
                    }
                    else if(position==1){
                        new CommonMethod(context,LoginActivity.class);
                    }
                    else if(position==2){
                        new CommonMethod(context,SignupActivity.class);
                    }
                    else if(position==3){
                        new CommonMethod(context,ProductActivity.class);
                    }*/
                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayCategoryList.size();
        }

    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {

        Context context;
        ArrayList<ProductList> productArrayList;
        ArrayList<ProductList> searchList;

        public ProductAdapter(HomeActivity homeActivity, ArrayList<ProductList> productArrayList) {
            this.context = homeActivity;
            this.productArrayList = productArrayList;
            this.searchList = new ArrayList<>();
            searchList.addAll(productArrayList);
        }

        public void filter(String s) {
            s = s.toLowerCase(Locale.getDefault());
            productArrayList.clear();
            if (s.length() == 0) {
                productArrayList.addAll(searchList);
            } else {
                for (ProductList cat : searchList) {
                    if (cat.getName().toLowerCase(Locale.getDefault()).contains(s)) {
                        productArrayList.add(cat);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product_horizontal, parent, false);
            return new MyHolder(view);
        }

        public class MyHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView name, price;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.custom_product_horizontal_image);
                name = itemView.findViewById(R.id.custom_product_horizontal_name);
                price = itemView.findViewById(R.id.custom_product_horizontal_price);
            }

        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.imageView.setImageResource(productArrayList.get(position).getImage());
            holder.name.setText(productArrayList.get(position).getName());
            holder.price.setText(getResources().getString(R.string.price_symbol) + productArrayList.get(position).getPrice() + "/" + productArrayList.get(position).getUnit());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", productArrayList.get(position).getName());
                    bundle.putString("price", getResources().getString(R.string.price_symbol) + productArrayList.get(position).getPrice() + "/" + productArrayList.get(position).getUnit());
                    bundle.putInt("image", productArrayList.get(position).getImage());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return productArrayList.size();
        }

    }

    /*private class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyHolder> {

        Context context;
        String[] categoryNameArray;
        int[] categoryImageArray;

        public CategoryAdapter(HomeActivity homeActivity, String[] categoryNameArray, int[] categoryImageArray) {
            this.context = homeActivity;
            this.categoryNameArray = categoryNameArray;
            this.categoryImageArray = categoryImageArray;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_category,parent,false);
            return new MyHolder(view);
        }

        public class MyHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView name;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.custom_category_name);
                imageView = itemView.findViewById(R.id.custom_category_image);
            }

        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.name.setText(categoryNameArray[position]);
            holder.imageView.setImageResource(categoryImageArray[position]);
        }

        @Override
        public int getItemCount() {
            return categoryNameArray.length;
        }

    }*/
}