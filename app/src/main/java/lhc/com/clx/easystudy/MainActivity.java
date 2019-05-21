package lhc.com.clx.easystudy;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import lhc.com.clx.easystudy.sqlite.MySQLiteOpenHelper;
import lhc.com.clx.easystudy.sqlite.SQLdm;

public class MainActivity extends AppCompatActivity {



    TextView tvQuestion = null;
    TextView tvAnswer = null;
    TextView tvInfo = null;
    Button btnA = null;
    Button btnB = null;
    Button btnC = null;
    Button btnD = null;
    Button btnE = null;
    Button btnNext = null;
    Button btnWrong = null;
    Button btnRestart = null;


    int current_wrong = 0;
    //boolean empty = false;

    int number;// = cursor.getInt(0);
    String question;// = cursor.getString(1);
    String answer;// = cursor.getString(2);
    String A;// = cursor.getString(3);
    String B;// = cursor.getString(4);
    String C;// = cursor.getString(5);
    String D;// = cursor.getString(6);
    int correct;
    int wrong;
    String info;// = cursor.getString(10);

//    SQLdm s;
//    SQLiteDatabase db;// = s.openDatabase(getApplicationContext());

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    private static int REQUEST_EXTERNAL_STORAGE;


    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



        void show_all(){
            SQLdm s = new SQLdm();
            SQLiteDatabase db = s.openDatabase(getApplicationContext());
            Cursor cursor = db.rawQuery("select * from cn1_1",null);

            cursor.moveToFirst();



            Log.e("sql","counter = "+cursor.getCount());


            for (int i=0;i<cursor.getCount();i++){

                String string = "";

                string += "id=" + String.valueOf(cursor.getInt(0));
                string += "\tcorrect="+string.valueOf(cursor.getInt(8));
                string += "\twrong="+string.valueOf(cursor.getInt(9));

                Log.e("sql",string);
                cursor.moveToNext();

            }







        }


    void setNew(){
        SQLdm s = new SQLdm();
        SQLiteDatabase db = s.openDatabase(getApplicationContext());

        ContentValues values = new ContentValues();
        values.put("correct",0);
        values.put("wrong",0);

        db.update("cn1_1",values,null,null);

    }


    void setCorrect(int id){
        SQLdm s = new SQLdm();
        SQLiteDatabase db = s.openDatabase(getApplicationContext());

        ContentValues values = new ContentValues();
        values.put("correct",correct+1);
        values.put("wrong",wrong-1);
        db.update("cn1_1",values,"id=?",new String[]{String.valueOf(id)});
    }

    void setWrong(int id){
        SQLdm s = new SQLdm();
        SQLiteDatabase db = s.openDatabase(getApplicationContext());

        ContentValues values = new ContentValues();
        values.put("wrong",wrong+1);
        values.put("correct",correct-1);
        db.update("cn1_1",values,"id=?",new String[]{String.valueOf(id)});
    }



    int getWrongMax(){
        SQLdm s = new SQLdm();
        SQLiteDatabase db = s.openDatabase(getApplicationContext());
        Cursor cursor = db.rawQuery("select MAX(wrong) from cn1_1",null);
        cursor.moveToFirst();

        return cursor.getInt(0);
    }



    void showCorrectAnswer(){

        switch (answer){
            case "A":
                tvAnswer.setText("正确答案："+answer+"."+A);
                break;
            case "B":
                tvAnswer.setText("正确答案："+answer+"."+B);
                break;
            case "C":
                tvAnswer.setText("正确答案："+answer+"."+C);
                break;
            case "D":
                tvAnswer.setText("正确答案："+answer+"."+D);
                break;
        }
        tvInfo.setText("答案解析：\r\n"+info);
        ///tvQuestion.setText("正确答案："+answer+"\r\n"+info);
    }

    void refresh()
    {



        show_all();

//        if (empty){
//            //空的
//            //已经没有题目了
//        }

        tvAnswer.setText("");

        SQLdm s = new SQLdm();
        SQLiteDatabase db = s.openDatabase(getApplicationContext());

        //获取错误的最大值

        int max = getWrongMax();



        if (max < 0){
            //所有题目都答完了
//            Toast.makeText("")

            tvQuestion.setText("已经全部都答完");
            btnA.setVisibility(View.GONE);//隐藏按钮
            btnB.setVisibility(View.GONE);//隐藏按钮
            btnC.setVisibility(View.GONE);//隐藏按钮
            btnD.setVisibility(View.GONE);//隐藏按钮
            btnE.setVisibility(View.GONE);//隐藏按钮
            btnNext.setVisibility(View.GONE);
            btnWrong.setVisibility(View.GONE);
            btnRestart.setVisibility(View.VISIBLE);

            return;

        }



        Cursor cursor = db.rawQuery("select * from cn1_1  where cn1_1.wrong = "+max+" ORDER BY random()",null);

        if(cursor.moveToFirst() == false){
            //没有了
            current_wrong++;
            //empty = true;
            return;

        }else{
            //empty = false;
        }


        Log.e("clx", String.valueOf(cursor.getInt(0)));
        Log.e("clx",cursor.getString(1));
        Log.e("clx",cursor.getString(2));
        Log.e("clx",cursor.getString(3));
        Log.e("clx",cursor.getString(4));
        Log.e("clx",cursor.getString(5));

        Log.e("clx", String.valueOf(cursor.getColumnIndex("question")));


        number = cursor.getInt(0);
        question = cursor.getString(1);
        answer = cursor.getString(2);
        A = cursor.getString(3);
        B = cursor.getString(4);
        C = cursor.getString(5);
        D = cursor.getString(6);
        correct = cursor.getInt(8);
        wrong = cursor.getInt(9);
        info = cursor.getString(10);

        tvQuestion.setText(number+". "+question);
        btnA.setText("A. "+A);
        btnB.setText("B. "+B);
        btnC.setText("C. "+C);
        btnD.setText("D. "+D);
        tvAnswer.setVisibility(View.GONE);
        tvInfo.setVisibility(View.GONE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvQuestion = findViewById(R.id.TextViewQuestion);
        tvAnswer = findViewById(R.id.TextViewAnswer);
        tvInfo = findViewById(R.id.TextViewInfo);
        btnA = findViewById(R.id.ButtonA);
        btnB = findViewById(R.id.ButtonB);
        btnC = findViewById(R.id.ButtonC);
        btnD = findViewById(R.id.ButtonD);
        btnE = findViewById(R.id.ButtonE);
        btnNext = findViewById(R.id.ButtonNext);
        btnWrong = findViewById(R.id.ButtonWrong);
        btnRestart = findViewById(R.id.ButtonRestart);


        tvAnswer.setText("");

//
//        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(MainActivity.this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    REQUESTCODE);
//        }


//        verifyStoragePermissions(MainActivity.this);





        refresh();



        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tvQuestion.setText(info);
                btnA.setVisibility(View.GONE);//隐藏按钮
                btnB.setVisibility(View.GONE);//隐藏按钮
                btnC.setVisibility(View.GONE);//隐藏按钮
                btnD.setVisibility(View.GONE);//隐藏按钮
                btnE.setVisibility(View.GONE);//隐藏按钮
                btnNext.setVisibility(View.VISIBLE);

                showCorrectAnswer();

                if (answer.equals("A")){
                    btnWrong.setVisibility(View.VISIBLE);
                    setCorrect(number);//正确

                }else{
                    setWrong(number);//错误
                }
                tvAnswer.setVisibility(View.VISIBLE);
                tvInfo.setVisibility(View.VISIBLE);

            }
        });


        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tvQuestion.setText(info);
                btnA.setVisibility(View.GONE);//隐藏按钮
                btnB.setVisibility(View.GONE);//隐藏按钮
                btnC.setVisibility(View.GONE);//隐藏按钮
                btnD.setVisibility(View.GONE);//隐藏按钮
                btnE.setVisibility(View.GONE);//隐藏按钮
                btnNext.setVisibility(View.VISIBLE);
                //btnWrong.setVisibility(View.VISIBLE);

                showCorrectAnswer();

                if (answer.equals("B")){
                    btnWrong.setVisibility(View.VISIBLE);
                    setCorrect(number);//正确
                }else{
                    setWrong(number);//错误
                }
                tvAnswer.setVisibility(View.VISIBLE);
                tvInfo.setVisibility(View.VISIBLE);
            }
        });



        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // tvQuestion.setText(info);
                btnA.setVisibility(View.GONE);//隐藏按钮
                btnB.setVisibility(View.GONE);//隐藏按钮
                btnC.setVisibility(View.GONE);//隐藏按钮
                btnD.setVisibility(View.GONE);//隐藏按钮
                btnE.setVisibility(View.GONE);//隐藏按钮
                btnNext.setVisibility(View.VISIBLE);
                //btnWrong.setVisibility(View.VISIBLE);

                showCorrectAnswer();

                if (answer.equals("C")){
                    btnWrong.setVisibility(View.VISIBLE);
                    setCorrect(number);//正确
                }else{
                    setWrong(number);//错误
                }
                tvAnswer.setVisibility(View.VISIBLE);
                tvInfo.setVisibility(View.VISIBLE);
            }
        });


        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // tvQuestion.setText(info);
                btnA.setVisibility(View.GONE);//隐藏按钮
                btnB.setVisibility(View.GONE);//隐藏按钮
                btnC.setVisibility(View.GONE);//隐藏按钮
                btnD.setVisibility(View.GONE);//隐藏按钮
                btnE.setVisibility(View.GONE);//隐藏按钮
                btnNext.setVisibility(View.VISIBLE);
                //btnWrong.setVisibility(View.VISIBLE);

                showCorrectAnswer();

                if (answer.equals("D")){
                    btnWrong.setVisibility(View.VISIBLE);
                    setCorrect(number);//正确
                }else{
                    setWrong(number);//错误
                }
                tvAnswer.setVisibility(View.VISIBLE);
                tvInfo.setVisibility(View.VISIBLE);
            }
        });

        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //不确定
                //tvQuestion.setText(info);
                btnA.setVisibility(View.GONE);//隐藏按钮
                btnB.setVisibility(View.GONE);//隐藏按钮
                btnC.setVisibility(View.GONE);//隐藏按钮
                btnD.setVisibility(View.GONE);//隐藏按钮
                btnE.setVisibility(View.GONE);//隐藏按钮
                btnNext.setVisibility(View.GONE);//不显示下一题
                btnWrong.setVisibility(View.VISIBLE);
                tvAnswer.setVisibility(View.VISIBLE);
                tvInfo.setVisibility(View.VISIBLE);


            }
        });



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnA.setVisibility(View.VISIBLE);//隐藏按钮
                btnB.setVisibility(View.VISIBLE);//隐藏按钮
                btnC.setVisibility(View.VISIBLE);//隐藏按钮
                btnD.setVisibility(View.VISIBLE);//隐藏按钮
                btnE.setVisibility(View.VISIBLE);//隐藏按钮
                btnNext.setVisibility(View.GONE);
                btnWrong.setVisibility(View.GONE);

                //setCorrect(number);//正确


                refresh();
            }
        });

        btnWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnA.setVisibility(View.VISIBLE);//隐藏按钮
                btnB.setVisibility(View.VISIBLE);//隐藏按钮
                btnC.setVisibility(View.VISIBLE);//隐藏按钮
                btnD.setVisibility(View.VISIBLE);//隐藏按钮
                btnE.setVisibility(View.VISIBLE);//隐藏按钮
                btnNext.setVisibility(View.GONE);
                btnWrong.setVisibility(View.GONE);

                setWrong(number);//错误



                refresh();
            }
        });


        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //重置


                setNew();//




                btnA.setVisibility(View.VISIBLE);//隐藏按钮
                btnB.setVisibility(View.VISIBLE);//隐藏按钮
                btnC.setVisibility(View.VISIBLE);//隐藏按钮
                btnD.setVisibility(View.VISIBLE);//隐藏按钮
                btnE.setVisibility(View.VISIBLE);//隐藏按钮
                btnNext.setVisibility(View.GONE);
                btnWrong.setVisibility(View.GONE);
                btnRestart.setVisibility(View.GONE);
                refresh();
            }
        });


    }
}
