package com.example.dublinparkswebapp.park;

import com.example.dublinparkswebapp.council.Council;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


//@XmlRootElement(name = "Play_Areas-table")
//@XmlAccessorType(XmlAccessType.PROPERTY)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "park")

public class Park {

    @ManyToOne
    // @JoinColumn(name = "council_id")
    // @JoinColomn(name = "council")
    // @ManyToOne
    private Council council;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(nullable = false, unique = false, length = 100)
    private String Name;



    @Column(nullable = true, unique = false, length = 400)
    private String fullAddress;


    @Column(nullable = true, unique = false, length = 500)
    private String openingHours;


    @Column(nullable = true, unique = false, length = 100)
    private String phone;


    @Column(nullable = true, unique = false, length = 100)
    private String email;


    @Column(nullable = true, unique = false, length = 100)
    private String Website;

    @Column(nullable = true, unique = false, length = 100)
    private String Category;

    @Column(nullable = true, unique = false, length = 500)
    private String Directions;

    @Column(nullable = true, unique = false, length = 100)
    private String Surface_Type;

    @Column(nullable = true, unique = false, length = 100)
    private String Comments;

    @Column(nullable = true, unique = false, length = 100)
    private String WheelchairAccessible;

    @Column(nullable = true, unique = false, length = 100)
    private String Disabled_Parking;
    @Column(nullable = true, unique = false, length = 100)
    private String Park_Ranger;
    @Column(nullable = true, unique = false, length = 100)
    private String Toilets;
    @Column(nullable = true, unique = false, length = 100)
    private String Disabled_Toilets;
    @Column(nullable = true, unique = false, length = 100)
    private String Baby_Changing;
    @Column(nullable = true, unique = false, length = 100)
    private String Seating;
    @Column(nullable = true, unique = false, length = 100)
    private String Drinking_Water;
    @Column(nullable = true, unique = false, length = 100)
    private String Council_Name;
    @Column(nullable = true, unique = false, length = 300)
    private String Facilities;

}

