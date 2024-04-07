package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class User {

	private static String type;
	private static String first_name;
	private static String last_name;
	private static String birthdate;
	private static String sex;
	private String UID;
	private String password;
	private String address;
	private String phone;
	private String email;
	
	private ArrayList<Document> healthRecords = new ArrayList<Document>();
	private ArrayList<Visit> visits = new ArrayList<Visit>();
	private ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();
	private ArrayList<Conversation> conversations = new ArrayList<Conversation>();
	private ArrayList<Vitals> vitals = new ArrayList<Vitals>();
	
	
	public User(String fn, String ln, String pw, String bd, String em, String sx) {
		
		type = "Patient";
		
		first_name = fn;
		last_name = ln;
		password = pw;
		birthdate = bd;
		email = em;
		sex = sx;
		
		UID = first_name.charAt(0) + last_name + birthdate.substring(2, 4);
		System.out.println(UID);
		
		try {
			
			File userFile = new File(UID + "_PatientInfo.txt");

			if (userFile.createNewFile()) {
				System.out.println("File created: " + userFile.getName());
			} else {
				System.out.println("File already exists.");
			}
		
		FileWriter fw = new FileWriter(userFile);
		
		fw.write(password + "\n"
				+ "Patient\n"
				+ first_name + "\n"
				+ last_name + "\n"
				+ email + "\n"
				+ birthdate + "\n"
				+ sex);
		fw.close();
		
	    } catch (IOException e) {
	    	
	    	System.out.println("An error occurred.");
	    	e.printStackTrace();
	    }
		
	}
	
	public String getType() {
		return type;
	}
	
	public String getName() {
		return (first_name + " " + last_name);
	}
	
	public String getBirthday() {
		return birthdate;
	}
	
	public String getSex() {
		return sex;
	}
	
	public String getUID() {
		return UID;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String str) {
		address = str;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String str) {
		phone = str;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String str) {
		email = str;
	}
	
	public ArrayList<Document> getDocuments() {
		return healthRecords;
	}
	
	public void addDocument(Document doc) {
		healthRecords.add(doc);
	}
	
	public ArrayList<Visit> getVisits() {
		return visits;
	}
	
	public void addVisit(Visit vis) {
		visits.add(vis);
	}
	
	public ArrayList<Conversation> getConversation() {
		return conversations;
	}
	
	public void newConversation(Conversation con) {
		conversations.add(con);
	}
	
	public ArrayList<Prescription> getPrescriptions() {
		return prescriptions;
	}
	
	public void addPrescription(Prescription pres) {
		prescriptions.add(pres);
	}
	
	public ArrayList<Vitals> getVitals() {
		return vitals;
	}
	
	public void addVitals(Vitals vit) {
		vitals.add(vit);
	}
	
}
