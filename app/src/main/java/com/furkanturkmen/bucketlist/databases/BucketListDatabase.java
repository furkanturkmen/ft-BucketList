package com.furkanturkmen.bucketlist.databases;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.furkanturkmen.bucketlist.models.BucketListItem;

@Database(entities = BucketListItem.class, version = 1)
public abstract class BucketListDatabase extends RoomDatabase {

    private static BucketListDatabase instance;

    public abstract BucketListDao bucketListDao();

    public static synchronized BucketListDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BucketListDatabase.class, "BucketListDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static final class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private BucketListDao bucketListItemDao;

        private PopulateDbAsyncTask(BucketListDatabase db) {
            bucketListItemDao = db.bucketListDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bucketListItemDao.insert(new BucketListItem("Big Ben", "Holiday", false));
            bucketListItemDao.insert(new BucketListItem("Big Apple", "Holiday", true));
            bucketListItemDao.insert(new BucketListItem("Eiffel tower", "Holiday", true));
            return null;
        }
    }
}