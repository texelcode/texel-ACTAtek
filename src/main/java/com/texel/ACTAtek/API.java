package com.texel.ACTAtek;

import com.texel.ACTAtek.service.ACTAtekLocator;
import com.texel.ACTAtek.service.ACTAtekPortType;
import com.texel.ACTAtek.service.ACTAtekStub;
import com.texel.ACTAtek.xsd.AutoInOutOption;
import com.texel.ACTAtek.xsd.Department;
import com.texel.ACTAtek.xsd.GetLogsCriteria;
import com.texel.ACTAtek.xsd.GetUsersCriteria;
import com.texel.ACTAtek.xsd.Group;
import com.texel.ACTAtek.xsd.Log;
import com.texel.ACTAtek.xsd.TerminalStatus;
import com.texel.ACTAtek.xsd.Trigger;
import com.texel.ACTAtek.xsd.User;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.xml.rpc.ServiceException;
import org.apache.log4j.Logger;

public class API implements Serializable{
  
    static Logger logger = Logger.getLogger(API.class.getName());
        
    private URL endPointURL ;
    private ACTAtekLocator locator = new ACTAtekLocator();
    private ACTAtekPortType api ;
    private ACTAtekStub stub;
    private String IPAddress;
    private long sessionID;
        
    public API (String IPAddress){
        this.IPAddress = IPAddress;    
    }
        
    private ACTAtekPortType API(String IPAddress) throws MalformedURLException, ServiceException{
            
        endPointURL = new URL("https://" + IPAddress + "/cgi-bin/rpcrouter");
        
        locator = new ACTAtekLocator();
        api = locator.getACTAtek(endPointURL) ;
                    
        stub = (ACTAtekStub)this.api;
        stub.setTimeout(1800000); 
                  
        return api;
        
    }
        
    public long Login(String IPAddress,String ACTAAdmin, String ACTAPass) throws MalformedURLException, ServiceException, RemoteException {
            
        api = API(IPAddress);
              
        sessionID = api.login(ACTAAdmin,ACTAPass);	 
                
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+" | Login");
                   
        return sessionID;
	
    }
    
    public void Logout(String IPAddress, long sessionID) throws MalformedURLException, ServiceException, RemoteException {
        
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Logout");
	
        api = API(IPAddress);
         
        api.logout(sessionID);
        
    }
    
    public TerminalStatus getTerminalStatus(String IPAddress, long sessionID) throws MalformedURLException, ServiceException, RemoteException {
        
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Get Terminal Status");
        
        api = API(IPAddress);
               
        TerminalStatus termStatus = api.getTerminalStatus(sessionID);
        
        return termStatus;
            
    }
    
    public List<Log> getLogs(String IPAddress, long sessionID, GetLogsCriteria criteria) throws MalformedURLException, ServiceException, RemoteException{
        
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Get Event Logs");
        
        api = API(IPAddress);
        
        List<Log> logs = new ArrayList<Log>();
        
        Log[] log = api.getLogs(sessionID, criteria);
        
        if(log!=null) logs.addAll(Arrays.asList(log));
        
        return logs;
        
    }
    
    public List<Department> getDepartments(String IPAddress, long sessionID) throws MalformedURLException, ServiceException, RemoteException{
        
        api = API(IPAddress);
            
        List<Department> Departments = new ArrayList<Department>();
            
        for(int dptID=0;dptID<=40;dptID++){
                
            Department[] ACTADept = api.getDepartments(sessionID, dptID);
            
            if (ACTADept!=null){
                Departments.addAll(Arrays.asList(ACTADept));
            }
                    
        }
            
        return Departments;
    
    }
    
    public void addDepartment(String IPAddress, long sessionID, Department ACTADpt) throws MalformedURLException, ServiceException, RemoteException{
        
        api = API(IPAddress);
            
        int dptID = ACTADpt.getDepartmentID();
            
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Add Department ID : "+dptID);
            
        api.addDepartment(sessionID, ACTADpt);
            
    }
    
    public void addDepartments(String IPAddress, long sessionID, List<Department> Departments) throws MalformedURLException, ServiceException, RemoteException{
        
        //api = API(IPAddress);
            
        for (Department ACTADpt : Departments){
            
            addDepartment(IPAddress,sessionID,ACTADpt);
                
            //int dptID = ACTADpt.getDepartmentID();
            
            //logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Add Department ID : "+dptID);

            //api.addDepartment(sessionID, ACTADpt);
                
        }
            
    }
    
    public void updateDepartment(String IPAddress, long sessionID, Department ACTADpt) throws MalformedURLException, ServiceException, RemoteException{
        
        api = API(IPAddress);
            
        int dptID = ACTADpt.getDepartmentID();
            
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Update Department ID : "+dptID);
            
        api.updateDepartment(sessionID,dptID, ACTADpt);
            
    }
    
    public void updateDepartments(String IPAddress, long sessionID, List<Department> Departments) throws MalformedURLException, ServiceException, RemoteException{
        
        //api = API(IPAddress);
            
        for (Department ACTADpt : Departments){
            
            updateDepartment(IPAddress,sessionID,ACTADpt);
                
            //int dptID = ACTADpt.getDepartmentID();
            
            //logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Update Department ID : "+dptID);

            //api.updateDepartment(sessionID,dptID, ACTADpt);
                
        }
            
    }
    
    public void deleteDepartment(String IPAddress, long sessionID, Department ACTADpt) throws MalformedURLException, ServiceException, RemoteException{
        
        api = API(IPAddress);
            
        int dptID = ACTADpt.getDepartmentID();
            
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Delete Department ID : "+dptID);
            
        api.deleteDepartment(sessionID,dptID);
            
    }
    
    public void deleteDepartments(String IPAddress, long sessionID, List<Department> Departments) throws MalformedURLException, ServiceException, RemoteException{
        
        //api = API(IPAddress);
            
        for (Department ACTADpt : Departments){
            
            deleteDepartment(IPAddress,sessionID,ACTADpt);
                
            //int dptID = ACTADpt.getDepartmentID();
            
            //logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Update Department ID : "+dptID);

            //api.updateDepartment(sessionID,dptID, ACTADpt);
                
        }
            
    }
    
    public List<Group> getGroups(String IPAddress, long sessionID) throws MalformedURLException, ServiceException, RemoteException{
        
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Get Groups");
            
        api = API(IPAddress);
            
        List<Group> groups = new ArrayList<Group>();
            
        Group[] group = api.getGroups(sessionID, null);
           
        if (group!=null){
            groups.addAll(Arrays.asList(group));
        }
             
        return groups;
    
    }
    
    public void addGroup(String IPAddress, long sessionID,Group group)throws MalformedURLException, ServiceException, RemoteException{
        
        api = API(IPAddress);
         
        int groupID = group.getGroupID();
            
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Add Group ID : "+groupID);
           
        api.addGroup(sessionID, group);
            
    }
    
    public void addGroups(String IPAddress, long sessionID,List <Group> groups)throws MalformedURLException, ServiceException, RemoteException{
        
        //api = API(IPAddress);
            
        for (Group group : groups){
            
            addGroup(IPAddress,sessionID,group);
                    
            //int groupID = group.getGroupID();
            //logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Add Group ID : "+groupID);
                    
            //api.addGroup(sessionID, group);
                    
        }
           
    }
    
    public void updateGroup(String IPAddress, long sessionID,Group group)throws MalformedURLException, ServiceException, RemoteException{
        
        api = API(IPAddress);
        
        int groupID = group.getGroupID();
            
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Update Group ID : "+groupID);
          
        api.updateGroup(sessionID, groupID, group);
            
    }
    
    public void updateGroups(String IPAddress, long sessionID,List <Group> groups)throws MalformedURLException, ServiceException, RemoteException{
        
        //api = API(IPAddress);
            
        for (Group group : groups){
                    
            updateGroup(IPAddress,sessionID,group);
            //int groupID = group.getGroupID();
            //logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Update Group ID : "+groupID);
                    
            //api.updateGroup(sessionID, groupID, group);
                    
        }
           
    }
    
    public void deleteGroup(String IPAddress, long sessionID,Group group) throws MalformedURLException, ServiceException, RemoteException{
        
        api = API(IPAddress);
        
        int groupID = group.getGroupID();
            
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Delete Group ID : "+groupID);
          
        api.deleteGroup(sessionID, groupID);
            
    }
    
    public void deleteGroups(String IPAddress, long sessionID,List <Group> groups)throws MalformedURLException, ServiceException, RemoteException{
        
        //api = API(IPAddress);
            
        for (Group group : groups){
                    
            deleteGroup(IPAddress,sessionID,group);
            //int groupID = group.getGroupID();
            //logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Update Group ID : "+groupID);
                    
            //api.updateGroup(sessionID, groupID, group);
                    
        }
           
    }
    
    public List<Trigger> getTriggers(String IPAddress, long sessionID) throws MalformedURLException, ServiceException, RemoteException{
        
        api = API(IPAddress);
            
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Get Triggers");
      
        List<Trigger> triggers = new ArrayList<Trigger>();
            
        Trigger[] trigger = api.getTriggers(sessionID, null);
            
        if (trigger!=null){
            triggers.addAll(Arrays.asList(trigger));
        }
             
        return triggers;
    
    }
    
    public void updateTrigger(String IPAddress, long sessionID,Trigger trigger)throws MalformedURLException, ServiceException, RemoteException{
        
        api = API(IPAddress);
        
        String name = trigger.getTriggerName();
            
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Update Trigger : "+name);
        
        api.updateTrigger(sessionID, trigger);
            
    }
    
    public void updateTriggers(String IPAddress, long sessionID,List<Trigger> triggers)throws MalformedURLException, ServiceException, RemoteException{
        
        //api = API(IPAddress);
            
        for (Trigger trigger:triggers){
                
            updateTrigger(IPAddress,sessionID,trigger);
            
            //String name = trigger.getTriggerName();
            
            //logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Update Trigger : "+name);
                    
            //api.updateTrigger(sessionID, trigger);
                
        }
           
    }
    
    public List<User> getUserBatch(String IPAddress,long sessionID,int offset, int limit) throws MalformedURLException, ServiceException, RemoteException {
        
        api = API(IPAddress);
            
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Get Users");
       
        List<User> users = new ArrayList<User>();
            
        User[] User = api.getUserBatch(sessionID, offset, limit);
            
        if (User!=null){
            users.addAll(Arrays.asList(User));
        }
            
        return users;
        
    }
    
    public List<User> getUsers(String IPAddress,long sessionID) throws MalformedURLException, ServiceException, RemoteException {
        
        api = API(IPAddress);
            
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Get Users");
      
        List<User> users = new ArrayList<User>();
            
        User[] User = api.getUsers(sessionID, null);
            
        if (User!=null){
            users.addAll(Arrays.asList(User));
        }
            
        return users;
        
    }
    
    public User getUser(String IPAddress,long sessionID,String userID) throws MalformedURLException, ServiceException, RemoteException {
        
        api = API(IPAddress);
        
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Get User ID "+userID);
        
        GetUsersCriteria criteria = new GetUsersCriteria();
        
        criteria.setUserID(userID);
     
        User[] User = api.getUsers(sessionID, criteria);
        
        return User[0];
        
    }
    
    public List<User> getUserCriteria(String IPAddress,long sessionID,GetUsersCriteria criteria) throws MalformedURLException, ServiceException, RemoteException {
        
        api = API(IPAddress);
        
        String ID = criteria.getPartialUserID();
        
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Get Users with Partial ID : "+ID);
      
        List<User> users = new ArrayList<User>();
            
        User[] User = api.getUsers(sessionID, criteria);
            
        if (User!=null){
            users.addAll(Arrays.asList(User));
        }
            
        return users;
        
    }
    
    public void addUser(String IPAddress,long sessionID,User user) throws MalformedURLException, ServiceException, RemoteException {
        
        api = API(IPAddress);
        
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Add User ID : "+user.getUserID());
            
        api.addUser(sessionID, user);
        
    }
    
    public void addUsers(String IPAddress,long sessionID,List<User> Users) throws MalformedURLException, ServiceException, RemoteException {
        
            //api = API(IPAddress);
            
            //User[] User = Users.toArray(new User[Users.size()]);
            
            //for (User user : User){
            //    logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Add User ID : "+user.getUserID());
            //}
            
            //api.addUsers(sessionID, User);
        
        for(User user:Users){
            
            addUser(IPAddress,sessionID,user);
        
        }
            
    }
    
    public void updateUser(String IPAddress,long sessionID, String userID, User user) throws MalformedURLException, ServiceException, RemoteException {
      
        api = API(IPAddress);
            
        //String userID = user.getUserID();
            
        api.updateUser(sessionID, userID, user);
            
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Update User ID : "+userID);
        
    }
    
    public void updateUsers(String IPAddress,long sessionID,List<User> Users) throws MalformedURLException, ServiceException, RemoteException {
        
        //api = API(IPAddress);
            
        //List<UserID> UserIDs = new ArrayList<UserID>();
                        
        for (User user : Users){
                
            String userID = user.getUserID();
                
            updateUser(IPAddress,sessionID,userID, user);
                
            //UserIDs.add(UserID);
            
        }
            
        //User[] User = Users.toArray(new User[Users.size()]);
            
        //for (User user : User){
                
        //        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Update User ID : "+user.getUserID());
                
        //}
            
        //UserID[] uids = UserIDs.toArray(new UserID[UserIDs.size()]);
            
        //api.updateUsers(sessionID, uids, User);
            
    }
    
    public void deleteUser(String IPAddress,long sessionID, String userID) throws MalformedURLException, ServiceException, RemoteException {
        
        api = API(IPAddress);
        
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Delete User ID : "+userID);
        
        api.deleteUser(sessionID, userID);
        
    }
    
    public void deleteUsers(String IPAddress,long sessionID, List<User> Users) throws MalformedURLException, ServiceException, RemoteException {
        
        for(User user:Users){
            
            String userID = user.getUserID();
            
            deleteUser(IPAddress,sessionID,userID);
        
        }
        
    }
    
    public Calendar getTerminalTime(String IPAddress, long sessionID) throws MalformedURLException, ServiceException, RemoteException {
        
        api = API(IPAddress);
            
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Get Terminal Time");
        
        return api.getTerminalDateTime(sessionID);
            
    }
    
    public void setTerminalTime(String IPAddress, long sessionID, Calendar time) throws MalformedURLException, ServiceException, RemoteException {
            
        api = API(IPAddress);
        
        DateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Set Terminal Date and Time : "+dt.format(time.getTime()));
      
        api.setTerminalDateTime(sessionID, time);
        
    }
    
    public AutoInOutOption getAutoINOUT(String IPAddress, long sessionID) throws MalformedURLException, ServiceException, RemoteException {
            
        api = API(IPAddress);
            
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Get Auto IN-OUT Option");
     
        return api.getAutoInOut(sessionID);
        
    }
    
    public void setAutoINOUT(String IPAddress, long sessionID,AutoInOutOption option) throws MalformedURLException, ServiceException, RemoteException {
        
        api = API(IPAddress);
            
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Set Auto IN-OUT Option "+option.toString());
      
        api.setAutoInOut(sessionID, option);
        
    }
    
    public String getTZ(String IPAddress, long sessionID)throws MalformedURLException, ServiceException, RemoteException {
        
        api = API(IPAddress);
            
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Get Time Zone");
        
        return  api.getTimeZone(sessionID);
            
    }
    
    public void setTZ(String IPAddress, long sessionID, String TZ) throws MalformedURLException, ServiceException, RemoteException {
        
        api = API(IPAddress);
            
        logger.info("IP Address : "+IPAddress+" | Session ID : " + sessionID+ " | Set Time Zone : "+TZ);
     
        api.setTimeZone(sessionID, TZ);
        
    }
    
    public int getUserCount(String IPAddress, long sessionID)throws MalformedURLException, ServiceException, RemoteException {
        
        api = API(IPAddress);
        int userCount = api.getUserCount(sessionID);
        return userCount;
        
    }
    
    public byte[] systemBackUp(String IPAddress, long sessionID)throws MalformedURLException, ServiceException, RemoteException {
        
        api = API(IPAddress);
        byte[] systemData = api.systemBackup(sessionID);
        return systemData;
        
    }
}
