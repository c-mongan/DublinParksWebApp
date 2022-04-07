package com.example.dublinparkswebapp.council;


import com.example.dublinparkswebapp.park.Park;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
//@AllArgsConstructor
//@NoArgsConstructor
@Table(name = "council")
public class Council  {

 //  @OneToMany(mappedBy = "park_id")
    @Getter @Setter
   @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "council")
    private List<Park> parks;




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Integer id;

    @Getter @Setter
    @Column(nullable = false, unique = false , length = 45)
    private String Name;


    @Getter @Setter
    @Column(nullable = true, unique = true , length = 200)
    private String fullAddress;

    @Getter @Setter
    @Column(nullable = true, unique = false , length = 500)
    private String openingHours;

    @Getter @Setter
    @Column(nullable = true, unique = false , length = 45)
    private String phone;

    @Getter @Setter
    @Column(nullable = true, unique = false , length = 45)
    private String email;

    public Council() {

    }

    public Council(List<Park> parks, String name, String fullAddress, String openingHours, String phone, String email) {
        this.parks = parks;
        Name = name;
        this.fullAddress = fullAddress;
        this.openingHours = openingHours;
        this.phone = phone;
        this.email = email;
    }




    public void addPark(Park park) {
        getParks().add(park);
    }
}





