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

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.et_rname)
    EditText mEtRname;
    @BindView(R.id.et_rpwd)
    EditText mEtRpwd;
    @BindView(R.id.btn_rlogin)
    Button mBtnRlogin;
    @BindView(R.id.btn_rcancel)
    Button mBtnRcancel;

    private MySqliteHelper helper;
    int userflag ;//定义一个标示判断 用户名是否存在
    private String name;
    private String mypwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_rlogin, R.id.btn_rcancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_rlogin:
                insert();
                break;
            case R.id.btn_rcancel:
                finish();
                break;
        }
    }


    public void  insert()
    {


        helper = new MySqliteHelper(getApplicationContext());
        SQLiteDatabase db=helper.getWritableDatabase();    //建立打开可读可写的数据库实例



        //查询一下，是否用户名重复
        String sql1 = "select * from users";
        Cursor cursor = db.rawQuery(sql1, null);
        while (cursor.moveToNext()) {
            //第一列为id
            name =  cursor.getString(1); //获取第2列的值,第一列的索引从0开始
            mypwd = cursor.getString(2);//获取第3列的值

            if((mEtRname.getText().toString().isEmpty())||(mEtRpwd.getText().toString().isEmpty())){

                Toast.makeText(this, "不能为空，请重新输入", Toast.LENGTH_SHORT).show();

                break;
            }


            userflag = 1;  //不存在此用户


            if((mEtRname.getText().toString().equals(name)))
            {
                Toast.makeText(this, "已存在此用户，请重新注册", Toast.LENGTH_SHORT).show();


                userflag =0;
                break;
            }

        }

        if(userflag==1)
        {
            String sql2 = "insert into users(name,pwd) values ('"+mEtRname.getText().toString()+"','"+mEtRpwd.getText().toString()+"')";
            db.execSQL(sql2);
            Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();

            Intent CollectActivity = new Intent();
            CollectActivity.setClass(this,CollectActivity.class);
            Bundle bundle = new Bundle(); //该类用作携带数据
            bundle.putString("name", mEtRname.getText().toString());
            bundle.putString("pwd",mEtRpwd.getText().toString());
            CollectActivity.putExtras(bundle);   //向CollectActivity传值
            this.startActivity(CollectActivity);
            finish();//退出
        }





    }

}
