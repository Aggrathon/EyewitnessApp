package aggrathon.eyewitnessapp.data;

public class PersonalInformation {

	public String language;
	public int testNumber;
	public boolean previousParticipations;
	public int age;
	public String nationality;
	public String sex;
	public int height;
	public String country;




	public PersonalInformation() {}

	@Override
	public String toString() {
		return "Personal Information: " +
				"\n\tLanguage: "+language +
				"\n\tTestnumber: "+testNumber +
				"\n\tPrevious Participations: "+previousParticipations +
				"\n\tAge: "+age+
				"\n\tNationality: "+nationality+
				"\n\tSex: "+sex+
				"\n\tHeight: "+height+
				"\n\tCountry: "+country+"\n";
	}
}
