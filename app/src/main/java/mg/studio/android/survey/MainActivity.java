package mg.studio.android.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.*;
import org.json.JSONStringer;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    int viewId[] = new int[15];
    int viewType[] = {-1,0,0,0,1,1,2,0,0,0,0,0,0,3,3};
    String jsonStr = "[{\"question\":\"where are you from\",\"answer\":[{\"1\":\"from china\"},{\"2\":\"from baxi\"},{\"3\":\"from guba\"}]},{\"question\":\"how old are you ?\",\"answer\":[{\"1\":\"20\"},{\"2\":\"21\"},{\"3\":\"22\"}]}]";
    JSONArray qArray;
    int curLanguage = 0;//chinese = 0 english = 1
    int qLen;

    int curView = 0;
    int curCreateQuestion;
    ArrayList<String> str = new ArrayList<String>();
    ArrayList<String> resultStr = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooselanguage);

        /*
        initViewID();



        try {
            //String To JSONObject
            jsonObj = new JSONObject(jsonStr);
            qLen = jsonObj.getJSONObject("survey").getInt("len");
            qArray = jsonObj.getJSONObject("survey").getJSONArray("questions");

            //取数据
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.welcome);
        */

    }


    public void next(View view) {

        if(curView == 0){
            CheckBox cb = findViewById(R.id.accept);
            if(cb.isChecked()==false){
                Toast.makeText(this,"Please accept",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        System.out.println("next");
        //record cur answer
        if(curView != 0){
            RadioGroup rg = findViewById(R.id.rg);
            if(rg.getCheckedRadioButtonId() == -1){
                Toast.makeText(this,"Please choose a answer!",Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton rb = findViewById(rg.getCheckedRadioButtonId());
            resultStr.add(rb.getText().toString());
        }

        if(curView >= qLen){
            setContentView(R.layout.show_result);
            Button btn = findViewById(R.id.endBtn);
            if (curLanguage==0)btn.setText("结束");
            LinearLayout resultLL = findViewById(R.id.resultLL);
            for(int i = 0;i<qLen;i++){
                TextView  qTV = new TextView(this);
                TextView  aTV = new TextView(this);
                try{
                    qTV.setText(qArray.getJSONObject(i).getString("question").toString());
                    qTV.setTextColor(getResources().getColor(R.color.colorBlack));
                    qTV.setTextSize(20);
                    aTV.setText(resultStr.get(i));
                }catch (Exception e){

                }
                resultLL.addView(qTV);
                resultLL.addView(aTV);
            }
            return;
        }

        setContentView(R.layout.question_one);
        Button btn = findViewById(R.id.next);
        if(curLanguage == 0)btn.setText("下一题");
        //Use  JsonObject to init Layout

        TextView q = findViewById(R.id.question);
        TextView qNum = findViewById(R.id.qNum);
        RadioGroup rg = findViewById(R.id.rg);
        JSONObject qAndA;

        try {
            qAndA = qArray.getJSONObject(curView);
            q.setText(qAndA.getString("question"));
            qNum.setText("Question" + Integer.toString(curView+1));
            if(curLanguage == 0)qNum.setText("第" + Integer.toString(curView+1) + "题");
            JSONArray aArray = qAndA.getJSONArray("answer");
            for(int i = 0;i<aArray.length();i++){
                RadioButton rb = new RadioButton(this);
                rb.setText(aArray.getJSONObject(i).getString("answer"));
                rg.addView(rb);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



        curView++;

    }

    private void initViewID(){
        viewId[0] = R.layout.welcome;
        viewId[1] = R.layout.question_one;
        viewId[2] = R.layout.question_two;
        viewId[3] = R.layout.question_three;
        viewId[4] = R.layout.question_four;
        viewId[5] = R.layout.question_five;
        viewId[6] = R.layout.question_six;
        viewId[7] = R.layout.question_seven;
        viewId[8] = R.layout.question_eight;
        viewId[9] = R.layout.question_nine;
        viewId[10] = R.layout.question_ten;
        viewId[11] = R.layout.question_eleven;
        viewId[12] = R.layout.question_twelve;
        viewId[13] = R.layout.show_result;
        viewId[14] = R.layout.finish_survey;

    }


    public void saveFile(View view) {
        String st = Environment.getExternalStorageState();
        File sdFile = getExternalFilesDir(null);
        String fileNameBack = ".json";
        String fileNameFst = "saveData";
        int index = 0;
        File saveData = new  File(sdFile,fileNameFst + fileNameBack);
        while(saveData.exists()){
            index++;
            saveData = new  File(sdFile,fileNameFst + Integer.toString(index) +fileNameBack);
        }

        String s =  saveData.toString();
        try {
            FileOutputStream fout = new FileOutputStream(saveData);
            JSONObject jo = new JSONObject();
            //String to Json Object
            for(int i = 0;i< resultStr.size();i++){
                jo.put("answer" + Integer.toString(i),resultStr.get(i));
            }
            fout.write(jo.toString().getBytes());
            Log.i("0",jo.toString());
            fout.flush();
            fout.close();
            Toast.makeText(this,"保存成功:"+saveData,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();


        }

    }
    private void sendwebinfo(String requestURL) {
        try {
            //1,找水源--创建URL
            URL url = new URL(requestURL);//放网站
            //2,开水闸--openConnection
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
            //3，建管道--InputStream
            InputStream inputStream = httpURLConnection.getInputStream();
            //4，建蓄水池蓄水-InputStreamReader
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            //5，水桶盛水--BufferedReader
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuffer buffer = new StringBuffer();
            String temp = null;

            while ((temp = bufferedReader.readLine()) != null) {
                //取水--如果不为空就一直取
                buffer.append(temp);
            }
            bufferedReader.close();//记得关闭
            reader.close();
            inputStream.close();



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void getwebinfo() {
        try {
            //1,找水源--创建URL
            URL url = new URL("https://yangcao.group/survey/down.php");//放网站
            //2,开水闸--openConnection
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
            //3，建管道--InputStream
            InputStream inputStream = httpURLConnection.getInputStream();
            //4，建蓄水池蓄水-InputStreamReader
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            //5，水桶盛水--BufferedReader
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuffer buffer = new StringBuffer();
            String temp = null;

            while ((temp = bufferedReader.readLine()) != null) {
                //取水--如果不为空就一直取
                buffer.append(temp);
            }
            bufferedReader.close();//记得关闭
            reader.close();
            inputStream.close();
            initViewID();
            jsonStr = buffer.toString();
            jsonStr = jsonStr.replace("~"," ");
            System.out.println(jsonStr);
            try {
                //String To JSONObject
                qArray = new JSONArray(jsonStr);
                qLen = qArray.length();

                //取数据
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void chooseChinese(View view) {
        setContentView(R.layout.begin_page);
        Button  startBtn = findViewById(R.id.start);
        Button  createBtn = findViewById(R.id.create);
        startBtn.setText("填写问卷");
        createBtn.setText("创建问卷");
        curLanguage = 0;

    }

    public void chooseEnglish(View view) {
        setContentView(R.layout.begin_page);
        Button  startBtn = findViewById(R.id.start);
        Button  createBtn = findViewById(R.id.create);
        startBtn.setText("Fill in the questionnaire");
        createBtn.setText("Create the questionnaire");
        curLanguage = 1;
    }

    public void startAnswer(View view) {
        final String url = "https://yangcao.group/survey/down.php";
        CountDownLatch cdl=new CountDownLatch(1);
        new MyThread(cdl).start();
        try {
            cdl.await();//主线程等待子线程执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setStartAView();

    }

    public void end(View view) {
        setContentView(R.layout.finish_survey);
        if(curLanguage==0){
            Button btn = findViewById(R.id.finishBtn);
            btn.setText("结束");
            TextView line0 = findViewById(R.id.__line0);
            TextView line1 = findViewById(R.id.__line1);
            line0.setText("谢谢您的参与!");
            line1.setText("");
        }
    }

    public void finish(View view) {
        System.exit(0);
    }

    public void exitCreate(View view) {
        setContentView(R.layout.begin_page);
        if(curLanguage==1){
            Button  startBtn = findViewById(R.id.start);
            Button  createBtn = findViewById(R.id.create);
            startBtn.setText("Fill in the questionnaire");
            createBtn.setText("Create the questionnaire");
        }
    }

    public class MyThread extends Thread{
        private CountDownLatch cdl;
        public MyThread(CountDownLatch cdl){
            this.cdl=cdl;
        }
        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();

            getwebinfo();

            cdl.countDown();
        }
    }
    public  void setStartAView(){
        setContentView(R.layout.welcome);
        if(curLanguage==0){
            TextView line0 = findViewById(R.id._line0);
            TextView line1 = findViewById(R.id._line1);
            TextView line2 = findViewById(R.id._line2);
            TextView line3 = findViewById(R.id._line3);
            TextView line4 = findViewById(R.id._line4);
            TextView line5 = findViewById(R.id._line5);
            Button btn = findViewById(R.id._line6);
            CheckBox check = findViewById(R.id.accept);
            line0.setText("欢迎参加问卷的调查");
            line1.setText("");
            line2.setText("再我们开始之前，你需要注意以下：");
            line3.setText("1.请如实回答问题");
            line4.setText("2.我们只是做一个研究，你不要对你的信息作假");
            line5.setText("3.请不要跳过问题");
            btn.setText("开始");
            check.setText("我接受协议");
        }

    }

    public void createQuestion(View view) {
        setContentView(R.layout.create_question);
        if(curLanguage == 1){
            Button btn = (Button)findViewById(R.id.fabuBtn);
            btn.setText("Release the questionnaire");
            Button exitBtn = (Button)findViewById(R.id.exitBtn);
            exitBtn.setText("back");
        }

    }

    public void addQuestionEdit(View view) {
        LinearLayout sV_LL = findViewById(R.id.sV_LL);
        LinearLayout editll =  new LinearLayout(this);
        LinearLayout ll =  new LinearLayout(this);
        final LinearLayout qAndALL =  new LinearLayout(this);
        qAndALL.setId(curCreateQuestion);
        qAndALL.setOrientation(LinearLayout.VERTICAL);
        editll.setHorizontalGravity(1);
        editll.setOrientation(LinearLayout.VERTICAL);
        editll.setBackgroundColor(getResources().getColor(R.color.white));
        ll.setHorizontalGravity(1);
        TextView qTV = new TextView(this);
        qTV.setTextSize(25);
        qTV.setText(Integer.toString(++curCreateQuestion));
        EditText ed = new EditText(this);
        ed.setTextSize(25);
        ed.setHint("请输入问题");
        if(curLanguage == 1)ed.setHint("Please enter the question");
        ll.addView(qTV);
        ll.addView(ed);
        qAndALL.addView(ll);
        editll.addView(qAndALL);
        Button addAnswerBtn = new Button(this);
        addAnswerBtn.setText("添加答案");
        if(curLanguage == 1)addAnswerBtn.setText("Add the answer");
        addAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll =  new LinearLayout(MainActivity.this);
                ll.setHorizontalGravity(1);
                EditText tv = new EditText(MainActivity.this);
                tv.setHint("请输入你的答案");
                if(curLanguage == 1)tv.setHint("Please enter the answer");
                tv.setTextSize(15);
                ll.addView(tv);
                qAndALL.addView(ll);
            }
        });
        editll.addView(addAnswerBtn);
        sV_LL.addView(editll);
        editll.setBackgroundColor(getResources().getColor(R.color.grey));


    }

    public void getQAndAInfo(View view){
        JSONArray qArray = new JSONArray();
        for(int i = 0;i<curCreateQuestion;i++){
            JSONObject  jo = new JSONObject();
            LinearLayout qAndAll = findViewById(i);
            LinearLayout tv =  (LinearLayout) qAndAll.getChildAt(0);
            EditText et = (EditText)tv.getChildAt(1);
            try {
                jo.put("question",et.getText().toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            JSONArray  aArray  =  new JSONArray();
            for(int j = 0;j<qAndAll.getChildCount()-1;j++){
                LinearLayout aEtLL = (LinearLayout)qAndAll.getChildAt(j+1);
                EditText aEt = (EditText)aEtLL.getChildAt(0);
                JSONObject aObj = new JSONObject();
                try{
                    aObj.put("answer",aEt.getText().toString());
                    aArray.put(aObj);
                }catch (Exception e){
                }


            }
            try {
                jo.put("answer",aArray);
                qArray.put(jo);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Toast.makeText(this,"Release",Toast.LENGTH_SHORT).show();
        final String url = "https://yangcao.group/survey/up.php?qanda=" + qArray.toString().replace(" ","~");
        System.out.println(url);
        new Thread(new Runnable() {//创建子线程
            @Override
            public void run() {
                sendwebinfo(url);//把路径选到MainActivity中
            }
        }).start();
    }
}
