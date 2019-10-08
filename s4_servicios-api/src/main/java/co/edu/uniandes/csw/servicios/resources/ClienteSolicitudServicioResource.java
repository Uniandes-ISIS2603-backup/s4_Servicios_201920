package co.edu.uniandes.csw.servicios.resources;

import co.edu.uniandes.csw.servicios.ejb.ClienteSolicitudServicioLogic;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.mappers.WebApplicationExceptionMapper;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "clientes/{id}/servicios".
 *
 * @author estudiante
 * @version 1.0
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteSolicitudServicioResource {

    private static final Logger LOGGER = Logger.getLogger(ClienteSolicitudServicioResource.class.getName());

    @Inject
    private ClienteSolicitudServicioLogic clienteSolicitudServicioLogic;

    @Inject
    private SolicitudServicioLogic solicitudServicioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.


    /**
     * Busca y devuelve todos los servicios que existen en un cliente.
     *
     * @param clientesId El ID del cliente del cual se buscan los servicios
     * @return JSONArray {@link SolicitudServicioDTO} - Los servicios encontrados en el
     * cliente. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<SolicitudServicioDTO> getServicios(@PathParam("clientesId") Long clientesId) {
        LOGGER.log(Level.INFO, "ClienteSolicitudServicioResource getServicios: input: {0}", clientesId);
        List<SolicitudServicioDTO> lista = serviciosListEntity2DTO(authorBookLogic.getBooks(authorsId));
        LOGGER.log(Level.INFO, "AuthorBooksResource getBooks: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve el libro con el ID recibido en la URL, relativo a un
     * autor.
     *
     * @param authorsId El ID del autor del cual se busca el libro
     * @param booksId El ID del libro que se busca
     * @return {@link BookDetailDTO} - El libro encontrado en el autor.
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     * si el libro no está asociado al autor
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @GET
    @Path("{booksId: \\d+}")
    public BookDetailDTO getBook(@PathParam("authorsId") Long authorsId, @PathParam("booksId") Long booksId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "AuthorBooksResource getBook: input: authorsId {0} , booksId {1}", new Object[]{authorsId, booksId});
        if (bookLogic.getBook(booksId) == null) {
            throw new WebApplicationException("El recurso /books/" + booksId + " no existe.", 404);
        }
        BookDetailDTO detailDTO = new BookDetailDTO(authorBookLogic.getBook(authorsId, booksId));
        LOGGER.log(Level.INFO, "AuthorBooksResource getBook: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de libros de un autor con la lista que se recibe en el
     * cuerpo
     *
     * @param authorsId El ID del autor al cual se le va a asociar el libro
     * @param books JSONArray {@link BookDetailDTO} - La lista de libros que se
     * desea guardar.
     * @return JSONArray {@link BookDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @PUT
    public List<BookDetailDTO> replaceBooks(@PathParam("authorsId") Long authorsId, List<BookDetailDTO> books) {
        LOGGER.log(Level.INFO, "AuthorBooksResource replaceBooks: input: authorsId {0} , books {1}", new Object[]{authorsId, books});
        for (BookDetailDTO book : books) {
            if (bookLogic.getBook(book.getId()) == null) {
                throw new WebApplicationException("El recurso /books/" + book.getId() + " no existe.", 404);
            }
        }
        List<BookDetailDTO> lista = booksListEntity2DTO(authorBookLogic.replaceBooks(authorsId, booksListDTO2Entity(books)));
        LOGGER.log(Level.INFO, "AuthorBooksResource replaceBooks: output: {0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el libro y e autor recibidos en la URL.
     *
     * @param authorsId El ID del autor al cual se le va a desasociar el libro
     * @param booksId El ID del libro que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @DELETE
    @Path("{booksId: \\d+}")
    public void removeBook(@PathParam("authorsId") Long authorsId, @PathParam("booksId") Long booksId) {
        LOGGER.log(Level.INFO, "AuthorBooksResource deleteBook: input: authorsId {0} , booksId {1}", new Object[]{authorsId, booksId});
        if (bookLogic.getBook(booksId) == null) {
            throw new WebApplicationException("El recurso /books/" + booksId + " no existe.", 404);
        }
        authorBookLogic.removeBook(authorsId, booksId);
        LOGGER.info("AuthorBooksResource deleteBook: output: void");
    }

    /**
     * Convierte una lista de SolicitudServicioEntity a una lista de SolicitudServicioDTO.
     *
     * @param entityList Lista de SolicitudServicioEntity a convertir.
     * @return Lista de SolicitudServicioDTO convertida.
     */
    private List<SolicitudServicioDTO> serviciosListEntity2DTO(List<SolicitudServicioEntity> entityList) {
        List<SolicitudServicioDTO> list = new ArrayList<>();
        for (SolicitudServicioEntity entity : entityList) {
            list.add(new SolicitudServicioDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de BookDetailDTO a una lista de BookEntity.
     *
     * @param dtos Lista de BookDetailDTO a convertir.
     * @return Lista de BookEntity convertida.
     */
    private List<BookEntity> booksListDTO2Entity(List<BookDetailDTO> dtos) {
        List<BookEntity> list = new ArrayList<>();
        for (BookDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
