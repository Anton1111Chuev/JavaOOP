package model;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SQLlite_Handler {
    private Connection conn;
    private final static String url = "jdbc:sqlite:db/task.db3";

    private HashMap<String, Integer> levels = new HashMap<>();

    private static SQLlite_Handler instance = null;

    public static synchronized SQLlite_Handler getInstance() throws SQLException {
        if (instance == null)
            instance = new SQLlite_Handler();
        return instance;
    }

    // Объект, в котором будет храниться соединение с БД
    private Connection connection;

    private SQLlite_Handler() throws SQLException {
        // Регистрируем драйвер, с которым будем работать
        // в нашем случае Sqlite
        //DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных
        this.connection = DriverManager.getConnection(url);
        this.setAllLevel();
    }

    public HashMap<String, Integer> getLevels(){
        return this.levels;
    }

    public List<Model> getAllTasks() {

        // Statement используется для того, чтобы выполнить sql-запрос
        try (Statement statement = this.connection.createStatement()) {
            // В данный список будем загружать наши продукты, полученные из БД
            List<Model> tasks = new ArrayList<Model>();
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ITALY);
            // В resultSet будет храниться результат нашего запроса,
            // который выполняется командой statement.executeQuery()
            ResultSet resultSet = statement.executeQuery("SELECT id, autor, created, deadline, level FROM task where not finished");
            // Проходимся по нашему resultSet и заносим данные
            while (resultSet.next()) {
                tasks.add(new Model(resultSet.getInt("id"),
                        resultSet.getString("autor"),
                        formatter.parse(resultSet.getString("created")),
                        formatter.parse(resultSet.getString("deadline")),
                        resultSet.getInt("level")));
            }
            // Возвращаем наш список
            return tasks;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void setAllLevel(){
        try (Statement statement = this.connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT level_name, id FROM level");

            HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
            while (resultSet.next()) {
                this.levels.put(resultSet.getString("level_name"),
                        resultSet.getInt("id"));
            }
            System.out.println(hashMap);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Добавление продукта в БД
    public void addTask(Model model) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ITALY);

        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO Task('autor', 'created', 'deadline', 'level') " +
                        "VALUES(?, ?, ?, ?)")) {
            statement.setObject(1, model.getAutor());
            statement.setObject(2, formatter.format(model.getCreated()));
            statement.setObject(3, formatter.format(model.getDeadline()));
            statement.setObject(4, model.getLevel());
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление продукта по id
    public void deleteTask(int id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM Products WHERE id = ?")) {
            statement.setObject(1, id);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
