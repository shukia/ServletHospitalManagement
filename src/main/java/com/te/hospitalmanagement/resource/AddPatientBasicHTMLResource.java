package com.te.hospitalmanagement.resource;

import com.te.hospitalmanagement.dto.PatientDto;
import com.te.hospitalmanagement.service.PatientService;
import com.te.hospitalmanagement.service.serviceImpl.PatientServiceImpl;
import org.owasp.encoder.Encode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/AddPatientBasicHTML")
public class AddPatientBasicHTMLResource extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        unsafe(request, response);

        safe(request, response);
    }

    private void unsafe(HttpServletRequest request, HttpServletResponse response) {

        String name = request.getParameter("name");
        String email = request.getParameter("email");

        PatientDto patientDto = new PatientDto(name, email);

        PatientService patientService = new PatientServiceImpl();

        patientService.savePatient(patientDto);

        Optional<PatientDto> savedPatientDto = patientService.getPatient(name);

        savedPatientDto.ifPresent(thePatient -> {
            try {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.write("<br><br>Unsafe HTML context:<br>");
                out.write("<h1>" + thePatient.getName() + "</h1>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void safe(HttpServletRequest request, HttpServletResponse response) {

        String name = request.getParameter("name");
        String email = request.getParameter("email");

        PatientDto patientDto = new PatientDto(name, email);

        PatientService patientService = new PatientServiceImpl();

        patientService.savePatient(patientDto);

        Optional<PatientDto> savedPatientDto = patientService.getPatient(name);

        savedPatientDto.ifPresent(thePatient -> {
            try {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.write("<br><br>Safe HTML context:<br>");
                out.write("<h1>" + Encode.forHtml(thePatient.getName()) + "</h1>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
