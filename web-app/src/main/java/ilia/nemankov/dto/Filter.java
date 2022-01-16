package ilia.nemankov.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "filter")
@XmlAccessorType(XmlAccessType.FIELD)
public class Filter {

    @XmlElement
    private Integer count;
    @XmlElement
    private Integer page;
    @XmlElement
    private List<String> order;
    @XmlElement
    private List<String> filter;
}
