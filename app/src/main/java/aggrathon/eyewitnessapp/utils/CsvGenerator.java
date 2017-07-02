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

	private void nextValue(String header) {
		if (!staticHeader) {
			if (this.header.length() > 0)
				this.header.append(",");
			this.header.append('"').append(header).append('"');
		}
		if (values.length() > 0 && values.charAt(values.length()-1) != '\n')
			values.append(",");
	}

	public void addString(String name, String value) {
		nextValue(name);
		if(value != null && value.length() > 0)
			values.append('"').append(value.replace(',', '.')).append('"');
	}

	public void addInt(String name, int value) {
		nextValue(name);
		values.append(value);
	}

	public void addFloat(String name, float value) {
		nextValue(name);
		values.append(String.format("%.3f", value).replace(',','.'));
	}

	public void addBooleanAsInt(String name, boolean value) {
		nextValue(name);
		values.append(value? 1 : 0);
	}


	public void beginRow() {
		if (header.length() > 0) {
			staticHeader = true;
			values.append("\r\n");
		}
	}


	public boolean hasContent() { return values.length() > 0; }

	public String getValues() {
		return values.toString();
	}

	public String getHeader() {
		return header.toString();
	}
}
