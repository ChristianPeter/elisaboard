/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.neptune.elisaboard.presentation.control;

import javax.ejb.Stateless;
import sol.neptune.elisaboard.presentation.entity.PresentationStream;
import sol.neptune.elisaboard.viewport.entity.Viewport;
import sol.neptune.elisaboard.viewport.entity.ViewportSlot;

/**
 *
 * @author murdoc
 */
@Stateless
public class PresentationControl {
    
    
    public PresentationStream getPresentationStreamForViewport(Viewport viewport){
        switch (viewport.getTemplate()){
           case DEFAULT : return viewport.getSlotA().getPresentationStream();
           default: return null;
        }
    }
    
}
