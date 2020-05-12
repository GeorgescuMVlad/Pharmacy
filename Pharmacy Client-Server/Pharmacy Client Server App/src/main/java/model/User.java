package model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Proxy;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import static lombok.AccessLevel.PRIVATE;


@AllArgsConstructor             // Creates constructor with all of the fields as arguments
@NoArgsConstructor              // Creates constructor with no arguments (Default)
@FieldDefaults(level = PRIVATE) // Sets the visibility of all fields to PRIVATE
@Builder                        // Builder Pattern (Lab 2)
@ToString                       // ToString method implementation for class
@Getter                         // Getters for all fields of the class
@Setter                         // Setters for all fields of the class
@Table(name = "user")
@Entity
@Proxy(lazy = false)
public class User implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    @Column(name = "mail")
    String mail;

    @Column(name = "password")
    String password;

    @Column(name = "name")
    String name;

    @Column(name = "money")
    String money;

    @Column(name = "role")
    boolean role;

    public boolean getRole() {
        return this.role;
    }

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "user_drug",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "drug_id", referencedColumnName = "id"))
    @OrderColumn(name = "id") @GeneratedValue(strategy= GenerationType.IDENTITY)
    private List<Drug> drugs = new ArrayList<>();



}
