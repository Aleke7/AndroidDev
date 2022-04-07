package com.project.mvvmtodo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.project.mvvmtodo.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class, Category::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ): RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(Category("Urgent"))
                dao.insert(Category("Important"))
                dao.insert(Category("Urgent Important"))

                val task1 = Task("Go to university", categoryId = 1)
                val task2 = Task("Read book", categoryId = 1, status = true)
                val task3 = Task("Go to GYM", categoryId = 2)
                val task4 = Task("Do labs", categoryId = 2, status = true)
                val task5 = Task("Call someone", categoryId = 3)

                dao.insert(task1.copy(categoryTitle = dao.getCategory(task1.categoryId).title))
                dao.insert(task2.copy(categoryTitle = dao.getCategory(task2.categoryId).title))
                dao.insert(task3.copy(categoryTitle = dao.getCategory(task3.categoryId).title))
                dao.insert(task4.copy(categoryTitle = dao.getCategory(task4.categoryId).title))
                dao.insert(task5.copy(categoryTitle = dao.getCategory(task5.categoryId).title))
            }
        }
    }
}