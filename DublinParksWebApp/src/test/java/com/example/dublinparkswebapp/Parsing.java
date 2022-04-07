package com.example.dublinparkswebapp;
import com.example.dublinparkswebapp.council.Council;
import com.example.dublinparkswebapp.council.CouncilRepository;
import com.example.dublinparkswebapp.park.Park;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.net.*;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import com.example.dublinparkswebapp.park.ParkRepository;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class Parsing {


    @Autowired
    private ParkRepository aparkRepository;

    @Autowired
    private CouncilRepository councilRepository;


    ArrayList<String> Urls = new ArrayList<String>();
    ArrayList<String> Pages = new ArrayList<String>();

    Document document;
    Document docUrls;



    @Test
    public void parseDLR() throws IOException {


        ArrayList Parks = new ArrayList();
        Council council = new Council(Parks, "Dún Laoghaire-Rathdown County Council", "Dún Laoghaire-Rathdown County Council, County Hall, Marine Road, Dún Laoghaire, Co.Dublin, A96 K6C9",
                "Monday\t9a.m.–5p.m.\n" +
                        "Tuesday\t9a.m.–5p.m.\n" +
                        "Wednesday\t9a.m.–5p.m.\n" +
                        "Thursday\t9a.m.–5p.m.\n" +
                        "Friday\t9a.m.–4:30p.m.\n" +
                        "Saturday Closed" + " Sunday Closed","01 205 4700", "info@dlrcoco.ie");

        String pUrl = "https://www.dlrcoco.ie/en/parks/blackrock-park";

        String pUrl2 = "https://www.dlrcoco.ie/en/parks/cabinteely-park";

        String pUrl3 = "https://www.dlrcoco.ie/en/parks/fernhill-park-and-gardens-0";

        String pUrl4 = "https://www.dlrcoco.ie/en/parks-outdoors/parks/marlay-park";

        String pUrl5 = "https://www.dlrcoco.ie/en/parks-outdoors/parks/killiney-hill-park";

        String pUrl6 = "https://www.dlrcoco.ie/en/parks/shanganagh-park";

        String pUrl7 = "https://www.dlrcoco.ie/en/parks/peoples-park";


        parseDLR(pUrl, Parks, council);
        parseDLR(pUrl2, Parks, council);
        parseDLR(pUrl3, Parks, council);
        parseDLR(pUrl4, Parks, council);
        parseDLR(pUrl5, Parks, council);
        parseDLR(pUrl6, Parks, council);
        parseDLR(pUrl7, Parks, council);


    }


    public void parseDLR(String pUrl, ArrayList Parks, Council council) throws IOException {

        document = Jsoup.connect(pUrl).timeout(10 * 10000).get();


        Elements parkTitle = document.getElementsByTag("h1");

        for (org.jsoup.nodes.Element h1 : parkTitle) {

            Elements Name = new Elements();
            if (h1.attr("class").equalsIgnoreCase("page-header white")) {
                Name = document.getElementsByClass("page-header white");
                String name = (Name.text());
            } else if (h1.attr("class").equalsIgnoreCase("page-header"))
                Name = document.getElementsByClass("page-header");
            String name = (Name.text());


            Elements openingHours = document.getElementsByClass("field field-name-field-hours field-type-text field-label-hidden");
            String openinghours = (openingHours.text());
            Elements directions = document.getElementsByClass("field field-name-field-address field-type-text-long field-label-above");
            String Directions = (directions.text());
            Elements wheelchair = document.getElementsByClass("clearfix text-formatted field field--name-field-accessibility-features field--type-text-long field--label-hidden");
            String website = pUrl;
            Elements facilities = document.getElementsByClass("text.location__facilities.full__facilities");


            Park park = new Park();
            park.setName(name);

            if (facilities.isEmpty()) {
                park.setFacilities("N/A");

            } else {
                park.setFacilities(facilities.text());
            }


            String fullAddress = Name.first().text() + ", " + directions.text();
            park.setFullAddress(fullAddress);

            park.setDirections(directions.text());


            if (openingHours.isEmpty()) {
                park.setOpeningHours("N/A");

            } else {
                park.setOpeningHours(openingHours.first().text());
            }


            park.setPhone("01 205 4700");
            park.setEmail("info@dlrcoco.ie");
            String council_name = ("Dún Laoghaire-Rathdown County Council");
            park.setCouncil_Name(council_name);
            park.setWebsite(website);


            park.setCouncil(council);
            council.addPark(park);

            Council savedCouncil = councilRepository.save(council);
        }
    }


    @Test
    public void parseDublinCity() throws IOException {
        Document document;
        Document docUrls;


        ArrayList Parks = new ArrayList();
        Council council = new Council(Parks, "Dublin City Council", "Dublin City Council\n" +
                "Customer Services\n" +
                "Block 3, Floor 0\n" +
                "Civic Offices\n" +
                "Wood Quay\n" +
                "Dublin 8\n" +
                "D08 RF3F", "Monday to Friday\n" +
                "9am - 5pm", "01 222 2222\n" +
                "\n", "customerservices@dublincity.ie");

        String urls = "https://www.dublincity.ie/residential/parks/dublin-city-parks/visit-park?facilities=All&keys=&page=0";
        String urls2 = "https://www.dublincity.ie/residential/parks/dublin-city-parks/visit-park?facilities=All&keys=&page=" + "1";
        String urls3 = "https://www.dublincity.ie/residential/parks/dublin-city-parks/visit-park?facilities=All&keys=&page=" + "2";
        String urls4 = "https://www.dublincity.ie/residential/parks/dublin-city-parks/visit-park?facilities=All&keys=&page=" + "3";


        iterPages(urls, Parks, council);
        iterPages(urls2, Parks, council);
        iterPages(urls3, Parks, council);
        iterPages(urls4, Parks, council);
        Council savedCouncil = councilRepository.save(council);


    }

    public void iterPages(String urls, ArrayList Parks, Council council) throws IOException {
        Document document;

        document = Jsoup.connect(urls).timeout(10 * 10000).get();


        Elements parkTitle = document.getElementsByTag("h2");

        for (org.jsoup.nodes.Element h2 : parkTitle) {

            if (h2.attr("class").equalsIgnoreCase("search-result__title")) {


                String replaceUrls = h2.text().replace(' ', '-').toLowerCase(Locale.ROOT);
                String fullyFormattedUrls1 = replaceUrls.replace('/', '-');
                String fullyFormattedUrls2 = fullyFormattedUrls1.replace(".", "");
                String fullyFormattedUrls3 = fullyFormattedUrls2.replace("'s", "s");
                String fullyFormattedUrls = fullyFormattedUrls3.replace("'", "-");
                String newUrls = "https://www.dublincity.ie/residential/parks/dublin-city-parks/visit-park/" + fullyFormattedUrls;
                Urls.add(newUrls);


            }

        }
        System.out.println(Urls);
        //Prints page url's


        for (int i = 0; i < Urls.size(); i++) {

            String allUrls = Urls.get(i);

            parseDCC(allUrls, Parks, council);
        }


    }


    public void parseDCC(String url, ArrayList Parks, Council council) throws IOException {


        Document document = Jsoup.connect(url).timeout(10 * 10000).get();


        Elements Name = document.getElementsByClass("field field--name-title field--type-string field--label-hidden");
        Elements address1 = document.getElementsByClass("full__top__location-link location__top__location-link");
        Elements openingHours = document.getElementsByClass("opening-times__today-hours");
        Elements phone = document.getElementsByClass("field field--name-field-location-phone field--type-telephone field--label-hidden");
        Elements fulladdress = document.getElementsByClass("address");
        Elements facilities = document.getElementsByClass("location__facilities-heading.ul.li");

        String website = url;


        Park park = new Park();
        park.setName((Name.first().text()));




        String fullAddress = Name.first().text() + ", " + fulladdress.first().text();
        park.setFullAddress(fullAddress);


        if (openingHours.isEmpty()) {
            park.setOpeningHours("N/A");

        } else {
            park.setOpeningHours(openingHours.first().text());
        }


        park.setPhone("01 222 2222");
        park.setEmail("parks@dublincity.ie");
        String council_name = ("Dublin City Council");
        park.setCouncil_Name(council_name);
        park.setWebsite(website);
        park.setFacilities(facilities.text());


        park.setCouncil(council);
        council.addPark(park);
    }

    @Test
    public void parseFingal() {

        String url = "https://data.smartdublin.ie/dataset/3df0c5e3-05e0-477c-904c-9d6afda732a1/resource/dc447994-05e2-4189-b816-fd532d62dab6/download/fccplayareasp20110901-1706.xml";

        try {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(false);
        documentBuilderFactory.setValidating(false);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        URLConnection urlConnection = new URL(url).openConnection();
        urlConnection.addRequestProperty("Accept", "application/xml");
            org.w3c.dom.Document document = documentBuilder.parse(urlConnection.getInputStream());
        document.getDocumentElement().normalize();

            ArrayList Parks = new ArrayList();
            Council council = new Council(Parks, "Fingal County Council", "Fingal County Council County Hall, Main Street, Swords County Dublin K67 X8Y2", "Monday - Thursday 9am - 5pm; Friday - 9am - 4.30pm.", "01 890 5000", "customercareunit@fingal.ie");
            NodeList nodeList = document.getElementsByTagName("Play_Areas");

            for (int count = 0; count < nodeList.getLength(); count++) {

                Node aNode = nodeList.item(count);

                if (aNode.getNodeType() == Node.ELEMENT_NODE) {

                    org.w3c.dom.Element element = (org.w3c.dom.Element) aNode;


                    String Name = element.getElementsByTagName("Name").item(0).getTextContent();
                    String address1 = element.getElementsByTagName("Address1").item(0).getTextContent();
                    String address2 = element.getElementsByTagName("Address2").item(0).getTextContent();
                    String address3 = element.getElementsByTagName("Address3").item(0).getTextContent();
                    String address4 = element.getElementsByTagName("Address4").item(0).getTextContent();
                    String openingHours = element.getElementsByTagName("Opening_Hours").item(0).getTextContent();
                    String facilities = element.getElementsByTagName("Type").item(0).getTextContent();
                    String fullAddress = Name + ", " + address1 + ", " + address2 + ", " + address3 + " " + address4;
                    String phone = element.getElementsByTagName("Phone").item(0).getTextContent();
                    String email = element.getElementsByTagName("Email").item(0).getTextContent();
                    String Website = element.getElementsByTagName("Website").item(0).getTextContent();
                    String Category = element.getElementsByTagName("Category").item(0).getTextContent();
                    String Directions = element.getElementsByTagName("Directions").item(0).getTextContent();
                    String Surface_Type = element.getElementsByTagName("Surface_Type").item(0).getTextContent();
                    String Comments = element.getElementsByTagName("Comments").item(0).getTextContent();
                    String WheelchairAccessible = element.getElementsByTagName("Accessible_Play_Items").item(0).getTextContent();
                    String Disabled_Parking = element.getElementsByTagName("Disabled_Parking").item(0).getTextContent();
                    String Park_Ranger = element.getElementsByTagName("Park_Ranger").item(0).getTextContent();
                    String Toilets = element.getElementsByTagName("Toilets").item(0).getTextContent();
                    String Disabled_Toilets = element.getElementsByTagName("Disabled_Toilets").item(0).getTextContent();
                    String Baby_Changing = element.getElementsByTagName("Baby_Changing").item(0).getTextContent();
                    String Seating = element.getElementsByTagName("Seating").item(0).getTextContent();
                    String Drinking_Water = element.getElementsByTagName("Drinking_Water").item(0).getTextContent();
                    String council_name = ("Fingal County Council");


                    Park park = new Park();
                    park.setName(Name);
                    park.setFullAddress(fullAddress);
                    park.setOpeningHours(openingHours);
                    park.setFacilities(facilities);
                    park.setPhone(phone);
                    park.setEmail(email);
                    park.setWebsite(Website);
                    park.setCategory(Category);
                    park.setDirections(Directions);
                    park.setSurface_Type(Surface_Type);
                    park.setComments(Comments);
                    park.setWheelchairAccessible(WheelchairAccessible);
                    park.setDisabled_Parking(Disabled_Parking);
                    park.setPark_Ranger(Park_Ranger);
                    park.setToilets(Toilets);
                    park.setDisabled_Toilets(Disabled_Toilets);
                    park.setBaby_Changing(Baby_Changing);
                    park.setSeating(Seating);
                    park.setDrinking_Water(Drinking_Water);
                    park.setCouncil_Name(council_name);
                    park.setCouncil(council);
                    council.addPark(park);
                    Council savedCouncil = councilRepository.save(council);

                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }


}
