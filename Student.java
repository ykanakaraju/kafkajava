package com.tekcrux.kafka;

public class Student {
	private int studentId;
	private String studentName;
	//private String studentEmail; 
	
	public Student(int id, String name) {
		this.studentId = id;
		this.studentName = name;
		//this.studentEmail = email;
	}
	public int getStudentId() { 
		return studentId; 
	}
	public String getStudentName() { 
		return studentName; 
	}
	/*public String getStudentEmail() { 
		return studentEmail; 
	}*/
}
