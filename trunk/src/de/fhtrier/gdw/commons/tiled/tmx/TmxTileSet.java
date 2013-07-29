//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.03 at 06:49:48 PM CET 
//


package de.fhtrier.gdw.commons.tiled.tmx;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tmxTileSet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tmxTileSet">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="properties" type="{}tmxProperties"/>
 *         &lt;element name="image" type="{}tmxImage"/>
 *         &lt;element name="tile" type="{}tmxTile" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="firstgid" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="source" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="width" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="height" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="tilewidth" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="tileheight" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="spacing" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="margin" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tmxTileSet", propOrder = {
    "properties",
    "image",
    "tile"
})
public class TmxTileSet {

    @XmlElement(required = true)
    protected TmxProperties properties;
    @XmlElement(required = true)
    protected TmxImage image;
    @XmlElement(required = true)
    protected List<TmxTile> tile;
    @XmlAttribute(name = "firstgid", required = true)
    protected int firstgid;
    @XmlAttribute(name = "source")
    protected String source;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "width")
    protected Integer width;
    @XmlAttribute(name = "height")
    protected Integer height;
    @XmlAttribute(name = "tilewidth")
    protected Integer tilewidth;
    @XmlAttribute(name = "tileheight")
    protected Integer tileheight;
    @XmlAttribute(name = "spacing")
    protected Integer spacing;
    @XmlAttribute(name = "margin")
    protected Integer margin;

    /**
     * Gets the value of the properties property.
     * 
     * @return
     *     possible object is
     *     {@link TmxProperties }
     *     
     */
    public TmxProperties getProperties() {
        return properties;
    }

    /**
     * Sets the value of the properties property.
     * 
     * @param value
     *     allowed object is
     *     {@link TmxProperties }
     *     
     */
    public void setProperties(TmxProperties value) {
        this.properties = value;
    }

    /**
     * Gets the value of the image property.
     * 
     * @return
     *     possible object is
     *     {@link TmxImage }
     *     
     */
    public TmxImage getImage() {
        return image;
    }

    /**
     * Sets the value of the image property.
     * 
     * @param value
     *     allowed object is
     *     {@link TmxImage }
     *     
     */
    public void setImage(TmxImage value) {
        this.image = value;
    }

    /**
     * Gets the value of the tile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TmxTile }
     * 
     * 
     */
    public List<TmxTile> getTile() {
        if (tile == null) {
            tile = new ArrayList<TmxTile>();
        }
        return this.tile;
    }

    /**
     * Gets the value of the firstgid property.
     * 
     */
    public int getFirstgid() {
        return firstgid;
    }

    /**
     * Sets the value of the firstgid property.
     * 
     */
    public void setFirstgid(int value) {
        this.firstgid = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWidth(Integer value) {
        this.width = value;
    }

    /**
     * Gets the value of the height property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHeight(Integer value) {
        this.height = value;
    }

    /**
     * Gets the value of the tilewidth property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTilewidth() {
        return tilewidth;
    }

    /**
     * Sets the value of the tilewidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTilewidth(Integer value) {
        this.tilewidth = value;
    }

    /**
     * Gets the value of the tileheight property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTileheight() {
        return tileheight;
    }

    /**
     * Sets the value of the tileheight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTileheight(Integer value) {
        this.tileheight = value;
    }

    /**
     * Gets the value of the spacing property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSpacing() {
        return spacing;
    }

    /**
     * Sets the value of the spacing property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSpacing(Integer value) {
        this.spacing = value;
    }

    /**
     * Gets the value of the margin property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMargin() {
        return margin;
    }

    /**
     * Sets the value of the margin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMargin(Integer value) {
        this.margin = value;
    }

}