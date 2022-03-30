package com.cleanup.todoc.data.database;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.data.database.dao.ProjectDao;
import com.cleanup.todoc.data.database.model.Project;

import java.util.List;

public class ProjectRepository {

    private final ProjectDao projectDao;

    public ProjectRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    //PROJECT
    public LiveData<List<Project>> getProjects() {
        return this.projectDao.getProjects();
    }

    public LiveData<Project> selectProject(long projectId) {
        return this.projectDao.selectedProject(projectId);
    }

    public void addProject(Project project) {
        projectDao.createProject(project);
    }
}
