package ru.nsu.promocodesharebackend;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.nsu.promocodesharebackend.model.Shop;
import ru.nsu.promocodesharebackend.parser.Parser;

import java.io.IOException;
import java.util.List;

public class ParserTest {
    private static Parser parser;
    private static String categoriesURL;
    private static String shopsURL;
    private static List<Shop> shops;

    @BeforeAll
    public static void beforeAll() throws IOException {
        parser = new Parser();

        // Init paths
        /*Properties properties = new Properties();
        properties.load(ParserTests.class.getResourceAsStream("/parser-test.properties"));
        groupsDocPath = properties.getProperty("groups_doc_path");
        specCoursesDocPath = properties.getProperty("spec_courses_doc_path");
        timetablesDirectoryPath = properties.getProperty("timetables_directory_path");*/

        categoriesURL = "https://promokod.pikabu.ru/category";
        shopsURL = "https://promokod.pikabu.ru/shops";

        // Parse shops
        /*InputStream groupsDoc = ParserTests.class.getResourceAsStream(groupsDocPath);
        groups = parser.parseGroupsNumber(Jsoup.parse(groupsDoc, "UTF-8", ""));*/

        shops = parser.parsePikabuShops(categoriesURL, shopsURL);

        for (Shop shop: shops) {
            System.out.println(shop);
        }

       /* // Parse spec courses
        InputStream specCoursesDoc = ParserTests.class.getResourceAsStream(specCoursesDocPath);
        specCourses = parser.parseSpecCourses(Jsoup.parse(specCoursesDoc, "UTF-8", ""), 3, 4);

        // Parse timetables
        Map<Group, Document> groupsDocumentsMap = groups
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        group -> {
                            InputStream groupDoc = ParserTests.class
                                    .getResourceAsStream(timetablesDirectoryPath + group.getGroupNumber() + ".html");
                            try {
                                return Jsoup.parse(groupDoc, "UTF-8", "");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }));
        timetable = parser.parseTimetables(specCourses, groupsDocumentsMap, 3);*/
    }

    @Test
    public void test() {

    }
}
