package com.cleanup.todoc.data.database;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.data.database.dao.TaskDao;
import com.cleanup.todoc.data.database.model.Task;

import java.util.List;

public class TaskRepository {

    private TaskDao taskDao;

    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    // TASK
    public LiveData<List<Task>> getAllTask() {
        return taskDao.getAllTask();
    }

    public void addTask(Task task) {
        taskDao.insertTask(task);
    }

    public void deleteTask(Task task) {
        taskDao.delete(task);
    }

    public LiveData<List<Task>> getTasksOrderByNameAZ() {
        return taskDao.getTasksOrderByNameAZ();
    }

    public LiveData<List<Task>> getTasksOrderByNameZA() {
        return taskDao.getTasksOrderByNameZA();
    }

    public LiveData<List<Task>> getTasksOrderByCreationTimesTampAsc() {
        return taskDao.getTasksOrderByCreationTimesTampAsc();
    }

    public LiveData<List<Task>> getTasksOrderByCreationTimesTampDesc() {
        return taskDao.getTasksOrderByCreationTimesTampDesc();
    }
}
