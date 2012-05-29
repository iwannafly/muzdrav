package ru.nkz.ivcgzo.serverRegPatient;
/**
 * Класс для передачи хранения сгенерированных запросов и массивов индексов
 */
public class InputData {
    private String queryText;
    private int[] indexes;

    public InputData(final String inputText, final int[] inputIndexes) {
        this.queryText = inputText;
        this.indexes = inputIndexes;
    }

    public final String getQueryText() {
        return queryText;
    }

    public final void setQueryText(final String inputText) {
        this.queryText = inputText;
    }

    public final int[] getIndexes() {
        return indexes;
    }

    public final void setIndexes(final int[] inputIndexes) {
        this.indexes = inputIndexes;
    }
}
