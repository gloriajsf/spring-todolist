package com.service;

import java.util.List;

import com.model.Task;

public interface TaskService {
	void save(Task task);
	Task findById(Long id);
    List<Task> findByUserId(Long userId);
    void deleteTasksById(Long id);
}