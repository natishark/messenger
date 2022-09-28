package com.messenger.mess.model.repository;

import com.messenger.mess.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
