package com.gabriele.eventhub.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketType type;

    @Column(nullable = false)
    private BigDecimal pricePaid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }

    public TicketType getType() { return type; }
    public void setType(TicketType type) { this.type = type; }

    public BigDecimal getPricePaid() { return pricePaid; }
    public void setPricePaid(BigDecimal pricePaid) { this.pricePaid = pricePaid; }

    public TicketStatus getStatus() { return status; }
    public void setStatus(TicketStatus status) { this.status = status; }
}