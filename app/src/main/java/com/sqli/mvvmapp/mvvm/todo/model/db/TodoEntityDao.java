package com.sqli.mvvmapp.mvvm.todo.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface TodoEntityDao {
    @Query("SELECT * FROM todo WHERE user_id = :userId")
    public Single<List<TodoEntity>> getTodos(Long userId);

    @Insert
    public void insert(TodoEntity todoEntity);

    @Delete
    public void delete(TodoEntity todoEntity);
}
