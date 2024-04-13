package edu.sjsu.cmpe272.simpleblog.server;



import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageJpaRepo extends JpaRepository<Messages, Long> {
    List<Messages> findAllByOrderByMessageIdDesc();


}
