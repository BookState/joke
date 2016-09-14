package com.tj.uimading.utils;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tj.uimading.R;
import com.tj.uimading.base.MySqliteHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollectActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.btn_register)
    Button mBtnRegister;

    int loginflag ;//登录时判断用户密码是否输入正确
    private MySqliteHelper helper;
    private String name;
    private String mypwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);

        mEtName.setText(getIntent().getStringExtra("name"));
        mEtPwd.setText(getIntent().getStringExtra("pwd"));
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                select();
                break;
            case R.id.btn_register:
                Intent intent=new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }


    public void select()
    {

        helper = new MySqliteHelper(getApplicationContext());
        SQLiteDatabase db=helper.getWritableDatabase();

        String sql = "select * from users";

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            //第一列为id
            name =  cursor.getString(1); //获取第2列的值,第一列的索引从0开始
            mypwd = cursor.getString(2);//获取第3列的值



            if((mEtName.getText().toString().equals(name))&&(mEtPwd.getText().toString().equals(mypwd)))
            {
                Toast.makeText(this, "用户登录成功", Toast.LENGTH_SHORT).show();
                loginflag=1;

                //intent bundle传值
//                Intent MainActivity = new Intent();
//                MainActivity .setClass(this,MainActivity.class);
//                Bundle bundle = new Bundle(); //该类用作携带数据
//                bundle.putString("user", user.getText().toString());
//                MainActivity.putExtras(bundle);   //向MainActivity传值
//                this.startActivity(MainActivity);
//                finish();//退出

            }


        }

        if((mEtName.getText().toString().isEmpty())||(mEtPwd.getText().toString().isEmpty())){

            Toast.makeText(this, "不能为空，请重新输入", Toast.LENGTH_SHORT).show();
        }


        if(loginflag!=1)
        {
            Toast.makeText(this, "账号或者密码错误,请重新输入", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
        //Toast.makeText(this, "已经关闭数据库", Toast.LENGTH_SHORT).show();
    }

}
