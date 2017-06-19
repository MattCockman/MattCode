package com.example.admin.mattcode;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by admin on 17-Jun-17.
 */

public class fileNameListFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final CharSequence[] cs = ((MainActivity)getActivity()).filenames.toArray(new CharSequence[((MainActivity)getActivity()).filenames.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose File to Open")
                .setItems(cs, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity)getActivity()).readFromFile(getActivity(),cs[which].toString());
                    }
                });
        return builder.create();
    }
}
