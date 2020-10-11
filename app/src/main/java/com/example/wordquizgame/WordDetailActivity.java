package com.example.wordquizgame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wordquizgame.model.WordItem;
import com.google.gson.Gson;

public class WordDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_details);

        ImageView animalImg = findViewById(R.id.image_view);
        TextView animalWord = findViewById(R.id.word_text_view);

        Intent intent = getIntent();
        String itemJson = intent.getStringExtra("item");
        WordItem item = new Gson().fromJson(itemJson, WordItem.class);

        animalImg.setImageResource(item.imageResId);
        animalWord.setText(item.word);
    }
}