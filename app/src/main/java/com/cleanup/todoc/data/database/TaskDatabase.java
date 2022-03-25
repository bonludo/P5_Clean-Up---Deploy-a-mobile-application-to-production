package com.cleanup.todoc.data.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.data.database.dao.ProjectDao;
import com.cleanup.todoc.data.database.dao.TaskDao;
import com.cleanup.todoc.data.database.model.Project;
import com.cleanup.todoc.data.database.model.Task;

@androidx.room.Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {

        // --- SINGLETON ---

        private static TaskDatabase INSTANCE;

        // --- DAO ---

        public abstract TaskDao taskDao();

        public abstract ProjectDao projectDao();

        // --- INSTANCE ---

        public static TaskDatabase getInstance(Context context) {

            if (INSTANCE == null) {

                synchronized (TaskDatabase.class) {

                    if (INSTANCE == null) {

                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                TaskDatabase.class, "taskdatabase")
                                .addCallback(prepopulateDatabase())
                                .build();

                    }

                }

            }
            return INSTANCE;
        }

        private static Callback prepopulateDatabase (){
            return new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    Project[] projects = Project.getAllProjects();
                    for (Project project : projects) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("id",project.getId());
                        contentValues.put("name", project.getName());
                        contentValues.put("color",project.getColor());
                        db.insert("Project", OnConflictStrategy.IGNORE,contentValues);
                    }
                }
            };

        }
    }
