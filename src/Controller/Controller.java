package Controller;

import model.Model;
import model.SQLlite_Handler;
import view.BaseView;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class Controller {

    private BaseView dialog ;

    public void  addTask(String autor, String deadline, String level) throws SQLException, ParseException {

        SQLlite_Handler handler = SQLlite_Handler.getInstance();
        int level_id = handler.getLevels().get(level);

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ITALY);

        handler.addTask(new Model(autor, formatter.parse(deadline), level_id));

        this.getAllData();
    }

    public void  getAllData() throws SQLException, ParseException {

        try {
            // Создаем экземпляр по работе с БД
            SQLlite_Handler handler = SQLlite_Handler.getInstance();
            // Добавляем запись
            //dbHandler.addProduct(new Product("Музей", 200, "Развлечения"));
            // Получаем все записи и выводим их на консоль
            List<Model> tasks = handler.getAllTasks();
            String text = "";
            for (Model task : tasks) {
                String actialTaskText = task.toString();
                System.out.println(actialTaskText);
                text = text + actialTaskText + System.lineSeparator();
            }
            this.setText(text);


            HashMap<String, Integer> levels = handler.getLevels();

            levels.forEach((k, v)->{
                dialog.addComboBox(k);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setText(String text){
        this.dialog.setTextPanel(text);
    }

    public Controller() throws SQLException, ParseException {

        dialog = new BaseView(this);

        this.getAllData();


        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
