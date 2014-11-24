/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.neptune.elisaboard.viewport.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sol.neptune.elisaboard.viewport.boundary.ViewportsResource;
import sol.neptune.elisaboard.viewport.entity.Viewport;
import sol.neptune.elisaboard.viewport.entity.ViewportTemplate;

/**
 *
 * @author murdoc
 */
@ViewScoped
@Named("viewportPage")
public class ViewportPage implements Serializable{
    private static final Logger LOG = Logger.getLogger(ViewportPage.class.getName());
    
    
    @Inject
    private ViewportsResource resource;
    
    
    @PostConstruct
    public void init(){
        LOG.info("ViewportPage.init()");
        initViewports();
    }
    
    private void initViewports(){
        allViewports.clear();
        allViewports.addAll(resource.findAll());
        LOG.info(String.valueOf(allViewports.size()));
       
    }
    
    private List<Viewport> allViewports = new ArrayList<>();

    public List<Viewport> getAllViewports() {
        return allViewports;
    }

    public void setAllViewports(List<Viewport> allViewports) {
        this.allViewports = allViewports;
    }
    
    
    public void selectLayout(Viewport viewport, String layout){
        LOG.info("setLayout " +viewport.getId() + layout);
        viewport.setTemplate(ViewportTemplate.valueOf(layout));
        resource.merge(viewport);
    }
    
    
    
    
}
