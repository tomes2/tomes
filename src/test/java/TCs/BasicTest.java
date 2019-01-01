package TCs;

import Pages.*;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class BasicTest {





    @BeforeMethod
    public void before(){
        //System.setProperty("webdriver.chrome.driver",this.getClass().getClassLoader().getResource("chromedriver.exe"));


        System.setProperty("webdriver.chrome.driver", "src\\test\\Resources\\chromedriver.exe");

        //System.setProperty("webdriver.chrome.driver","C:\\Selenide\\chromedriver.exe");
        System.setProperty("selenide.browser","Chrome");


    }


    MainPage mp = new MainPage();
    RegPage rg = new RegPage();
    OpenNewAccountPage ona = new OpenNewAccountPage();
    AccountsOverviewPage aop = new AccountsOverviewPage();
    AccountDetailsPage adp = new AccountDetailsPage();










    public String[][] getExcelData(String xlsName, String sheetName) {

        String[][] arrayExcelData = null;
        try {
            FileInputStream fs = new FileInputStream(xlsName);
            WorkbookSettings ws = new WorkbookSettings();
            ws.setEncoding("Cp1252");
            Workbook wb = Workbook.getWorkbook(fs, ws);
            Sheet sh = wb.getSheet(sheetName);

            int totalNoOfCols = sh.getColumns();
            int totalNoOfRows = sh.getRows();

            arrayExcelData = new String[totalNoOfRows - 1][totalNoOfCols];

            for (int i = 1; i < totalNoOfRows; i++) {

                for (int j = 0; j < totalNoOfCols; j++) {
                    arrayExcelData[i - 1][j] = sh.getCell(j, i).getContents();
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return arrayExcelData;


    }





    @DataProvider
    public Object[][] inputData() {
        Object[][] arrayObject = getExcelData(this.getClass().getClassLoader().getResource("Input.xls").getPath(), "Test");
        return arrayObject;
    }


    @DataProvider
    public Object[][] inputDataLogin() {
        Object[][] arrayObject = getExcelData(this.getClass().getClassLoader().getResource("Input.xls").getPath(), "Login");
        return arrayObject;
    }





}
