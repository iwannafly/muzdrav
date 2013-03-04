package ru.nkz.ivcgzo.serverPrint;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * Класс для работы с htm-шаблонами для формирования сводок.
 * Шаблон представляет собой htm файл, где на месте подстановок стоят метки
 * вида "~labelName".
 * @author Avdeev Alexander
 */
public class HtmTemplate {
    /**
     * Текст шаблона.
     */
    private String template = null;
    /**
     * Список всех меток, найденных в шаблоне.
     */
    private List<String> labels;

    /**
     *
     * @param templatePath - путь к шаблону
     */
    public HtmTemplate(final String templatePath) {
        template = readTemplateFromFile(templatePath);
        labels = findAllLabelsInTemplate();
    }

    /**
     * @return список всех меток
     */
    public final List<String> getLabels() {
        return labels;
    }

    /**
     * @return полный текст шаблона
     */
    public final String getTemplateText() {
        return template;
    }

    /**
     * @return количество меток в шаблоне
     */
    public final int getLabelsCount() {
        return labels.size();
    }

    /**
     * Читает htm-файл с определенным именем из папки с шаблонами по умолчанию
     * для данного типа ОС.
     * @param templatePath
     * @return полный текст htm-шаблона
     */
    private String readTemplateFromFile(final String templatePath) {
        try {
//            BufferedReader in = new BufferedReader(new FileReader(templatePath));
            BufferedReader in = new BufferedReader(new InputStreamReader(
                new FileInputStream(templatePath), "utf-8"));
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

    /**
     * Вырезает имя метки, начиная со знака "~" и заканчивая символом перед ближайшим
     * к нему знаком:
     * <ul>
     *     <li>пробела,</l1>
     *     <li>перевода каретки,</l1>
     *     <li>открывающего тега.</l1>
     * </ul>
     * Например: из строки "some symbols ~label some another symbols" вырежет "~label".
     * @param tildaPosition - позиция знака "~" (начального знака каждой метки)
     * @return полное имя метки (включая знак ~)
     */
    private String cutLabelName(final int tildaPosition) {
        int firstSpaceAfterTildaPosition = template.indexOf(" ", tildaPosition);
        int firstEnterAfterTildaPosition = template.indexOf("\n", tildaPosition);
        int firstTegAfterTildaPosition = template.indexOf("<", tildaPosition);
        if (firstSpaceAfterTildaPosition < firstEnterAfterTildaPosition) {
            if (firstSpaceAfterTildaPosition < firstTegAfterTildaPosition) {
                return template.substring(tildaPosition, firstSpaceAfterTildaPosition);
            } else {
                return template.substring(tildaPosition, firstTegAfterTildaPosition);
            }
        } else {
            if (firstEnterAfterTildaPosition < firstTegAfterTildaPosition) {
                return template.substring(tildaPosition, firstEnterAfterTildaPosition);
            } else {
                return template.substring(tildaPosition, firstTegAfterTildaPosition);
            }
        }
    }

    /**
     * Формирует список всех меток, присутствующих в шаблоне.
     * @return список всех меток в шаблоне
     */
    private List<String> findAllLabelsInTemplate() {
        List<String> tmpLabels = new ArrayList<String>();
        int indexOfCurrentLabel = 0;
        int lastIndexOfLabel = template.lastIndexOf("~");
        while (indexOfCurrentLabel < lastIndexOfLabel) {
            indexOfCurrentLabel = template.indexOf("~", indexOfCurrentLabel);
            tmpLabels.add(cutLabelName(indexOfCurrentLabel));
            indexOfCurrentLabel++;
        }
        return tmpLabels;
    }

    /**
     * Заменяет все метки в шаблоне на переданные в метод значения, по возрастанию индекса метки,
     * начиная с 0-го. Если значений передано меньше, чем меток в шаблоне, заменяет все метки, для
     * которых есть соответствующие им значения, и, в зависимости от значения
     * resetUnusedLabel, либо удаляет лишние метки, либо оставляет их без изменения.
     * Если передано большее кол-во значений, чем кол-во меток в шаблоне, генерирует
     * исключение, ничего при этом не заменяя.
     * @param resetUnusedLabels - флаг необходимости удаления меток, для которых не нашлось
     * соответствующего значения (при кол-ве значений, меньшем, чем кол-во меток):
     * <ul>
     *     <li><b>true</b> - удаляет лишние метки,</l1>
     *     <li><b>false</b> - оставляет лишние метки без изменения.</l1>
     * </ul>
     * @param values - значения, заменяющие метки в шаблоне
     * @throws Exception - исключение, генерируемое в случае,если кол-во переданных значений больше
     * количества меток в шаблоне
     */
    public final void replaceLabels(final boolean resetUnusedLabels, final String... values)
            throws Exception {
        if (values.length <= labels.size()) {
            for (int i = 0; i < values.length; i++) {
                replaceLabel(i, values[i]);
            }
            if ((resetUnusedLabels) && (values.length < labels.size())) {
                for (int i = values.length; i < labels.size(); i++) {
                    replaceLabel(i, "");
                }
            }
        } else {
            throw new Exception("Кол-во значений больше количества меток");
        }
    }

    /**
     * Заменяет все метки в шаблоне на переданные в метод значения, по возрастанию индекса метки,
     * начиная с <b>startlIndex</b> и заканчивая <b>endIndex</b> включительно. Метки до
     * <b>labelIndex</b> и после <b>endIndex</b> остаются без изменений.
     * Для нормальной работы метода необходимы следующие условия:
     * <ul>
     *     <li><b>startIndex</b> больше или равен 0</l1>
     *     <li><b>startIndex</b> меньше <b>endIndex</b></li>
     *     <li><b>endIndex</b> меньше количества меток в шаблоне</li>
     *     <li>количество переданных элементов равно количеству индексов от
     *     <b>startIndex</b> до <b>endIndex</b> включительно</li>
     * </ul>
     * В случае невыполнения любого из этих условий метод генерирует исключение,
     * ничего при этом не заменяя.
     * @param startIndex - индекс первой метки для замены
     * @param endIndex - индекс последней метки для замены
     * @param values - значения, заменяющие метки в шаблоне
     * @throws Exception
     */
    public final void replaceLabels(final int startIndex, final int endIndex,
            final String... values) throws Exception {
        if ((labels.size() > endIndex)
                && (startIndex >= 0)
                && (startIndex < endIndex)
                && (values.length == (endIndex - startIndex) + 1)) {
            int i = startIndex;
            for (String value:values) {
                replaceLabel(i, value);
                i++;
            }
        } else {
            throw new Exception("Кол-во значений не равно количеству меток");
        }
    }

    /**
     * Заменяет метку с индексом <b>labelIndex</b> на значение <b>value</b>. В случае
     * отсутствия метки генерируется исключение.
     * @param labelIndex - индекс метки
     * @param value - значение, заменяющее метку в шаблоне
     * @throws Exception - исключение, генерируемое в случае отсутствия метки с заданным индексом
     */
    public final void replaceLabel(final int labelIndex, final String value) throws Exception {
        if (labelIndex <= labels.size()) {
            if ((value != null) && (!value.equals("null"))) {
                template = template.replaceFirst(labels.get(labelIndex), value);
            } else {
                template = template.replaceFirst(labels.get(labelIndex), "");
            }
        } else {
            throw new Exception("В документе нет метки с таким индексом");
        }
    }

    /**
     * Заменяет метку с именем (текстом) <b>lableName</b> на значение <b>value</b>. В случае
     * отсутствия метки генерируется исключение.
     * @param labelName - имя метки (текст метки)
     * @param value - значение, заменяющее метку в шаблоне
     * @throws Exception - исключение, генерируемое в случае отсутствия метки с заданным именем
     */
    public final void replaceLabel(final String labelName, final String value) throws Exception {
        if (labels.contains(labelName)) {
            if ((value != null) && (!value.equals("null"))) {
                template = template.replaceFirst(labelName, value);
            } else {
                template = template.replaceFirst(labelName, "");
            }
        } else {
            throw new Exception("В документе нет метки с таким именем");
        }
    }

    /**
     * Заменяет текст <b>textForReplace</b> значением <b>value</b>. Работает для любого текста в
     * шаблоне.
     * @param textForReplace - текст, заменяемый в шаблоне
     * @param value - текст, заменяющий метку в шаблоне
     */
    public final void replaceText(final String textForReplace, final String value) {
        template = template.replaceFirst(textForReplace, value);
    }

    /**
     * Удаляет все метки в шаблоне
     */
    public final void resetAllLabels() {
        try {
            for (String labelName:labels) {
                replaceLabel(labelName, "");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
