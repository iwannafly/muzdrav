/**
 */
package ru.nkz.ivcgzo.serverRegPatient;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;


/**
 * Класс генерации текста sql запроса.
 * Генерирует текст запроса, на основании заполненных полей в thrift объекте
 * @author Avdeev Alexander
 */
public final class QueryGenerator<T> {
    private final Field[] fields;
    private final String[] fieldNames;
    // private final Map<String, Integer> definedFields;

    public QueryGenerator(final Class<T> cls, final String... fieldList) {
        fields = cls.getFields();
        fieldNames = fieldList;
    }

    private <TriftObject extends TBase<?, ThriftField>, ThriftField extends TFieldIdEnum>
    Map<String, Integer> fieldIsSetCheck(final TriftObject patient) {
        int thriftFieldId = 0;
        Map<String, Integer> definedFields = new HashMap<String, Integer>();
        for (Field fld:fields) {
            thriftFieldId++;
            if (fld.getName() != "metaDataMap") {
                ThriftField thriftField = patient.fieldForId(thriftFieldId);
                if (patient.isSet(thriftField)) {
                    definedFields.put(fieldNames[thriftFieldId - 1],
                            thriftFieldId - 1);
                }
            }
        }
        return definedFields;
    }

    public <TriftObject extends TBase<?, ThriftField>, ThriftField extends TFieldIdEnum>
    String genSelectQuery(final TriftObject patient, final String selectQueryTemplate) {
        String resultQuery = selectQueryTemplate + " " + "WHERE";
        Map<String, Integer> definedFields = fieldIsSetCheck(patient);
        for (Map.Entry<String, Integer> entry : definedFields.entrySet()) {
            resultQuery += String.format(" %s = ? AND", entry.getKey());
        }
        if (definedFields.isEmpty()) {
            resultQuery = resultQuery.replace(" WHERE", ";");
        } else {
            resultQuery = resultQuery.substring(0,
                    resultQuery.length() - "AND".length()) + ";";
        }
        return resultQuery;
    }

    public <TriftObject extends TBase<?, ThriftField>, ThriftField extends TFieldIdEnum>
    int[] genIndexes(final TriftObject patient) {
        Map<String, Integer> definedFields = fieldIsSetCheck(patient);
        int[] indexes = new int[definedFields.size()];
        int i = 0;
        for (Map.Entry<String, Integer> entry : definedFields.entrySet()) {
            indexes[i] = entry.getValue();
            i++;
        }
        return indexes;
    }
}
