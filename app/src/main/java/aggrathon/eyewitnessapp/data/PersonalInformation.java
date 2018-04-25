package aggrathon.eyewitnessapp.data;

public class PersonalInformation {

	public String language;
	public int testId;
	public String personalId;
	public boolean previousParticipations;
	public int age;
	public String nationality;
	public String sex;
	public int height;
	public float visualAcuityLeft;
	public float visualAcuityRight;


	public PersonalInformation(int id, String lang) {
		testId = id;
		language = lang;
		nationality = "";
		sex = "";
		age = 20;
		personalId = "";
	}

	@Override
	public String toString() {
		return "Personal Information: " +
				"\n\tLanguage: "+language +
				"\n\tTest ID: "+ testId +
				"\n\tPersonal ID: "+ personalId +
				"\n\tPrevious Participations: "+previousParticipations +
				"\n\tAge: "+age+
				"\n\tNationality: "+nationality+
				"\n\tSex: "+sex+
				"\n\tHeight: "+height+
				"\n\tLeft Eye: "+visualAcuityLeft+
				"\n\tRight Eye: "+visualAcuityRight+"\n";
	}
}
