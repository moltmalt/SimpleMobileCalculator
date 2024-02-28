package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvres, tvsol;
    MaterialButton btnclear, btnsigns, btnmodulo, btndivide, btnmultiply, btnsubtract, btnadd, btnequal, btndot, btnallclear;
    MaterialButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvres = findViewById(R.id.tvRes);
        tvsol = findViewById(R.id.tvSol);

        assignId(btnclear, R.id.btnclear);
        assignId(btnallclear, R.id.btnallclear);
        assignId(btnsigns, R.id.btnsigns);
        assignId(btnmodulo, R.id.btnmodulo);
        assignId(btndivide, R.id.btndivide);
        assignId(btnmultiply, R.id.btnmultiply);
        assignId(btnsubtract, R.id.btnsubtract);
        assignId(btnadd, R.id.btnadd);
        assignId(btnequal, R.id.btnequal);
        assignId(btndot, R.id.btndot);
        assignId(btn7, R.id.btn7);
        assignId(btn8, R.id.btn8);
        assignId(btn9, R.id.btn9);
        assignId(btn4, R.id.btn4);
        assignId(btn5, R.id.btn5);
        assignId(btn6, R.id.btn6);
        assignId(btn1, R.id.btn1);
        assignId(btn2, R.id.btn2);
        assignId(btn3, R.id.btn3);

    }

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MaterialButton btn = (MaterialButton) v;
        String btnText =  btn.getText().toString();
        String dataToCalculate = tvsol.getText().toString();

        if(btnText.equals("AC")){
            tvsol.setText("");
            tvres.setText("0");
            return;
        }

        if(btnText.equals("=")){
            tvsol.setText(tvres.getText());
            return;
        }

        if(btnText.equals("C")){
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-1);
        }else{
            dataToCalculate = dataToCalculate+btnText;
        }

        tvsol.setText(dataToCalculate);

        String finalRes = calculateAns(dataToCalculate);

        if(!finalRes.equals("Error")) tvres.setText(finalRes);
    }

    String calculateAns(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalRes = context.evaluateString(scriptable,data,"Javascript",1, null).toString();
            if(finalRes.endsWith(".0")) finalRes = finalRes.replace(".0", "");
            return finalRes;
        }catch(Exception e){
            return "Error";
        }
    }
}