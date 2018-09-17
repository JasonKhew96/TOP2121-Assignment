package com.github.travel;

import static spark.Spark.*;
import com.google.gson.Gson;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class App {

    private static void createTable() {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:guide.db");

            stmt = c.createStatement();
            String sql = "CREATE TABLE GUIDE"
                    + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "DETAILS_ID TEXT NOT NULL,"
                    + "TITLE TEXT NOT NULL,"
                    + "IMAGE TEXT NOT NULL,"
                    + "INTRO TEXT NOT NULL,"
                    + "CONTENT TEXT NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private static void populateTable() {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:guide.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "INSERT INTO GUIDE (DETAILS_ID, TITLE, IMAGE, INTRO, CONTENT) "
                    + "VALUES ('amsterdam', 'The NetherLand', 'https://cdn-image.travelandleisure.com/sites/default/files/styles/tlp_295x187_one_fourth/public/1447946383/amsterdam-DGLP1115.jpg', 'Armsterdam', 'With 60 miles of canals and nearly 7,000 buildings dating from the 18th century or earlier, Amsterdam is one of Europe’s best preserved cities—the center has barely changed since Rembrandt’s time. Yet it’s no mere museum piece: T+L’s Amsterdam travel guide shows that, alongside the historic monuments and Old Masters, the city is a modern capital with all the trappings a traveler could want, including a wide range of hotels, innovative restaurants, and stylish shopping.<br><br>Colorful, quirky and charming, the old-world city is lined with 17th century building, canals, and the constant sight of bicycles whizzing by. But it also bears the hallmarks of a thriving and downright lenient metropolis. While many young people visit Amsterdam for the marijuana scene—and perhaps to peek at the legal prostitutes in the Red Light District—the city is teeming with high culture, from the world-class art in the Van Gogh Museum and the Rijksmuseumn to P.C. Hooftstraat, the shopping street where you can find plenty of the contemporary, cutting-edge design that has become a source of pride in Amsterdam and the Netherlands.<br><br>There’s no bad time to go to this Dutch capital, but in general, May through October is the main travel season, when temperatures are warmest. Crowds peak in July and August. The other top time for a trip to Amsterdam is tulip season: The city bursts into bloom as early as March, and the plumage can extend into May. Rain is possible year-round, and winter can bring occasional snow and slush, so pack layers. Overall, it’s generally mild, but often wet, so bring an umbrella and suitable clothing at any time of year.');";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO GUIDE (DETAILS_ID, TITLE, IMAGE, INTRO, CONTENT) "
                    + "VALUES ('atlanta', 'United States', 'https://cdn-image.travelandleisure.com/sites/default/files/styles/tlp_295x187_one_fourth/public/1449679670/atlanta-DGLP1115.jpg', 'Atlanta', 'The beating heart of the American South, Atlanta is a bustling financial and commercial center that also boasts a thriving cultural community. Originally a railroad town that served as a gateway to more remote corners of the Southeast, the vibrant city is now the largest in Georgia and remains a crucial transportation hub. Home to world-renowned chefs, entrepreneurs, and media moguls, Atlanta’s magnetic charisma is grounded in its historic roots, but enhanced by its present-day charm and amenities.<br><br>Known for having a diverse population, progressive views, and rolling hills, the Peach City also boasts consistently nice weather for much of the year. The best times to travel to Atlanta are in the spring, when the azaleas and dogwoods are in full bloom, and the fall, when the Appalachian foothill foliage is at its best. Atlanta winters are usually mild, and fewer hotels and restaurants are booked. (During the summer, however, average temperatures often exceed 90 degrees, and rain is frequent.)<br><br>The city was a touchpoint during the Civil Rights Movement in the 1960s, and visitors can learn about this era at the Center for Civil and Human Rights and the Martin Luther King, Jr. Center for Nonviolent Social Change, which includes access to the house in which the civil rights leader grew up and the church at which he was a pastor. Other popular attractions include the High Museum of Art, Georgia Aquarium (the largest in the hemisphere), Piedmont Park, and the College Football Hall of Fame. Atlanta also has a burgeoning restaurant scene, with famous culinary talents bringing new interest to already dynamic neighborhoods, like chef Kevin Gillespie’s creative, genre-defying new Glenwood Park spot, Gunsmoke, or Angus Brown and Nhan Le’s ambitious Asian cooking at Octopus Bar in South Buckhead. A mix of big-name hotels and antique-adorned bed and breakfasts; sports bars and craft cocktail dens; and well-curated boutiques and artisanal shops provide locals and visitors endless options for entertainment. To help narrow down the choices, T+L’s Atlanta travel guide highlights the best places to eat, stay, shop, and visit in Georgia’s state capital and the surrounding area.');";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO GUIDE (DETAILS_ID, TITLE, IMAGE, INTRO, CONTENT) "
                    + "VALUES ('bali', 'Indonesia', 'https://cdn-image.travelandleisure.com/sites/default/files/styles/tlp_295x187_one_fourth/public/1480957710/bali-DGLP1116.jpg', 'Bali', 'Bali is known as the Island of the Gods for a reason. It’s one of 17,500 islands in the Indonesian archipelago, yet even among its colorful neighbors—and even after decades of tourism development—it stands alone in its incomparable beauty. It is part of the Coral Triangle, which has the highest diversity of marine species on earth, making the coral reefs that surround the island a spectacular sight. Despite the island’s small size, its population holds most of Indonesia’s Hindu minority. Bali is known for surfing, ancient temples, and palaces, but also has active volcanoes and wild jungles. It has white-sand beaches in the south, and striking black sand in the north and west.<br><br>Bali has been a popular tourist destination for travelers from all over the world, but especially Australians and Americans, for the past few decades. As a result, the island is well accustomed to meeting the needs of international tourists. Travel + Leisure’s Bali travel guide will help you make the perfect trip to see Bali’s ancient and natural wonders.');";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO GUIDE (DETAILS_ID, TITLE, IMAGE, INTRO, CONTENT) "
                    + "VALUES ('beijing', 'China', 'https://cdn-image.travelandleisure.com/sites/default/files/styles/tlp_295x187_one_fourth/public/beijing-dglp1115.jpg', 'Beijing', 'Beijing is best summed up by the words of novelist and playwright Lao She (1899-1966), one of the city’s most famous sons: “filthy, beautiful, decadent, bustling, chaotic, idle, lovable.” From sprawling imperial gardens to cramped hutong alleyways, Beijing pulses with vitality and contradiction.<br><br>For Ming dynasty emperors—who presided over the creation of masterworks like the Temple of Heaven, the Forbidden City, and the later stages of the Great Wall of China—Beijing was nothing less than the center of the universe. There are still a million reasons to visit Beijing today, and T+L’s Beijing travel guide can tell you why.<br><br>It is the nation’s cultural and political center, and the second largest city in China. It has both modern marvels and famous temples and gardens woven through the buzzing metropolis (the capital’s modernization drive prior to the 2008 Beijing Summer Olympics led to the creation of iconic new buildings like the CCTV Tower, Bird’s Nest, and Water Cube). It can be somewhat more relaxed than Shanghai or Hong Kong, but equally as packed with people. The best ways to get around are cabs or the subway, but renting a bike will help you see as much of Beijing as possible. It’s a massive city, with enviable sites spread far and wide.<br><br>A trip to Beijing is best during the autumn months, from September to November. The weather is nice, and there are fewer tourists. Winters are icy, but the sites are less crowded then; springs are arid but crowds are heavy then, coming in by the droves for the Spring Festival. Summer is blazing hot with heavy rainstorms, but also peak tourist season. A warning: Air pollution is worst in winter and summer.');";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private static void selectTable() {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:guide.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM GUIDE;")) {
                while (rs.next()) {
                    String details_id = rs.getString("details_id");
                    String title = rs.getString("title");
                    String image = rs.getString("image");
                    String intro = rs.getString("intro");
                    String content = rs.getString("content");
                }
            }
            stmt.close();
            c.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private static Guide getGuide() {
        Connection c;
        Statement stmt;
        Guide guide = new Guide();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:guide.db");
            c.setAutoCommit(false);

            List<Items> items_arr = new ArrayList<>();

            stmt = c.createStatement();
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM GUIDE;")) {
                while (rs.next()) {
                    Items items = new Items();
                    String details_id = rs.getString("details_id");
                    String title = rs.getString("title");
                    String image = rs.getString("image");
                    String intro = rs.getString("intro");

                    items.setId(details_id);
                    items.setImg(image);
                    items.setIntro(intro);
                    items.setTitle(title);

                    items_arr.add(items);
                }
                guide.setItems(items_arr);
            }
            stmt.close();
            c.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return guide;
    }

    private static Details getDetails(String id) {
        Connection c;
        Statement stmt;
        Details details = new Details();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:guide.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM GUIDE WHERE DETAILS_ID = '" + id + "' LIMIT 1;")) {
                rs.next();
                String title = rs.getString("title");
                String intro = rs.getString("intro");
                String content = rs.getString("content");

                details.setTitle(title);
                details.setIntro(intro);
                details.setContent(content);
            }
            stmt.close();
            c.close();
        } catch (ClassNotFoundException | SQLException e) {
            if (!"ResultSet closed".equals(e.getMessage())) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            details.setContent("undefined");
        }
        return details;
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        Gson gson = new Gson();

        staticFiles.location("/public");
//        get("/hello", (request, response) -> "Hello World!");
        get("/getGuide", (request, response) -> gson.toJson(getGuide()));
        get("/getDetails/:id", (request, response) -> gson.toJson(getDetails(request.params(":id"))));
    }
    
    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
