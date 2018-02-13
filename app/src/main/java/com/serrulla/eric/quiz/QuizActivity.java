package com.serrulla.eric.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    private final int[] button_ids = {
            R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        TextView question_text = findViewById(R.id.question_text);  //R is for resource
        question_text.setText(R.string.question_content);

        String[] answer_texts = getResources().getStringArray(R.array.answer_texts);

        for (int i = 0; i < answer_texts.length; i++){
            RadioButton rb = findViewById(button_ids[i]);
            rb.setText(answer_texts[i]);
        }


    }
}
