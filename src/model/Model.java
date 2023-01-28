package model;
import java.util.Date;

public  class Model {
    protected Integer id;
    protected String autor;
    protected Date created;
    protected Date deadline;
    protected int level;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }



    public Model(Integer id, String autor, Date created, Date deadline, int level) {
        this.id = id;
        this.autor = autor;
        this.deadline = deadline;
        this.level = level;
        this.created = created;
    }

    public Model(int id, String autor,  Date deadline, int level) {
        this(id, autor, new Date(), deadline, level);
    }
    public Model( String autor,  Date deadline, int level) {
        this(null, autor, new Date(), deadline, level);
    }
    @Override
    public String toString() {
        return String.format("ID: %s | Автор: %s | Создана: %s | Срок выполнения: %s | Срочность: %s",
                this.id, this.autor, this.created, this.deadline, this.level);
    }
}
