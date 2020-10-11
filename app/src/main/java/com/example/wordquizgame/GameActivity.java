package com.example.wordquizgame;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wordquizgame.model.WordItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView animalImg;
    private TextView scoreTV;
    private int scoreInt = 0, roundInt = 0;
    private Button[] choiceButtons = new Button[4];
    private ArrayList<WordItem> items = new ArrayList<>();
    private String mAns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //find view
        scoreTV = findViewById(R.id.scoreTextV);
        animalImg = findViewById(R.id.question_imageView);
        choiceButtons[0] = findViewById(R.id.choice_1_button);
        choiceButtons[1] = findViewById(R.id.choice_2_button);
        choiceButtons[2] = findViewById(R.id.choice_3_button);
        choiceButtons[3] = findViewById(R.id.choice_4_button);

        //set button listener
        choiceButtons[0].setOnClickListener(this);
        choiceButtons[1].setOnClickListener(this);
        choiceButtons[2].setOnClickListener(this);
        choiceButtons[3].setOnClickListener(this);

        initialGame();
    }

    public void AreTheGameFinish() {
        if (roundInt == 5) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(GameActivity.this).setTitle("สรุปผล");
            dialog.setMessage("คุณได้ " + scoreInt + " คะแนน\n\nต้องการเล่นเกมใหม่หรือไม่");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    roundInt = 0;
                    scoreInt = 0;
                    String score = String.format(
                            Locale.getDefault(), "%d คะแนน", scoreInt
                    );
                    scoreTV.setText(score);
                    initialGame();
                }
            });
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    roundInt = 0;
                    scoreInt = 0;
                    finish();
                }
            });
            dialog.show();
        } else {
            initialGame();
        }
    }

    public void initialGame() {
        //get array of data
        Collections.addAll(items, WordListActivity.arrItem);
        //random to pick animal
        Random rand = new Random();
        int ansIndex = rand.nextInt(items.size());
        //random to pick choice button
        int choiceIndex = rand.nextInt(4);
        //set value
        animalImg.setImageResource(items.get(ansIndex).imageResId);
        choiceButtons[choiceIndex].setText(items.get(ansIndex).word);
        mAns = items.get(ansIndex).word;
        //remove selected item
        items.remove(ansIndex);
        //set fake ans to button
        Collections.shuffle(items);
        for (int i = 0, j = 0; i < 4; i++, j++) {
            if (i != choiceIndex) {
                choiceButtons[i].setText(items.get(j).word);
            }
        }
        items.clear();
    }

    @Override
    public void onClick(View view) {
        Button button = findViewById(view.getId());
        String choice = button.getText().toString();
        if (mAns.equals(choice)) {
            Toast.makeText(GameActivity.this, "Correct!!", Toast.LENGTH_SHORT).show();
            scoreInt++;
            roundInt++;
            String score = String.format(
                    Locale.getDefault(), "%d คะแนน", scoreInt
            );
            scoreTV.setText(score);
        } else {
            Toast.makeText(GameActivity.this, "Incorrect!!", Toast.LENGTH_SHORT).show();
            roundInt++;
        }
        AreTheGameFinish();
    }
}