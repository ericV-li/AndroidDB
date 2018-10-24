package com.eric.dbdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.eric.db.DbException;
import com.eric.db.KeyValue;
import com.eric.db.LJDao;
import com.eric.db.sqlite.WhereBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author li
 * @Package com.eric.dbdemo
 * @Title: MainActivity
 * @Description: Copyright (c)
 * Create DateTime: 2017/05/01
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User user1 = new User();
        user1.setUserId("100");
        user1.setUserName("赵日天");
        user1.setSalary(1000);

        User user2 = new User();
        user2.setUserId("200");
        user2.setUserName("梅长苏");
        user2.setSalary(2000);

        User user3 = new User();
        user3.setUserId("300");
        user3.setUserName("隔壁老王");
        user3.setSalary(2500);
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        try {
            LJDao.getDbManager().save(userList);
        } catch (DbException e) {
            e.printStackTrace();
        }

        //删除某个主键对应值的记录
        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    LJDao.getDbManager().deleteById(User.class, 2);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
        //根据条件更新某个字段
        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    KeyValue keyValue = new KeyValue("userName", "李白");
                    LJDao.getDbManager().update(User.class, WhereBuilder.b("userId", "=", "00001"), keyValue);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
//按条件查询
        findViewById(R.id.btn_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    List<User> userList = LJDao.getDbManager().selector(User.class).where("userName", "like", "%老王%").findAll();
                    Toast.makeText(MainActivity.this, userList.get(0).getUserName(), Toast.LENGTH_LONG).show();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
