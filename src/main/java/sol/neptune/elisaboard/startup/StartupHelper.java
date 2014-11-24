/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.neptune.elisaboard.startup;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import sol.neptune.elisaboard.presentation.boundary.PresentationResource;
import sol.neptune.elisaboard.presentation.entity.DocumentType;
import sol.neptune.elisaboard.presentation.entity.PresentationDocument;
import sol.neptune.elisaboard.presentation.entity.PresentationItem;
import sol.neptune.elisaboard.viewport.boundary.ViewportsResource;
import sol.neptune.elisaboard.viewport.entity.Viewport;

/**
 *
 * @author murdoc
 */
@Startup
@Singleton
public class StartupHelper {

    private static final Logger LOG = Logger.getLogger(StartupHelper.class.getName());

    @Inject
    private PresentationResource pr;
    
    @Inject
    private ViewportsResource vr;

    private boolean createDemoData = true;

    @PostConstruct
    public void init() {
        LOG.info("Startup ...");

        if (createDemoData) {
            LOG.info("creat demodata...");
            Viewport vp = vr.findOrCreateMainViewport();
            
            vr.initViewport();
            
            
            
            for (int c = 0; c < 5; c++) {
                PresentationItem i = new PresentationItem();
                i.setName("Demoitem " + c);
                i.setActive(true);
                i.setDuration(10);
                i.setPosition(c);

                PresentationDocument doc = new PresentationDocument();
                doc.setDocumentType(DocumentType.NOTE);
                doc.setName("Demo Document " + c);

                i.setDocument(doc);
                i.setPresentationStream(vp.getSlotA().getPresentationStream());
                vp.getSlotA().getPresentationStream().getItems().add(i);
                pr.persist(i);
            }
        }
    }
}
