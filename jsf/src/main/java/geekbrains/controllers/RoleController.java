package geekbrains.controllers;

import geekbrains.service.RoleService;
import geekbrains.service.UserService;
import geekbrains.service.dao.RoleDAO;
import geekbrains.service.dao.UserDAO;
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
public class RoleController implements Serializable {

    private Logger logger = LoggerFactory.getLogger(RoleController.class);
    private RoleDAO role;

    @EJB
    private RoleService roleService;

    public RoleDAO getRole() {
        return role;
    }

    public void setRole(RoleDAO role) {
        this.role = role;
    }

    public String createRole(){
        this.role = new RoleDAO();
        return "/admin/role.xhtml?faces-redirect=true";
    }

    public List<RoleDAO> getAllRoles(){
        return roleService.findAll();
    }

    public String editRole(RoleDAO role) {
        this.role = role;
        return "/admin/role.xhtml?faces-redirect=true";
    }

    public void deleteRole(RoleDAO role) {
        this.role = role;
        roleService.delete(role.getId());
       // return "/admin/roles.xhtml?faces-redirect=true";
    }

    public String saveRole() {
        if(role.getId()==null){
            roleService.insert(role);
        }else{
            roleService.update(role);
        }
        return "/admin/roles.xhtml?faces-redirect=true";
    }

}
