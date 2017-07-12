package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	Task findById(Long id);
	List<Task> findByUserId(Long userId);
	@Modifying
    @Transactional
    @Query("delete from Task t where t.id = ?1")
    void deleteTasksById(Long id);
}
