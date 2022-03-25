package com.cleanup.todoc.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.data.database.ProjectRepository;
import com.cleanup.todoc.data.database.TaskDatabase;
import com.cleanup.todoc.data.database.TaskRepository;
import com.cleanup.todoc.ui.TaskViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;
    private final TaskRepository taskSource;
    private final ProjectRepository projectSource;
    private Executor executor;


    public static ViewModelFactory getInstance(Context context) {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(context);
                }
            }
        }
        return factory;
    }

    public ViewModelFactory(Context context) {
        TaskDatabase taskDatabase = TaskDatabase.getInstance(context);
        this.taskSource = new TaskRepository(taskDatabase.taskDao());
        this.projectSource = new ProjectRepository(taskDatabase.projectDao());
        this.executor = Executors.newSingleThreadExecutor();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(taskSource,projectSource,executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}