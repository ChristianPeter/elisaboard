/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.neptune.elisaboard.presentation.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
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
public class PresentationListPage implements Serializable {

    private static final Logger LOG = Logger.getLogger(PresentationListPage.class.getName());

    @Inject
    private PresentationResource resource;

    List<PresentationItem> allItems = new ArrayList<>();

    public List<PresentationItem> getAllItems() {
        return allItems;
    }

    private PresentationItem selectedItem;

    @PostConstruct
    public void init() {
        initAllItems();
    }

    private void initAllItems() {
        allItems.clear();
        allItems.addAll(resource.findAllByGraph());
    }

    public String selectItem(PresentationItem item) {
        LOG.info("select item: " + item.toString());
        setSelectedItem(item);
        return "";
    }

    public String cancel() {
        setSelectedItem(null);
        return "";
    }

    public String delete(PresentationItem item) {
        setSelectedItem(null);
        resource.delete(item);
        initAllItems();
        return "";
    }

    public String save() {
        selectedItem = resource.merge(selectedItem);
        //setSelectedItem(null);
        initAllItems();
        return "";
    }

    public String createItem() {
        selectedItem = new PresentationItem();
        return "";
    }

    public PresentationItem getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(PresentationItem selectedItem) {
        this.selectedItem = selectedItem;
    }

}
