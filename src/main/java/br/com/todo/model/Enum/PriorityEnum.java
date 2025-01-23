package br.com.todo.model.Enum;

public enum PriorityEnum {
    UM("1"),
    DOIS("2"),
    TRES("3"),
    QUATRO("4"),
    CINCO("5");

    private final String description;

    PriorityEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static PriorityEnum getPriorityEnum(String description) {
        for (PriorityEnum priorityEnum : PriorityEnum.values()) {
            if (priorityEnum.getDescription().equalsIgnoreCase(description)) {
                return priorityEnum;
            }
        }
        System.out.println("PriorityEnum not found");
        return null;
    }
}
