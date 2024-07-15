package telran.employees;

import org.json.JSONObject;

public class WageEmployee extends Employee {
	private int hours;
	private int wage; //one hour salary cost
	public WageEmployee() {
	}
	public WageEmployee(long id, int basicSalary, String department, int hours, int wage) {
		super(id, basicSalary, department);
		this.hours = hours;
		this.wage = wage;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public int getWage() {
		return wage;
	}
	public void setWage(int wage) {
		this.wage = wage;
	}
	@Override
	public int computeSalary() {
		return super.computeSalary() + wage * hours;
	}
	@Override
    protected void fillJSONObject(JSONObject jsonObject) {
    	fillClassName(jsonObject);
    	super.fillJSONObject(jsonObject);
    	jsonObject.put("wage", wage);
    	jsonObject.put("hours", hours);
    }
    @Override
    protected void fillEmployee(JSONObject jsonObject) {
    	super.fillEmployee(jsonObject);
    	wage = jsonObject.getInt("wage");
    	hours = jsonObject.getInt("hours");
    }
  
    @Override
    public String toString() {
        return super.toString() + String.format(", Hours: %d, Wage: %d", hours, wage);
    }
	
}
