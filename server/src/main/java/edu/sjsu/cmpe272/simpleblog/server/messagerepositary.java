package edu.sjsu.cmpe272.simpleblog.server;

import java.util.List;



public interface MessageRepository extends JpaRepository<Messageentry, Long> {
    List<Messageentry> findAllByOrderByMessageIdDesc();
    List<Messageentry> findAllByOrderByMessageIdAsc();
}


