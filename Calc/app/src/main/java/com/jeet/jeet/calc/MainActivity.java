package com.jeet.jeet.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.log;
import static java.lang.Math.log10;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sin;
import static java.lang.Math.tan;

public class MainActivity extends AppCompatActivity {
    int[] pri = {2, 2, 3, 3, 1, 1, 4, 5, 1, 1, 1, 1, 1,1};
    List<Float> input = new ArrayList<Float>();
    List<Integer> check = new ArrayList<Integer>();
    List<Float> operands = new ArrayList<Float>();
    List<Float> operators = new ArrayList<Float>();
    int operandsTop = -1, operatorsTop = -1, readFrom = 0, inputTop = 0, checkTop = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void reset(View view) {
        TextView resultView = (TextView) findViewById(R.id.resultText);
        resultView.setText("");
        operandsTop = -1;
        operatorsTop = -1;
        inputTop = 0;
        checkTop = 0;
        readFrom = 0;
        TextView  textError = (TextView) findViewById(R.id.textError);
        textError.setText("");
    }
    public void onBackspace(View view) {
        TextView resultView = (TextView) findViewById(R.id.resultText);
        try {
            String s = resultView.getText().toString();
            resultView.setText(s.substring(0, s.length() - 1));
        }
        catch(Exception e) {
            onError();
        }
    }
    public void setOperator(View view) {
        TextView resultView = (TextView) findViewById(R.id.resultText);
        String s = resultView.getText().toString();

        //Set operator now
        try {
            Button b = (Button) view;
            resultView.setText(s + b.getText().toString());
            String idString = b.getResources().getResourceName(b.getId());
            String sub = idString.substring(24, idString.length());


            float idFloat = new Float(sub).floatValue();

            if (idFloat <= 6.0 || idFloat == 8.0) {
                inputTop++;
                checkTop++;
            }
            input.add(inputTop, idFloat);
            check.add(checkTop, 0);
            if (idFloat <= 6.0 || idFloat == 7.0 || (idFloat <= 14 && idFloat >= 9)) {
                inputTop++;
                checkTop++;
            }
            //Update readFrom
            s = resultView.getText().toString();
            readFrom = s.length();
        }
        catch(Exception e){
            onError();
        }
    }
    public void setOperand(View view) {
        TextView resultView = (TextView) findViewById(R.id.resultText);

        //Update resultView
        String s = resultView.getText().toString();
        Button b = (Button) view;
        resultView.setText(s + b.getText().toString());
        s = resultView.getText().toString();

        Float operand = Float.valueOf(s.substring(readFrom, s.length()));
        input.add(inputTop, operand);
        check.add(checkTop, 1);

    }
    public void setPi(View view) {
        TextView resultView = (TextView) findViewById(R.id.resultText);
        resultView.setText(resultView.getText()+"pi");
        input.add(inputTop,(float)Math.PI);
        check.add(checkTop,1);
    }
    public void setE(View view) {
        TextView resultView = (TextView) findViewById(R.id.resultText);
        resultView.setText(resultView.getText()+"e");
        input.add(inputTop,(float)Math.E);
        check.add(checkTop,1);
    }
    public void calculate(View view) {
        TextView resultView = (TextView) findViewById(R.id.resultText);
        String s = resultView.getText().toString();
        /*
        for(int i = 0 ; i <= checkTop ; i++) {
            resultView.setText(resultView.getText()+String.valueOf(input.get(i)));
        }
        */
        //Start Executing
        try {
            for (int i = 0; i <= inputTop; i++) {
                if (check.get(i) == 1) {
                    operandsTop++;
                    operands.add(operandsTop, input.get(i));
                }
                if (check.get(i) == 0) {
                    if (operatorsTop == -1) {
                        operatorsTop++;
                        operators.add(operatorsTop, input.get(i));
                    } else {
                        int inOperator = Math.round(operators.get(operatorsTop));
                        int outOperator = Math.round(input.get(i));
                        if (pri[inOperator - 1] <= pri[outOperator - 1]) {
                            do {
                                if (outOperator == 7)
                                    break;
                                if (inOperator == 7 && outOperator == 8) {
                                    operatorsTop -= 1;
                                    break;
                                }

                                float result = calculate_switch(inOperator);
                                operands.set(operandsTop, result);
                                operatorsTop--;
                                if (operatorsTop == -1)
                                    break;
                                inOperator = Math.round(operators.get(operatorsTop));

                            }
                            while (pri[inOperator - 1] <= pri[outOperator - 1]);
                            if (!(inOperator == 7 && outOperator == 8)) {
                                operatorsTop++;
                                operators.add(operatorsTop, (float) outOperator);
                            }
                        } else {
                            operatorsTop++;
                            operators.add(operatorsTop, (float) outOperator);
                        }
                    }
                }
            }
            if (operatorsTop >= 0) {
                for (int i = operatorsTop; i >= 0; i--) {
                    float result = calculate_switch(Math.round(operators.get(i)));
                    operands.set(operandsTop, result);
                }
            }
            if (operandsTop == 0) {
                resultView.setText(String.valueOf(operands.get(operandsTop)));
            }
            operandsTop = -1;
            operatorsTop = -1;
            inputTop = 0;
            input.add(inputTop, operands.get(0));
            readFrom = 0;
            checkTop = 0;
        }
        catch(Exception e){
            onError();
        }
    }
    public float calculate_switch(int operator) {
        float result=0;
        switch(operator) {
            case 1:result = operands.get(operandsTop-1) / operands.get(operandsTop);
                operandsTop--;
                break;
            case 2:result = operands.get(operandsTop-1) * operands.get(operandsTop);
                operandsTop--;
                break;
            case 3:result = operands.get(operandsTop-1) + operands.get(operandsTop);
                operandsTop--;
                break;
            case 4:result = operands.get(operandsTop-1) - operands.get(operandsTop);
                operandsTop--;
                break;
            case 6:result = (float)pow(operands.get(operandsTop-1).doubleValue(),operands.get(operandsTop).doubleValue());
                operandsTop--;
                break;
            case 9:result = (float)sin(operands.get(operandsTop).doubleValue());
                break;
            case 10:result = (float)cos(operands.get(operandsTop).doubleValue());
                break;
            case 11:result = (float)tan(operands.get(operandsTop).doubleValue());
                break;
            case 12 : result = (float)log10(operands.get(operandsTop).doubleValue());
                break;
            case 13: result = (float)log(operands.get(operandsTop).doubleValue());
                break;
        }
        return result;
    }
    public void onError() {
        TextView errorText = (TextView) findViewById(R.id.textError);
        errorText.setText("Error");
    }
}
