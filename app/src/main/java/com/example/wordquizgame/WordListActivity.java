package com.example.wordquizgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordquizgame.model.WordItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

public class WordListActivity extends AppCompatActivity {

    static public WordItem[] arrItem = {
            new WordItem(R.drawable.cat, "CAT"),
            new WordItem(R.drawable.dog, "DOG"),
            new WordItem(R.drawable.dolphin, "DOLPHIN"),
            new WordItem(R.drawable.tiger, "TIGER"),
            new WordItem(R.drawable.lion, "LION"),
            new WordItem(R.drawable.penguin, "PENGUIN"),
            new WordItem(R.drawable.rabbit, "RABBIT"),
            new WordItem(R.drawable.pig, "PIG"),
            new WordItem(R.drawable.koala, "KOALA")
    };
    private ArrayList<WordItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        Collections.addAll(items, arrItem);

        // สร้าง Layout manager
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);

        // สร้าง Adapter object
        MyAdapter adapter = new MyAdapter(WordListActivity.this, items);

        // เข้าถึง  RecyclerView ใน layout
        RecyclerView rv = findViewById(R.id.word_list_recycler_view);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(lm); // กำหนด layout manager ให้กับ RecyclerView
        rv.setAdapter(adapter); // กำหนด adapter ให้กับ RecyclerView
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    final Context mContext;
    final ArrayList<WordItem> mWordList;

    MyAdapter(Context mContext, ArrayList<WordItem> mWordList) {
        this.mContext = mContext;
        this.mWordList = mWordList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
        return new MyViewHolder(v, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.imageView.setImageResource(mWordList.get(position).imageResId);
        holder.wordTextView.setText(mWordList.get(position).word);
        holder.item = mWordList.get(position);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView wordTextView;
        WordItem item;

        MyViewHolder(@NonNull View itemView, final Context mContext) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            wordTextView = itemView.findViewById(R.id.word_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String itemJson = new Gson().toJson(item);
                    Intent i = new Intent(mContext, WordDetailActivity.class).putExtra("item", itemJson);
                    mContext.startActivity(i);
                }
            });
        }
    }
}