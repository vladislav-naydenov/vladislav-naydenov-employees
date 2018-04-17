package com.sirma.employees.controller;

import java.time.temporal.ChronoUnit;
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

        List<EmployeePair> employeesPairs = mergeEmployeesToPairs(createEmployeesMap(employees));

        return employeesPairs
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

    private List<EmployeePair> mergeEmployeesToPairs(Map<Long, List<Employee>> employeesMap) {
        return employeesMap.values()
                .stream()
                .map(employees -> {
                    if (employees.size() > 1) {
                        long daysOfFirstEmployee = ChronoUnit.DAYS.between(employees.get(0).getDateFrom(), employees.get(0).getDateTo());
                        long daysOfSecondEmployee = ChronoUnit.DAYS.between(employees.get(1).getDateFrom(), employees.get(1).getDateTo());
                        long totalDaysWorkedOnProject = daysOfFirstEmployee + daysOfSecondEmployee;

                        return new EmployeePair(employees.get(0).getEmpId(), employees.get(1).getEmpId(), totalDaysWorkedOnProject);
                    }

                    return EmployeePair.empty();
                })
                .collect(Collectors.toList());
    }
}
