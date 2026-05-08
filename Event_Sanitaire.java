public class Event_Sanitaire {
    private Type_Event_Sant type;
    private String description;

    public Event_Sanitaire(Type_Event_Sant type, String description) {
        this.type = type;
        this.description = description;
    }
    public Type_Event_Sant getType() { return type; }
    public void setType(Type_Event_Sant type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Event_Sanitaire{");
        sb.append("type=").append(type);
        sb.append(", description='").append(description).append("'");
        sb.append("}");
        return sb.toString();
    }

}
