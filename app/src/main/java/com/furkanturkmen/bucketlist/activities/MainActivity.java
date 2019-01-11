package com.furkanturkmen.bucketlist.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.furkanturkmen.bucketlist.R;
import com.furkanturkmen.bucketlist.adapters.BucketListItemAdapter;
import com.furkanturkmen.bucketlist.models.BucketListItem;
import com.furkanturkmen.bucketlist.utils.BucketListItemOnClickListener;
import com.furkanturkmen.bucketlist.viewmodels.BucketListItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BucketListItemViewModel bucketListItemViewModel;
    private RecyclerView recyclerViewBucketListItems;
    private BucketListItemAdapter bucketListItemAdapter;
    public List<BucketListItem> mbucketListItems = new ArrayList<>();
    private BucketListItemOnClickListener listener;
    private FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionButton = findViewById(R.id.button_addItem);
        recyclerViewBucketListItems = findViewById(R.id.recyclerView_bucketList);
        recyclerViewBucketListItems.setLayoutManager(new LinearLayoutManager(this));

        listener = new BucketListItemOnClickListener() {
            @Override
            public void itemOnClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("Item", mbucketListItems.get(position));
                MainActivity.this.startActivityForResult(intent, 45678);
            }
        };

        bucketListItemAdapter = new BucketListItemAdapter(this, mbucketListItems, listener);
        recyclerViewBucketListItems.setAdapter(bucketListItemAdapter);

        bucketListItemViewModel = ViewModelProviders.of(this).get(BucketListItemViewModel.class);
        bucketListItemViewModel.getAllItems().observe(this, new Observer<List<BucketListItem>>() {
            @Override
            public void onChanged(@Nullable List<BucketListItem> items) {
                mbucketListItems = items;
                bucketListItemAdapter.setCheckListItems(items);
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                MainActivity.this.startActivityForResult(intent, 45678);
            }
        });


    }
}