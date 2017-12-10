package aggrathon.eyewitnessapp.light.data;

public class PersonalInformation {

	public String language;
	public int testId;
	public boolean previousParticipations;
	public int age;
	public String nationality;
	public String sex;
	public int height;


	public PersonalInformation(int id, String lang) {
		testId = id;
		language = lang;
		nationality = "";
		sex = "";
		age = 20;
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
				"\n\tHeight: "+height+"\n";
	}
}
