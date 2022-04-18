package com.yash.project.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

    @Id
    private int user_id;
    private String email;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String password;
    private String is_deleted;
    private String is_active;
    private String  is_locked;
    private int invalid_attempt_count;
    private String password_update_date;


    //public String contact;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_role",joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles;


    //@OneToOne(mappedBy = "users")
    //private Customer customer;


    public String contact;

    private String gst;
    private String company_contact;
    private String company_name;
    private String company_address;

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getCompany_contact() {
        return company_contact;
    }

    public void setCompany_contact(String company_contact) {
        this.company_contact = company_contact;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }


    /*public int role_id;

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }*/




    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


    /*public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }*/


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return   password;
    }

    public void setPassword(String password)
    {
        /*String passwordError="";
        if(password.length()<8)
        {
            passwordError="Password length should be atleast 8";
        }
        else
        {
            passwordError="Password Accepted";
        }*/
        this.password=password;
        //this.password =new BCryptPasswordEncoder().encode(password);
        //return passwordError;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getIs_locked() {
        return is_locked;
    }

    public void setIs_locked(String is_locked) {
        this.is_locked = is_locked;
    }

    public int getInvalid_attempt_count() {
        return invalid_attempt_count;
    }

    public void setInvalid_attempt_count(int invalid_attempt_count) {
        this.invalid_attempt_count = invalid_attempt_count;
    }

    public String getPassword_update_date() {
        return password_update_date;
    }

    public void setPassword_update_date(String password_update_date) {
        this.password_update_date = password_update_date;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
