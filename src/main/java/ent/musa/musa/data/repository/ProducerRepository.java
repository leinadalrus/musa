package ent.musa.musa.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ent.musa.musa.models.Producer;

public interface ProducerRepository extends JpaRepository<Producer, Long> {
}
