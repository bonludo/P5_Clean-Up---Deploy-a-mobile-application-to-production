package com.cleanup.todoc.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.data.database.ProjectRepository;
import com.cleanup.todoc.data.database.TaskRepository;
import com.cleanup.todoc.data.database.model.Project;
import com.cleanup.todoc.data.database.model.Task;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final TaskRepository taskDataSource;
    private final ProjectRepository projectDataSource;
    private final Executor executor;

    @Nullable
    private LiveData<List<Project>> projects;

    private SortMethod sortMethod = SortMethod.NONE;

    public TaskViewModel(TaskRepository taskDataSource, ProjectRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    @Nullable
    public LiveData<List<Project>> getProjects() {
        return projects;
    }

    public void init() {
        if (projects != null) {
            return;
        }
        projects = projectDataSource.getProjects();
    }

    public LiveData<List<Task>> getAllTasks() {
        switch (sortMethod) {
            case NONE:
                return taskDataSource.getAllTask();
            case ALPHABETICAL:
                return taskDataSource.getTasksOrderByNameAZ();
            case ALPHABETICAL_INVERTED:
                return taskDataSource.getTasksOrderByNameZA();
            case OLD_FIRST:
                return taskDataSource.getTasksOrderByCreationTimesTampAsc();
            case RECENT_FIRST:
                return taskDataSource.getTasksOrderByCreationTimesTampDesc();
        }
        return taskDataSource.getAllTask();
    }

    public void addTask(Task task) {
        executor.execute(() -> {
            taskDataSource.addTask(task);
        });
    }

    public void deleteTask(Task task) {
        executor.execute(() -> {
            taskDataSource.deleteTask(task);
        });
    }

    public void setSort(SortMethod orderBy) {
        sortMethod = orderBy;
    }

    /**
     * List of all possible sort methods for task
     */
    public enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * No sort
         */
        NONE
    }

}
