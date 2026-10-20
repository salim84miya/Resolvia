package com.salim.actora.tickets.repository;

import com.salim.actora.tickets.entity.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket,Long> {

    List<SupportTicket> findAllByCustomerIdOrderByCreatedAtDesc(Long customerId);
}
