package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONObject;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // private members:
    Sandwich sandwich;
    private TextView tv_name;
    private TextView tv_nameAKA;
    private TextView tv_placeOfOrigin;
    private TextView tv_description;
    private ListView tv_ingredientsList;

    // private text:
    private String name;
    private String nameAKA;
    private String placeOfOrigin;
    private String description;
    private List<String> ingredientsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();

        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);

        String json = sandwiches[position]; // json string of the sandwich

        sandwich = JsonUtils.parseSandwichJson(json); // gets the specific sandwich from the json

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        // loads the data into the specific view:
        populateUI();

        // takes the url and loads an image into the IMGVIEW
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);


        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        //finish();
        //Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        // find views first:
        tv_nameAKA = findViewById(R.id.nameAKA_tv);
        tv_placeOfOrigin = findViewById(R.id.placeOfOrigin_tv);
        tv_description = findViewById(R.id.description_tv);
        tv_ingredientsList = findViewById(R.id.ingredients_tv);

        // load text into place holders:
        nameAKA = sandwich.getAlsoKnownAs().get(0);
        placeOfOrigin = sandwich.getPlaceOfOrigin();
        description = sandwich.getDescription();
        ingredientsList = sandwich.getIngredients();

        // load text into the views:
        tv_nameAKA.setText(nameAKA);
        tv_placeOfOrigin.setText(placeOfOrigin);
        tv_description.setText(description);

        // set the ingredients list views:
        ArrayAdapter<String> ingredientsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingredientsList);
        tv_ingredientsList.setAdapter(ingredientsAdapter);
    }

}
