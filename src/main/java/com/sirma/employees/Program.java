package com.sirma.employees;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;

import com.sirma.employees.controller.EmployeesController;
import com.sirma.employees.entity.Employee;
import com.sirma.employees.entity.EmployeePair;

public class Program {

    private static final String EMPLOYEES_FILE_PATH = "/employees.txt";
    private static final String[] DATE_FORMATS =
            {
                    "yyyy.MM.dd G 'at' HH:mm:ss z",
                    "EEE, MMM d, ''yy",
                    "yyyyy.MMMMM.dd GGG hh:mm aaa",
                    "EEE, d MMM yyyy HH:mm:ss Z",
                    "yyMMddHHmmssZ",
                    "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
                    "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                    "YYYY-'W'ww-u",
                    "EEE, dd MMM yyyy HH:mm:ss z",
                    "EEE, dd MMM yyyy HH:mm zzzz",
                    "yyyy-MM-dd'T'HH:mm:ssZ",
                    "yyyy-MM-dd'T'HH:mm:ss.SSSzzzz",
                    "yyyy-MM-dd'T'HH:mm:sszzzz",
                    "yyyy-MM-dd'T'HH:mm:ss z",
                    "yyyy-MM-dd'T'HH:mm:ssz",
                    "yyyy-MM-dd'T'HH:mm:ss",
                    "yyyy-MM-dd'T'HHmmss.SSSz",
                    "yyyy-MM-dd",
                    "yyyyMMdd",
                    "dd/MM/yy",
                    "dd/MM/yyyy",
                    "yyyy/MM/dd",
                    "dd MMM yyyy",
                    "dd MMMM yyyy",
                    "yyyy MMMMM d",
                    "yyyy d MMMMM"
            };

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
        try {
            Instant instant = DateUtils.parseDateStrictly(date, DATE_FORMATS).toInstant();
            return instant.atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        //        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        return LocalDate.parse(date, formatter);
    }
}
