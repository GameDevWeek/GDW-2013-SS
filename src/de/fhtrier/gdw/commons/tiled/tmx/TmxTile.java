//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.03 at 06:49:48 PM CET 
//

package de.fhtrier.gdw.commons.tiled.tmx;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for tmxTile complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="tmxTile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="properties" type="{}tmxProperties"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="gid" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tmxTile", propOrder = { "properties" })
public class TmxTile {

	@XmlElement(required = true)
	protected TmxProperties properties;
	@XmlAttribute(name = "id")
	protected Integer id;
	@XmlAttribute(name = "gid")
	protected Integer gid;

	/**
	 * Gets the value of the properties property.
	 * 
	 * @return possible object is {@link TmxProperties }
	 * 
	 */
	public TmxProperties getProperties() {
		return properties;
	}

	/**
	 * Sets the value of the properties property.
	 * 
	 * @param value
	 *            allowed object is {@link TmxProperties }
	 * 
	 */
	public void setProperties(TmxProperties value) {
		this.properties = value;
	}

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setId(Integer value) {
		this.id = value;
	}

	/**
	 * Gets the value of the gid property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getGid() {
		return gid;
	}

	/**
	 * Sets the value of the gid property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setGid(Integer value) {
		this.gid = value;
	}

}
