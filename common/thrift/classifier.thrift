namespace java ru.nkz.ivcgzo.thriftCommon.classifier

/**
 * Класс, состоящий из двух полей: код и название, где код - число.
 */
struct IntegerClassifier {
	 1: i32 pcod;
	 2: string name;
}

/**
 * Класс, состоящий из двух полей: код и название, где код - строка.
 */
struct StringClassifier {
	 1: string pcod;
	 2: string name;
}
