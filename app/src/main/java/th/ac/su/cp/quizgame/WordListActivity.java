package th.ac.su.cp.quizgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import th.ac.su.cp.quizgame.model.WordItem;

public class WordListActivity extends AppCompatActivity {
    static public WordItem[]items={
            new WordItem(R.drawable.cat,"CAT"),
            new WordItem(R.drawable.dog,"DOG"),
            new WordItem(R.drawable.dolphin,"DOLPHIN"),
            new WordItem(R.drawable.tiger,"TIGER"),
            new WordItem(R.drawable.lion,"LION"),
            new WordItem(R.drawable.penguin,"PENGUIN"),
            new WordItem(R.drawable.rabbit,"RABBIT"),
            new WordItem(R.drawable.pig,"PIG"),
            new WordItem(R.drawable.koala,"KOALA"),

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);


        List<WordItem> wordList =Arrays.asList(items);
        // สร้าง Layout Manager Object
        LinearLayoutManager lm = new LinearLayoutManager(WordListActivity.this);
        // สร้าง Adapter Object
        MyAdapter adapter = new MyAdapter(WordListActivity.this,wordList);
        //เจ้าถึง RecyclerView  กับ Layout
        RecyclerView rv = findViewById(R.id.word_list_recycle_view);
        rv.setLayoutManager(lm);//กำหนด layout manager ให้กับ RecyclerView
        rv.setAdapter(adapter);//กำหนด  Adapter ให้กับRecyclerView
    }

    static class MyAdapter extends RecyclerView.Adapter <MyAdapter.MyViewHolder> {
        final Context mContext;
        final List<WordItem> mWordList ;

        public MyAdapter(Context context,List<WordItem> wordList) {
            this.mWordList=wordList;
            this.mContext=context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_word, parent, false);
            MyViewHolder vh = new MyViewHolder(mContext,v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
            holder.imageView.setImageResource(mWordList.get(position).imageResId);
            holder.wordTextView.setText(mWordList.get(position).word);
            holder.item=mWordList.get(position);
          /*  holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast t = Toast.makeText(mContext,items[position].word,Toast.LENGTH_SHORT);
                    t.show();
                }
            });*/
        }

        @Override
        public int getItemCount() {
            return mWordList.size();
        }

       static class MyViewHolder extends RecyclerView.ViewHolder {
            View rootView;
            ImageView imageView;
            TextView wordTextView;
            WordItem item;

            public MyViewHolder(final Context context, View itemView) {
                super(itemView);
                rootView=itemView;
                imageView = itemView.findViewById(R.id.image_view);
                wordTextView = itemView.findViewById(R.id.word_text_view_detail);
                rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,item.word,Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context,WordDetailsActivity.class);
                     /*   intent.putExtra("word",item.word);
                        intent.putExtra("image",item.imageResId);
*/
                        String itemJson =new Gson().toJson(item);
                        intent.putExtra("item",itemJson);
                        context.startActivity(intent);
                        /*
                        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setTitle("");
                        dialog.setMessage("");
                        dialog.setPositiveButton("ok",null);
                        dialog.show();
                        //เหมือนกับข้างบน

                         new AlertDialog.Builder(context)
                                .setTitle("MyDialog")
                                .setMessage(item.word)
                                .setPositiveButton("OK",null)
                                .show();
                           */

                    }
                });

            }
        }
    }
}