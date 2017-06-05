package aggrathon.eyewitnessapp.data;

import android.text.TextUtils;

public class PersonalInformation {

	public String language;
	public String testId;
	public boolean previousParticipations;
	public int age;
	public String nationality;
	public String sex;
	public int height;
	public String country;
	public float visualAcuityLeft;
	public float visualAcuityRight;


	public PersonalInformation(String id, String lang) {
		testId = id;
		language = lang;
	}

	@Override
	public String toString() {
		return "Personal Information: " +
				"\n\tLanguage: "+language +
				"\n\tTest ID: "+ testId +
				"\n\tPrevious Participations: "+previousParticipations +
				"\n\tAge: "+age+
				"\n\tNationality: "+nationality+
				"\n\tSex: "+sex+
				"\n\tHeight: "+height+
				"\n\tCountry: "+country+
				"\n\tLeft Eye: "+visualAcuityLeft+
				"\n\tRight Eye: "+visualAcuityRight+"\n";
	}

	public static String getCsvHeaders() {
		return TextUtils.join("\",\"",new String[] {
				"\"Test ID", "Language", "Nationality", "Home Country", "Age",
				"Height", "Sex", "Previous Participant", "Left Eye", "Right Eye", "Average Eye", "Min Eye", "Max Eye\""
		});
	}

	public String getCsvData() {
		return
			"\""+ testId +"\","+
			"\""+language +"\","+
			"\""+nationality.replace("\"","") +"\","+
			"\""+country.replace("\"","") + "\"," +
			age + ","+
			height +","+
			"\""+sex +"," +
			"\""+previousParticipations +"\""+
			visualAcuityLeft+","+
			visualAcuityRight+","+
			((visualAcuityLeft+visualAcuityRight)/2)+","+
			Math.min(visualAcuityLeft, visualAcuityRight)+","+
			Math.max(visualAcuityLeft, visualAcuityRight);
	}
}
