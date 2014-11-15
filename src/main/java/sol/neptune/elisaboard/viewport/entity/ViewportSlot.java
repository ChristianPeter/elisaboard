/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.neptune.elisaboard.viewport.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import sol.neptune.elisaboard.common.entity.AbstractEntity;
import sol.neptune.elisaboard.presentation.entity.PresenationStream;

/**
 *
 * @author murdoc
 */
@Entity
public class ViewportSlot extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private ViewportSlotType slotType = ViewportSlotType.PRESENTATIONSTREAM;

    public ViewportSlotType getSlotType() {
        return slotType;
    }

    public void setSlotType(ViewportSlotType slotType) {
        this.slotType = slotType;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private PresenationStream presentationStream;

    public PresenationStream getPresentationStream() {
        return presentationStream;
    }

    public void setPresentationStream(PresenationStream presentationStream) {
        this.presentationStream = presentationStream;
    }
    
    // TODO: widgets
            
    
}
