package com.sirma.employees.entity;

import java.util.Objects;

public class EmployeePair {

    private long employeeId1;
    private long employeeId2;
    private long daysWorkedOnProject;

    public EmployeePair(long employeeId1, long employeeName2, long daysWorkedOnProject) {
        this.employeeId1 = employeeId1;
        this.employeeId2 = employeeName2;
        this.daysWorkedOnProject = daysWorkedOnProject;
    }

    public long getEmployeeId1() {
        return employeeId1;
    }

    public long getEmployeeId2() {
        return employeeId2;
    }

    public long getDaysWorkedOnProject() {
        return daysWorkedOnProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmployeePair that = (EmployeePair) o;
        return daysWorkedOnProject == that.daysWorkedOnProject &&
                Objects.equals(employeeId1, that.employeeId1) &&
                Objects.equals(employeeId2, that.employeeId2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId1, employeeId2, daysWorkedOnProject);
    }

    @Override
    public String toString() {
        return "EmployeePair{" +
                "employeeId1='" + employeeId1 + '\'' +
                ", employeeId2='" + employeeId2 + '\'' +
                ", daysWorkedOnProject=" + daysWorkedOnProject +
                '}';
    }
}
