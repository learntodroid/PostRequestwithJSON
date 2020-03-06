package com.learntodroid.postrequestwithjson;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private EditText title, comment, author;
    private Button send;

    private CommentsRepository commentsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        title = findViewById(R.id.activity_comments_title);
        comment = findViewById(R.id.activity_comments_comment);
        author = findViewById(R.id.activity_comments_author);
        radioGroup = findViewById(R.id.activity_comments_post_type);
        send = findViewById(R.id.activity_comments_send);

        commentsRepository = commentsRepository.getInstance();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment c = new Comment(
                        title.getText().toString(),
                        comment.getText().toString(),
                        author.getText().toString()
                );

                switch(radioGroup.getCheckedRadioButtonId()) {
                    case -1:
                        return;

                    case R.id.activity_comments_post_type_json_body:
                        commentsRepository.getCommentsService().createComment(c).enqueue(new Callback<Comment>() {
                            @Override
                            public void onResponse(Call<Comment> call, Response<Comment> r) {
                                Toast.makeText(getApplicationContext(), "Comment " + r.body().getComment() + " created", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onFailure(Call<Comment> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Error Creating Comment: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;

                    case R.id.activity_comments_post_type_form_encoded_fields:
                        commentsRepository.getCommentsService().createComment(c.getTitle(), c.getComment(), c.getAuthor()).enqueue(new Callback<Comment>() {
                            @Override
                            public void onResponse(Call<Comment> call, Response<Comment> r) {
                                Toast.makeText(getApplicationContext(), "Comment " + r.body().getComment() + " created", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Comment> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Error Creating Comment: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;

                    case R.id.activity_comments_post_type_form_encoded_field_set:
                        HashMap<String, String> fields = new HashMap<>();
                        fields.put("title", c.getTitle());
                        fields.put("comment", c.getComment());
                        fields.put("author", c.getAuthor());
                        commentsRepository.getCommentsService().createComment(fields).enqueue(new Callback<Comment>() {
                            @Override
                            public void onResponse(Call<Comment> call, Response<Comment> r) {
                                Toast.makeText(getApplicationContext(), "Comment " + r.body().getComment() + " created", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Comment> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Error Creating Comment: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;

                    default:
                        throw new IllegalStateException("Unexpected value: " + radioGroup.getCheckedRadioButtonId());
                }
            }
        });
    }
}
