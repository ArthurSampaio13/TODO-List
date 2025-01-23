package br.com.todo.model;

import br.com.todo.model.Enum.PriorityEnum;
import br.com.todo.model.Enum.StatusEnum;

import java.time.LocalDate;

public class Task {
    private long id;
    private String nome;
    private String descricao;
    private LocalDate dataTermino;
    private StatusEnum status;
    private PriorityEnum priority;
    private String categoria;
    private long idUsuario;

    public Task(long id, String nome, String descricao, LocalDate dataTermino,
                StatusEnum status, PriorityEnum priority, String categoria, long idUsuario) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataTermino = dataTermino;
        this.status = status;
        this.priority = priority;
        this.categoria = categoria;
        this.idUsuario = idUsuario;
    }

    public Task(String nome, String descricao, long userId, LocalDate dataTermino,
                StatusEnum status, PriorityEnum priority, String categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.idUsuario = userId;
        this.dataTermino = dataTermino;
        this.status = status;
        this.priority = priority;
        this.categoria = categoria;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public PriorityEnum getPriority() {
        return priority;
    }

    public void setPriority(PriorityEnum priority) {
        this.priority = priority;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "TaskDTO {" +
                "\n  Categoria: '" + categoria + '\'' +
                ",\n  Prioridade: " + priority +
                ",\n  Status: " + status +
                ",\n  Data de Término: " + dataTermino +
                ",\n  Descrição: '" + descricao + '\'' +
                ",\n  Nome: '" + nome + '\'' +
                "\n}";
    }
}
