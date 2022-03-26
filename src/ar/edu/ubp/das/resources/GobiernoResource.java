package ar.edu.ubp.das.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONException;

import ar.edu.ubp.das.beans.UsuarioBean;
import ar.edu.ubp.das.db.Dao;
import ar.edu.ubp.das.db.DaoFactory;

@Path("/gobierno")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class GobiernoResource {
	public GobiernoResource() {
		super();
	}
	
	@GET
	@Path("/status")
	public Response Status() throws JSONException {
		return Response.status(Response.Status.OK).entity("Up and Running").build();
	}
	
	@PUT
	@Path("/registrar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response RegistrarUsuario(UsuarioBean usuario) throws JSONException {
		try {
			Dao<UsuarioBean, UsuarioBean> dao = DaoFactory.getDao("Usuarios", "ar.ubp.edu.das");
			if(dao.insert(usuario) != null) {
				// mandar email
				return Response.status(Response.Status.OK).entity("Usuario Registrado Correctamente").build();
			}
			return Response.status(Response.Status.BAD_REQUEST).entity("Usuario Ya Existe").build();
		} catch (SQLException e) {
			e.getStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity("Error en conexion a la base de datos").build();
		}
	}
	
	@PUT
	@Path("/login")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response LoguearUsuario(@FormDataParam("cuil") String cuil, @FormDataParam("clave") String clave) {
		try {
			Dao<UsuarioBean, UsuarioBean> dao = DaoFactory.getDao("Usuarios", "ar.edu.ubp.das");
			UsuarioBean usuario = new UsuarioBean();
			usuario.setCuil(cuil);
			usuario.setClave(clave);
			List<UsuarioBean> usuarios = dao.select(usuario);
			if (!usuarios.isEmpty()) {				
				// cambiar el resultado retornado dependiendo si está habilitado o no o si esta bloqueado
				// caso de usuario habilitado
				return Response.status(Response.Status.OK).entity(usuarios.get(0)).build();
			}
			return Response.status(Response.Status.BAD_REQUEST).entity("Usuario No Existe").build();
		} catch (SQLException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Error en conexion a la base de datos").build();
		}
	}
}
