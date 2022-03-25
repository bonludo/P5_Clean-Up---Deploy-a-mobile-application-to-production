package com.cleanup.todoc.data.database.model;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Comparator;

/**
 * <p>Model for the tasks of the application.</p>
 *
 * @author Gaëtan HERFRAY
 */

@Entity(foreignKeys = @ForeignKey(entity = Project.class,
        parentColumns = "id",
        childColumns = "projectId"))
public class Task {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(index = true)
    private long projectId;

    /**
     * The name of the task
     */
    // Suppress warning because setName is called in constructor
    @SuppressWarnings("NullableProblems")
    @NonNull
    private String name;

    /**
     * The timestamp when the task has been created
     */
    private long creationTimestamp;

    /**
     * Instantiates a new Task.
     *
     * @param projectId         the unique identifier of the project associated to the task to set
     * @param name              the name of the task to set
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    public Task(long projectId, @NonNull String name, long creationTimestamp) {
        this.setProjectId(projectId);
        this.setName(name);
        this.setCreationTimestamp(creationTimestamp);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Nullable
    public Project getProject() {
        return Project.getProjectById(projectId);
    }


    public long getProjectId() {
        return projectId;
    }

    private void setProjectId(long projectId) {
        this.projectId = projectId;
    }


    @NonNull
    public String getName() {
        return name;
    }

    private void setName(@NonNull String name) {
        this.name = name;
    }


    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    private void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
}
