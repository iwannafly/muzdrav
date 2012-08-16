package ru.nkz.ivcgzo.serverRegPatient;

import static org.junit.Assert.assertEquals;

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
        System.out.println(htmTemplate.getTemplateText());
    }
}
