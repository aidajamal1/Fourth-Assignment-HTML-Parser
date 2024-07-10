import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Parser {
    static final List<Country> countries = new ArrayList<>();
    String filePath;

    public Parser(String filePath) {
        this.filePath = filePath;
    }

    public Parser() {
        this.filePath = "src/Resources/country-list.html";
    }

    public List<Country> sortByName() {
        List<Country> sortedByName = new ArrayList<>(countries);
        sortedByName.sort(Comparator.comparing(Country::getName));
        return sortedByName;
    }

    public List<Country> sortByPopulation() {
        List<Country> sortedByPopulation = new ArrayList<>(countries);
        sortedByPopulation.sort(Comparator.comparingInt(Country::getPopulation).reversed());
        return sortedByPopulation;
    }

    public List<Country> sortByArea() {
        List<Country> sortedByArea = new ArrayList<>(countries);
        sortedByArea.sort(Comparator.comparingDouble(Country::getArea).reversed());
        return sortedByArea;
    }

    public void setUp() throws IOException {
        String htmlText = new String(Files.readAllBytes(Paths.get(filePath)));
        Document document = Jsoup.parse(htmlText);
        Elements countryElements = document.select("div.country");
        for (Element countryElement : countryElements) {
            String name = "no name";
            String capital = "no capital";
            int population = 0;
            double area = 0.0d;
            Element nameElement = countryElement.select("h3.country-name").first();
            if (nameElement != null)
                name = nameElement.text();

            Element capitalElement = countryElement.select("span.country-capital").first();
            if (capitalElement != null)
                capital = capitalElement.text();

            Element populationElement = countryElement.select("span.country-population").first();
            if (populationElement != null)
                population = Integer.parseInt(populationElement.text());

            Element areaElement = countryElement.select("span.country-area").first();
            if (areaElement != null)
                area = Double.parseDouble(areaElement.text());
            Country countryInfo = new Country(name, capital, population, area);

            countries.add(countryInfo);

        }

    }

}
