/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KLibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Kara
 * These methods are not very robust, could definitely improve. 
 * Create table colNames and types really should be split into two parts
 * I should try to create a documentation thing that pops up when hovering over.
 */
public class DatabaseOperations 
{
    private static Connection myConn = null;
    private static String user = "root";
    private static String pass = "";
    Statement myStat = null;
    //private static String tableName = null;
        
    public DatabaseOperations(String databaseName) throws SQLException 
    {
        this.myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+databaseName,user,pass); 
        myStat = myConn.createStatement();
        //this.tableName = tableName;
    }
    public DatabaseOperations( Connection myConn)throws SQLException 
    {
           this.myConn = myConn;
           
        myStat = myConn.createStatement();
    }
    
    public void CreateTable(String name, String[] colNamesAndExtraStatements, String[] colTypes)
            throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS "+name+"(";
        for (int i = 0;i<colNamesAndExtraStatements.length;i++) 
        {
            query = query + colNamesAndExtraStatements[i]+" "+colTypes[i]+",";
        }
        query = query.substring(0,query.length()-1) + ");";
        System.out.println(query);
        myStat.execute(query);
    }
    
    public void InsertInToTable(String tableName,String[] colNames, String[] values)
            throws SQLException 
    {
        String query = "INSERT INTO `"+tableName+"`(";
        for (String colName : colNames)
        {
            query = query + colName + ",";
        }
        query = query.substring(0,query.length()-1) + ")VALUES(";
        for (String value : values)
        {
            query = query + "'"+value+"'"+",";
        }
        query = query.substring(0,query.length()-1) + ");";
        System.out.println(query);
        myStat.executeUpdate(query);
    }
    
    public void QueryWithBasicWhereClause(String tableName, String[] colNamesReturned,String[] where) 
            throws SQLException
    {
        String query = "SELECT ";
        for (String colName : colNamesReturned)
        {
            query = query + colName + ",";
        }
        query = query.substring(0,query.length()-1) + " FROM "+tableName+" WHERE ";
        
        for(String whereClause : where)
        {
            query = query + whereClause + ",";
        }
        query = query.substring(0,query.length()-1) + " ; ";
        
        System.out.println(query);
        myStat.executeUpdate(query);
                
    }
}
