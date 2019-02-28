package TCs;

import org.testng.annotations.Test;

import java.io.IOException;

public class Geneteka extends BasicTest {


    //----urodzenia
    @Test(dataProvider = "inputGene")
    public void geneUrodzenia(
            String teren,
            String parafia,
            String dataod,
            String datado,
            String output


    ) throws InterruptedException, IOException {
        gp.indeksy(teren, parafia, dataod, datado)
                .urodzenia(output);

    }


    //------malzenstwa
    @Test(dataProvider = "inputGene")
    public void geneMalzenstwa(
            String teren,
            String parafia,
            String dataod,
            String datado,
            String output


    ) throws InterruptedException, IOException {
        gp.indeksy(teren, parafia, dataod, datado)
                .malzenstwa(output);


    }


    //----zgony


    @Test(dataProvider = "inputGene")
    public void geneZgony(
            String teren,
            String parafia,
            String dataod,
            String datado,
            String output


    ) throws InterruptedException, IOException {
        gp.indeksy(teren, parafia, dataod, datado)
                .zgony(output);


    }


    @Test
    public void tescik() throws IOException {

        gp.tescik();
    }





}  //koniec clasy
