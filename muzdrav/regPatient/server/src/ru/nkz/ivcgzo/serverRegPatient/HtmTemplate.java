package ru.nkz.ivcgzo.serverRegPatient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class HtmTemplate {
    private static final String WINDOWS_TEMPLATE_FOLDER = "C:\\muzdrav_repots";
    private static final String UNIX_TEMPLATE_FOLDER = "/home/as/Work/muzdrav_reports";
    private String template = null;    
    private List<String> labels;

    public HtmTemplate(final String templateFileName) {
        template = readTemplateFromFile(templateFileName);
    }

    public List<String> getLabels() {
        return labels;
    }

    public final String getTemplateText() {
        return template;
    }

    private String readTemplateFromFile(final String templateFileName) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(templateFileName));
            StringBuilder strBuild = new StringBuilder();
            String tmpStr;
            while ((tmpStr = in.readLine()) != null) {
                // добавляем /n вручную, т.к. readLine() пропускает символ перевода каретки
                strBuild.append(tmpStr).append("\n");
            }
            in.close();
            return strBuild.toString();
        } catch (IOException e) {
            return "";
        }
    }
    private String cutLabelName(int tildaPosition) {
        return template;
        
    }
    private List<String> findAllLabelsInTemplate() {
        
        return labels;        
    }
    
    

}
