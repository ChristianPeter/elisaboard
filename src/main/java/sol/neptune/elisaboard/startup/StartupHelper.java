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
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import sol.neptune.elisaboard.presentation.boundary.PresentationResource;
import sol.neptune.elisaboard.presentation.domain.DocumentType;
import sol.neptune.elisaboard.presentation.domain.PresentationDocument;
import sol.neptune.elisaboard.presentation.domain.PresentationItem;

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

    @PostConstruct
    public void init() {
        LOG.info("Startup ...");
        for (int c = 0; c < 10; c++) {
            PresentationItem i = new PresentationItem();
            i.setName("Demoitem " + c);
            i.setActive(true);
            i.setDuration(10);
            i.setPosition(c);

            PresentationDocument doc = new PresentationDocument();
            doc.setDocumentType(DocumentType.NOTE);
            doc.setName("Demo Document " + c);

            i.setDocument(doc);

            pr.persist(i);
        }
    }
}
