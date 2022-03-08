package ar.edu.ubp.das.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ar.edu.ubp.das.db.Dao;
import ar.edu.ubp.das.beans.UsuarioBean;

public class MSUsuariosDao extends Dao<UsuarioBean, UsuarioBean> {

	@Override
	public UsuarioBean delete(UsuarioBean arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsuarioBean insert(UsuarioBean usuario) throws SQLException {
		// TODO Auto-generated method stub
		try {
    		this.connect();
    		
    		this.setProcedure("dbo.insertar_usuario(?,?,?,?,?)");
			this.setParameter(1, usuario.getCuil());
			this.setParameter(2, usuario.getNombre());
			this.setParameter(3, usuario.getApellido());
			this.setParameter(4, usuario.getEmail());
			this.setParameter(5, usuario.getClave());
			
			if(this.executeUpdate() <= 0) {return null;}
			
			return usuario;
    	}
    	finally {
            this.close();
    	}	
	}

	@Override
	public UsuarioBean make(ResultSet result) throws SQLException {
		UsuarioBean u = new UsuarioBean();
	    u.setCuil(result.getString("cuil"));
	    u.setNombre(result.getString("nombre"));
	    u.setApellido(result.getString("apellido"));
	    u.setEmail(result.getString("email"));
	    u.setClave(result.getString("clave"));
	    u.setEmailVerificado(result.getString("email_verificado"));
return u;
	}

	@Override
	public List<UsuarioBean> select(UsuarioBean usuario) throws SQLException {
		// TODO Auto-generated method stub
		try {
    		this.connect();
    	
    		this.setProcedure("dbo.get_usuario(?,?)");
    		this.setParameter(1, usuario.getCuil());
    		this.setParameter(2, usuario.getClave());
    		//this.setProcedure("dbo.get_all_usuarios");
    		
    		//System.out.println(this.executeQuery());
    		return this.executeQuery();
    	}
    	finally {
            this.close();
    	}
	}

	@Override
	public UsuarioBean update(UsuarioBean arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean valid(UsuarioBean arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
