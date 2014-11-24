/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.neptune.elisaboard.viewport.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import sol.neptune.elisaboard.common.entity.AbstractEntity;

/**
 *
 * @author murdoc
 */
@Entity
public class Viewport extends AbstractEntity implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private ViewportSlot slotA;

    public ViewportSlot getSlotA() {
        return slotA;
    }

    public void setSlotA(ViewportSlot slotA) {
        this.slotA = slotA;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private ViewportSlot slotB;

    public ViewportSlot getSlotB() {
        return slotB;
    }

    public void setSlotB(ViewportSlot slotB) {
        this.slotB = slotB;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private ViewportSlot slotC;

    public ViewportSlot getSlotC() {
        return slotC;
    }

    public void setSlotC(ViewportSlot slotC) {
        this.slotC = slotC;
    }

//    private List<ViewportSlot> slots = new ArrayList<>();
//
//    public List<ViewportSlot> getSlots() {
//        return slots;
//    }
//
//    public void setSlots(List<ViewportSlot> slots) {
//        this.slots = slots;
//    }
    @Enumerated
    private ViewportTemplate template;

    public ViewportTemplate getTemplate() {
        return template;
    }

    public void setTemplate(ViewportTemplate template) {
        this.template = template;
    }

}
