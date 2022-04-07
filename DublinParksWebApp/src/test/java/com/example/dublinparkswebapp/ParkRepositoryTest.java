package com.example.dublinparkswebapp;

import com.example.dublinparkswebapp.council.Council;
import com.example.dublinparkswebapp.council.CouncilRepository;
import com.example.dublinparkswebapp.park.Park;
import com.example.dublinparkswebapp.park.ParkRepository;
import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ParkRepositoryTest {
    @Autowired
    private ParkRepository parkRepository;







    @Test
    public void testListAll() {

        Iterable<Park> parks = parkRepository.findAll();
        Assertions.assertThat(parks).hasSizeGreaterThan(0);

        for (Park park : parks) {

            System.out.println(park);
        }

    }

    @Test
    public void testUpdate() {

        Integer parkId = 1;
        Optional<Park> optionalPark = parkRepository.findById(parkId);
        Park park = optionalPark.get();

        park.setDirections("Test");
        parkRepository.save(park);

        Park updatedPark = parkRepository.findById(parkId).get();
        Assertions.assertThat(updatedPark.getDirections()).isEqualTo("Test");


    }


    @Test
    public void testGet() {
        Integer parkId = 2;

        Optional<Park> optionalPark = parkRepository.findById(parkId);
        Park park = optionalPark.get();
        Assertions.assertThat(optionalPark).isPresent();
        System.out.println(optionalPark.get());


    }

    @Test
    public void testDelete() {
        Integer parkId = 1;
        parkRepository.deleteById(parkId);
        Optional<Park> optionalPark = parkRepository.findById(parkId);
        Assertions.assertThat(optionalPark).isNotPresent();


    }


}







