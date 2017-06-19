package com.example.admin.mattcode;


import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  {
    LayoutInflater inflater;    //Used to create individual pages
    ViewPager vp;               //Reference to class to swipe views

    MyAdapter mAdapter;
    String code ;
    ViewPager mPager;
    protected ArrayList<String> filenames;
    private String names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        names = readFromFile(this, "filenames.txt");
        filenames = readFileNames();

        mAdapter = new MyAdapter(getSupportFragmentManager(),this);

        mPager = (ViewPager)findViewById(R.id.viewPager);
        mPager.setAdapter(mAdapter);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout) ;
        tabLayout.setupWithViewPager(mPager);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflowmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DialogFragment newFragment;
        switch (item.getItemId()) {
            case R.id.action_new:
                code = "";
                ((mattCodeFragment)mAdapter.getRegisteredFragment(0)).setCode(code);
                return true;

            case R.id.action_open:
                newFragment = new fileNameListFragment();
                newFragment.show(getSupportFragmentManager(), "fileNameListFragmentDialog");
                return true;

            case R.id.action_save:
                newFragment = new saveFragment();
                newFragment.show(getSupportFragmentManager(), "saveFragmentDialog");
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    protected boolean writeToFile(String filename, Context context) {
            String data = code;
            if(data == null){
                Toast.makeText(this, "Nothing to save", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                File path = context.getFilesDir();
                File file = new File(path, filename + ".txt");
                FileOutputStream stream ;
                try{
                    stream = new FileOutputStream(file);
                    stream.write(data.getBytes());
                    stream.close();
                    saveFileNames(this, filename);
                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                    return true;
                } catch (FileNotFoundException e){
                    return false;
                } catch (IOException e) {
                    return false;
                }
            }
    }

    protected String readFromFile(Context context, String filename) {

        String code = "";

        try {
            InputStream inputStream = context.openFileInput(filename + ".txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString).append("\n");
                }

                inputStream.close();
                code = stringBuilder.toString();
                ((mattCodeFragment)mAdapter.getRegisteredFragment(0)).setCode(code);
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            Toast.makeText(this, "File not found Hippo", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
            Toast.makeText(this, "Can not read file", Toast.LENGTH_SHORT).show();
        }

        return code;
    }

    private boolean saveFileNames(Context context, String filename){
        filenames.add(filename);
        File path = context.getFilesDir();
        File file = new File(path, "filenames.al");
        FileOutputStream stream ;
        try{
            stream = new FileOutputStream(file);
            ObjectOutputStream of = new ObjectOutputStream(stream);
            of.writeObject(filenames);
            of.flush();
            of.close();
            stream.close();
            return true;
        } catch (FileNotFoundException e){
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    private ArrayList<String> readFileNames(){
        ArrayList<String> filenames = null;
        InputStream inputStream;
        try {
            inputStream = this.openFileInput("filenames.al");
            ObjectInputStream oi = new ObjectInputStream(inputStream);
            filenames = (ArrayList<String>)oi.readObject();
            oi.close();
        } catch (FileNotFoundException e) {
            Log.e("InternalStorage", e.getMessage());
        } catch (IOException e) {
            Log.e("InternalStorage", e.getMessage());
        } catch (ClassNotFoundException e) {
            Toast.makeText(this, "Class not found", Toast.LENGTH_SHORT).show();
        }
        if(filenames == null){
            filenames = new ArrayList<String>();
        }
        return filenames ;
    }





    public static class MyAdapter extends FragmentPagerAdapter {
        Context context;
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
        public MyAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment =null;
            switch (position) {
                case 0:
                    fragment = Fragment.instantiate(context, mattCodeFragment.class.getName());
                    break;
                case 1:
                    fragment = Fragment.instantiate(context, javaViewFragment.class.getName());
                    break;
            }
            return fragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        public CharSequence getPageTitle(int position){
            switch(position){
                case 0:
                    return "Speak" ;
                case 1:
                    return "Java";
                default:
                    return null;
            }
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }


}




