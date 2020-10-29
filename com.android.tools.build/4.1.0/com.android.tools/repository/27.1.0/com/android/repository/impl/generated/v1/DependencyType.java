
package com.android.repository.impl.generated.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.android.repository.api.Dependency;
import com.android.repository.impl.meta.TrimStringAdapter;


/**
 * DO NOT EDIT
 * This file was generated by xjc from repo-common-01.xsd. Any changes will be lost upon recompilation of the schema.
 * See the schema file for instructions on running xjc.
 *
 *
 *                 A dependency of one package on another, including a minimum revision of the
 *                 depended-upon package.
 *
 *
 * <p>Java class for dependencyType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="dependencyType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="min-revision" type="{http://schemas.android.com/repository/android/common/01}revisionType" minOccurs="0"/&gt;
 *       &lt;/all&gt;
 *       &lt;attribute name="path" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dependencyType", propOrder = {

})
@SuppressWarnings({
    "override",
    "unchecked"
})
public class DependencyType
    extends Dependency
{

    @XmlElement(name = "min-revision")
    protected com.android.repository.impl.generated.v1.RevisionType minRevision;
    @XmlAttribute(name = "path", required = true)
    @XmlJavaTypeAdapter(TrimStringAdapter.class)
    protected String path;

    /**
     * Gets the value of the minRevision property.
     *
     * @return
     *     possible object is
     *     {@link com.android.repository.impl.generated.v1.RevisionType }
     *
     */
    public com.android.repository.impl.generated.v1.RevisionType getMinRevision() {
        return minRevision;
    }

    /**
     * Sets the value of the minRevision property.
     *
     * @param value
     *     allowed object is
     *     {@link com.android.repository.impl.generated.v1.RevisionType }
     *
     */
    public void setMinRevisionInternal(com.android.repository.impl.generated.v1.RevisionType value) {
        this.minRevision = value;
    }

    /**
     * Gets the value of the path property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPath(String value) {
        this.path = value;
    }

    public void setMinRevision(com.android.repository.impl.meta.RevisionType value) {
        setMinRevisionInternal(((com.android.repository.impl.generated.v1.RevisionType) value));
    }

    public ObjectFactory createFactory() {
        return new ObjectFactory();
    }

}
