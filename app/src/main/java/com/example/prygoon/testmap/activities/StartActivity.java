package com.example.prygoon.testmap.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prygoon.testmap.DataManager;
import com.example.prygoon.testmap.R;
import com.example.prygoon.testmap.model.User;
import com.example.prygoon.testmap.model.UserDao;

import org.greenrobot.greendao.database.Database;


public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText;
    private Button mLoginButton;
    private Button mAddUserButton;
    private DataManager mDataManager;
    private User mUser;
    private Database mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mEditText = findViewById(R.id.editText);
        mLoginButton = findViewById(R.id.login_button);
        mAddUserButton = findViewById(R.id.add_user_button);
        mDataManager = DataManager.getInstance(this);
        mDatabase = mDataManager.getDatabase();

        mAddUserButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.add_user_button: {

                String username = mEditText.getText().toString();
                User user = new User(null, username, username.toUpperCase());

                try {
                    mDataManager.getDaoSession().insert(user);
                    Toast.makeText(getApplicationContext(), R.string.sucsess_input, Toast.LENGTH_SHORT).show();
                } catch (SQLiteConstraintException ex) {
                    Toast.makeText(getApplicationContext(), R.string.wrong_input, Toast.LENGTH_SHORT).show();
                }
                break;
            }

            case R.id.login_button: {
                String username = mEditText.getText().toString();

                try {
                    mUser = mDataManager.getDaoSession().getUserDao()
                            .queryBuilder()
                            .where(UserDao.Properties.SearchName.eq(username.toUpperCase()))
                            .unique();
                    if (mUser != null) {
                        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                        intent.putExtra("user", mUser);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Пользователя не существует", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}

