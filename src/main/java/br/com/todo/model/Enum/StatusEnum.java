package br.com.todo.model.Enum;

public enum StatusEnum {
    TODO("TODO"),
    DOING("Doing"),
    DONE("Done");

    private final String description;

    StatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static StatusEnum getByDescription(String description) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.getDescription().equalsIgnoreCase(description)) {
                return status;
            }
        }
        System.out.println("Descrição não encontrada");
        return null;
    }
}
