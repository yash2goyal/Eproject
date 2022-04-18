package com.yash.project.controller;
import java.lang.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.yash.project.entities.*;
//import com.yash.project.entities.Customer;
import com.yash.project.repo.*;
//import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
//import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
//import org.graalvm.compiler.lir.LIRInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
//import sun.util.calendar.LocalGregorianCalendar;

import java.util.regex.Pattern;

@RestController
public class CombinedController {

    @Autowired
    UserRepo userRepo;


    @Autowired
    CustomerRepo customerRepo;
    Customer customer=new Customer();
    @Autowired
    User_roleRepo user_roleRepo;
    User_role user_role=new User_role();
    @Autowired
    SellerRepo sellerRepo;
    Seller seller=new Seller();
   User user=new User();


    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }


    @RequestMapping(value = "/viewAllCustomer",method = RequestMethod.GET)
    public List<UserDAO> ListAllCustomer()
    {
        User user=new User();
        List<User> users=userRepo.findAll();
        List<User_role> user_roles=user_roleRepo.findAll();

        List<UserDAO> finalUser=new ArrayList<>();
        /*for(User p:users)

        {


                UserDAO userDAO = new UserDAO();
                userDAO.setUser_id(p.getUser_id());
                userDAO.setName(p.getFirst_name() + " " + p.getLast_name());
                userDAO.setEmail(p.getEmail());
                userDAO.setIs_active(p.getIs_active());
                finalUser.add(userDAO);


        }*/

        for(int i=0;i<users.size();i++)

        {     //boolean flag=false;

              int role_id=0;
                for(int j=0;j<user_roles.size();j++)
                {
                    if(users.get(i).getUser_id()==user_roles.get(j).getUser_id())
                    {
                        role_id=user_roles.get(j).getRole_id();
                    }

                }


                if(role_id==2) {
                    UserDAO userDAO = new UserDAO();
                    userDAO.setUser_id(users.get(i).getUser_id());
                    userDAO.setName(users.get(i).getFirst_name() + " " + users.get(i).getLast_name());
                    userDAO.setEmail(users.get(i).getEmail());
                    userDAO.setIs_active(users.get(i).getIs_active());
                    finalUser.add(userDAO);
                }

        }


        return finalUser;

    }





    @RequestMapping(value = "/viewAllSeller",method = RequestMethod.GET)
    public List<UserDAO> ListAllSeller()
    {
        User user=new User();
        List<User> users=userRepo.findAll();
        List<User_role> user_roles=user_roleRepo.findAll();

        List<UserDAO> finalUser=new ArrayList<>();
        /*for(User p:users)

        {


                UserDAO userDAO = new UserDAO();
                userDAO.setUser_id(p.getUser_id());
                userDAO.setName(p.getFirst_name() + " " + p.getLast_name());
                userDAO.setEmail(p.getEmail());
                userDAO.setIs_active(p.getIs_active());
                finalUser.add(userDAO);


        }*/

        for(int i=0;i<users.size();i++)

        {     //boolean flag=false;
            int role_id=0;
            for(int j=0;j<user_roles.size();j++)
            {
                if(users.get(i).getUser_id()==user_roles.get(j).getUser_id())
                {
                    role_id=user_roles.get(j).getRole_id();
                }

            }


            if(role_id==3) {
                UserDAO userDAO = new UserDAO();
                userDAO.setUser_id(users.get(i).getUser_id());
                userDAO.setName(users.get(i).getFirst_name() + " " + users.get(i).getLast_name());
                userDAO.setEmail(users.get(i).getEmail());
                userDAO.setIs_active(users.get(i).getIs_active());
                finalUser.add(userDAO);
            }

        }


        return finalUser;

    }



    public boolean CheckPhone(String phoneNumber)
    {
        return phoneNumber.matches("[0-9]+");
    }

    @RequestMapping(value = "/registerAsCustomer",method = RequestMethod.GET)
    public List<String> create(@RequestBody User user)
    {

        //user_role=null;
        boolean flag=false;
        String middle_name=" ";
        boolean flagForUserId=false;
        List<User> users=userRepo.findAll();
        int user_id=0;
        for(User p:users)
        {
/*            if(user.getUser_id()==p.getUser_id())
            {
                flagForUserId=true;
            }*/

            if(user.getEmail().equals(p.getEmail())==true)
            {
                flag=true;
            }
            user_id=p.getUser_id();

        }

        user_id=user_id+1;
        user_role.setUser_id(user_id);
        user_role.setRole_id(2);

        String errorMessage="";
        List<String> validationMessages=new LinkedList<>();
        boolean isEmailCorrect=patternMatches(user.getEmail(),"^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"+"[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");


        String phoneNumber=user.getContact();
        boolean isPhoneCorrect=CheckPhone(phoneNumber);


        //System.out.println(phoneNumber.length());



        if(isEmailCorrect==false)
        {
            errorMessage="Invalid Email";
            validationMessages.add("Invalid Email");

        }
        if(flag==true)
        {
            errorMessage=errorMessage+" Email already exists ";
            validationMessages.add("Email already exists");
        }
        /*if(flagForUserId==true)
        {
            errorMessage=errorMessage+"User Id already exists";
        }*/
        if(isPhoneCorrect==false || phoneNumber.length()<=9)
        {
            errorMessage=errorMessage+" Invalid phone number ";
            validationMessages.add("Invalid phone number");
        }
        //System.out.println(user.getPassword());
        String password=user.getPassword();
        int passwordLength=password.length();
        boolean flagForDigitCheckInPassword=false;

        for(int i=0;i<passwordLength;i++)
        {
            if (Character.isDigit(password.charAt(i))) {
                flagForDigitCheckInPassword=true;
            }

        }



        if(user.getPassword().length()<8)
        {
            validationMessages.add("Password length should be atleast 8");
        }
        if(flagForDigitCheckInPassword==false)
        {
            validationMessages.add("Password must contain atleast one Number,one lower case,one upper case and one special symbole");
        }

        String encryptedPassword=new BCryptPasswordEncoder().encode(password);

        if(user.getPassword().length()>=8 && flag==false && isEmailCorrect==true && isPhoneCorrect==true && phoneNumber.length()>9 && flagForDigitCheckInPassword==true)
        {
            errorMessage=errorMessage+"Record Added";
            validationMessages.add("Customer successfully created,you will shortly get a link to activate your account");
            user.setUser_id(user_id);
            user.setPassword(encryptedPassword);
            user.setIs_deleted("no");
            user.setIs_active("no");
            user.setIs_locked("no");

            user.setInvalid_attempt_count(0);
            user.setPassword_update_date("NoUpdation");
            userRepo.save(user);

            //System.out.println(user_role.getRole_id()+"ye role id he user role table ki");
            //System.out.println(user_role.getUser_id()+"ye user id he user role table ki");

            customer.setUser_id(user.getUser_id());
            customer.setContact(user.getContact());
            customerRepo.save(customer);
            user_roleRepo.save(user_role);
        }



        //System.out.println(customer.getUser_id());

        //customer.setContact(user.getContact());
        //customerRepo.save(customer);

        return validationMessages;

    }  // customer api ends here

    //seller api starts here

    @RequestMapping(value = "/registerAsSeller",method = RequestMethod.GET)
    public List<String> createSeller(@RequestBody User user)
    {

        //user_role=null;
        boolean flag=false;
        String middle_name=" ";
        //boolean flagForUserId=false;
        List<User> users=userRepo.findAll();
        int user_id=0;
        boolean flagForGst=false;
        boolean flagForCompanyName=false;

        //List<String> validationMessages=new LinkedList<>();
        for(User p:users)
        {
/*            if(user.getUser_id()==p.getUser_id())
            {
                flagForUserId=true;
            }*/
            System.out.println(p.getPassword());
            if(user.getCompany_name().equals(p.getCompany_name())==true)
            {
                flagForCompanyName=true;
            }

            if(user.getGst().equals(p.getGst())==true)
            {
                flagForGst=true;
            }
            if(user.getEmail().equals(p.getEmail())==true )
            {
                flag=true;
            }
            user_id=p.getUser_id();

        }

        user_id=user_id+1;
        user_role.setUser_id(user_id);
        user_role.setRole_id(3);

        String errorMessage="";
        List<String> validationMessages=new LinkedList<>();
        boolean isEmailCorrect=patternMatches(user.getEmail(),"^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"+"[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");


        String phoneNumber=user.getCompany_contact();
        boolean isPhoneCorrect=phoneNumber.matches("[0-9]+");
        //.out.println(phoneNumber.length());



        if(isEmailCorrect==false)
        {
            errorMessage="Invalid Email";
            validationMessages.add("Invalid Email");

        }
        if(flag==true)
        {
            errorMessage=errorMessage+" Email already exists ";
            validationMessages.add("Email already exists");
        }
        if(flagForGst==true)
        {
            errorMessage=errorMessage+" gst already exists ";
            validationMessages.add("GST already registered");
        }
        if(flagForCompanyName==true)
        {
            errorMessage=errorMessage+" Company already exists ";
            validationMessages.add("Company already registered");
        }
        /*if(flagForUserId==true)
        {
            errorMessage=errorMessage+"User Id already exists";
        }*/
        if(isPhoneCorrect==false || phoneNumber.length()<=9)
        {
            errorMessage=errorMessage+" Invalid phone number ";
            validationMessages.add("Invalid phone number");
        }
        //System.out.println(user.getPassword());
        String password=user.getPassword();
        int passwordLength=password.length();
        boolean flagForDigitCheckInPassword=false;

        for(int i=0;i<passwordLength;i++)
        {
            if (Character.isDigit(password.charAt(i))) {
                flagForDigitCheckInPassword=true;
            }

        }



        if(user.getPassword().length()<8)
        {
            validationMessages.add("Password length should be atleast 8");
        }
        if(flagForDigitCheckInPassword==false)
        {
            validationMessages.add("Password must contain atleast one Number,one lower case,one upper case and one special symbole");
        }

        String encryptedPassword=new BCryptPasswordEncoder().encode(password);

        if(user.getPassword().length()>=8 && flagForCompanyName==false &&flagForGst==false && flag==false && isEmailCorrect==true && isPhoneCorrect==true && phoneNumber.length()>9 && flagForDigitCheckInPassword==true)
        {
            errorMessage=errorMessage+"Record Added";
            validationMessages.add("Seller successfully created,you will shortly get a link to activate your account");
            user.setUser_id(user_id);
            user.setPassword(encryptedPassword);
            user.setIs_deleted("no");
            user.setIs_active("no");
            user.setIs_locked("no");

            user.setInvalid_attempt_count(0);
            user.setPassword_update_date("NoUpdation");
            userRepo.save(user);

            //System.out.println(user_role.getRole_id()+"ye role id he user role table ki");
            //System.out.println(user_role.getUser_id()+"ye user id he user role table ki");

            seller.setUser_id(user.getUser_id());
            seller.setGst(user.getGst());
            seller.setCompany_name(user.getCompany_name());
            seller.setCompany_contact(user.getCompany_contact());
            seller.setCompany_address(user.getCompany_address());
            sellerRepo.save(seller);
            /*customer.setUser_id(user.getUser_id());
            customer.setContact(user.getContact());
            customerRepo.save(customer);*/
            user_roleRepo.save(user_role);
        }



        //System.out.println(customer.getUser_id());

        //customer.setContact(user.getContact());
        //customerRepo.save(customer);

        return validationMessages;

    }//seller api ends here


   @GetMapping("/loginPage")
   public List<String> loggedInCustomer(@RequestBody User user) {



        String email=user.getEmail();
        String password=user.getPassword();

       List<User> users=userRepo.findAll();
       boolean finalFlag=false;

       boolean isPasswordMatch=false;
       List<String> validationMessages=new LinkedList<>();
       for(User p:users)
       {

           //System.out.println(p.getPassword());
           if(user.getEmail().equals(p.getEmail())==true)
           {

               isPasswordMatch = new BCryptPasswordEncoder().matches(password,p.getPassword());
           }


       }

       if(isPasswordMatch==false)
       {
           validationMessages.add("Invalid username/password");
        //   System.out.println(user.getInvalid_attempt_count());
       }
       if(isPasswordMatch==true)
       {
           validationMessages.add("logged in successfully");
       }

return validationMessages;
   }










       @Autowired
    AddressRepo addressRepo;
    @GetMapping("/address")
    public Address creareAddress(@RequestBody Address address){

        return addressRepo.save(address);
    }


    @GetMapping("/ActivateUser")
    public String ActivateCustomer(@RequestBody Customer customer)
    {
       String errorMessage=" ";
       String is_active=" ";
       List<User> users=userRepo.findAll();
       int CustomerUser_id=customer.getUser_id();
       System.out.println("this is the user id given by admin"+ CustomerUser_id);
       for(int i=0;i<users.size();i++)
       {
           if(CustomerUser_id==users.get(i).getUser_id())
           {
               is_active=users.get(i).getIs_active();

               if (is_active.equals("yes")) {
                   errorMessage = "user is already activated";
               } else {
                   users.get(i).setIs_active("yes");
                   userRepo.save(users.get(i));
                   errorMessage = "customer is succesfully activated";
               }
           }
           else
           {
               errorMessage="User does not existx";
           }


       }


        return errorMessage;
    }




    @GetMapping("/DeactivateUser")
    public String DeactivateCustomer(@RequestBody Customer customer)
    {
        String errorMessage=" ";
        String is_active=" ";
        boolean flag=false;
        List<User> users=userRepo.findAll();
        int CustomerUser_id=customer.getUser_id();
        System.out.println("this is the user id given by admin"+ CustomerUser_id);
        for(int i=0;i<users.size();i++)
        {
            if(CustomerUser_id==users.get(i).getUser_id())
            { flag=true;
                is_active=users.get(i).getIs_active();

                if (is_active.equals("no")) {
                    errorMessage = "user is already deactivated";
                } else {
                    users.get(i).setIs_active("no");
                    userRepo.save(users.get(i));
                    errorMessage = "user is succesfully deactivated";
                }
            }


        }

        if(flag==false)
        {
            errorMessage="user does not exist";
        }
        return errorMessage;
    }

    @GetMapping("/ViewProfileAsSeller")
    public SellerDAO viewProfile(@RequestBody Seller seller)
    {

        List<User> users=userRepo.findAll();
        SellerDAO sellerDAO=new SellerDAO();

        for(int i=0;i<users.size();i++)
        {
            if(seller.getUser_id()==users.get(i).getUser_id())
            {
               sellerDAO.setUser_id(users.get(i).getUser_id());
               sellerDAO.setFirst_name(users.get(i).getFirst_name());
               sellerDAO.setLast_name(users.get(i).getLast_name());
                sellerDAO.setIs_active(users.get(i).getIs_active());
                sellerDAO.setCompany_contact(users.get(i).getCompany_contact());
                sellerDAO.setCompany_name(users.get(i).getCompany_name());
                sellerDAO.setGst(users.get(i).getGst());

            }


        }


return sellerDAO;

    }


    @GetMapping("/ViewProfileAsCustomer")
    public SellerDAO viewProfileAsCustomer(@RequestBody Seller seller)
    {

        List<User> users=userRepo.findAll();
        SellerDAO sellerDAO=new SellerDAO();

        for(int i=0;i<users.size();i++)
        {
            if(seller.getUser_id()==users.get(i).getUser_id())
            {
                sellerDAO.setUser_id(users.get(i).getUser_id());
                sellerDAO.setFirst_name(users.get(i).getFirst_name());
                sellerDAO.setLast_name(users.get(i).getLast_name());
                sellerDAO.setIs_active(users.get(i).getIs_active());
                sellerDAO.setCompany_contact(users.get(i).getCompany_contact());

            }


        }


        return sellerDAO;

    }




    @GetMapping("/UpdateSellerProfile")
    public String UpdateSellerProfile(@RequestBody SellerDAO sellerDAO){
        String errorMessage="";
        String password1=" ";
        String first_name=" ";
        String last_name=" ";
        String Company_contact=" ";
        String company_name=" ";
        String gst=" ";

        boolean isPhoneCorrect=false;
        if(sellerDAO.getFirst_name()!=null) {
            first_name = sellerDAO.getFirst_name();
        }
        if(sellerDAO.getPassword()!=null)
        {
            password1=sellerDAO.getPassword();
        }

        if(sellerDAO.getLast_name()!=null) {

         last_name=sellerDAO.getLast_name();}
        if(sellerDAO.getCompany_contact()!=null) {
             Company_contact=sellerDAO.getCompany_contact();
            isPhoneCorrect=CheckPhone(Company_contact);
        }


        if(sellerDAO.getCompany_name()!=null) {

             company_name=sellerDAO.getCompany_name();}

        if(sellerDAO.getGst()!=null) {

             gst=sellerDAO.getGst();}

        boolean flagForCompanyName=false;
        boolean flagForGst=false;
        List<User> users=userRepo.findAll();
        //user.setFirst_name(first_name);
        //user.setLast_name(last_name);

        //System.out.println(isPhoneCorrect);
        if(sellerDAO.getCompany_contact()!=null) {
            if (isPhoneCorrect == false || Company_contact.length() <= 9) {
                errorMessage = "invalid phone";
            }
        }
        for(User p:users)
        {
            if(company_name.equals(p.getCompany_name())==true)
            {
                flagForCompanyName=true;
            }
            if(gst.equals(p.getGst())==true)
            {
                flagForGst=true;
            }

        }
        if(flagForGst==true)
        {
            errorMessage=errorMessage+ "gst already exist";
        }
        if(flagForCompanyName==true)
        {
            errorMessage=errorMessage+"Comapny name already exis";
        }

        if(sellerDAO.getCompany_contact()==null)
        {
            isPhoneCorrect=true;
        }
        for(int i=0;i<users.size();i++)
        {
            if(sellerDAO.getEmail().equals(users.get(i).getEmail())==true) {



                if (flagForGst == false && flagForCompanyName == false && isPhoneCorrect == true) {
                    if(sellerDAO.getFirst_name()!=null) {
                        users.get(i).setFirst_name(first_name);
                    }
                    if(sellerDAO.getPassword()!=null)
                    {
                        String encryptedPassword=new BCryptPasswordEncoder().encode(password1);
                        users.get(i).setPassword(encryptedPassword);
                    }

                    if(sellerDAO.getLast_name()!=null) {
                        users.get(i).setLast_name(last_name);
                    }
                    if(sellerDAO.getCompany_contact()!=null) {
                        users.get(i).setCompany_contact(Company_contact);
                    }
                    if(sellerDAO.getCompany_name()!=null) {
                        users.get(i).setCompany_name(company_name);
                    }
                    if(sellerDAO.getGst()!=null) {
                        users.get(i).setGst(gst);
                    }

                    userRepo.save(users.get(i));
                    errorMessage = "user succesfully updated";
                }
            }



        }





        return errorMessage;

    }




    @GetMapping("/UpdateSellerProfilePassword")
    public String UpdateSellerProfilePassword(@RequestBody SellerDAO sellerDAO){
        String errorMessage="";
        String password1=" ";
        String first_name=" ";
        String last_name=" ";
        String Company_contact=" ";
        String company_name=" ";
        String gst=" ";

        boolean isPhoneCorrect=false;
        if(sellerDAO.getFirst_name()!=null) {
            first_name = sellerDAO.getFirst_name();
        }
        if(sellerDAO.getPassword()!=null)
        {
            password1=sellerDAO.getPassword();
        }

        if(sellerDAO.getLast_name()!=null) {

            last_name=sellerDAO.getLast_name();}
        if(sellerDAO.getCompany_contact()!=null) {
            Company_contact=sellerDAO.getCompany_contact();
            isPhoneCorrect=CheckPhone(Company_contact);
        }


        if(sellerDAO.getCompany_name()!=null) {

            company_name=sellerDAO.getCompany_name();}

        if(sellerDAO.getGst()!=null) {

            gst=sellerDAO.getGst();}

        boolean flagForCompanyName=false;
        boolean flagForGst=false;
        List<User> users=userRepo.findAll();
        //user.setFirst_name(first_name);
        //user.setLast_name(last_name);

        //System.out.println(isPhoneCorrect);
        if(sellerDAO.getCompany_contact()!=null) {
            if (isPhoneCorrect == false || Company_contact.length() <= 9) {
                errorMessage = "invalid phone";
            }
        }
        for(User p:users)
        {
            if(company_name.equals(p.getCompany_name())==true)
            {
                flagForCompanyName=true;
            }
            if(gst.equals(p.getGst())==true)
            {
                flagForGst=true;
            }

        }
        if(flagForGst==true)
        {
            errorMessage=errorMessage+ "gst already exist";
        }
        if(flagForCompanyName==true)
        {
            errorMessage=errorMessage+"Comapny name already exis";
        }

        if(sellerDAO.getCompany_contact()==null)
        {
            isPhoneCorrect=true;
        }
        for(int i=0;i<users.size();i++)
        {
            if(sellerDAO.getEmail().equals(users.get(i).getEmail())==true) {



                if (flagForGst == false && flagForCompanyName == false && isPhoneCorrect == true) {
                    if(sellerDAO.getFirst_name()!=null) {
                        users.get(i).setFirst_name(first_name);
                    }
                    if(sellerDAO.getPassword()!=null)
                    {
                        String encryptedPassword=new BCryptPasswordEncoder().encode(password1);
                        users.get(i).setPassword(encryptedPassword);
                    }

                    if(sellerDAO.getLast_name()!=null) {
                        users.get(i).setLast_name(last_name);
                    }
                    if(sellerDAO.getCompany_contact()!=null) {
                        users.get(i).setCompany_contact(Company_contact);
                    }
                    if(sellerDAO.getCompany_name()!=null) {
                        users.get(i).setCompany_name(company_name);
                    }
                    if(sellerDAO.getGst()!=null) {
                        users.get(i).setGst(gst);
                    }

                    userRepo.save(users.get(i));
                    errorMessage = "user succesfully updated";
                }
            }



        }





        return errorMessage;

    }



    @GetMapping("/show")
    public String sh(){
        return "hello hello";
    }

}

