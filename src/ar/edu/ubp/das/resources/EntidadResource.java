package ar.edu.ubp.das.resources;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;

import ar.edu.ubp.das.beans.UsuarioBean;
import ar.edu.ubp.das.db.Dao;
import ar.edu.ubp.das.db.DaoFactory;

@Path("/entidad")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class EntidadResource {
	
	private int test1 (int nro) {
		
		return nro * 2;
	}
	
	@GET
	@Path("/{nro}")
	public Response getTest(@PathParam("nro") int nro) {
		
		int num = nro + 20;
		
		num = this.test1(num);
		
		System.out.println("Print desde test numero --------");
		
		return Response.status(Response.Status.OK)
				.entity(num).build();
		
	}
	
	
	/////////////////////////////////////////////////////
	 
	 @GET  ////////////Pruebo conexion a la base
	 @Path("/test")
	 public Response TestC() {
	 
	           Connection con = null;
	           String conUrl = "jdbc:sqlserver://localhost;databaseName=das;user=sa;password=pyxis";
	           
	 
		   try {
	            // ...
		    con = DriverManager.getConnection(conUrl);
		    System.out.println("Se conecto desde test conexion");
		    return Response.status(Response.Status.OK).entity(con.toString()).build();
	            // ... 
	   	  } catch (Exception e) {
	   		  e.printStackTrace();
	   		System.out.println("Fallo desde test conexion");
	   		  return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
	   		  }
	             finally {
	               if (con != null) try { con.close(); } catch(Exception e) {
	            	   return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
	               }
	             }
	  }
	   
	//////////////////////////////////////
	 
	 	@GET ////////////// Prueba sin DAO
		@Path("/getUsuariosT")
		public Response getUsuariosT() throws SQLException {
			
			Connection conn;
			CallableStatement stmt;
			ResultSet result;
			
			
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				conn = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=das", "sa", "pyxis");
				conn.setAutoCommit(true);
			
				LinkedList <UsuarioBean> usuarios = new LinkedList <UsuarioBean>();
				UsuarioBean usuario;
			
			try {
				stmt = conn.prepareCall("{CALL dbo.get_all_usuarios}");
				result = stmt.executeQuery();
				
				while(result.next()) {
					usuario = new UsuarioBean();
					usuario.setApellido(result.getString("apellido"));
					usuario.setCuil(result.getString("cuil"));
					usuario.setClave(result.getString("clave"));
					usuarios.add(usuario);
					
				}
				stmt.close();
				return Response.status(Response.Status.OK).entity(usuarios).build();
				
			}
			catch(SQLException e){
				throw e;
				}
			finally {
				conn.close();
			}
			
			}
			catch (ClassNotFoundException  | SQLException e) {
				return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
				
			}
			
		}
	 
	//////////////////////////////////////********************//////////////////////////////////
	
	@PUT 
	@Path("/getUsuario")
	@Consumes(MediaType.MULTIPART_FORM_DATA) 
	public Response getUsuario(@FormDataParam("cuil") String cuil,
							   @FormDataParam("clave") String clave) {
		
		UsuarioBean u = new UsuarioBean();
		
		u.setCuil(cuil); //20374028978
		u.setClave("12345678");
		
		
		 try {
	        	Dao<UsuarioBean,UsuarioBean> dao = DaoFactory.getDao("Usuarios", "ar.edu.ubp.das");
	        	
	        	List<UsuarioBean> usuarios = dao.select(u);
	        	JSONObject res = new JSONObject();
	        	System.out.println("Ya me trajo la consulta al servicio desde dao");
	        	System.out.println(usuarios.toString());
	        	
	        	LinkedList<JSONObject> list = new LinkedList<JSONObject>();
	        	
	        	for(UsuarioBean usuario: usuarios) {
	        		JSONObject json = new JSONObject();
	        		json.put("cuil", usuario.getCuil());
	        		json.put("apellido", usuario.getApellido());
	        		json.put("email", usuario.getEmail());
	        		list.add(json);
	        		
	        	}
	        	
	        	res.put("usuarios", list);
	        	
	            return Response.status(Response.Status.OK).entity(dao.select(u).get(0)).build();
	        }
	        catch(SQLException | JSONException ex) {
	        	System.out.println("Falla desde el servicio de getUsuario");
	            return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
	        }
		
	}
	
	
	
	
}
