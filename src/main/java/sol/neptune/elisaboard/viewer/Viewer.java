/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.neptune.elisaboard.viewer;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sol.neptune.elisaboard.viewport.boundary.ViewportsResource;
import sol.neptune.elisaboard.viewport.entity.Viewport;

/**
 *
 * @author murdoc
 */
@ViewScoped
public class Viewer implements Serializable {

    private static final Logger LOG = Logger.getLogger(Viewer.class.getName());

    @Inject
    private ViewportsResource vpResource;
    private Viewport viewport;

    @PostConstruct
    public void init() {
        LOG.info("Viewer: init()");
        viewport = vpResource.findMainViewportAndLoadAll();
    }

    @Named("viewerViewport")
    @Produces
    @SessionScoped
    public Viewport getViewport() {
        if (viewport == null){
            viewport = vpResource.findMainViewportAndLoadAll();
        }
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

}
