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
    private String error;

    public void checkErrors() {
        if (this.user == null) {
            this.error = "";
        } else if (this.user.getPassword() == null) {
            this.error = "Password shouldn't be empty.";
        } else if (!this.user.getPassword().equals(this.user.getPassword_match())) {
            this.error = "Password should match.";
        }
    }

    @EJB
    private UserService userService;

    public UserDAO getUser() {
        return user;
    }

    public void setUser(UserDAO user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String createUser() {
        this.user = new UserDAO();
        this.user.setPassword("");
        this.user.setPassword_match("");
        return "/admin/user.xhtml?faces-redirect=true";
    }

    public List<UserDAO> getAllUsers() {
        return userService.findAll();
    }

    public String editUser(UserDAO user) {
        this.user = user;
        this.user.setPassword(user.getPassword());
        this.user.setPassword_match(user.getPassword());
        return "/admin/user.xhtml?faces-redirect=true";
    }

    public void deleteUser(UserDAO user) {
        this.user = user;
        userService.delete(user.getId());
        //return "/admin/users.xhtml?faces-redirect=true";
    }

    public String saveUser() {
        if (user.getPassword() == null
                || !user.getPassword().equals(user.getPassword_match())) {
            return "/admin/user.xhtml?faces-redirect=true";
        }

        if (user.getId() == null) {
            userService.insert(user);
        } else {
            userService.update(user);
        }
        return "/admin/users.xhtml?faces-redirect=true";
    }

    public String logout() {
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
        return "/index.xhtml?faces-redirect=true";
    }

}
