package com.sirma.employees.controller;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sirma.employees.entity.Employee;
import com.sirma.employees.entity.EmployeePair;
import com.sirma.employees.exception.EmployeePairNotFoundException;
import com.sun.istack.internal.NotNull;

import static java.util.Objects.requireNonNull;

public class EmployeesController {

    @NotNull
    public EmployeePair filterEmployees(@NotNull List<Employee> employees) {
        requireNonNull(employees, "Parameter 'employees' cannot be null.");

        Map<Long, EmployeePair> employeesPairs = mergeEmployeesToPairs(createEmployeesMap(employees));

        return employeesPairs
                .values()
                .stream()
                .max(Comparator.comparing(EmployeePair::getDaysWorkedOnProject))
                .orElseThrow(() -> new EmployeePairNotFoundException("Employee pair not found"));
    }

    private Map<Long, List<Employee>> createEmployeesMap(@NotNull List<Employee> employees) {
        requireNonNull(employees, "Parameter 'employees' cannot be null.");

        return employees
                .stream()
                .collect(Collectors.groupingBy(Employee::getProjectId, Collectors.toList()));
    }

    private Map<Long, EmployeePair> mergeEmployeesToPairs(Map<Long, List<Employee>> employeesMap) {
        return employeesMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, employees -> {
            List<Employee> employeesList = employees.getValue();
            Duration duration1 = Duration.between(employeesList.get(0).getDateFrom(), employeesList.get(0).getDateTo());
            Duration duration2 = Duration.between(employeesList.get(1).getDateFrom(), employeesList.get(1).getDateTo());

            long duration = 0;
            if (duration1.toDays() > duration2.toDays()) {
                duration = duration1.toDays() - duration2.toDays();
            } else {
                duration = duration2.toDays() - duration1.toDays();
            }

            return new EmployeePair(employeesList.get(0).getEmpId(), employeesList.get(1).getEmpId(), duration);
        }));
    }
}
