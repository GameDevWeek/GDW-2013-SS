package de.fhtrier.gdw.commons.jackson.example;

import java.util.ArrayList;

import de.fhtrier.gdw.commons.jackson.JacksonReader;
import de.fhtrier.gdw.commons.jackson.JacksonWriter;

/**
 * An example use of the Jackson reader/writer
 * 
 * @author Santo Pfingsten
 */
public class JacksonExample {

	public static void main(String[] args) {
		testWrite();
		testRead();
	}

	private static void testWrite() {
		JacksonObjectExample student = new JacksonObjectExample();
		student.name = "Doofus";
		student.age = 24;
		student.gender = JacksonObjectExample.Gender.MALE;
		student.master = false;
		student.progress = 0.5f;
		student.courses = new ArrayList<>();
		student.courses.add(new JacksonObjectExample.Course("Android", 1));
		student.courses.add(new JacksonObjectExample.Course("C++", 2));
		student.courses.add(new JacksonObjectExample.Course("Webtech", 3));
		student.tags = new ArrayList<>();
		student.tags.add("yolo");
		student.tags.add("swag");
		student.tags.add("lol");

		try {
			JacksonWriter.write("test.json", student);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testRead() {
		try {
			JacksonObjectExample student = JacksonReader.read("test.json",
					JacksonObjectExample.class);
			System.out.println(student.name);
			System.out.println(student.age);
			System.out.println(student.gender);
			System.out.println(student.master);
			System.out.println(student.progress);
			if (student.courses == null) {
				System.out.println("null courses");
			} else {
				for (JacksonObjectExample.Course course : student.courses) {
					System.out.println("Course: " + course.name + " ("
							+ course.tries + ")");
				}
			}
			if (student.tags == null) {
				System.out.println("null tags");
			} else {
				for (String tag : student.tags) {
					System.out.println("Tag: " + tag);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
