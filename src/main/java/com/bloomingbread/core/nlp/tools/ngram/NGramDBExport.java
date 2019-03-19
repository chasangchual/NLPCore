package com.bloomingbread.core.nlp.tools.ngram;

import com.bloomingbread.core.nlp.tools.db.SQLiteJDBCAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NGramDBExport {
    SQLiteJDBCAdapter sqLiteJDBCAdapter = null;
    String exportDBName = "ngram.db";
    String exportTableName = "ngrams";

    public NGramDBExport() {
        sqLiteJDBCAdapter = new SQLiteJDBCAdapter();
    }

    public NGramDBExport(final String dbName) {
        sqLiteJDBCAdapter = new SQLiteJDBCAdapter();
        this.exportDBName = dbName;
    }

    public NGramDBExport(final String dbName, final String tableName) {
        sqLiteJDBCAdapter = new SQLiteJDBCAdapter();
        this.exportDBName = dbName;
        this.exportTableName = tableName;
    }

//    public static void createNewTable() {
//        // SQLite connection string
//        String url = "jdbc:sqlite:C://sqlite/db/tests.db";
//
//        // SQL statement for creating a new table
//        String sql = "CREATE TABLE IF NOT EXISTS warehouses (\n"
//                + "	id integer PRIMARY KEY,\n"
//                + "	name text NOT NULL,\n"
//                + "	capacity real\n"
//                + ");";
//
//        try (Connection conn = DriverManager.getConnection(url);
//             Statement stmt = conn.createStatement()) {
//            // create a new table
//            stmt.execute(sql);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }

    public Gram get(final int n, final String ngramStr) throws SQLException, ClassNotFoundException {
        StringBuffer sb = new StringBuffer("SELECT id, gram, str, count FROM " + exportTableName);
        sb.append("WHERE ngram = " + String.valueOf(n) + " AND ");
        sb.append("WHERE str = '" + ngramStr + "' ; ");

        try {
            Connection conn = sqLiteJDBCAdapter.getConnection(exportDBName);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString());
            while(rs.next()) {
                rs.getString("str");
                new Gram();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return null;
    }

    public void insert(final Gram ngram) throws SQLException, ClassNotFoundException {
        StringBuffer sb = new StringBuffer("INSERT INTO " + exportTableName);
        sb.append("(id, ngram, str, count)");
        sb.append(" VALUES ");
        sb.append("(");
//        sb.append(ngram.)
        sb.append(");");
    }

    public void dropTable() throws SQLException, ClassNotFoundException {
        String sql = "DROP TABLE IF EXISTS " + exportTableName;
        executeStatement(sql);
    }

    public void createTable() throws SQLException, ClassNotFoundException {
        createTable(false);
    }

    public void createTable(final boolean creatIfNotExist) throws SQLException, ClassNotFoundException {
        StringBuffer sb = new StringBuffer("CREATE TABLE ");
        sb.append(creatIfNotExist ? "IF NOT EXISTS " : "");
        sb.append(this.exportTableName);
        sb.append(" (");
        sb.append(" 	id integer PRIMARY KEY,");
        sb.append(" 	ngram integer NOT NULL,");
        sb.append(" 	str text NOT NULL,");
        sb.append(" 	count integer NOT NULL");
        sb.append(" );");

        executeStatement(sb.toString());
    }

    private void executeStatement(final String sql) throws SQLException, ClassNotFoundException {
        try {
            Connection conn = sqLiteJDBCAdapter.getConnection(exportDBName);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        NGramDBExport nGramDBExport = new NGramDBExport();
        nGramDBExport.createTable();
    }
}
