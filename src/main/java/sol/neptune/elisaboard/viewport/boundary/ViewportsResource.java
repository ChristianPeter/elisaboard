/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.neptune.elisaboard.viewport.boundary;

import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import sol.neptune.elisaboard.common.boundary.BaseResource;
import sol.neptune.elisaboard.presentation.entity.PresentationDocument;
import sol.neptune.elisaboard.presentation.entity.PresentationItem;
import sol.neptune.elisaboard.presentation.entity.PresentationStream;
import sol.neptune.elisaboard.viewport.entity.Viewport;
import sol.neptune.elisaboard.viewport.entity.ViewportSlot;
import sol.neptune.elisaboard.viewport.entity.ViewportSlotType;
import sol.neptune.elisaboard.viewport.entity.ViewportTemplate;

/**
 *
 * @author murdoc
 */
@Stateless
public class ViewportsResource extends BaseResource<Viewport>{

    private static final Logger LOG = Logger.getLogger(ViewportsResource.class.getName());

    @PersistenceContext
    private EntityManager em;

    public List<Viewport> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Viewport.class));
        return em.createQuery(cq).getResultList();
    }

    public Viewport findOrCreateMainViewport() {
        LOG.info("findOrCreateMainViewport");
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Viewport.class));

        final TypedQuery q = em.createQuery(cq);
        q.setMaxResults(1);
        try {
            Viewport result = (Viewport) q.getSingleResult();
            LOG.info("found: " + result);
            return result;
        } catch (javax.persistence.NoResultException e) {
            return initViewport();
        }
    }

    public Viewport findMainViewportAndLoadAll() {

        final EntityGraph<Viewport> eg = em.createEntityGraph(Viewport.class);

        eg.addSubgraph("slotA", ViewportSlot.class).addSubgraph("presentationStream", PresentationStream.class).addSubgraph("items", PresentationItem.class).addSubgraph("document", PresentationDocument.class).addAttributeNodes("name", "documentType", "htmlData");

        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Viewport.class));

        final TypedQuery q = em.createQuery(cq);
        q.setHint("javax.persistence.loadgraph", eg);
        q.setMaxResults(1);

        return (Viewport) q.getSingleResult();
    }

    public Viewport initViewport() {
        LOG.info("initViewport");
        Viewport viewport = new Viewport();
        viewport.setTemplate(ViewportTemplate.DEFAULT);

        // create default viewportslot depending on template.... later more
        ViewportSlot slot = new ViewportSlot();
        viewport.setSlotA(slot);
        slot.setSlotType(ViewportSlotType.PRESENTATIONSTREAM);

        PresentationStream stream = new PresentationStream();
        slot.setPresentationStream(stream);

        ViewportSlot slot2 = new ViewportSlot();
        viewport.setSlotB(slot2);
        slot2.setSlotType(ViewportSlotType.WIDGETS);

        em.persist(viewport);
        return viewport;
    }

    @Override
    public EntityManager getEm() {
        return em;
    }
}
