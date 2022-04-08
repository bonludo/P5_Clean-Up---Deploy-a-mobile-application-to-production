package com.cleanup.todoc.data.database.dao;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.LiveDataTestUtil;
import com.cleanup.todoc.data.database.TaskDatabase;
import com.cleanup.todoc.data.database.model.Project;
import com.cleanup.todoc.data.database.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;


public class TaskDaoTest {

    private TaskDatabase database;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),

                TaskDatabase.class)

                .allowMainThreadQueries()

                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }


    private static final long PROJECT_ID = 3;

    private static final Project PROJECT_1 = new Project(1, "Projet Tartampion", 0xFFEADAD1);
    private static final Project PROJECT_2 = new Project(2, "Projet Lucidia", 0xFFB4CDBA);
    private static final Project PROJECT_3 = new Project(PROJECT_ID, "Projet Circus", 0xFFeb2718);

    private static final Task TASK_1 = new Task(1, "aaa", 123);
    private static final Task TASK_2 = new Task(2, "zzz", 124);
    private static final Task TASK_3 = new Task(3, "hhh", 125);

    private void projectList() {
        this.database.projectDao().createProject(PROJECT_1);
        this.database.projectDao().createProject(PROJECT_2);
        this.database.projectDao().createProject(PROJECT_3);
    }

    private void taskList() {
        this.database.taskDao().insertTask(TASK_1);
        this.database.taskDao().insertTask(TASK_2);
        this.database.taskDao().insertTask(TASK_3);
    }

    @Test
    public void getAllTask() throws InterruptedException {
        projectList();
        taskList();

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTask());

        assertEquals(3, tasks.size());
    }


    @Test
    public void insertTask() throws InterruptedException {
        projectList();
        this.database.taskDao().insertTask(TASK_1);

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTask());

        assertEquals(1, tasks.size());
        assertTrue(tasks.get(0).getName().equals(TASK_1.getName())
                && tasks.get(0).getCreationTimestamp() == TASK_1.getCreationTimestamp());
    }

    @Test
    public void delete() throws InterruptedException {
        projectList();
        taskList();

     //   assertEquals(3, database.taskDao()..size());

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTask());

        assertEquals(3, tasks.size());

        this.database.taskDao().delete(tasks.get(0));

        tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTask());

        assertEquals(2, tasks.size());

    }

    @Test
    public void getTasksOrderByNameAZ() throws InterruptedException {
        projectList();
        taskList();

        List<Task> tasks = LiveDataTestUtil.getValue(
                this.database.taskDao().getTasksOrderByNameAZ()
        );

        assertTrue(tasks.get(0).getName().equals(TASK_1.getName())
                && tasks.get(0).getCreationTimestamp() == TASK_1.getCreationTimestamp());
        assertTrue(tasks.get(1).getName().equals(TASK_3.getName())
                && tasks.get(1).getCreationTimestamp() == TASK_3.getCreationTimestamp());
        assertTrue(tasks.get(2).getName().equals(TASK_2.getName())
                && tasks.get(2).getCreationTimestamp() == TASK_2.getCreationTimestamp());
    }

    @Test
    public void getTasksOrderByNameZA() throws InterruptedException {
        projectList();
        taskList();

        List<Task> tasks = LiveDataTestUtil.getValue(
                this.database.taskDao().getTasksOrderByNameZA()
        );

        assertTrue(tasks.get(0).getName().equals(TASK_2.getName())
                && tasks.get(0).getCreationTimestamp() == TASK_2.getCreationTimestamp());
        assertTrue(tasks.get(1).getName().equals(TASK_3.getName())
                && tasks.get(1).getCreationTimestamp() == TASK_3.getCreationTimestamp());
        assertTrue(tasks.get(2).getName().equals(TASK_1.getName())
                && tasks.get(2).getCreationTimestamp() == TASK_1.getCreationTimestamp());

    }

    @Test
    public void getTasksOrderByCreationTimesTampAsc() throws InterruptedException {

        projectList();
        taskList();

        List<Task> tasks = LiveDataTestUtil.getValue(
                this.database.taskDao().getTasksOrderByCreationTimesTampAsc()
        );

        assertTrue(tasks.get(0).getName().equals(TASK_1.getName())
                && tasks.get(0).getCreationTimestamp() == TASK_1.getCreationTimestamp());
        assertTrue(tasks.get(1).getName().equals(TASK_2.getName())
                && tasks.get(1).getCreationTimestamp() == TASK_2.getCreationTimestamp());
        assertTrue(tasks.get(2).getName().equals(TASK_3.getName())
                && tasks.get(2).getCreationTimestamp() == TASK_3.getCreationTimestamp());


    }

    @Test
    public void getTasksOrderByCreationTimesTampDesc() throws InterruptedException {

        projectList();
        taskList();

        List<Task> tasks = LiveDataTestUtil.getValue(
                this.database.taskDao().getTasksOrderByCreationTimesTampDesc()
        );

        assertEquals(tasks.get(0).getName(), TASK_3.getName());
        assertEquals(tasks.get(0).getCreationTimestamp(), TASK_3.getCreationTimestamp());
        assertEquals(tasks.get(1).getName(), TASK_2.getName());
        assertEquals(tasks.get(1).getCreationTimestamp(), TASK_2.getCreationTimestamp());
        assertEquals(tasks.get(2).getName(), TASK_1.getName());
        assertEquals(tasks.get(2).getCreationTimestamp(), TASK_1.getCreationTimestamp());

    }
}