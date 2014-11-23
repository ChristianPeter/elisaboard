/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.neptune.elisaboard.presentation.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import sol.neptune.elisaboard.common.entity.AbstractEntity;

/**
 *
 * @author murdoc
 */
@Entity
public class PresentationStream extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "presentationStream", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @OrderBy("position")
    private List<PresentationItem> items = new ArrayList<>();

    public List<PresentationItem> getItems() {
        return items;
    }

    public void setItems(List<PresentationItem> items) {
        this.items = items;
    }

}
