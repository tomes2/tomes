package Pages;

import com.codeborne.selenide.*;
import com.csvreader.CsvWriter;
import com.opencsv.CSVWriter;
import net.bytebuddy.asm.Advice;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

public class GenetekaPage extends CommonPage {


    //ogolne
    SelenideElement teren = $(By.id("sel_w"));
    SelenideElement parafia = $(By.id("select2-sel_rid-container"));
    SelenideElement parafiaSearch = $(By.xpath("//input[@type='search']"));
    SelenideElement dataOd = $(By.xpath("//input[@name='from_date']"));
    SelenideElement dataDo = $(By.xpath("//input[@name='to_date']"));
    SelenideElement wyszukaj = $(By.xpath("//input[@value='Wyszukaj']"));

    //urodzenia
    SelenideElement next = $(By.xpath("//div[@id='table_b_paginate']/a[@class='paginate_button next']"));  // aktywny
    SelenideElement next2 = $(By.xpath("//div[@id='table_b_paginate']/a[@class='paginate_button next disabled']"));  //nieaktywny
    SelenideElement zmianaWWW = $(By.xpath("//a[@id='table_b_next']"));

    //malzenstwa
    SelenideElement malTab = $(By.xpath("//a[contains(text(),'Małżeństwa')]"));
    SelenideElement malWczytywanie = $(By.id("table_s_processing"));
    SelenideElement next_mal = $(By.xpath("//div[@id='table_s_paginate']/a[@class='paginate_button next']"));  // aktywny
    SelenideElement zmianaWWWmal = $(By.xpath("//a[@id='table_s_next']"));
    //zgony
    SelenideElement zgonyTab = $(By.xpath("//a[contains(text(),'Zgony')]"));
    SelenideElement zgonyWczytywanie = $(By.id("table_d_processing"));
    SelenideElement next_zgony = $(By.xpath("//div[@id='table_d_paginate']/a[@class='paginate_button next']"));  // aktywny
    SelenideElement next2_zgony = $(By.xpath("//div[@id='table_d_paginate']/a[@class='paginate_button next disabled']"));  //nieaktywny
    SelenideElement zmianaWWWzgony = $(By.xpath("//a[@id='table_d_next']"));


    public GenetekaPage indeksy(String ter, String par, String dao, String dad) throws InterruptedException, IOException {

        clearBrowserCache();
        Configuration.browserSize = "1550x810";
        open("http://geneteka.genealodzy.pl/index.php?op=gt&lang=pol&w=07mz");
        teren.waitUntil(Condition.visible, 30000, 5).click();
        teren.selectOption(ter);
        parafia.click();
        parafiaSearch.setValue(par);
        parafiaSearch.sendKeys(Keys.ENTER);
        dataOd.setValue(dao);
        dataDo.setValue(dad);
        wyszukaj.click();


        return this;
    }


    //-----------URODZENIA
    public GenetekaPage urodzenia(String path) throws IOException, InterruptedException {

        String sciezka = "D:\\" + path + ".csv";

        CsvWriter writer = new CsvWriter(sciezka, ';', Charset.forName("Cp1250"));
        writer.write("Rok");
        writer.write("Akt");
        writer.write("Imię");
        writer.write("Nazwisko");
        writer.write("Przydomek");
        writer.write("Imię ojca");
        writer.write("Imię matki");
        writer.write("Nazwisko matki");
        writer.write("Parafia");
        writer.write("Miejscowość");
        writer.write("Uwagi");
        writer.endRecord();

        Thread.sleep(500);  //stopowanie bo za szybko czasem cos dziala

        do {

            ElementsCollection ileRekordowStrona = $$(By.xpath("//div[@id='table_b_wrapper']//table[@id='table_b']//tbody//tr"));  //spr ile rekordow na stronie

            for (int i = 1; i < ileRekordowStrona.size() + 1; i++) {

                String rekord = "//div[@id='table_b_wrapper']//table[@id='table_b']//tbody//tr[" + i + "]/td";  //stworzenie xpath dla calego rekordu(wiersza)
                // ElementsCollection calyRekord = $$(By.xpath(rekord));

                for (int j = 1; j < 5; j++) {   //od roku do nazwiska, 4 kolumny

                    String wartosc = "//div[@id='table_b_wrapper']//table[@id='table_b']//tbody//tr[" + i + "]/td[" + j + "]";  //stworzenie xpath dla j wartosci z jednego wiersza
                    SelenideElement wartoscRekordu = $(By.xpath(wartosc));
                    writer.write(wartoscRekordu.getText());

                } // petla dla 4 wartosci kazdego rekordu

                //-----zaczytanie przezwisk przydomkow

                String vel = "//div[@id='table_b_wrapper']//table[@id='table_b']//tbody//tr[" + i + "]//img[contains(@title,'Inne nazwiska')]";
                SelenideElement przezwiska = $(By.xpath(vel));

                if (przezwiska.isDisplayed()) {
                    String str = przezwiska.getAttribute("title");
                    writer.write(str.substring(15, str.length()));
                } //----
                // jeżeli nie ma to niech wstawia puste
                else writer.write(" ");


                for (int j = 5; j < 10; j++) {   //pobranie danych od imie ojca do miejscowosc wlacznie

                    String wartosc = "//div[@id='table_b_wrapper']//table[@id='table_b']//tbody//tr[" + i + "]/td[" + j + "]";  //stworzenie xpath dla j wartosci z jednego wiersza
                    SelenideElement wartoscRekordu = $(By.xpath(wartosc));
                    writer.write(wartoscRekordu.getText());


                } // koniec pobrania danych do miejscowosci, ponizej zaczynaja sie uwagi

                String uwagi = "//div[@id='table_b_wrapper']//table[@id='table_b']//tbody//tr[" + i + "]//img[contains(@title,'Uwagi') or contains(@title,'Data')]";
                SelenideElement uwagiStrona = $(By.xpath(uwagi));

                if (uwagiStrona.isDisplayed()) {
                    String uw = uwagiStrona.getAttribute("title").replace("Uwagi: ", " ").replace("Data urodzenia: ", ", ");
                    writer.write(uw);

                } //----
                // jeżeli nie ma to niech omija

                writer.endRecord();

            } // petla dla wszystkich rekordow na stronie

            zmianaWWW.click();
            //przystopowanie automatu
        } while (next.isDisplayed());  //koniec przechodzenia miedzy stronami

        writer.close();
        return this;
    }  //end of procedure URODZENIA


    //---------------------MALZENSTWA
    public GenetekaPage malzenstwa(String path) throws InterruptedException, IOException {

        String sciezka = "D:\\" + path + ".csv";

        CsvWriter writer = new CsvWriter(sciezka, ';', Charset.forName("Cp1250"));

        // CSVWriter writer = new CSVWriter(new FileWriter(sciezka, Charset.forName("Cp1250")));

        writer.write("Rok");
        writer.write("Akt");
        writer.write("Imię");
        writer.write("Nazwisko");
        writer.write("Przydomek");
        writer.write("Rodzice");
        writer.write("Imię");
        writer.write("Nazwisko");
        writer.write("Przydomek");
        writer.write("Rodzice");
        writer.write("Parafia");
        writer.write("Uwagi");
        writer.endRecord();

        malTab.click();
        malWczytywanie.waitUntil(Condition.disappear, 15000, 3);


        do {
            Thread.sleep(500);  //gdyby dzialo sie cos dziwnego usunac to
            ElementsCollection ileRekordowStrona = $$(By.xpath("//div[@id='table_s_wrapper']//table[@id='table_s']//tbody//tr"));


            for (int i = 1; i < ileRekordowStrona.size() + 1; i++) {

                String rekord = "//div[@id='table_s_wrapper']//table[@id='table_s']//tbody//tr[" + i + "]/td";  //stworzenie xpath dla calego rekordu(wiersza)

                // ElementsCollection calyRekord = $$(By.xpath(rekord));

                for (int j = 1; j < 5; j++) {   //bez +1 bo robimy do 9 wartosci, uwagi po tej petli zaczytywane osobno

                    String wartosc = "//div[@id='table_s_wrapper']//table[@id='table_s']//tbody//tr[" + i + "]/td[" + j + "]";  //stworzenie xpath dla j wartosci z jednego wiersza

                    SelenideElement wartoscRekordu = $(By.xpath(wartosc));
                    writer.write(wartoscRekordu.getText());


                } // petla dla 4 wartosci kazdego rekordu

                //-----przezwiska mlodego

                String vel = "//div[@id='table_s_wrapper']//table[@id='table_s']//tbody/tr[" + i + "]/td[4]/img";
                SelenideElement przezwiska = $(By.xpath(vel));

                if (przezwiska.isDisplayed()) {
                    String str = przezwiska.getAttribute("title");
                    writer.write(str.substring(15, str.length()));

                } //----
                // jeżeli nie ma to niech wstawia puste
                else writer.write(" ");


                for (int j = 5; j < 8; j++) {   //od rodzice do nazwiska panny

                    String wartosc = "//div[@id='table_s_wrapper']//table[@id='table_s']//tbody//tr[" + i + "]/td[" + j + "]";  //stworzenie xpath dla j wartosci z jednego wiersza

                    SelenideElement wartoscRekordu = $(By.xpath(wartosc));
                    writer.write(wartoscRekordu.getText());

                } // petla dla 4 wartosci kazdego rekordu


                //-----przezwiska mlodek

                String vel2 = "//div[@id='table_s_wrapper']//table[@id='table_s']//tbody/tr[" + i + "]/td[7]/img";
                SelenideElement przezwiska2 = $(By.xpath(vel2));

                if (przezwiska2.isDisplayed()) {
                    String str = przezwiska2.getAttribute("title");
                    writer.write(str.substring(15, str.length()));

                } //----
                // jeżeli nie ma to niech wstawia puste
                else writer.write(" ");


                for (int j = 9; j < 10; j++) {   //od rodzice panny do uwag

                    String wartosc = "//div[@id='table_s_wrapper']//table[@id='table_s']//tbody//tr[" + i + "]/td[" + j + "]";  //stworzenie xpath dla j wartosci z jednego wiersza

                    SelenideElement wartoscRekordu = $(By.xpath(wartosc));
                    writer.write(wartoscRekordu.getText());

                } // petla dla 2 wartosci kazdego rekordu


                String uwagi = "//div[@id='table_s_wrapper']//table[@id='table_s']//tbody//tr[" + i + "]//img[contains(@title,'Uwagi') or contains(@title,'Data ślubu') or contains(@title,'Miejscowość')]";
                SelenideElement uwagiStrona = $(By.xpath(uwagi));

                if (uwagiStrona.isDisplayed()) {
                    String uwagens = uwagiStrona.getAttribute("title").replace("Uwagi: ", " ").replace("Data ślubu: ", " ").replace("Miejscowość: ", " ");
                    writer.write(uwagens);

                } //----jeżeli nie ma to niech omija

                writer.endRecord();

            } // petla dla wszystkich rekordow na stronie

            zmianaWWWmal.click();
        } while (next_mal.isDisplayed());  //koniec przechodzenia miedzy stronami

        writer.close();

        return this;
    }  //koniec malzenstw


    //-------------------------------ZGONY
    public GenetekaPage zgony(String path) throws InterruptedException, IOException {

        String sciezka = "D:\\" + path + ".csv";
        // FileWriter writer = new FileWriter(sciezka);
        CsvWriter writer = new CsvWriter(sciezka, ';', Charset.forName("Cp1250"));
        writer.write("Rok");
        writer.write("Akt");
        writer.write("Imię");
        writer.write("Nazwisko");
        writer.write("Przydomek");
        writer.write("Ojciec");
        writer.write("Matka");
        writer.write("Nazwisko matki");
        writer.write("Parafia");
        writer.write("Miejscowość");
        writer.write("Uwagi");
        writer.endRecord();

        zgonyTab.click();
        zgonyWczytywanie.waitUntil(Condition.disappear, 15000, 3);

        do {
            Thread.sleep(500);  //gdyby dzialo sie cos dziwnego usunac to
            ElementsCollection ileRekordowStrona = $$(By.xpath("//div[@id='table_d_wrapper']//table[@id='table_d']//tbody//tr"));

            for (int i = 1; i < ileRekordowStrona.size() + 1; i++) {

                String rekord = "//div[@id='table_d_wrapper']//table[@id='table_d']//tbody//tr[" + i + "]/td";  //stworzenie xpath dla calego rekordu(wiersza)
                // ElementsCollection calyRekord = $$(By.xpath(rekord));

                for (int j = 1; j < 5; j++) {   //bez +1 bo robimy do 9 wartosci, uwagi po tej petli zaczytywane osobno

                    String wartosc = "//div[@id='table_d_wrapper']//table[@id='table_d']//tbody//tr[" + i + "]/td[" + j + "]";  //stworzenie xpath dla j wartosci z jednego wiersza
                    SelenideElement wartoscRekordu = $(By.xpath(wartosc));
                    writer.write(wartoscRekordu.getText());


                } // petla dla 4 wartosci kazdego rekordu

                //-----miejsce na uwagi i przezwiska

                String vel = "//div[@id='table_d_wrapper']//table[@id='table_d']//tbody//tr[" + i + "]//img[contains(@title,'Inne nazwiska')]";
                SelenideElement przezwiska = $(By.xpath(vel));

                if (przezwiska.isDisplayed()) {
                    String str = przezwiska.getAttribute("title");
                    writer.write(str.substring(15, str.length()));

                } //----
                // jeżeli nie ma to niech wstawia puste
                else writer.write(" ");


                for (int j = 5; j < 10; j++) {   //od ojca do uwag

                    String wartosc = "//div[@id='table_d_wrapper']//table[@id='table_d']//tbody//tr[" + i + "]/td[" + j + "]";  //stworzenie xpath dla j wartosci z jednego wiersza
                    SelenideElement wartoscRekordu = $(By.xpath(wartosc));
                    writer.write(wartoscRekordu.getText());

                } // petla dla 4 wartosci kazdego rekordu

                String uwagi = "//div[@id='table_d_wrapper']//table[@id='table_d']//tbody//tr[" + i + "]//img[contains(@title,'Uwagi') or contains(@title,'Data')]";
                SelenideElement uwagiStrona = $(By.xpath(uwagi));

                if (uwagiStrona.isDisplayed()) {
                    String uw = uwagiStrona.getAttribute("title").replace("Data zgonu:", ", ").replace("Uwagi:", " ");
                    // writer.write(uw.substring(7, uw.length()));
                    writer.write(uw);
                    //writer.write(uw);
                } //----
                // jeżeli nie ma to niech omija

                writer.endRecord();

            } // petla dla wszystkich rekordow na stronie

            zmianaWWWzgony.click();
        } while (next_zgony.isDisplayed());  //koniec przechodzenia miedzy stronami

        writer.close();

        return this;

    } //koniec zgonow


    public GenetekaPage tescik() throws IOException {
        clearBrowserCache();
        open("http://geneteka.genealodzy.pl/index.php?op=gt&lang=pol&bdm=S&w=05ld&rid=4469&search_lastname=&search_name=&search_lastname2=&search_name2=&from_date=1808&to_date=1811&ordertable=[[0,%22asc%22],[1,%22asc%22],[2,%22asc%22]]&searchtable=&rpp1=100&rpp2=50");

        SelenideElement tt = $(By.xpath("//div[@id='table_s_wrapper']//table[@id='table_s']//tbody//tr[2]//img[contains(@title,'Uwagi') or contains(@title,'Data')]"));
        String johny = tt.getAttribute("title").replace("Miejscowość", ", Miejscowość").replace("Data ślubu", ", Data ślubu");
        //  tt.getText();
        // tt.getValue();
        String sciezka = "D:\\jumbo.csv";


        //CsvWriter testcases = new CsvWriter(new FileWriter(sciezka,true),';', Charset.forName("Cp1250"));

        CsvWriter testcases = new CsvWriter(sciezka, ';', Charset.forName("Cp1250"));


        testcases.write(johny);
        testcases.write("fff");
        testcases.write("\n");
        testcases.write("fff");
        testcases.endRecord();
        testcases.write("2fff");
        testcases.close();

        return this;
    }


} //end of class




