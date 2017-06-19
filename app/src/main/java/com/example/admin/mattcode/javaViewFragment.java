package com.example.admin.mattcode;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by admin on 16-Jun-17.
 */

public class javaViewFragment extends Fragment {
    private String code;
    TextView codeView;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.viewjava, container, false);
       codeView = (TextView) v.findViewById(R.id.codeView) ;
        codeView.setMovementMethod(new ScrollingMovementMethod());

        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            codeView.setText(((MainActivity)getActivity()).code);
        }
    }
}
