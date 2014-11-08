/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.neptune.elisaboard.presentation.boundary;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import sol.neptune.elisaboard.presentation.domain.PresentationItem;

/**
 *
 * @author murdoc
 */
@Stateless
public class PresentationResource {
    
    @PersistenceContext
    private EntityManager em;
    
    public List<PresentationItem> findAll(){
        System.out.println("EM is " + em);
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(PresentationItem.class));
        return em.createQuery(cq).getResultList();
    }
    
    public PresentationItem merge(PresentationItem item){
        return em.merge(item);
    }
    
    public void persist(PresentationItem item){
        em.persist(item);
    }
    
    public PresentationItem findById(Long id){
        return em.find(PresentationItem.class, id);
    }
}
