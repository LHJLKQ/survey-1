package mg.studio.android.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    int viewId[] = new int[14];
    int curView = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initViewID();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }


    public void next(View view) {

        setContentView(viewId[++curView]);
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
        viewId[13] = R.layout.finish_survey;
    }


}
