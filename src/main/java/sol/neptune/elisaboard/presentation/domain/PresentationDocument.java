/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.neptune.elisaboard.presentation.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

/**
 *
 * @author murdoc
 */
@Entity
public class PresentationDocument extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String name;
    
    @Enumerated
    private DocumentType documentType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.documentType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PresentationDocument other = (PresentationDocument) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.documentType != other.documentType) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PresentationDocument{" + "name=" + name + ", documentType=" + documentType + '}';
    }
    
    
}
