package br.com.werison.hrworker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.werison.hrworker.entities.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

}
