package com.furkanturkmen.bucketlist.utils;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.furkanturkmen.bucketlist.databases.BucketListDao;
import com.furkanturkmen.bucketlist.databases.BucketListDatabase;
import com.furkanturkmen.bucketlist.models.BucketListItem;

import java.util.List;

public class BucketListItemRepository {
    private BucketListDao bucketListDao;
    private LiveData<List<BucketListItem>> allItems;

    public BucketListItemRepository (Application application){
        BucketListDatabase database = BucketListDatabase.getInstance(application);
        bucketListDao = database.bucketListDao();
        allItems = bucketListDao.getAllItems();
    }

    public void insert(BucketListItem item) {
        new InsertItemAsyncTask(bucketListDao).execute(item);
    }

    public void update(BucketListItem item) {
        new UpdateItemAsyncTask(bucketListDao).execute(item);
    }

    public void delete(BucketListItem item) {
        new DeleteItemAsyncTask(bucketListDao).execute(item);
    }

    public LiveData<List<BucketListItem>> getAllItems() {
        return allItems;
    }

    private static class InsertItemAsyncTask extends AsyncTask<BucketListItem, Void, Void> {
        private BucketListDao bucketListDao;

        private InsertItemAsyncTask(BucketListDao bucketListDao) {
            this.bucketListDao = bucketListDao;
        }

        @Override
        protected Void doInBackground(BucketListItem... bucketListItems) {
            bucketListDao.insert(bucketListItems[0]);
            return null;
        }
    }

    private static class UpdateItemAsyncTask extends AsyncTask<BucketListItem, Void, Void> {
        private BucketListDao bucketListDao;

        private UpdateItemAsyncTask(BucketListDao bucketListDao) {
            this.bucketListDao = bucketListDao;
        }

        @Override
        protected Void doInBackground(BucketListItem... bucketListItems) {
            bucketListDao.update(bucketListItems[0]);
            return null;
        }
    }

    private static class DeleteItemAsyncTask extends AsyncTask<BucketListItem, Void, Void> {
        private BucketListDao bucketListDao;

        private DeleteItemAsyncTask(BucketListDao bucketListDao) {
            this.bucketListDao = bucketListDao;
        }

        @Override
        protected Void doInBackground(BucketListItem... bucketListItems) {
            bucketListDao.delete(bucketListItems[0]);
            return null;
        }
    }
}