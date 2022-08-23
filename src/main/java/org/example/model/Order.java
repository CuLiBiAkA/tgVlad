package org.example.model;

import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tg_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Timestamp
    @Column(name = "time")
    private String time;
    @Column(name = "status")
    private String status;


    @Column(name = "feedback")
    private String feedBack;

    @Column(name = "price")
    private Integer price;
    @Column(name = "flag")
    private Boolean flag;


    @Column(name = "opisanie")
    private String opisanie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @Override
    public String toString() {
        return "\n Номер заявки: " + id +
                "\n статус заявки: " + status + '\'' +
                "\n цена за работу: " + price +
                "\n описание :" + opisanie + '\'';
    }
}
