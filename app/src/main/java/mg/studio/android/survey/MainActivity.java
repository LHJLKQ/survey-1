package mg.studio.android.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    int viewId[] = new int[15];
    int viewType[] = {-1,0,0,0,1,1,2,0,0,0,0,0,0,3,3};
    int count_4 = 0;
    int count_5 = 0;
    int curView = 0;
    ArrayList<String> str = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initViewID();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }


    public void next(View view) {
        System.out.println("next");

        if(viewType[curView] == 0){
            RadioGroup rg = findViewById(R.id.rg);
            RadioButton rb = findViewById(rg.getCheckedRadioButtonId());

            if(rb == null){
                Toast.makeText(MainActivity.this, "Please choose one at the list", Toast.LENGTH_SHORT).show();
                return;
            }
            System.out.println(rb);
            str.add(rb.getText().toString());
        }else if(viewType[curView] == 1){
            if(curView == 4){
                ArrayList<CheckBox> list = new ArrayList<CheckBox>();
                CheckBox cb0 = findViewById(R.id.cb_0);
                CheckBox cb1 = findViewById(R.id.cb_1);
                CheckBox cb2= findViewById(R.id.cb_2);
                CheckBox cb3 = findViewById(R.id.cb_3);
                CheckBox cb4 = findViewById(R.id.cb_4);
                CheckBox cb5 = findViewById(R.id.cb_5);
                CheckBox cb6 = findViewById(R.id.cb_6);
                list.add(cb0); list.add(cb1); list.add(cb2); list.add(cb3); list.add(cb4); list.add(cb5); list.add(cb6);
                for(CheckBox cb:list){
                    if(cb.isChecked()){
                        System.out.println(cb.getText());
                        str.add(cb.getText().toString());
                        count_4++;
                    }
                }
                if(count_4==0){
                    Toast.makeText(MainActivity.this, "Please choose at least one", Toast.LENGTH_SHORT).show();
                    return;
                }
            }else{
                ArrayList<CheckBox> list = new ArrayList<CheckBox>();
                CheckBox cb0 = findViewById(R.id.cb_7);
                CheckBox cb1 = findViewById(R.id.cb_8);
                CheckBox cb2= findViewById(R.id.cb_9);
                CheckBox cb3 = findViewById(R.id.cb_10);
                CheckBox cb4 = findViewById(R.id.cb_11);
                CheckBox cb5 = findViewById(R.id.cb_12);
                CheckBox cb6 = findViewById(R.id.cb_13);
                list.add(cb0); list.add(cb1); list.add(cb2); list.add(cb3); list.add(cb4); list.add(cb5); list.add(cb6);
                for(CheckBox cb:list){
                    if(cb.isChecked()){
                        System.out.println(cb.getText());
                        str.add(cb.getText().toString());
                        count_5++;
                    }
                }
                if(count_5==0){
                    Toast.makeText(MainActivity.this, "Please choose at least one", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }else if(viewType[curView] == 2){
            EditText et = findViewById(R.id.edbox);
            System.out.println(et.getText());
            str.add(et.getText().toString());
        }else if(viewType[curView] == -1){
            CheckBox cb = (CheckBox)findViewById(R.id.accept);
            if(!cb.isChecked()){
                System.out.println("please Accept");
                Toast.makeText(MainActivity.this, "Please accept", Toast.LENGTH_SHORT).show();
                return;
            }
        }else{

        }

        System.out.println(curView);
        setContentView(viewId[++curView]);
        int index = 0;
        if(curView == 13){
            TextView tv1 = findViewById(R.id.awnser_q1);
            tv1.setText(str.get(index++));
            TextView tv2 = findViewById(R.id.awnser_q2);
            tv2.setText(str.get(index++));
            TextView tv3 = findViewById(R.id.awnser_q3);
            tv3.setText(str.get(index++));
            TextView tv4 = findViewById(R.id.awnser_q4);
            String t4 = "";
            for(int i = 0;i<count_4;i++){
                t4 += i+1;
                t4 += ".";
                t4 += str.get(index++);
            }
            tv4.setText(t4);
            TextView tv5 = findViewById(R.id.awnser_q5);
            String t5 = "";
            for(int i = 0;i<count_5;i++){
                t5 += i+1;
                t5 += ".";
                t5 += str.get(index++);

            }
            tv5.setText(t5);
            TextView tv6 = findViewById(R.id.awnser_q6);
            tv6.setText(str.get(index++));
            TextView tv7 = findViewById(R.id.awnser_q7);
            tv7.setText(str.get(index++));
            TextView tv8 = findViewById(R.id.awnser_q8);
            tv8.setText(str.get(index++));
            TextView tv9 = findViewById(R.id.awnser_q9);
            tv9.setText(str.get(index++));
            TextView tv10 = findViewById(R.id.awnser_q10);
            tv10.setText(str.get(index++));
            TextView tv11 = findViewById(R.id.awnser_q11);
            tv11.setText(str.get(index++));
            TextView tv12 = findViewById(R.id.awnser_q12);
            tv12.setText(str.get(index++));
        }
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


}
