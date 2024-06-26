package telran.employees;

import java.util.Iterator;
import java.util.*;

//So far we do consider optimization
public class CompanyMapsImpl implements Company {
	TreeMap<Long, Employee> employees = new TreeMap<>();
	HashMap<String, List<Employee>> employeesDepartment = new HashMap<>();
	TreeMap<Float, List<Manager>> factorManagers = new TreeMap<>();
	
	@Override
	public Iterator<Employee> iterator() {
		return employees.values().iterator();
	}

	@Override
	public void addEmployee(Employee empl) {
	    Long id = empl.getId();
	    Employee empoyee = employees.putIfAbsent(id, empl);
	    if (empoyee != null) {
	        throw new IllegalStateException("Employee already exists");
	    }

	    addToIndexMap(employeesDepartment, empl.getDepartment(), empl);

	    if (empl instanceof Manager) {
	        addToIndexMap(factorManagers, ((Manager) empl).factor, (Manager) empl);
	    }
	}
    
    private <K, T> void addToIndexMap(Map<K, List<T>> map, K key, T employee) {
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(employee);
    }

    @Override
    public Employee getEmployee(long id) {
        return employees.get(id);
    }


    @Override
    public Employee removeEmployee(long id) {
        Employee empl = employees.remove(id);
        if (empl == null) {
            throw new NoSuchElementException("Employee does not exist");
        }

        removeFromIndexMap(employeesDepartment, empl.getDepartment(), empl);

        if (empl instanceof Manager) {
            removeFromIndexMap(factorManagers, ((Manager) empl).factor, (Manager) empl);
        }
        return empl;
    }

    private <K, T> void removeFromIndexMap(Map<K, List<T>> map, K key, T employee) {
        map.computeIfPresent(key, (k, employees) -> {
            employees.remove(employee);
            return employees.isEmpty() ? map.remove(key) : employees;
        });
    }

    @Override
    public int getDepartmentBudget(String department) {
        List<Employee> deptEmployees = employeesDepartment.getOrDefault(department, Collections.emptyList());
        int total = deptEmployees.stream().mapToInt(Employee::computeSalary).sum();
        return total;
    }

    @Override
    public String[] getDepartments() {
        Set<String> departments = employeesDepartment.keySet();
        String[] departmentArray = departments.stream().sorted().toArray(String[]::new);
        return departmentArray;
    }

    @Override
    public Manager[] getManagersWithMostFactor() {
        return factorManagers.isEmpty() ? new Manager[0] :
               factorManagers.get(factorManagers.lastKey()).stream().toArray(Manager[]::new);
    }

}
