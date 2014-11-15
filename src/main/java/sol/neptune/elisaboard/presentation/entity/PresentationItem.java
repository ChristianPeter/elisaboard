/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.neptune.elisaboard.presentation.entity;

import sol.neptune.elisaboard.common.entity.AbstractEntity;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author murdoc
 */
@Entity
public class PresentationItem extends AbstractEntity implements Serializable {
    @ManyToOne
    private PresenationStream presenationStream;

    private static final long serialVersionUID = 1L;

    @NotNull
    private int position;
    @NotNull
    private String name;
    @NotNull
    private boolean active;
    @NotNull
    private int duration = 20; // duration in secs
    
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    private PresentationDocument document;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public PresentationDocument getDocument() {
        return document;
    }

    public void setDocument(PresentationDocument document) {
        this.document = document;
    }

    
    
    @Override
    public String toString() {
        return "PresentationItem{" + "position=" + position + ", name=" + name + ", active=" + active + ", duration=" + duration + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.position;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + (this.active ? 1 : 0);
        hash = 97 * hash + this.duration;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PresentationItem other = (PresentationItem) obj;
        if (this.position != other.position) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.active != other.active) {
            return false;
        }
        if (this.duration != other.duration) {
            return false;
        }
        return true;
    }

}
