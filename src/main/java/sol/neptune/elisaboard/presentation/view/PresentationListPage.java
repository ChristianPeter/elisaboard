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
import sol.neptune.elisaboard.presentation.control.PresentationControl;
import sol.neptune.elisaboard.presentation.entity.DocumentType;
import sol.neptune.elisaboard.presentation.entity.PresentationDocument;
import sol.neptune.elisaboard.presentation.entity.PresentationItem;
import sol.neptune.elisaboard.presentation.entity.PresentationStream;
import sol.neptune.elisaboard.service.ExcelProcessor;
import sol.neptune.elisaboard.viewport.boundary.ViewportsResource;
import sol.neptune.elisaboard.viewport.entity.Viewport;
import sol.neptune.elisaboard.viewport.entity.ViewportSlot;
import sol.neptune.elisaboard.viewport.entity.ViewportSlotType;

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
    
    @Inject
    private PresentationControl controller;
    
    private Viewport viewport;
    
    private ViewportSlot selectedSlot;
    
    List<PresentationItem> allItems = new ArrayList<>();
    
    private PresentationItem selectedItem;
    
    private Part file;
    
    @Produces
    @ViewScoped
    @Named("viewport")
    public Viewport getViewport() {
        return viewport;
    }
    
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
    
    public List<PresentationItem> getAllItems() {
        return allItems;
    }
    
    @PostConstruct
    public void init() {
        LOG.info("init");
        viewport = vpResource.findOrCreateMainViewport();
        // after viewport was loaded or created, select the first slot
        selectedSlot = viewport.getSlotA();
        
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
//        allItems.addAll(resource.findAllByGraph());
        if (selectedSlot != null && selectedSlot.getSlotType() == ViewportSlotType.PRESENTATIONSTREAM) {
//            final PresentationStream ps = controller.getPresentationStreamForViewport(viewport);
            allItems.addAll(resource.findAllItemsForPresentationStream(selectedSlot.getPresentationStream()));
        }
    }
    
    public String selectItem(PresentationItem item) {
        LOG.info("select item: " + item.toString());
        setSelectedItem(resource.findById(item.getId()));
        return null;
    }
    
    public String cancel() {
        LOG.info("Cancel()");
//        setSelectedItem(null);
        return null;
    }
    
    public String delete(PresentationItem item) {
        setSelectedItem(null);
        resource.delete(item);
        initAllItems();
        return null;
    }
    
    public String save() {
        final PhaseId phaseId = FacesContext.getCurrentInstance().getCurrentPhaseId();
        LOG.info("save()" + selectedItem + phaseId.getName());
        selectedItem = resource.merge(selectedItem);
        //setSelectedItem(null);
        initAllItems();
        return null;
    }
    
    public String createItem() {
        selectedItem = new PresentationItem();
        selectedItem.setDocument(new PresentationDocument());
        selectedItem.setPresentationStream(selectedSlot.getPresentationStream());
        return null;
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
    
    @Produces
    @ViewScoped
    @Named("selectedSlot")
    public ViewportSlot getSelectedSlot() {
        return selectedSlot;
    }
    
    public void setSelectedSlot(ViewportSlot selectedSlot) {
        this.selectedSlot = selectedSlot;
    }
    
    public String selectSlot(ViewportSlot slot) {
        selectedSlot = slot;
        selectedItem = null;
        initAllItems();
        return null;
    }
    
}
