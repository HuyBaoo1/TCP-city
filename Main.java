package main;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

class Inhabitants implements Serializable {
	private static final long serialVersionUID = 1L;//ensures compatibility between the serialized object and the class definition by explicitly specifying the version.
	private String name;
	private String DoB;
	private String MS;
	
	public Inhabitants(String name,String DoB, String MS) {
		this.name = name;
		this.DoB = DoB;
		this.MS = MS;
	}
	
	public String getName() {
		return name;
	}
	public String getDoB() {
		return DoB;
	}
	public String getMS() {
		return MS;
	}
	 public void setMaritalStatus(String MS) {
	        this.MS = MS;
	    }
}

class City implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
    	private Set<Inhabitants> inhabitants;
    
    public City(String name) {
    	this.name = name;
    	this.inhabitants = new HashSet<>();
    }
    public Inhabitants searchInhabitant(String name) {
        for (Inhabitants inhabitant : inhabitants) {
            if (inhabitant.getName().equals(name)) {
            	return inhabitant;
            }
        }
        return null; 
    }
    
    public void addInhabitant(String name, String DoB, String MS) {
    	Inhabitants newInhabitant = new Inhabitants(name,DoB,MS);
    	inhabitants.add(newInhabitant);
    }
    public Set<Inhabitants> getAllInhabitants() {
        return inhabitants;
    }
    
    public String getName() {
    	return name;
    }
    public Set<String> getAllDoBs() {
        Set<String> dobs = new HashSet<>();
        for (Inhabitants inhabitant : inhabitants) {
            dobs.add(inhabitant.getDoB());
        }
        return dobs;
    }

    public String getMaritalStatus(String name) {
        Inhabitants inhabitant = searchInhabitant(name);
        if (inhabitant != null) {
            return inhabitant.getMS();
        } else {
            return "Not found";
        }
    }
}

public class Main implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
        City city = new City("Frankfurt");

        city.addInhabitant("HuyBao", "2003", "Married");
        city.addInhabitant("Harry", "2004", "Single");
        city.addInhabitant("Michael Jordan", "1978", "Married");

    
        Inhabitants huy = city.searchInhabitant("Huy Bao");
        if (huy != null) {
            huy.setMaritalStatus("Single");
        }

       
        int unmarriedCount = 0;
        for (Inhabitants inhabitant : city.getAllInhabitants()) {
            if (inhabitant.getMS().equals("Single")) {
                unmarriedCount++;
            }
        }
        System.out.println("Welcome to Frankfurt");
        System.out.println("Total unmarried inhabitants of Frankfurt: " + unmarriedCount);
        
        Inhabitants search = city.searchInhabitant("HuyBao");
        if(search != null) {
        	System.out.println("Inhabitant found: " + search.getName() + ", DOB: " + search.getDoB() + ", Marital Status: " + search.getMS());
        } else {
        	System.out.println("Not found");
        }
    }
}
