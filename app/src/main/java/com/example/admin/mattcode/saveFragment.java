package com.example.admin.mattcode;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by admin on 17-Jun-17.
 */

public class saveFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialogsave, null);

        final EditText filename = (EditText) v.findViewById(R.id.filename);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)
                // Add action buttons
                .setPositiveButton(R.string.savebutton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String s = filename.getText().toString();
                        if(!(s.matches("[a-zA-Z]+"))){
                            Toast.makeText(getActivity(), "File name must only contain letters", Toast.LENGTH_SHORT).show();
                        } else {
                            ((MainActivity)getActivity()).writeToFile(s, getActivity());
                        }
                    }
                })
                .setNegativeButton(R.string.cancelbutton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        saveFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
