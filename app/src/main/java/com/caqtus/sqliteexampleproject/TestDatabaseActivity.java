package com.caqtus.sqliteexampleproject;

import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Switch;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;


/**
 * Created by Amiran Toronjadze on 2015 2015 18-Jan-15 9:53 AM 9:53 AM.
 */
/**
 * We use here a ListActivity for displaying the data.
 */

public class TestDatabaseActivity extends ListActivity {
    private CommentsDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new CommentsDataSource(this);
        try {
            datasource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //getting all comments
        List<Comment> values = datasource.getAllComments();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }


    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view){
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;

        switch(view.getId()){
            case R.id.add:
                Log.i("onClick", "add");
                String[] comments = new String[] {"Cool", "Very nice", "Hate it", "its OK"};
                int nextInt = new Random().nextInt(4);

                // save the new comment to the database
                comment = datasource.createComment(comments[nextInt]);
                adapter.add(comment);
                adapter.notifyDataSetChanged();
                break;

            case R.id.delete:
                Log.i("onClick", "delete");
                if(getListAdapter().getCount() > 0){
                    comment = (Comment) getListAdapter().getItem(0);

                    //remove from database
                    datasource.deleteComment(comment);
                    //remove from arrayList
                    adapter.remove(comment);
                    break;
                }
            break;
        }

        //Notifies the attached observers that the underlying data has been changed
        //and any View reflecting the data set should refresh itself.
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onResume(){
        try {
            datasource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.onResume();
    }

    @Override
    public void onPause(){
        try {
            datasource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    public void onDestroy(){
        try {
            datasource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }



}
