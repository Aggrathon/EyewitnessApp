package aggrathon.eyewitnessapp.utils;


public class CsvGenerator {

	StringBuilder header;
	StringBuilder values;

	boolean staticHeader;

	public CsvGenerator() {
		header = new StringBuilder();
		values = new StringBuilder();
		staticHeader = false;
	}

	private void appendHeader(String name) {
		if (!staticHeader) {
			if (header.length() > 0)
				header.append(",");
			header.append('"').append(name).append('"');
		}
	}

	private void appendValue(String value) {
		if (values.length() > 0 && values.charAt(values.length()-1) != '\n')
			values.append(",");
		values.append('"').append(value).append('"');
	}

	private void appendValue(int value) {
		if (values.length() > 0)
			values.append(",");
		values.append(value);
	}

	public void addString(String name, String value) {
		appendHeader(name);
		appendValue(value);
	}

	public void addInt(String name, int value) {
		appendHeader(name);
		appendValue(value);
	}

	public void addFloat(String name, float value) {
		appendHeader(name);
		appendValue(String.format("%.3f", value));
	}

	public void addBooleanAsInt(String name, boolean value) {
		appendHeader(name);
		appendValue(value? 1 : 0);
	}


	public void beginRow() {
		if (header.length() > 0) {
			staticHeader = true;
			values.append("\r\n");
		}
	}


	public String getValues() {
		return values.toString();
	}

	public String getHeader() {
		return header.toString();
	}
}
