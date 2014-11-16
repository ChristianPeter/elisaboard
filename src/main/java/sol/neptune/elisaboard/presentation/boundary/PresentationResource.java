/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.neptune.elisaboard.presentation.boundary;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import sol.neptune.elisaboard.presentation.entity.PresentationDocument;
import sol.neptune.elisaboard.presentation.entity.PresentationItem;
import sol.neptune.elisaboard.presentation.entity.PresentationStream;

/**
 *
 * @author murdoc
 */
@Stateless
public class PresentationResource {

    private static final Logger LOG = Logger.getLogger(PresentationResource.class.getName());

    @PersistenceContext
    private EntityManager em;

    public List<PresentationItem> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(PresentationItem.class));
        return em.createQuery(cq).getResultList();
    }

    public List<PresentationItem> findAllByGraph() {

        final EntityGraph<PresentationItem> eg = em.createEntityGraph(PresentationItem.class);

        eg.addSubgraph("document", PresentationDocument.class).addAttributeNodes("name", "documentType");

        Properties props = new Properties();
        props.put("javax.persistence.loadgraph", eg);

        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(PresentationItem.class));
        final TypedQuery qqq = em.createQuery(cq);

        qqq.setHint("javax.persistence.loadgraph", eg);
        return qqq.getResultList();
    }

    public PresentationItem merge(PresentationItem item) {
        return em.merge(item);
    }

    public void persist(PresentationItem item) {
        em.persist(item);
    }

    public PresentationItem findById(Long id) {
        final EntityGraph<PresentationItem> eg = em.createEntityGraph(PresentationItem.class);

        eg.addSubgraph("document", PresentationDocument.class).addAttributeNodes("name", "documentType", "htmlData");
        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.loadgraph", eg);
        return em.find(PresentationItem.class, id, props);
    }

    public void delete(PresentationItem item) {
        em.remove(em.merge(item));
    }

    public Collection<? extends PresentationItem> findAllItemsForPresentationStream(PresentationStream s) {
        final EntityGraph<PresentationItem> eg = em.createEntityGraph(PresentationItem.class);

        eg.addSubgraph("document", PresentationDocument.class).addAttributeNodes("name", "documentType");

        Properties props = new Properties();
        props.put("javax.persistence.loadgraph", eg);
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        final Root root = cq.from(PresentationItem.class);
        cq.select(root);
        cq.where(cb.equal(root.get("presentationStream"), s));
        final TypedQuery qqq = em.createQuery(cq);

        qqq.setHint("javax.persistence.loadgraph", eg);
        return qqq.getResultList();
    }
}
