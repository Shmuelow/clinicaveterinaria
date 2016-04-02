/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.intecap.clinicaveterinaria.dao;

import co.edu.intecap.clinicaveterinaria.modelo.conexion.Conexion;
import co.edu.intecap.clinicaveterinaria.modelo.vo.MedicoVo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author capacitaciones
 */
public class MedicoDao extends Conexion implements GenericoDao<MedicoVo>{

    @Override
    public void insertar(MedicoVo object) {
        PreparedStatement sentencia = null;
        
        try {
        conectar();
        //crear consulta de la insersion
        String sql = "insert into medico(nombre, correo, docmento, tarjeta_profesional,estado) values(?,?,?,?,?)";
        sentencia=cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        //asignar parametros a la insersion
        sentencia.setString(1, object.getNombre());
        sentencia.setString(2, object.getCorreo());
        sentencia.setString(3, object.getDocumento());
        sentencia.setString(4, object.getTarjetaProfesional());
        sentencia.setBoolean(5, object.getEstado());
        //ejecutar la insersion
        sentencia.executeUpdate();
        //obtener llave de registro del medico
        ResultSet rs = sentencia. getGeneratedKeys();
        if (rs.next()){
            object.setIdMedico(rs.getInt(1));
            
        }
        } catch (Exception e) {
        e.printStackTrace(System.err);
        } finally{
        desconectar();
        }        
    }

    @Override
    public void editar(MedicoVo object) {
        PreparedStatement sentencia;
        try {
            conectar();
            //crear string del sql de actualizacion
            String sql = "update medico set id_medico = ?, nombre = ?, correo = ?, documento = ?, tarjeta_profesional = ?, estado = ?, id_medico = ?"; 
            sentencia = cnn.prepareStatement(sql);
            sentencia.setInt(1, object.getIdMedico());
            sentencia.setString(2, object.getNombre());
            sentencia.setString(3, object.getCorreo());
            sentencia.setString(4, object.getDocumento());
            sentencia.setString(5, object.getTarjetaProfesional());
            sentencia.setBoolean(6, object.getEstado());
            sentencia.setInt(7, object.getIdMedico());
            //ejecutar la actualizacion
            sentencia.executeUpdate();
        } catch (Exception e) {
        e.printStackTrace(System.err);
        }finally{
            desconectar();
        }
    }

    @Override
    public List<MedicoVo> consultar() {
        PreparedStatement sentencia;
        List<MedicoVo> lista = new ArrayList<>();
        try {
            conectar();
            //consulta de todos los registros de la tabla
            String sql = "select * from medico";
            sentencia = cnn.prepareStatement(sql);
            // obtener los registros de la tabla.
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()) {
                MedicoVo medico = new MedicoVo();
                medico.setIdMedico(rs.getInt("id_medico"));
                medico.setNombre(rs.getString("nombre"));
                medico.setCorreo(rs.getString("correo"));
                medico.setDocumento(rs.getString("documento"));
                medico.setTarjetaProfesional(rs.getString("tarjeta_profesional"));
                medico.setEstado (rs.getBoolean("estado"));
                lista.add(medico);
            }   
        } catch (Exception e) {
        e.printStackTrace(System.err);
        }finally{
            desconectar();
        }
        return lista;
    }

    @Override
    public MedicoVo consultar(int id) {
        PreparedStatement sentencia;
        MedicoVo obj = new MedicoVo();
        try {
            conectar();
            //consulta de un registro de la tabla según la llave primaria
            //primaria
            String sql = "select * from mascota where id_mascota = ?";
            sentencia = cnn.prepareStatement(sql);
            sentencia.setInt(1, id);
            // obtener los registros de la tabla.
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()) {
                //obtener el id de la mascota del cursor y asignarlo al atributo idMacota de un objeto de la clase mascotaVo
                obj.setIdMedico(rs.getInt("id_medico"));
                obj.setNombre(rs.getString("nombre"));
                obj.setCorreo(rs.getString("correo"));
                obj.setDocumento(rs.getString("documento"));
                obj.setTarjetaProfesional(rs.getString("tarjeta_profesional"));
                obj.setEstado (rs.getBoolean("estado"));
            }
        } catch (Exception e) {
        e.printStackTrace(System.err);
        }finally{
            desconectar();
        }
        return obj;
    }
    
}
