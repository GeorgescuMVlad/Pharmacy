package model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Proxy;
import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor             // Creates constructor with all of the fields as arguments
@NoArgsConstructor              // Creates constructor with no arguments (Default)
@FieldDefaults(level = PRIVATE) // Sets the visibility of all fields to PRIVATE
@Builder                        // Builder Pattern (Lab 2)
@ToString                       // ToString method implementation for class
@Getter                         // Getters for all fields of the class
@Setter                         // Setters for all fields of the class
@Table(name = "pharmacy")
@Entity
@Proxy(lazy = false)
public class Pharmacy {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    @Column(name = "money")
    String money;

}
