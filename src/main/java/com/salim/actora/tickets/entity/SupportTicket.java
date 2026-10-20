package com.salim.actora.tickets.entity;

import com.salim.actora.tickets.data.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
public class SupportTicket {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Long customerId;

    @NotNull
    private Long orderId;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private String title;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
