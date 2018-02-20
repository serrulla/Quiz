package com.serrulla.eric.quiz;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private final int[] button_ids = {
            R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4
    };

    private RadioGroup rgroup;

    //Questions data
    private String[] questions;
    private String[][] answers;
    private int[] solutions;

    private int[] responses;  //Answers from the user

    private int curr;  //current question index
    private Button btn_next;
    private TextView question_label;
    private TextView question_text;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                nextQuestion();
            }
        });

        rgroup = findViewById(R.id.answers);
        question_label = findViewById(R.id.question_label);
        question_text = findViewById(R.id.question_text);




        curr = 0;
        loadQuestions();
        showQuestion();

    }

    private void nextQuestion() {
        responses[curr] = getResponse();
        //if question is the last one, show results
        if (curr == questions.length-1){
            giveResults();
        }
        else{
            curr++;
        }
        //if question is the last one, change button text
        if (curr == questions.length-1){
            btn_next.setText(R.string.btn_check);
        }
        showQuestion();
    }

    private void giveResults(){
        int good = 0, bad = 0;
        for (int i=0; i<responses.length; i++){
            if (responses[i] == solutions[i]){
                good++;
            } else{
                bad++;
            }
        }
        String msg = String.format("Respostes correctes: %d | Respostes incorrectes: %d", good, bad );

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultats");
        builder.setMessage(msg);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.restart, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                restartQuiz();
            }

        });
        builder.create().show();

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        //finish();
    }

    private void restartQuiz() {
        for (int i=0; i< responses.length; i++){
            responses[i]=0;
        }
        curr = 0;
        showQuestion();
    }

    private void loadQuestions() {
        Resources res = getResources();
        questions = res.getStringArray(R.array.questions);
        solutions = res.getIntArray(R.array.solutions);
        String[] answ = res.getStringArray(R.array.answers);
        answers = new String[answ.length][]; //length de answers pero no de la segunda porque hay que hacer el split para ir guardándolas
        for (int i=0; i< answ.length; i++){
            answers[i] = answ[i].split(";");
        }
        responses = new int[answ.length];
    }

    private void showQuestion() {
        question_label.setText(String.format("Pregunta %d/%d", curr+1, questions.length));
        TextView question_text = findViewById(R.id.question_text);  //R is for resource
        question_text.setText(questions[curr]);

        for (int i = 0; i < answers[curr].length; i++){
            RadioButton rb = findViewById(button_ids[i]);
            rb.setText(answers[curr][i]);
        }
    }

    private void checkAnswer(){
        int quin = getResponse();
        if (quin != -1){
            if (quin == solutions[0]){
                Toast.makeText(this, "Correcte!", Toast.LENGTH_SHORT).show();
            } else {
            Toast.makeText(this, "Incorrecte...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private int getResponse() {
        int id_checked = rgroup.getCheckedRadioButtonId();
        int quin = -1;
        for (int i = 0; i< button_ids.length; i++){
            if (id_checked == button_ids[i]){
                    quin = i+1;
            }
        }
        return quin;
    }
}
