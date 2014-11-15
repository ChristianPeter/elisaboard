/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.neptune.elisaboard.presentation.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.PhaseId;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.xml.parsers.ParserConfigurationException;
import sol.neptune.elisaboard.presentation.boundary.PresentationResource;
import sol.neptune.elisaboard.presentation.entity.DocumentType;
import sol.neptune.elisaboard.presentation.entity.PresentationDocument;
import sol.neptune.elisaboard.presentation.entity.PresentationItem;
import sol.neptune.elisaboard.service.ExcelProcessor;
import sol.neptune.elisaboard.viewport.boundary.ViewportsResource;
import sol.neptune.elisaboard.viewport.entity.Viewport;

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
    
    @Inject
    private ViewportsResource vpResource;

    @Inject
    private ExcelProcessor excelProcessor;

    
    private Viewport viewport;

    @Produces
    @ViewScoped
    @Named("viewport")
    public Viewport getViewport() {
        if (viewport == null){
            viewport = vpResource.findOrCreateMainViewport();
        }
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
    
    
    
    
    
    List<PresentationItem> allItems = new ArrayList<>();

    public List<PresentationItem> getAllItems() {
        return allItems;
    }

    private PresentationItem selectedItem;

    private Part file;

    @PostConstruct
    public void init() {
        initAllItems();
    }

    public List<SelectItem> getAllDocumentTypes() {
        List<SelectItem> items = new ArrayList<>();

        for (DocumentType d : DocumentType.values()) {
            SelectItem i = new SelectItem();
            i.setValue(d);
            i.setLabel(d.name());
        }
        return items;
    }

    private void initAllItems() {
        allItems.clear();
        allItems.addAll(resource.findAllByGraph());
    }

    public String selectItem(PresentationItem item) {
        LOG.info("select item: " + item.toString());
        setSelectedItem(resource.findById(item.getId()));
        return "";
    }

    public String cancel() {
//        setSelectedItem(null);
        return "";
    }

    public String delete(PresentationItem item) {
        setSelectedItem(null);
        resource.delete(item);
        initAllItems();
        return "";
    }

    public String save() {
        final PhaseId phaseId = FacesContext.getCurrentInstance().getCurrentPhaseId();
        LOG.info("save()" + selectedItem + phaseId.getName());
        selectedItem = resource.merge(selectedItem);
        //setSelectedItem(null);
        initAllItems();
        return "";
    }

    public String createItem() {
        selectedItem = new PresentationItem();
        selectedItem.setDocument(new PresentationDocument());
        return "";
    }

    public void processFile() {
        LOG.info("processFile ... " + file);
//        final Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
//        LOG.info("Locale : " + locale.getDisplayName());
        if (file != null) {
            try {
                final InputStream is = file.getInputStream();
                selectedItem.getDocument().setName(file.getSubmittedFileName());
                final String result = excelProcessor.processExcel(is);
                selectedItem.getDocument().setHtmlData(result);
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(PresentationListPage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(PresentationListPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        setFile(null);
    }

    public void uploadAjax(AjaxBehaviorEvent event) {
        LOG.info("uploadAjax ... " + file);
    }

    public PresentationItem getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(PresentationItem selectedItem) {
        this.selectedItem = selectedItem;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

}
