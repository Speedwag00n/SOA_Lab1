package ilia.nemankov.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "filter")
@XmlAccessorType(XmlAccessType.FIELD)
public class Filter {

    @XmlElement
    private Integer count;
    @XmlElement
    private Integer page;
    @XmlElement
    private String order;
    @XmlElement
    private String filter;
}
