package model;

import java.util.ArrayList;
import java.util.List;


public class Student {
	private List<Student> studentList;
	private SNP snp;
	private String group;
	private int skippingCauseOfSick;
	private int skippingCauseOfOther;
	private int skippingWithNoReason;

	public int getSkipByName(String s){
		switch (s){
			case "sick":
				return skippingCauseOfSick;
			case "other":
				return skippingCauseOfOther;
			case "no reason":
				return skippingWithNoReason;
			case "all":
				return skippingWithNoReason+skippingCauseOfOther+skippingCauseOfSick;
			default:
				return 0;
		}
	}

	public int getSkippingCauseOfSick() {
		return skippingCauseOfSick;
	}

	public void setSkippingCauseOfSick(int skippingCauseOfSick) {
		this.skippingCauseOfSick = skippingCauseOfSick;
	}

	public int getSkippingCauseOfOther() {
		return skippingCauseOfOther;
	}

	public void setSkippingCauseOfOther(int skippingCauseOfOther) {
		this.skippingCauseOfOther = skippingCauseOfOther;
	}

	public int getSkippingWithNoReason() {
		return skippingWithNoReason;
	}

	public void setSkippingWithNoReason(int skippingWithNoReason) {
		this.skippingWithNoReason = skippingWithNoReason;
	}

	public int getSkippingAtAll() {
		return skippingAtAll;
	}

	public void setSkippingAtAll(int skippingAtAll) {
		this.skippingAtAll = skippingAtAll;
	}

	private int skippingAtAll;

	public Student(SNP snp, String group, int skippingCauseOfSick, int skippingCauseOfOther, int skippingWithNoReason){
		this.snp = snp;
		this.group = group;
		this.skippingCauseOfSick = skippingCauseOfSick;
		this.skippingCauseOfOther=skippingCauseOfOther;
		this.skippingWithNoReason=skippingWithNoReason;
		this.skippingAtAll = skippingCauseOfSick + skippingCauseOfOther + skippingWithNoReason;
	}

	public Student(){
		studentList = new ArrayList<>();
	}

	public List<Student> getStudentList(){
		return studentList;
	}

	public void setStudentList(List<Student> StudentList){
		this.studentList = StudentList;
	}

	public void addStudent(Student Student){
		studentList.add(Student);
	}

	public SNP getSnp(){
		return snp;
	}

	public String getSurname(){
		return snp.getSurname();
	}

	public String getName() { return  snp.getName(); }

	public String getPatronym() { return  snp.getPatronym(); }

	public String getStudentName() { return this.snp.getName(); }


//	public String getStudentBirthdayString() {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
//		String formattedString = StudentBirthday.format(formatter);
//		return  formattedString;
//	}
//
//
//
//	public String getStudentLastAppointmentString() {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
//		String formattedString = StudentLastAppointment.format(formatter);
//		return  formattedString;
//	}


	public void setSnp(SNP snp){
		this.snp = snp;
	}

	public String getAlignSnp(){
		return snp.getSurname() + " " + snp.getName() + " " + snp.getPatronym();
	}

	public void setAlignSnp(String alignSnp){
		this.snp = new SNP(alignSnp);
	}

	public String getGroup(){
		return group;
	}

	public void setGroup(String group){
		this.group = group;
	}

}
