/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.neptune.elisaboard.user.entity;

import sol.neptune.elisaboard.common.entity.AbstractEntity;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 *
 * @author murdoc
 */
@Entity
public class ElisaUser extends AbstractEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @NotNull
    private String username;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    
    @NotNull
    private boolean active = true;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

   
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ElisaUser other = (ElisaUser) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.firstname, other.firstname)) {
            return false;
        }
        if (!Objects.equals(this.lastname, other.lastname)) {
            return false;
        }
        if (this.active != other.active) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.username);
        hash = 97 * hash + Objects.hashCode(this.firstname);
        hash = 97 * hash + Objects.hashCode(this.lastname);
        hash = 97 * hash + (this.active ? 1 : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "ElisaUser{" + "username=" + username + ", firstname=" + firstname + ", lastname=" + lastname + ", active=" + active + '}';
    }
    
    
    
    
    
}
