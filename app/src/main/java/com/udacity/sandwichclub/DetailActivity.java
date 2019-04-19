package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView dOriginTextView;
    private TextView dDescriptionTextView;
    private TextView dIngredientsTextView;
    private TextView dAlsoKnownAsTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        dOriginTextView = findViewById(R.id.origin_tv);
        dDescriptionTextView = findViewById(R.id.description_tv);
        dIngredientsTextView = findViewById(R.id.ingredients_tv);
        dAlsoKnownAsTextView = findViewById(R.id.also_known_tv);

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
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);



    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        setTitle(sandwich.getMainName());

        dOriginTextView.setText(sandwich.getPlaceOfOrigin());
        dDescriptionTextView.setText(sandwich.getDescription());

        for(int i = 0; i< sandwich.getAlsoKnownAs().size(); i++){
            String alias = sandwich.getAlsoKnownAs().get(i);
            if(i != 0){
                dAlsoKnownAsTextView.append(", ");
            }
            dAlsoKnownAsTextView.append(alias);

        }

        for(String ingredient : sandwich.getIngredients()){
            dIngredientsTextView.append(" • " + ingredient + "\n");
        }
    }
}
