package geekbrains.controllers;

import geekbrains.service.UserService;
import geekbrains.service.dao.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;

/**
 * @Author Farida Gareeva
 * Created 15.07.2020
 */
@Named
@SessionScoped
public class UserController implements Serializable {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserDAO user;

    @EJB
    private UserService userService;

    public UserDAO getUser() {
        return user;
    }

    public void setUser(UserDAO user) {
        this.user = user;
    }

    public String createUser(){
        this.user = new UserDAO();
        return "/admin/user.xhtml?faces-redirect=true";
    }

    public List<UserDAO> getAllUsers(){
        return userService.findAll();
    }

    public String editUser(UserDAO user) {
        this.user = user;
        return "/admin/user.xhtml?faces-redirect=true";
    }

    public void deleteUser(UserDAO user) {
        this.user = user;
        userService.delete(user.getId());
        //return "/admin/users.xhtml?faces-redirect=true";
    }

    public String saveUser() {
        if(user.getId()==null){
            userService.insert(user);
        }else{
            userService.update(user);
        }
        return "/admin/users.xhtml?faces-redirect=true";
    }

    public String logout() {
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
        return "/index.xhtml?faces-redirect=true";
    }

}
