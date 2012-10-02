package ru.nkz.ivcgzo.serverRegPatient;

import org.junit.Before;
import org.junit.Test;

public class TestHtmTemplate {
    private HtmTemplate htmTemplate;
    
    @Before
    public final void setUp() throws Exception {
        htmTemplate = new HtmTemplate("/home/as/Work/muzdrav_reports/MedCardAmbPriem.htm");
    }

    @Test
    public final void getTemplateText_isValueCorrect() {
        try {
            htmTemplate.replaceLabels(false,"==1==","==2==","==3==", "", "==5==");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(htmTemplate.getTemplateText());
    }

    @Test
    public final void getLabels_isListSizeCorrect() {
        System.out.println(htmTemplate.getLabels().size());
        for (String curLabel:htmTemplate.getLabels()){
            System.out.println(curLabel);
        }        
    }
    
}
