package com.sirma.employees;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.sirma.employees.controller.EmployeesController;
import com.sirma.employees.entity.Employee;
import com.sirma.employees.entity.EmployeePair;

public class Program {

    private static final String EMPLOYEES_FILE_PATH = "/employees.txt";

    public static void main(String[] args) {
        List<String> fileContent = readFile(EMPLOYEES_FILE_PATH);

        EmployeesController controller = new EmployeesController();
        EmployeePair filteredEmployees = controller.filterEmployees(parseEmployeesFromFileContent(fileContent));
        System.out.println(filteredEmployees);
    }

    private static List<String> readFile(String path) {
        URL fileUrl = Program.class.getResource(path);

        try {
            return Files.lines(Paths.get(fileUrl.toURI())).collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<Employee> parseEmployeesFromFileContent(List<String> lines) {
        return lines.stream().map(line -> {
            String[] empProperties = line.split(",");

            LocalDate dateFrom = parseDate(empProperties[2].trim());
            LocalDate dateTo = parseDate(empProperties[3].trim());
            return new Employee(Long.valueOf(empProperties[0].trim()), Long.valueOf(empProperties[1].trim()), dateFrom, dateTo);
        }).collect(Collectors.toList());
    }

    private static LocalDate parseDate(String date) {
        if (date.equals("NULL")) {
            return LocalDate.now();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
}
