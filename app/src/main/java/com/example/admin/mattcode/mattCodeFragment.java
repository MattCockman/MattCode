package com.example.admin.mattcode;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by admin on 16-Jun-17.
 */

public class mattCodeFragment extends Fragment {
    private EditText codeView ;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.mattcodeview, container, false);


        codeView = (EditText) v.findViewById(R.id.codeView) ;
        codeView.setMovementMethod(new ScrollingMovementMethod());
        final EditText codeInput = (EditText) v.findViewById(R.id.codeInput) ;
        Button codeSubmitButton = (Button) v.findViewById(R.id.buttonSubmitCode);

        codeSubmitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                String s = codeInput.getText().toString();
                s = s.concat("\n");
                s = codeView.getText().toString().concat(s);
                codeView.setText(s);
                codeInput.setText("");
                ((MainActivity)getActivity()).code = s;
            }
        });
        return v;

    }

    public void setCode(String newCode){
        codeView.setText(newCode);
    }

}
