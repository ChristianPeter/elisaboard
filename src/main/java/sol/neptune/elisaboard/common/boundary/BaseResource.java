/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.neptune.elisaboard.common.boundary;

import javax.persistence.EntityManager;
import sol.neptune.elisaboard.presentation.entity.PresentationItem;

/**
 *
 * @author murdoc
 * @param <T>
 */
public abstract class BaseResource<T> {

    public T merge(T item) {
        return getEm().merge(item);
    }

    public void persist(PresentationItem item) {
        getEm().persist(item);
    }

    public void delete(T item) {
        getEm().remove(getEm().merge(item));
    }

    public abstract EntityManager getEm();
}
