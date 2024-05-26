package com.example.lives_project;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextview;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;

    ImageView arrow_image;
    Button submitBtn;
    Button lastSelectedButton;
    Button back_button;
    boolean isWaitingForNextQuestion = false;
    boolean isDataLoaded = false;
    LottieAnimationView loading_animation;

    TextView question;

    int score = 0;
    int totalQuestion = 0;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";
    String correctOption = "";

    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt("currentQuestionIndex");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("QuizQuestions");

        ansA = findViewById(R.id.ansA);
        ansB = findViewById(R.id.ansB);
        ansC = findViewById(R.id.ansC);
        ansD = findViewById(R.id.ansD);
        arrow_image = findViewById(R.id.arrow_image);
        back_button = findViewById(R.id.prev_question_btn);
        submitBtn = findViewById(R.id.submit_btn);
        totalQuestionsTextview = findViewById(R.id.total_questions);
        loading_animation = findViewById(R.id.lottie_loading);
        question = findViewById(R.id.question);

        ansA.setVisibility(View.INVISIBLE);
        ansB.setVisibility(View.INVISIBLE);
        ansC.setVisibility(View.INVISIBLE);
        ansD.setVisibility(View.INVISIBLE);
        back_button.setVisibility(View.INVISIBLE);
        arrow_image.setVisibility(View.INVISIBLE);
        question.setVisibility(View.INVISIBLE);
        submitBtn.setVisibility(View.INVISIBLE);
        totalQuestionsTextview.setVisibility(View.INVISIBLE);
        loading_animation.setVisibility(View.VISIBLE);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        Button prevQuestionBtn = findViewById(R.id.prev_question_btn);
        prevQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousQuestion();
            }
        });


        loadQuestionsFromFirebase();
    }

    void loadQuestionsFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                totalQuestion = (int) dataSnapshot.getChildrenCount();

                ansA.setVisibility(View.VISIBLE);
                ansB.setVisibility(View.VISIBLE);
                ansC.setVisibility(View.VISIBLE);
                ansD.setVisibility(View.VISIBLE);
                arrow_image.setVisibility(View.VISIBLE);
                back_button.setVisibility(View.VISIBLE);
                submitBtn.setVisibility(View.VISIBLE);
                totalQuestionsTextview.setVisibility(View.VISIBLE);
                question.setVisibility(View.VISIBLE);
                loading_animation.setVisibility(View.INVISIBLE);

                loadNewQuestion();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loading_animation.setVisibility(View.VISIBLE);
                Toast.makeText(QuizActivity.this, "Failed to load questions.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (isWaitingForNextQuestion) return;

        int blue_light = ContextCompat.getColor(this, R.color.blue_light);
        Button clickedButton = (Button) view;
        try {
            if (clickedButton.getId() == R.id.submit_btn) {
                if (isDataLoaded) {
                    checkAnswer();
                    loadNewQuestionWithDelay();
                } else {
                    Toast.makeText(QuizActivity.this, "Please wait, data is loading.", Toast.LENGTH_SHORT).show();
                }
            } else {
                selectedAnswer = getSelectedAnswer(clickedButton);
                changeButtonColor(clickedButton, blue_light);
                if (lastSelectedButton != null && lastSelectedButton != clickedButton) {
                    resetButtonColor(lastSelectedButton);
                }
                lastSelectedButton = clickedButton;
            }
        } catch (Exception e) {
            Log.e("QuizActivity", "An error occurred in onClick()", e);
            Toast.makeText(QuizActivity.this, "Something is wrong.", Toast.LENGTH_SHORT).show();
        }
    }

    void checkAnswer() {
        int green = ContextCompat.getColor(this, R.color.green);
        int red = ContextCompat.getColor(this, R.color.red);

        Button selectedButton = getSelectedAnswerButton();
        if (selectedButton == null) {
            Log.e("QuizActivity", "Selected answer button is null");
            return;
        }

        if (selectedAnswer.equals(correctOption)) {
            score++;
            changeButtonColor(selectedButton, green);
        } else {
            changeButtonColor(selectedButton, red);
            changeButtonColor(getCorrectAnswerButton(), green);
        }
    }

    void loadNewQuestionWithDelay() {
        isWaitingForNextQuestion = true;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentQuestionIndex++;
                if (currentQuestionIndex < totalQuestion) {
                    loadNewQuestion();
                } else {
                    finishQuiz();
                }
                isWaitingForNextQuestion = false;
            }
        }, 2000);
    }

    void loadNewQuestion() {
        isDataLoaded = false;
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ansA);
        ansB = findViewById(R.id.ansB);
        ansC = findViewById(R.id.ansC);
        ansD = findViewById(R.id.ansD);
        selectedAnswer = "";

        DatabaseReference currentQuestionRef = databaseReference.child("question" + (currentQuestionIndex + 1));
        currentQuestionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Question question = dataSnapshot.getValue(Question.class);
                if (question != null) {
                    questionTextView.setText(question.getText());
                    ansA.setText(question.getOptions().get("A"));
                    ansB.setText(question.getOptions().get("B"));
                    ansC.setText(question.getOptions().get("C"));
                    ansD.setText(question.getOptions().get("D"));

                    correctOption = question.getCorrectOption();
                    resetButtonColors();
                    lastSelectedButton = null;
                    isDataLoaded = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(QuizActivity.this, "Failed to load question.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void finishQuiz() {
        String passStatus = "Great! You finished the quiz";
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Click here to try once more")
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .show();
    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }

    void changeButtonColor(Button button, int color) {
        if (button != null) {
            button.setBackgroundColor(color);
        }
    }

    void resetButtonColor(Button button) {
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
    }

    Button getSelectedAnswerButton() {
        switch (selectedAnswer) {
            case "A":
                return ansA;
            case "B":
                return ansB;
            case "C":
                return ansC;
            case "D":
                return ansD;
            default:
                return null;
        }
    }

    Button getCorrectAnswerButton() {
        switch (correctOption) {
            case "A":
                return ansA;
            case "B":
                return ansB;
            case "C":
                return ansC;
            case "D":
                return ansD;
            default:
                return null;
        }
    }

    String getSelectedAnswer(Button button) {
        if (button == ansA) return "A";
        if (button == ansB) return "B";
        if (button == ansC) return "C";
        if (button == ansD) return "D";
        return "";
    }

    void resetButtonColors() {
        int blue = ContextCompat.getColor(this, R.color.blue);
        ansA.setBackgroundColor(blue);
        ansB.setBackgroundColor(blue);
        ansC.setBackgroundColor(blue);
        ansD.setBackgroundColor(blue);
    }

    void showPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            loadNewQuestion();
        } else {
            Toast.makeText(QuizActivity.this, "This is the first question", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("QuizPreferences", MODE_PRIVATE);
        currentQuestionIndex = preferences.getInt("currentQuestionIndex", 0);
        loadNewQuestion();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getSharedPreferences("QuizPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("currentQuestionIndex", currentQuestionIndex);
        editor.apply();
    }


}
