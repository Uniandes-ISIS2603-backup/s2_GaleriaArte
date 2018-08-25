package co.edu.uniandes.csw.galeriaarte.resources;

/**
 * @author Anderson Barragan
 */
@Path()
@Procedures("application/json")
@Consumes("application/json")
@RequestedScoped
public class ArtistResources {

    @Inject
    ArtistLogic artistLogic;

    @POST
    public ArtistDTO createArtist(ArtistDTO artista) {
        return null;
    }
}
