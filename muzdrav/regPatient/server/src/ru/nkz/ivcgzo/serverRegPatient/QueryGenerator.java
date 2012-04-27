/**
 */
package ru.nkz.ivcgzo.serverRegPatient;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;


/**
 * Класс генерации текста sql запроса.
 * Открытые методы класса генерируют текст sql запроса
 * и массив порядковых номеров для всех заданных полей объекта.
 * @author Avdeev Alexander
 */
public final class QueryGenerator<T> {
    private final Field[] fields;
    private final String[] fieldNames;

    /**
     * Конструктор класса.
     * @param cls - класс объекта, для которого производится генерация
     * @param fieldList - массив названий полей в БД
     */
    public QueryGenerator(final Class<T> cls, final String... fieldList) {
        fields = cls.getFields();
        fieldNames = fieldList;
    }

    /**
     * Формирует на основе thrift-объекта Map, содержащий имена заполненных
     * полей и их порядковые номера в запросе.
     * @param tObject - thrift-объект, для которого надо составить запрос.
     * @return Map, где:
     * <ul>
     *     <li>key - имя поля в БД,</l1>
     *     <li>value - порядковый номер поля (счет от 0)</l1>
     * </ul>
     */
    private <TriftObject extends TBase<?, ThriftField>, ThriftField extends TFieldIdEnum>
    Map<String, Integer> fieldIsSetCheck(final TriftObject tObject) {
        int thriftFieldId = 0;
        Map<String, Integer> definedFields = new HashMap<String, Integer>();
        for (Field fld:fields) {
            thriftFieldId++;
            if (fld.getName() != "metaDataMap") {
                ThriftField thriftField = tObject.fieldForId(thriftFieldId);
                if (tObject.isSet(thriftField)) {
                    definedFields.put(fieldNames[thriftFieldId - 1],
                            thriftFieldId - 1);
                }
            }
        }
        return definedFields;
    }

    /**
     * Метод, генерирующий SELECT запрос на основе переданного ему
     * thrift-объекта. Все заданные поля заносятся в часть запроса после WHERE.
     * Если ни одно поле не задано - вернет исходный SELECT запрос.
     *
     * @param tObject - thrift-объект, для которого надо составить запрос.
     * @param selectQueryTemplate - шаблон запроса (SELECT запрос без WHERE части)
     * @return строка SELECT запроса, c WHERE частью, включающей все заполненные поля
     * исходного thrift-объекта.
     */
    public <TriftObject extends TBase<?, ThriftField>, ThriftField extends TFieldIdEnum>
    InputData genSelect(final TriftObject tObject, final String selectQueryTemplate) {
        StringBuilder resultQuery =
                new StringBuilder(selectQueryTemplate + " " + "WHERE");
        Map<String, Integer> definedFields = fieldIsSetCheck(tObject);
        int[] indexes = new int[definedFields.size()];
        int i = 0;
        for (Map.Entry<String, Integer> entry : definedFields.entrySet()) {
            resultQuery.append(String.format(" %s = ? AND", entry.getKey()));
            indexes[i] = entry.getValue();
            i++;
        }
        if (definedFields.isEmpty()) {
            resultQuery.replace(resultQuery.indexOf(" WHERE"), resultQuery.length(), ";");
        } else {
            resultQuery.delete(
                    resultQuery.length() - " AND".length(), resultQuery.length());
            resultQuery.append(";");
        }
        InputData inData = new InputData(resultQuery.toString(), indexes);
        return inData;
    }

    /**
     * Формирует массив порядковых номеров заполненных полей thrift-объекта,
     * необходимый в
     * {@link ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor
     * #execPreparedQueryT(String, T, String[], int[]) execPreparedQueryT(...) }
     * @param tObject - thrift-объект, для которого надо составить запрос.
     * @return целочисленный массив порядковых номеров полей thrift-объекта в
     * select запросе.
     */
    public <TriftObject extends TBase<?, ThriftField>, ThriftField extends TFieldIdEnum>
    int[] genIndexes(final TriftObject tObject) {
        Map<String, Integer> definedFields = fieldIsSetCheck(tObject);
        int[] indexes = new int[definedFields.size()];
        int i = 0;
        for (Map.Entry<String, Integer> entry : definedFields.entrySet()) {
            indexes[i] = entry.getValue();
            i++;
        }
        return indexes;
    }
}
