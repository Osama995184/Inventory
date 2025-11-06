/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

/**
 *
 * @author Osama
 */
public class Departments {
    private int id;
    private String department;
    
    public Departments(int id, String department){
        this.id = id;
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public int getId() {
        return id;
    }
    
}
