package org.example;

import org.example.domain.Nota;
import org.example.domain.Tema;
import org.example.repository.StudentXMLRepo;
import org.example.validation.NotaValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.example.domain.Student;
import org.example.repository.NotaXMLRepo;
import org.example.repository.TemaXMLRepo;
import org.example.service.Service;
import org.example.validation.StudentValidator;
import org.example.validation.TemaValidator;
import org.example.validation.ValidationException;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class AppTest  {

    public static Service service;

    @Before
    public void setup() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "src/fisiere/Studenti.xml";
        String filenameTema = "src/fisiere/Teme.xml";
        String filenameNota = "src/fisiere/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void testAddStudentValid() {
        String idStudent = "4Y1";
        String nume = "Ana";
        int grupa = 934;
        String email = "ana@yahoo.com";
        Student student = new Student(idStudent, nume, grupa, email);
        Student result = null;

        try {
            result = service.addStudent(student);
        } catch (ValidationException validationException) {
            System.out.println(validationException);
            assert(false);
        }

        assert(result == null);
    }

    @Test
    public void testAddStudentInvalid_NullId() {
        String idStudent = null;
        String nume = "Ana";
        int grupa = 934;
        String email = "ana@yahoo.com";
        Student student = new Student(idStudent, nume, grupa, email);

        try {
            service.addStudent(student);
        } catch (ValidationException validationException) {
            assertEquals("Id incorect!", validationException.getMessage());
        }
    }

    @Test
    public void testAddStudentInvalid_EmptyId() {
        String idStudent = "";
        String nume = "Ana";
        int grupa = 934;
        String email = "ana@yahoo.com";
        Student student = new Student(idStudent, nume, grupa, email);

        try {
            service.addStudent(student);
        } catch (ValidationException validationException) {
            assertEquals("Id incorect!", validationException.getMessage());
        }
    }

    @Test
    public void testAddStudentInvalid_DuplicateId() {
        String idStudent = "4Y1";
        String nume = "Ana";
        int grupa = 934;
        String email = "ana@yahoo.com";
        Student student = new Student(idStudent, nume, grupa, email);
        service.addStudent(student);

        String idStudent2 = "4Y1";
        String nume2 = "Ana2";
        int grupa2 = 934;
        String email2 = "ana@yahoo.com";
        Student student2 = new Student(idStudent2, nume2, grupa2, email2);
        Student result = null;

        try {
            result = service.addStudent(student2);
        } catch (ValidationException validationException) {
            System.out.println(validationException);
            assert(false);
        }

        assertEquals(result, student2);
    }

    @Test
    public void testAddStudentInvalid_NullName() {
        String idStudent = "4Y2";
        String nume = null;
        int grupa = 934;
        String email = "ana@yahoo.com";
        Student student = new Student(idStudent, nume, grupa, email);

        try {
            service.addStudent(student);
        } catch (ValidationException validationException) {
            assertEquals("Nume incorect!", validationException.getMessage());
        }
    }

    @Test
    public void testAddStudentInvalid_NegativeGroup() {
        String idStudent = "4Y3";
        String nume = "Ana";
        int grupa = -934;
        String email = "ana@yahoo.com";
        Student student = new Student(idStudent, nume, grupa, email);

        try {
            service.addStudent(student);
        } catch (ValidationException validationException) {
            assertEquals("Grupa incorecta!", validationException.getMessage());
        }
    }

    @Test
    public void testAddStudentValid_GroupZero() {
        String idStudent = "4Y5";
        String nume = "Ana";
        int grupa = 0;
        String email = "ana@yahoo.com";
        Student student = new Student(idStudent, nume, grupa, email);
        Student result = null;

        try {
            result = service.addStudent(student);
        } catch (ValidationException validationException) {
            System.out.println(validationException);
            assert(false);
        }

        assert(result == null);
    }

    @Test
    public void testAddStudentInvalid_GroupMinusOne() {
        String idStudent = "4Y6";
        String nume = "Ana";
        int grupa = -1;
        String email = "ana@yahoo.com";
        Student student = new Student(idStudent, nume, grupa, email);

        try {
            service.addStudent(student);
        } catch (ValidationException validationException) {
            assertEquals("Grupa incorecta!", validationException.getMessage());
        }
    }

    @Test
    public void testAddStudentValid_GroupPlusOne() {
        String idStudent = "4Y7";
        String nume = "Ana";
        int grupa = 1;
        String email = "ana@yahoo.com";
        Student student = new Student(idStudent, nume, grupa, email);
        Student result = null;

        try {
            result = service.addStudent(student);
        } catch (ValidationException validationException) {
            System.out.println(validationException);
            assert(false);
        }

        assert(result == null);
    }

    @Test
    public void testAddStudentInvalid_NullEmail() {
        String idStudent = "4Y4";
        String nume = "Ana";
        int grupa = 934;
        String email = null;
        Student student = new Student(idStudent, nume, grupa, email);

        try {
            service.addStudent(student);
        } catch (ValidationException validationException) {
            assertEquals("Email incorect!", validationException.getMessage());
        }
    }

    @Test
    public void addAssignmentValid() {
        String nrTema = "100";
        String descriere = "test";
        int deadline = 12;
        int primire = 11;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        try {
            service.addTema(tema);
            assert(true);
        } catch (ValidationException exception) {
            System.out.println("Validation exception: " + exception.getMessage());
            assert(false);
        }
    }

    @Test
    public void addAssignmentInvalid_assignmentNoEmpty() {
        String nrTema = "";
        String descriere = "test";
        int deadline = 12;
        int primire = 11;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        try {
            service.addTema(tema);
            assert(false);
        } catch (ValidationException exception) {
            assert(true);
        }
    }

    @Test
    public void addAssignmentInvalid_assignmentNoNull() {
        String nrTema = null;
        String descriere = "test";
        int deadline = 12;
        int primire = 11;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        try {
            service.addTema(tema);
            assert(false);
        } catch (ValidationException exception) {
            assert(true);
        }
    }

    @Test
    public void addAssignmentInvalid_assignmentNoDuplicate() {
        String nrTema = "101";
        String descriere = "test";
        int deadline = 12;
        int primire = 11;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        service.addTema(tema);

        String nrTema2 = "101";
        String descriere2 = "test";
        int deadline2 = 12;
        int primire2 = 11;
        Tema tema2 = new Tema(nrTema2, descriere2, deadline2, primire2);
        Tema result = null;

        try {
            result = service.addTema(tema2);
        } catch (ValidationException exception) {
            System.out.println("Validation exception: " + exception.getMessage());
            assert(false);
        }

        assertEquals(tema2, result);

    }

    @Test
    public void addAssignmentInvalid_descriptionEmpty() {
        String nrTema = "101";
        String descriere = "";
        int deadline = 12;
        int primire = 11;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        try {
            service.addTema(tema);
            assert(false);
        } catch (ValidationException exception) {
            assert(true);
        }
    }

    @Test
    public void addAssignmentInvalid_descriptionNull() {
        String nrTema = "101";
        String descriere = null;
        int deadline = 12;
        int primire = 11;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        try {
            service.addTema(tema);
            assert(false);
        } catch (ValidationException exception) {
            assert(true);
        }
    }

    @Test
    public void addAssignmentInvalid_deadlineSmallerThanOne() {
        String nrTema = "102";
        String descriere = "test";
        int deadline = 0;
        int primire = 11;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        try {
            service.addTema(tema);
            assert(false);
        } catch (ValidationException exception) {
            assert(true);
        }
    }

    @Test
    public void addAssignmentInvalid_deadlineBiggerThanFourteen() {
        String nrTema = "102";
        String descriere = "test";
        int deadline = 15;
        int primire = 11;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        try {
            service.addTema(tema);
            assert(false);
        } catch (ValidationException exception) {
            assert(true);
        }
    }

    @Test
    public void addAssignmentInvalid_primireSmallerThanOne() {
        String nrTema = "103";
        String descriere = "test";
        int deadline = 12;
        int primire = 0;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        try {
            service.addTema(tema);
            assert(false);
        } catch (ValidationException exception) {
            assert(true);
        }
    }
    @Test
    public void addAssignmentInvalid_primireBiggerThanFourteen() {
        String nrTema = "103";
        String descriere = "test";
        int deadline = 12;
        int primire = 15;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        try {
            service.addTema(tema);
            assert(false);
        } catch (ValidationException exception) {
            assert(true);
        }
    }


    @Test
    public void addGradeValid() {
        String idStudent = "4Y10";
        String nume = "Ana";
        int grupa = 934;
        String email = "ana@yahoo.com";
        Student student = new Student(idStudent, nume, grupa, email);
        service.addStudent(student);

        String nrTema = "110";
        String descriere = "test";
        int deadline = 5;
        int primire = 2;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        service.addTema(tema);

        String idGrade = "500";
        String idStudentForGrade = "4Y10";
        String idTemaForGrade = "110";
        double notaNo = 9.5;
        LocalDate data = LocalDate.of(2024, 2, 2);
        Nota nota = new Nota(idGrade, idStudentForGrade, idTemaForGrade, notaNo, data);


        try {
            double result = service.addNota(nota, "Good work. Some minor mistakes.");
            assert(result == notaNo);
        } catch (ValidationException exception) {
            System.out.println("Validation exception: " + exception.getMessage());
            assert(false);
        }
    }

    @Test
    public void integrationTest_addStudentValid_addAssignmentValid_addGradeValid() {

        try {
            deleteAdded();
            testAddStudentValid();
            addAssignmentValid();
            addGradeValid();
            assert(true);
        } catch (ValidationException exception) {
            System.out.println("Validation exception: " + exception.getMessage());
            assert(false);
        }
    }

    // Incremental integration

    @Test
    public void incrementalTestAddStudentValid() {
        String idStudent = "3Y1";
        String nume = "Ana";
        int grupa = 934;
        String email = "ana@yahoo.com";
        Student student = new Student(idStudent, nume, grupa, email);
        Student result = null;

        try {
            deleteAdded();
            result = service.addStudent(student);
        } catch (ValidationException validationException) {
            System.out.println(validationException);
            assert(false);
        }

        assert(result == null);
    }

    @Test
    public void incrementalTestAddStudentAndAssignmentValid() {
        String nrTema = "200";
        String descriere = "test";
        int deadline = 5;
        int primire = 2;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        try {
            deleteAdded();
            incrementalTestAddStudentValid();
            service.addTema(tema);
            assert(true);
        } catch (ValidationException exception) {
            System.out.println("Validation exception: " + exception.getMessage());
            assert(false);
        }
    }

    @Test
    public void incrementalTestAddStudentAndAssignmentAndGradeValid() {
        String idGrade = "500";
        String idStudentForGrade = "3Y1";
        String idTemaForGrade = "200";
        double notaNo = 9.5;
        LocalDate data = LocalDate.of(2024, 2, 2);
        Nota nota = new Nota(idGrade, idStudentForGrade, idTemaForGrade, notaNo, data);

        try {
            deleteAdded();
            incrementalTestAddStudentAndAssignmentValid();
            double result = service.addNota(nota, "Good work. Some minor mistakes.");
            assert(result == notaNo);
        } catch (ValidationException exception) {
            System.out.println("Validation exception: " + exception.getMessage());
            assert(false);
        }
    }

    @After
    public void deleteAdded() {
        service.deleteStudent("4Y1");
        service.deleteStudent("4Y5");
        service.deleteStudent("4Y7");
        service.deleteStudent("4Y10");
        service.deleteStudent("3Y1");
        service.deleteTema("100");
        service.deleteTema("101");
        service.deleteTema("110");
        service.deleteTema("200");
        service.deleteNota("500");

    }
}