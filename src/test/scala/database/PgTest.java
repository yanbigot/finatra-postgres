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

    public static void load(){
        postgres.getProcess().get().importFromFile(new File("import.sql"));

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
            load();
            final Statement crtlStatement = conn.createStatement();
            crtlStatement.execute("select schema_name\n" +
                    "from information_schema.schemata");
            crtlStatement.getResultSet();
            while(crtlStatement.getResultSet().next()){
                System.out.println("[" + crtlStatement.getResultSet().getString(1));
            }
            final Statement statement = conn.createStatement();
            String query = queryFromFile("D:\\SCALA_WORKSPACE\\finatra-postgres\\src\\test\\resources\\a.sql");
            statement.execute(query);
            while(statement.getResultSet().next()){
                System.out.println("[" + statement.getResultSet() + "]");
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
