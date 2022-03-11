package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Void createProject(Project project);
    //creer un project si il n'est pas présent

    @Query("SELECT * FROM Task WHERE Id = :projectId")
    LiveData<List<Project>> getProject(long projectId);
}
