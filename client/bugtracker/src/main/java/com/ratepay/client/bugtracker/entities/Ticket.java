package com.ratepay.client.bugtracker.entities;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
import com.ratepay.core.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Ticket extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    private Timestamp timestamp;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User author;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TicketType type;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TicketPriority priority;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Project project;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User developer;
    public Ticket(String title, String description, Timestamp timestamp, User author, TicketType type,
                  TicketPriority priority, Project project){
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.author = author;
        this.type = type;
        this.priority = priority;
        this.project = project;
        this.developer = null;
    }

    @Override
    public String getSelectTitle() {
        return title;
    }
}
