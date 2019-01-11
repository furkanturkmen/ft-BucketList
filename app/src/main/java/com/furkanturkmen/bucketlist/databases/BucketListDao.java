package com.furkanturkmen.bucketlist.databases;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.furkanturkmen.bucketlist.models.BucketListItem;

import java.util.List;

@Dao
public interface BucketListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BucketListItem item);

    @Update
    void update(BucketListItem item);

    @Delete
    void delete(BucketListItem item);

    @Query("SELECT * FROM BucketListItems")
    LiveData<List<BucketListItem>> getAllItems();
}
