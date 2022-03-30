package com.cleanup.todoc.data.database.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;


import com.cleanup.todoc.LiveDataTestUtil;
import com.cleanup.todoc.data.database.TaskDatabase;
import com.cleanup.todoc.data.database.model.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

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

    private static final long PROJECT_ID = 4L;
    private static final long NEXTPROJECT_ID = 5L;

    private static final Project PROJECT_DEMO = new Project(PROJECT_ID, "Philippe", 0xFFeb2718);
    private static final Project NEXTPROJECT_DEMO = new Project(NEXTPROJECT_ID, "Tartelette", 0xFFeb2718);

    @Test

    public void createAndSelectProject() throws InterruptedException {

        // BEFORE : Adding a new user

        this.database.projectDao().createProject(PROJECT_DEMO);

        // TEST

        Project project = LiveDataTestUtil.getValue(this.database.projectDao().selectedProject(PROJECT_ID));
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }

    @Test
    public void getProjects() throws InterruptedException {

        this.database.projectDao().createProject(NEXTPROJECT_DEMO);
        this.database.projectDao().createProject(PROJECT_DEMO);

        List<Project> project = LiveDataTestUtil.getValue(this.database.projectDao().getProjects());

        assertEquals(2, project.size());
    }

    @Test
    public void createProject() throws InterruptedException {

        this.database.projectDao().createProject(NEXTPROJECT_DEMO);

        Project project = LiveDataTestUtil.getValue(this.database.projectDao().selectedProject(NEXTPROJECT_ID));

        assertTrue(project.getName().equals(NEXTPROJECT_DEMO.getName()) && project.getId() == NEXTPROJECT_ID);

    }
}