package ifpr.pgua.eic.tarefas.model.entities;


import java.time.LocalDate;
import java.util.List;

public class Tarefas {
    
    private int id;
    private String titulo;
    private String descricao;
    private LocalDate dtprazo;
    private Categorias categoria;

    public Tarefas(int id, String titulo, String descricao, LocalDate dtprazo, Categorias categoria){
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dtprazo = dtprazo;
        this.categoria = categoria;
    }

    public Tarefas(String titulo, String descricao, LocalDate dtprazo, Categorias categoria){
        this.titulo = titulo;
        this.descricao = descricao;
        this.dtprazo = dtprazo;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDtprazo() {
        return dtprazo;
    }

    public void setDtprazo(LocalDate dtprazo) {
        this.dtprazo = dtprazo;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
