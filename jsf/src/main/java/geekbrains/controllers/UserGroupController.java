package geekbrains.controllers;

import geekbrains.service.RoleService;
import geekbrains.service.UserGroupService;
import geekbrains.service.dao.RoleDAO;
import geekbrains.service.dao.UserGroupDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @Author Farida Gareeva
 * Created 15.07.2020
 */
@Named
@SessionScoped
public class UserGroupController implements Serializable {

    private Logger logger = LoggerFactory.getLogger(UserGroupController.class);
    private UserGroupDAO group;

    @EJB
    private UserGroupService userGroupService;

    public UserGroupDAO getGroup() {
        return group;
    }

    public void setGroup(UserGroupDAO group) {
        this.group = group;
    }

    public String createUserGroup(){
        this.group = new UserGroupDAO();
        return "/admin/userGroup.xhtml?faces-redirect=true";
    }

    public List<UserGroupDAO> getAllUserGroups(){
        return userGroupService.findAll();
    }

    public String editUserGroup(UserGroupDAO group) {
        this.group = group;
        return "/admin/userGroup.xhtml?faces-redirect=true";
    }

    public void deleteUserGroup(UserGroupDAO group) {
        this.group = group;
        userGroupService.delete(group.getId());
       // return "/admin/userGroups.xhtml?faces-redirect=true";
    }

    public String saveUserGroup() {
        if(group.getId()==null){
            userGroupService.insert(group);
        }else{
            userGroupService.update(group);
        }
        return "/admin/userGroups.xhtml?faces-redirect=true";
    }

}
