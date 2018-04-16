package com.sirma.employees.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Employee {

    private long empId;
    private long projectId;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public Employee(long empId, long projectId, LocalDate dateFrom, LocalDate dateTo) {
        this.empId = empId;
        this.projectId = projectId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return empId == employee.empId &&
                projectId == employee.projectId &&
                Objects.equals(dateFrom, employee.dateFrom) &&
                Objects.equals(dateTo, employee.dateTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId, projectId, dateFrom, dateTo);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", projectId=" + projectId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}
