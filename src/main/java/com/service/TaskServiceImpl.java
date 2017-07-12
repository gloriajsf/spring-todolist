package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Task;
import com.repository.TaskRepository;
import com.repository.UserRepository;

@Service
public class TaskServiceImpl implements TaskService{
	@Autowired
    private TaskRepository taskRepository;
	@Autowired
    private UserRepository userRepository;
	@Override
	public void save(Task task) {
		taskRepository.save(task);
	}

	@Override
	public Task findById(Long id) {
		return taskRepository.findById(id);
	}

	@Override
	public List<Task> findByUserId(Long userId) {
		return taskRepository.findByUserId(userId);
	}

	@Override
	public void deleteTasksById(Long id) {
		taskRepository.deleteTasksById(id);
	}
}
