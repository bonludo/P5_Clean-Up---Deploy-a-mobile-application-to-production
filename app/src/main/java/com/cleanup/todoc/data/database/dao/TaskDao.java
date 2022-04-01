package com.cleanup.todoc.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.data.database.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTask();

    @Insert
    long insertTask(Task task);

    @Delete
    int delete(Task task);

    @Query("SELECT * FROM Task ORDER BY name ASC ")
    LiveData<List<Task>> getTasksOrderByNameAZ();

    @Query("SELECT * FROM Task ORDER BY name DESC ")
    LiveData<List<Task>> getTasksOrderByNameZA();

    @Query("SELECT * FROM Task ORDER BY creationTimestamp ASC ")
    LiveData<List<Task>> getTasksOrderByCreationTimesTampAsc();

    @Query("SELECT * FROM Task ORDER BY creationTimestamp DESC ")
    LiveData<List<Task>> getTasksOrderByCreationTimesTampDesc();
}
