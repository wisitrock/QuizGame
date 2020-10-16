package th.ac.su.cp.quizgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import th.ac.su.cp.quizgame.model.WordItem;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mShowScore;
    private ImageView mQustionImageView;
    private Button[] mButtons=new Button[4];
    private String mAnswerWord;
    private  Random mRandom;
    private  List<WordItem> mItemList;
    private int Score =0;
    private int Count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mShowScore=findViewById(R.id.text_score);
        mShowScore.setText(Count+" คะแนน");

        mQustionImageView = findViewById(R.id.qustion_image_view);
        mButtons[0]=findViewById(R.id.chioce_1_button);
        mButtons[1]=findViewById(R.id.chioce_2_button);
        mButtons[2]=findViewById(R.id.chioce_3_button);
        mButtons[3]=findViewById(R.id.chioce_4_button);

        mButtons[0].setOnClickListener(this);
        mButtons[1].setOnClickListener(this);
        mButtons[2].setOnClickListener(this);
        mButtons[3].setOnClickListener(this);

        mRandom = new Random();

        newQuiz(mItemList,mRandom);

    }

    private void newQuiz(List<WordItem> mItemList, Random mRandom) {

        mItemList =new ArrayList<>(Arrays.asList(WordListActivity.items)) ;
        //สุ่ม index ของคำศัพท์( คำถาม)
        int answerIndex = mRandom.nextInt(mItemList.size());
        //เข้าถึง index ทที่สุ่มได้
        WordItem item = mItemList.get(answerIndex);
        //แสดงรูปคำถาม
        mQustionImageView.setImageResource(mItemList.get(answerIndex).imageResId);
        //เก็บคำตอบไว้ในตัวแปล คลาส
        mAnswerWord=item.word;

        //สุ่มตำแหน่งปุ่มที่เป็นคำตอบ
        int randomButton =mRandom.nextInt(4);
        //เซ็ตคำให้ปุ่มนั้น
        mButtons[randomButton].setText(item.word);

        mItemList.remove(item);

        //เอา list ที่เหลือไป shuffles
        Collections.shuffle(mItemList);


        for(int i=0;i<4;i++){
                if(i == randomButton){
                    continue;
                }
                mButtons[i].setText(mItemList.get(i).word);
        }
    }

    @Override
    public void onClick(View view) {
       Button b=findViewById(view.getId()) ;
       String buttonText = b.getText().toString();
        Count++;
        Log.i("Countcheck", "Count: " + Count);


       if(buttonText.equals(mAnswerWord)){
           Toast.makeText(GameActivity.this,"ถูกต้องนะครับ",Toast.LENGTH_SHORT).show();
                        Score++;
           Log.i("Countcheck", "Score: " + Score);
           mShowScore.setText( Score+" คะแนน");
       }else{
           Toast.makeText(GameActivity.this,"ผิดนะครับ",Toast.LENGTH_SHORT).show();
       }
       newQuiz(mItemList,mRandom);
        if(Count>=5){
            AlertDialog.Builder dialog = new AlertDialog.Builder(GameActivity.this);
            dialog.setTitle("สรุปผล");
            dialog.setMessage("คุณได้ "+String.valueOf(Score)+" คะแนน\n\n"+"ต้องการเล่นใหม่หรือไม่");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Count=0;
                    Score=0;
                    mShowScore.setText(Score+" คะแนน");
                    newQuiz(mItemList,mRandom);
                }
            });
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            dialog.show();
        }
    }

}