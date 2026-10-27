package com.salim.actora.tickets.repository;

import com.salim.actora.tickets.entity.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket,Long> {

    Optional<List<SupportTicket>> findAllByCustomerIdOrderByCreatedAtDesc(Long customerId);

    Optional<List<SupportTicket>> findAllByCustomerIdAndOrderIdOrderByCreatedAtDesc(Long customerId,Long OrderId);
}
