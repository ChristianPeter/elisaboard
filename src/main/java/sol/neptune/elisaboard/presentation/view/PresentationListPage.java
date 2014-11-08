/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.neptune.elisaboard.presentation.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sol.neptune.elisaboard.presentation.boundary.PresentationResource;
import sol.neptune.elisaboard.presentation.domain.PresentationItem;

/**
 *
 * @author murdoc
 */
@ViewScoped
@Named("presentationList")
public class PresentationListPage implements Serializable{
    
    @Inject
    private PresentationResource resource;
    
    List<PresentationItem> allItems = new ArrayList<>();
    
    public List<PresentationItem> getAllItems(){
        return allItems;
    }
    
    @PostConstruct
    public void init(){
        allItems.clear();
        allItems.addAll(resource.findAll());
    }
    
    
    
}
