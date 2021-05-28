package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView question;
    private ProgressBar progress;
    private TextView stats;
    private Button btnTrue;
    private Button btnFalse;

    private final String SCORE_KEY = "SCORE";
    private final String INDEX_KEY = "INDEX";

    private QuizModel[] qBank = new QuizModel[]{
            new QuizModel(R.string.q1, true),
            new QuizModel(R.string.q2, false),
            new QuizModel(R.string.q3, true),
            new QuizModel(R.string.q4, false),
            new QuizModel(R.string.q5, true),
            new QuizModel(R.string.q6, false),
            new QuizModel(R.string.q7, true),
            new QuizModel(R.string.q8, false),
            new QuizModel(R.string.q9, true),
            new QuizModel(R.string.q10, false),
    };

    private int qIndex = 0;
    private int score = 0;
    final int prog = 100 / qBank.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(SCORE_KEY);
            qIndex = savedInstanceState.getInt(INDEX_KEY);
        }

        question.setText(qBank[qIndex].getQuestion());

        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate(true);
                changeQues();
            }
        });

        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate(false);
                changeQues();
            }
        });
    }

    private void findView() {
        question = findViewById(R.id.card_question);
        progress = findViewById(R.id.card_progress_bar);
        stats = findViewById(R.id.card_stats);
        btnTrue = findViewById(R.id.button_true);
        btnFalse = findViewById(R.id.button_false);
    }

    private void changeQues() {
        qIndex = (qIndex + 1) % 10;
        if (qIndex == 0) {
            AlertDialog.Builder quizAlert = new AlertDialog.Builder(this);
            quizAlert.setCancelable(false);
            quizAlert.setTitle("The quiz is finished");
            quizAlert.setMessage("Your score is " + score);
            quizAlert.setPositiveButton("Finish the quiz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            quizAlert.show();
        }
        question.setText(qBank[qIndex].getQuestion());
        progress.incrementProgressBy(prog);
        stats.setText(score + "/" + 10);
    }

    private void evaluate(boolean guess) {
        boolean currentQuestionAnswer = qBank[qIndex].isAnswer();

        if (currentQuestionAnswer == guess) {
            Toast.makeText(getApplicationContext(), R.string.correct_toast_message, Toast.LENGTH_SHORT).show();
            score++;
        } else {
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast_message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SCORE_KEY, score);
        outState.putInt(INDEX_KEY, qIndex);

    }
}