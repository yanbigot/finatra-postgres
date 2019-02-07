package database;

import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;

import java.io.*;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static ru.yandex.qatools.embed.postgresql.EmbeddedPostgres.cachedRuntimeConfig;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_6;

public class PgTest {

    final static EmbeddedPostgres postgres = new EmbeddedPostgres(V9_6);

    public static String startInstance(){
        // starting Postgres
        String url = null;
        try {
            postgres.start(cachedRuntimeConfig(Paths.get("embeddedPg")));
            url = postgres.start("localhost", 12400, "embeddedPG", "userName", "password");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static Connection getConnection(String url) throws SQLException {
        return DriverManager.getConnection(url);
    }

    public static void load(Connection c) throws SQLException {
//        postgres.getProcess().get().importFromFile(new File("import.sql"));
        c.createStatement().execute("CREATE  SCHEMA bdd_schema;");
        c.createStatement().execute("DROP TABLE IF EXISTS bdd_schema.MANAGER_COLLABORATOR;");
        c.createStatement().execute("DROP TABLE IF EXISTS bdd_schema.EMPLOYEES;");
        c.createStatement().execute("DROP TABLE IF EXISTS bdd_schema.FOLLOWUPS;");
        c.createStatement().execute("DROP TABLE IF EXISTS bdd_schema.TRAININGS;");
        c.createStatement().execute("DROP TABLE IF EXISTS bdd_schema.CURRICULUMS;");
        c.createStatement().execute("CREATE TABLE bdd_schema.MANAGER_COLLABORATOR(ggiManager varchar(20) NOT NULL,ggiCollaborator varchar(20) NOT NULL);");
        c.createStatement().execute("CREATE TABLE bdd_schema.EMPLOYEES(merge_key varchar(20) NOT NULL,data_map json NOT NULL);");
        c.createStatement().execute("CREATE TABLE bdd_schema.FOLLOWUPS(merge_key varchar(20) NOT NULL,data_map json NOT NULL);");
        c.createStatement().execute("CREATE TABLE bdd_schema.TRAININGS(merge_key varchar(20) NOT NULL,data_map json NOT NULL);");
        c.createStatement().execute(" CREATE TABLE bdd_schema.CURRICULUMS(merge_key varchar(20) NOT NULL,data_map json NOT NULL);");
        c.createStatement().execute("INSERT INTO bdd_schema.MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('1', '10');");
        c.createStatement().execute("INSERT INTO bdd_schema.MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('1', '11');");
        c.createStatement().execute("INSERT INTO bdd_schema.MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('1', '12');");
        c.createStatement().execute("INSERT INTO bdd_schema.MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('1', '13');");
        c.createStatement().execute("INSERT INTO bdd_schema.MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('2', '20');");
        c.createStatement().execute("INSERT INTO bdd_schema.EMPLOYEES(merge_key, data_map) VALUES('10', '{ \"ggi\": \"10\", \"name\": \"name\"}');");
        c.createStatement().execute("INSERT INTO bdd_schema.EMPLOYEES(merge_key, data_map) VALUES('11', '{ \"ggi\": \"11\", \"name\": \"name\"}');");
        c.createStatement().execute("INSERT INTO bdd_schema.EMPLOYEES(merge_key, data_map) VALUES('12', '{ \"ggi\": \"12\", \"name\": \"name\"}');");
        c.createStatement().execute("INSERT INTO bdd_schema.EMPLOYEES(merge_key, data_map) VALUES('13', '{ \"ggi\": \"13\", \"name\": \"name\"}');");
        c.createStatement().execute("INSERT INTO bdd_schema.EMPLOYEES(merge_key, data_map) VALUES('20', '{ \"ggi\": \"20\", \"name\": \"name\"}');");
        c.createStatement().execute("INSERT INTO bdd_schema.FOLLOWUPS(merge_key, data_map) VALUES('10', '{\"ggi\": \"10\", \"trainingId\": \"t_10\", \"version\": \"1\"}');");
        c.createStatement().execute("INSERT INTO bdd_schema.FOLLOWUPS(merge_key, data_map) VALUES('11', '{ \"ggi\": \"11\", \"trainingId\": \"t_11\", \"version\": \"1\"}');");
        c.createStatement().execute("INSERT INTO bdd_schema.FOLLOWUPS(merge_key, data_map) VALUES('12', '{\"ggi\": \"12\", \"trainingId\": \"t_12\", \"version\": \"1\"}');");
        c.createStatement().execute("INSERT INTO bdd_schema.FOLLOWUPS(merge_key, data_map) VALUES('13', '{\"ggi\": \"13\", \"trainingId\": \"t_13\", \"version\": \"1\"}');");
        c.createStatement().execute("INSERT INTO bdd_schema.FOLLOWUPS(merge_key, data_map) VALUES('20', '{\"ggi\": \"20\", \"trainingId\": \"t_20\", \"version\": \"1\"}');");
        c.createStatement().execute("INSERT INTO bdd_schema.TRAININGS(merge_key, data_map) VALUES('t_10', '{ \"trainingId\": \"t_10\", \"version\": \"1\", \"typeCode\": \"PRGM\"}');");
        c.createStatement().execute("INSERT INTO bdd_schema.TRAININGS(merge_key, data_map) VALUES('t_11', '{ \"trainingId\": \"t_11\", \"version\": \"1\", \"typeCode\": \"CRSE\"}');");
        c.createStatement().execute("INSERT INTO bdd_schema.TRAININGS(merge_key, data_map) VALUES('t_12', '{ \"trainingId\": \"t_12\", \"version\": \"1\", \"typeCode\": \"CRSE\"}');");
        c.createStatement().execute("INSERT INTO bdd_schema.TRAININGS(merge_key, data_map) VALUES('t_13', '{ \"trainingId\": \"t_13\", \"version\": \"1\", \"typeCode\": \"CRSE\"}');");
        c.createStatement().execute("INSERT INTO bdd_schema.TRAININGS(merge_key, data_map) VALUES('t_20', '{ \"trainingId\": \"t_20\", \"version\": \"1\", \"typeCode\": \"CRSE\"}');");
        c.createStatement().execute("--INSERT INTbdd_schema.O curriculums(merge_key, data_map) VALUES('t_10', '{ \"trainingId\": \"t_10\", \"curriculumId\": \"t_10\"}}');");
        c.createStatement().execute("INSERT INTO bdd_schema.CURRICULUMS(merge_key, data_map) VALUES('t_11', '{ \"trainingId\": \"t_11\", \"curriculumId\": \"t_10\"}');");
        c.createStatement().execute("INSERT INTO bdd_schema.CURRICULUMS(merge_key, data_map) VALUES('t_12', '{ \"trainingId\": \"t_12\", \"curriculumId\": \"t_10\"}');");
        c.createStatement().execute("INSERT INTO bdd_schema.CURRICULUMS(merge_key, data_map) VALUES('t_13', '{ \"trainingId\": \"t_13\", \"curriculumId\": \"t_10\"}');");
        c.createStatement().execute("INSERT INTO bdd_schema.CURRICULUMS(merge_key, data_map) VALUES('t_20', '{ \"trainingId\": \"t_20\", \"curriculumId\": \"t_10\"}');");
    }

    public static String queryFromFile(String path) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(path));
        String str;
        StringBuffer sb = new StringBuffer();
        while ((str = in.readLine()) != null) {
            sb.append(str + "\n ");
        }
        in.close();
        return sb.toString();
    }

    public static void stopInstance(){
        postgres.stop();
    }

    public static void main(String[] args) {
        String url = startInstance();
        try {
            final Connection conn = getConnection(url);
            conn.setAutoCommit(true);
            load(conn);

//            final Statement createSchema = conn.createStatement();
//            createSchema.execute("CREATE SCHEMA BDD_SCHEMA;" );

            final Statement crtlStatement = conn.createStatement();
            crtlStatement.execute("select schema_name from information_schema.schemata");
            crtlStatement.getResultSet();
            while(crtlStatement.getResultSet().next()){
                System.out.println("[" + crtlStatement.getResultSet().getString(1));
            }

            final Statement statement = conn.createStatement();
            String query = queryFromFile("D:\\SCALA_WORKSPACE\\finatra-postgres\\src\\test\\resources\\a.sql");
            statement.execute(query);
            while(statement.getResultSet().next()){
//                System.out.println("[" + statement.getResultSet().getString(0) + "]");
                System.out.println("---------------------------------------------------------------------------------------------");
                System.out.print("[" + statement.getResultSet().getString(1) + "]");
                System.out.print("[" + statement.getResultSet().getString(2) + "]");
                System.out.print("[" + statement.getResultSet().getString(3) + "]");
                System.out.print("[" + statement.getResultSet().getString(4) + "]");
                System.out.println("---------------------------------------------------------------------------------------------");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            stopInstance();
        }
    }
}
