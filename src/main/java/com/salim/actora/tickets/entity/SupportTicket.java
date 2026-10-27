package com.salim.actora.tickets.entity;

import com.salim.actora.tickets.data.TicketPriority;
import com.salim.actora.tickets.data.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "support_ticket_table")
public class SupportTicket {

    @Id
    @GeneratedValue
    @Column(name = "ticket_id")
    private Long id;

    @Column(name = "customer_id",nullable = false)
    private Long customerId;

    @Column(name = "order_id",nullable = false)
    private Long orderId;

    @Column(name = "ticket_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus status= TicketStatus.OPEN;

    @Column(name = "ticket_priority",nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketPriority priority;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "description")
    @NotNull
    private String description;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
