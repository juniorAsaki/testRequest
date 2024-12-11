package SecurityTest.example.testRequest.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "app-user")
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true , nullable = false)
    private String email;

    private String password;

    private boolean actif = false;

    private boolean rememberMe;

    @OneToOne(cascade = CascadeType.ALL)
    private Role role;
}
