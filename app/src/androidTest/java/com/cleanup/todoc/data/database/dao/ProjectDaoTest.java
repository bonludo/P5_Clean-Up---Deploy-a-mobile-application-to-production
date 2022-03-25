package com.cleanup.todoc.data.database.dao;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;


import com.cleanup.todoc.data.database.TaskDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


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

    @Test
    public void getProjects() {
    }

    @Test
    public void createProject() {
    }

    @Test
    public void selectedProject() {
    }
}