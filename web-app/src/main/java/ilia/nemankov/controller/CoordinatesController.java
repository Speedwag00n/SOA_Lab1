package ilia.nemankov.controller;

import ilia.nemankov.dto.CoordinatesDTO;
import ilia.nemankov.service.BadResponseException;
import ilia.nemankov.service.CoordinatesService;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import java.util.List;

@Stateless
@WebService(
        targetNamespace = "http://localhost:8090/ws/coordinates"
)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class CoordinatesController {

    private final CoordinatesService coordinatesService;

    public CoordinatesController()throws NamingException {
        Context context = new ContextProviderImpl().getContext();
        Object ref = context.lookup("pool/CoordinatesServiceImpl!ilia.nemankov.service.CoordinatesService");
        this.coordinatesService = (CoordinatesService) PortableRemoteObject.narrow(ref, CoordinatesService.class);
    }

    @WebMethod
    public CoordinatesDTO getCoordinate(@WebParam(name = "id") Long id) throws BadResponseException {
        CoordinatesDTO value = coordinatesService.findById(id);

        if (value != null) {
            return value;
        } else {
            throw new BadResponseException("Not found", 404);
        }
    }

    @WebMethod
    public List<CoordinatesDTO> getCoordinates() {
        return coordinatesService.findAll();
    }

    @WebMethod
    public CoordinatesDTO addCoordinates(@WebParam(name = "coordinate") CoordinatesDTO coordinate) throws BadResponseException {
        return coordinatesService.save(coordinate);
    }

    @WebMethod
    public boolean deleteCoordinates(@WebParam(name = "deleteId") Long id) throws BadResponseException {
        coordinatesService.delete(id);
        return true;
    }
}
