package utilitarios;

public class Validacoes {

	public static boolean hasOnlyNumber(Object object) {

		for (int i = 0; i < object.toString().length(); i++) {
			if (object.toString().charAt(i) == 0 || object.toString().charAt(i) == 0 || object.toString().charAt(i) == 1
					|| object.toString().charAt(i) == 2 || object.toString().charAt(i) == 3
					|| object.toString().charAt(i) == 4 || object.toString().charAt(i) == 5
					|| object.toString().charAt(i) == 6 || object.toString().charAt(i) == 7
					|| object.toString().charAt(i) == 8 || object.toString().charAt(i) == 9) {
				if (i == object.toString().length()) {
					return true;
				} else {
					continue;
				}
			} else {
				return false;
			}
		}

		return false;
	}

	public static boolean hasNotOnlyNumber(Object object) {

		for (int i = 0; i < object.toString().length(); i++) {
			if (object.toString().charAt(i) != 0 || object.toString().charAt(i) != 0 || object.toString().charAt(i) != 1
					|| object.toString().charAt(i) != 2 || object.toString().charAt(i) != 3
					|| object.toString().charAt(i) != 4 || object.toString().charAt(i) != 5
					|| object.toString().charAt(i) != 6 || object.toString().charAt(i) != 7
					|| object.toString().charAt(i) != 8 || object.toString().charAt(i) != 9) {
				if (i == object.toString().length()) {
					return true;
				} else {
					continue;
				}
			} else {
				return false;
			}

		}

		return false;
	}

	public static boolean hasNotOnlyNumberAndEspecialCharacters(Object object) {

		if (isNullOrEmpty(object)) {
			return false;
		}

		String objectString = object.toString().toLowerCase();

		for (int i = 0; i < objectString.length(); i++) {

			if (i == 0 && objectString.charAt(0) == ' ') {
				return false;
			}

			if (objectString.charAt(i) == 'q' || objectString.charAt(i) == 'w' || objectString.charAt(i) == 'e'
					|| objectString.charAt(i) == 'r' || objectString.charAt(i) == 't' || objectString.charAt(i) == 'y'
					|| objectString.charAt(i) == 'u' || objectString.charAt(i) == 'i' || objectString.charAt(i) == 'o'
					|| objectString.charAt(i) == 'p' || objectString.charAt(i) == 'a' || objectString.charAt(i) == 's'
					|| objectString.charAt(i) == 'd' || objectString.charAt(i) == 'f' || objectString.charAt(i) == 'g'
					|| objectString.charAt(i) == 'h' || objectString.charAt(i) == 'j' || objectString.charAt(i) == 'k'
					|| objectString.charAt(i) == 'l' || objectString.charAt(i) == 'ç' || objectString.charAt(i) == 'z'
					|| objectString.charAt(i) == 'x' || objectString.charAt(i) == 'c' || objectString.charAt(i) == 'v'
					|| objectString.charAt(i) == 'b' || objectString.charAt(i) == 'n' || objectString.charAt(i) == 'm'
					|| objectString.charAt(i) == ' ' || objectString.charAt(i) == 'á' || objectString.charAt(i) == 'é'
					|| objectString.charAt(i) == 'í' || objectString.charAt(i) == 'ó' || objectString.charAt(i) == 'ú'
					|| objectString.charAt(i) == 'ã' || objectString.charAt(i) == 'õ' || objectString.charAt(i) == 'ô'
					|| objectString.charAt(i) == 'á' || objectString.charAt(i) == 'á'
					|| objectString.charAt(i) == 'á') {
				if (i == objectString.length() - 1) {
					return true;
				} else {
					continue;
				}
			} else {
				return false;
			}

		}
		return false;

	}

	public static boolean isNullOrEmpty(Object object) {

		if (object == null) {
			return true;
		}

		String objectString = object.toString();

		if (objectString.isEmpty()) {
			return true;
		}

		return false;
	}

	public static String removeWhiteSpace(Object object) {
		if (isNullOrEmpty(object)) {
			return null;
		}

		String objectString = object.toString();
		objectString.toLowerCase();

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < objectString.length(); i++) {
			if (objectString.charAt(i) == ' ') {
				continue;
			} else {
				sb.append(objectString.charAt(i));
			}
		}

		return sb.toString();

	}

	public static String removeExceededWhiteSpace(Object object) {
		if (isNullOrEmpty(object)) {
			return null;
		}

		String objectString = object.toString();
		objectString.toLowerCase();

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < objectString.length(); i++) {

			if (!isNullOrEmpty(objectString.charAt(i))) {
				if (i != 0) {

					if (objectString.charAt(i) == ' ' && objectString.charAt(i - 1) == ' ') {
						continue;
					} else {
						sb.append(objectString.charAt(i));
					}

				} else {

					sb.append(objectString.charAt(i));
				}
			}

		}

		for (int i = 0; i < sb.length(); i++) {
			if (i == sb.length() - 1 && sb.charAt(i) == ' ') {
				sb.deleteCharAt(i);
			}
		}

		return sb.toString();
	}

}
