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
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.xml.parsers.ParserConfigurationException;
import sol.neptune.elisaboard.presentation.boundary.PresentationResource;
import sol.neptune.elisaboard.presentation.domain.PresentationItem;
import sol.neptune.elisaboard.service.ExcelProcessor;

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
    private ExcelProcessor excelProcessor;
    
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
    
    public void processFile() {
        LOG.info("processFile ... " + file);
//        final Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
//        LOG.info("Locale : " + locale.getDisplayName());
        if(file != null){
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

    public void uploadAjax(AjaxBehaviorEvent event){
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
