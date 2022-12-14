package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tg_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fistName_user")
    private String fistName;

    @Column(name = "user_tg_id")
    private Long userTgId;

    @Column(name = "addres")
    private String addres;

    @Column(name = "number")
    private String number;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "admin_flag")
    private Boolean adminFlag;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @Override
    public String toString() {
        return "Ваше имя: " + fistName + "\nВаш адрес: " + addres + "\nВаш номер: " + number ;
    }
}
